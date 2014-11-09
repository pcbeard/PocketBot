<?php

namespace LegendOfMCPE\PocketBot\network\raknet;

class OpenConnectRequest2 extends RaknetPacket{
	public function getPid(){
		return self::OPEN_CONNECTION_REQUEST_2;
	}
	public function encode(){
		
	}
}
