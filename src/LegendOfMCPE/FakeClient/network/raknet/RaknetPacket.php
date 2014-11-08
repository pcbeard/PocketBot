<?php

namespace LegendOfMCPE\FakeClient\network\raknet;
/**
 * Class RaknetPacket
 * @package LegendOfMCPE\FakeClient\network\raknet
 * Methods partly copied from RakLib by the PocketMine Team
 */
abstract class RaknetPacket implements RaknetIDs{
	protected $buffer;
	private $pointer;
	public abstract function getPid();
	public function decodeBuffer($buffer){
		$this->buffer = $buffer;
		$this->decode();
	}
	public function encode(){
		throw new \RuntimeException((new \ReflectionClass($this))->getShortName() . " cannot be encoded");
	}
	public function decode(){
		throw new \RuntimeException((new \ReflectionClass($this))->getShortName() . " cannot be decoded");
	}
	public function write($char){
		$this->buffer .= is_int($char) ? chr($char):$char;
	}
	public function readChar($char = false){
		$ord = ord($this->buffer{$this->pointer++});
		return $char ? chr($ord):$ord;
	}
	public function read($length){
		$substr = substr($this->buffer, $this->pointer, $length);
		$this->pointer += $length;
		return $substr;
	}
	public function writeShort($short){
		$this->buffer .= pack("v", $short);
	}
	public function readShort($signed = true){
		$short = unpack("v", $this->read(2))[1];
		if($signed){
			if(PHP_INT_SIZE === 8){ // 64-bit system
				return $short << 48 >> 48;
			}
			return $short << 16 >> 16;
		}
		return $short;
	}
	public function writeTriad($triad){
		$this->buffer .= substr(pack("V", $triad), 0, -1);
	}
	public function readTriad(){
		return unpack("V", $this->read(3) . "\x00")[1];
	}
	public function writeInt($int){
		$this->buffer .= pack("V", $int);
	}
	public function readInt(){
		if(PHP_INT_SIZE === 8){ // 64-bit system
			return unpack("V", $this->read(4))[1] << 32 >> 32;
		}
		return unpack("V", $this->read(4))[1];
	}
	public function writeLong($long){
		$long = strrev($long);
		if(PHP_INT_SIZE === 8){
			$this->buffer .= pack("NN", $long >> 32, $long & 0xFFFFFFFF);
		}else{
			if(bccomp($long, "0") == -1){
				$long = bcadd($long, "18446744073709551616");
			}
			$this->writeShort(bcmod(bcdiv($long, "281474976710656"), "65536"));
			$this->writeShort(bcmod(bcdiv($long, "4294967296"), "65536"));
			$this->writeShort(bcmod(bcdiv($long, "65536"), "65536"));
			$this->writeShort(bcmod($long, "65536"));
		}
	}
	public function readLong(){
		$buffer = strrev($this->read(8));
		if(PHP_INT_SIZE === 8){
			list(, $int1, $int2) = unpack("N*", $buffer);
			return ($int1 << 32) | $int2;
		}else{
			$value = "0";
			for($i = 0; $i < 8; $i += 2){
				$value = bcmul($value, "65536", 0);
				$value = bcadd($value, $this->readShort(substr($buffer, $i, 2)), 0);
			}
			if(bccomp($value, "9223372036854775807") == 1){
				$value = bcadd($value, "-18446744073709551616");
			}
			return $value;
		}
	}
}
