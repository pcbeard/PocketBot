<?php

namespace LegendOfMCPE\FakeClient\network;

class UDPSocket{
	private $socket;
	public function __construct($ip, $port){
		$this->socket = socket_create(AF_INET, SOCK_DGRAM, SOL_UDP);
		socket_connect($this->socket, $ip, $port);
	}
}
