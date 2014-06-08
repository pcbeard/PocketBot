<?php

class FakeClient extends Thread{
	const SOCK_SETUP = 0;
	const CON_CREATE = 1;
	const RUNNING = 2;
	const RUNTIME_ERR = 3;
	const DISCONNECTED = 4;
	const LEAVING = 5;
	const LEFT = 6;
	private $mode;
	private $id;
	private $ip;
	private $port;
	public function __construct($ip, $port, $id){
		$this->ip = $ip;
		$this->port = $port;
		$this->id = $id;
		$this->mode = self::SOCK_SETUP;
	}
	public function run(){
		$this->onRun();
		switch($this->mode){
			case self::SOCK_SETUP:
				console("<Client ".$this->id."> [ERROR] Unable to setup connection.");
				break;
			case self::CON_CREATE:
				console("<Client ".$this->id."> [ERROR] Unable to connect to server.");
				break;

		}
	}
	private function onRun(){
		$sock = socket_create(AF_INET, SOCK_DGRAM, SOL_UDP);
		if(!is_resource($sock)) return;
		if(socket_connect($sock, $this->ip, $this->port) === false) return;
		// the ten-step conversation
		$this->mode = self::CON_CREATE;
		$time = microtime(true);
		socket_write($sock, PRECONNECT_0."\x00".MAGIC);
		socket_set_timeout($sock, 5);
		while(($data = socket_read($sock, 64)) === false and microtime(true) - $time < 10){ // ten seconds limit
			$ping = microtime(true) - $time;
			$ping *= 1000;
			$ping &= 0xFFFFFFFFFFFFFFFF;
			socket_write($sock, PRECONNECT_1.Protocol::writeBinary($ping).MAGIC);
		}
		if($data === false){
			return;
		}
		console("Client {$this->id} connecting to server of name ".substr($data, 44));
		while(($data = socket_read($sock, 32)) === false){

		}
	}
	/**
	 * @return string
	 */
	public function getID(){
		return $this->id;
	}
	/**
	 * @return int
	 */
	public function getPort(){
		return $this->port;
	}
	/**
	 * @return int
	 */
	public function getMode(){
		return $this->mode;
	}
	/**
	 * @return mixed
	 */
	public function getIP(){
		return $this->ip;
	}
}
