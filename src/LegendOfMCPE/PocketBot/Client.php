<?php

namespace LegendOfMCPE\PocketBot;

use LegendOfMCPE\PocketBot\network\raknet;
use LegendOfMCPE\PocketBot\network\UDPSocket;

class Client extends \Thread{
	private $socket;
	private $connected = false;
	private $failureMessage = null;
	private $mtu;
	private $commandQueue;
	public function __construct($ip, $port, $username){
		$this->socket = new UDPSocket($ip, $port);
		$this->username = $username;
		$this->commandQueue = new Enum;
	}
	public function run(){
		if(!$this->socket->socketValid()){
			$this->fail("Could not create $this->socket");
			return;
		}
		$i = 0;
		$this->socket->setReceiveTimeout(0.5);
		/** @var raknet\OpenConnectionReply1 $pk */
		$pk = false;
		while($pk === false){
			try{
				$send = new raknet\OpenConnectionRequest1($i++);
			}
			catch(\RuntimeException $e){
				$this->fail("No response from $this->socket after 13 trials, aborting connection.");
				return;
			}
			$this->socket->send($send);
			$pk = $this->socket->receive();
		}
		$this->mtu = $pk->getMtu();

		while($this->connected){

		}
		$this->socket->close();
	}
	public function fail($msg){
		$this->failureMessage = $msg;
		console("[CRITICAL] $msg");
	}
	public function queueCommand($line){
		$this->commandQueue->add($line);
	}
}
