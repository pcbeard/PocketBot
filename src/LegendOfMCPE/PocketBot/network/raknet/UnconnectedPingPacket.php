<?php

namespace LegendOfMCPE\PocketBot\network\raknet;

class UnconnectedPingPacket extends RaknetPacket{
	private $pid;
	private $millis;
	public function __construct($isFirst, $millis){
		$this->pid = $isFirst ? self::CONNECTED_PING_OPEN_CONNECTION:self::UNCONNECTED_PING_OPEN_CONENCTIONS;
		$this->millis = $millis;
	}
	public function getPid(){
		return $this->pid;
	}
	public function encode(){
		$this->writeChar($this->getPid());
		$this->writeLong($this->millis);
		$this->write(self::MAGIC);
	}
}
