<?php

namespace LegendOfMCPE\PocketBot\network\raknet;

class OpenConnectionRequest1 extends RaknetPacket{
	private $mtu;
	public function __construct($order){
		$mtus = [1447, 1447, 1447, 1447, 1155, 1155, 1155, 1155, 531, 531, 531, 531, 531];
		if(!isset($mtus)){
			throw new \RuntimeException;
		}
		$this->mtu = $mtus[$order];
	}
	public function getPid(){
		return self::OPEN_CONNECTION_REQUEST_1;
	}
	public function encode(){
		$this->writeChar(self::OPEN_CONNECTION_REQUEST_1);
		$this->writeLong(self::MAGIC);
		$this->writeChar(self::RAKNET_PROTOCOL_VERSION);
		$this->write(str_repeat("\x00", $this->mtu));
	}
}
