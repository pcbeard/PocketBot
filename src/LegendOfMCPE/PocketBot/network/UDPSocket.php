<?php

namespace LegendOfMCPE\PocketBot\network;

use LegendOfMCPE\PocketBot\network\raknet\RaknetIDs;
use LegendOfMCPE\PocketBot\network\raknet\RaknetPacket;

class UDPSocket{
	/** @var resource */
	private $socket;

	/** @var string */
	private $ip;
	/** @var int */
	private $port;
	public function __construct($ip, $port){
		$this->socket = socket_create(AF_INET, SOCK_DGRAM, SOL_UDP);
		$this->ip = $ip;
		$this->port = $port;
	}
	public function socketValid(){
		return is_resource($this->socket);
	}
	public function send(RaknetPacket $packet){
		$buffer = $packet->encode();
		socket_sendto($this->socket, $buffer, strlen($buffer), 0, $this->ip, $this->port);
	}
	public function receive(){
		if(socket_recv($this->socket, $buffer, 2048, 0) === false){
			return false;
		}
		$class = RaknetIDs::getPacketClassByPid(ord(substr($buffer, 0, 1)));
		/** @var RaknetPacket $packet */
		$packet = new $class;
		$packet->decodeBuffer(substr($buffer, 1));
		return $packet;
	}
	public function setReceiveTimeout($timeout = 0.0){
		$timeout = (double) $timeout;
		socket_set_option($this->socket, SOL_SOCKET, SO_RCVTIMEO, [
			"sec" => (int) floor($timeout), "usec" => (int) (($timeout * 1000000) % 1000000)
		]);
	}
	public function close(){
		socket_close($this->socket);
	}
	public function __toString(){
		return "connection to server at $this->ip:$this->port";
	}
}
