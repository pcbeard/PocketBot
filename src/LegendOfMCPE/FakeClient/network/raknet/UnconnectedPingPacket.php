<?php

namespace LegendOfMCPE\FakeClient\network\raknet;

class UnconnectedPingPacket extends RaknetPacket{
	private $pid;
	public function __construct($isFirst){
		$this->pid = $isFirst ? self::CONNECTED_PING_OPEN_CONNECTION:self::UNCONNECTED_PING_OPEN_CONENCTIONS;
	}
	public function getPid(){
		return $this->pid;
	}
	public function encode(){

	}
}
