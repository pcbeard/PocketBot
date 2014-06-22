<?php

class Client extends Thread{
	const STANDBY = 0;
	const CONNECTING = 1;
	const PLAYING = 2;
	const STOPPED = 3;
	public $ip = "127.0.0.1", $port = 19132, $ign = "BotBot";
	public $state = self::STANDBY;
	public $socket;
	public function __construct($name){
		$this->name = $name;
		$this->start();
	}
	public function run(){
		while($this->state !== self::STOPPED){
			switch($this->state){
				case self::CONNECTING:
					$this->socket = socket_create(AF_INET, SOCK_DGRAM, SOL_UCP);
					if(!is_resource($this->socket) or !socket_bind($this->socket, $this->ip, $this->port)){
						$this->log("[ERROR] Unable to create connection: ".socket_strerror(socket_last_error($this->socket)));
					}
					socket_set_option($this->socket, SOL_SOCKET, SO_RESUEADDR, 0);
					socket_set_option($this->socket, SOL_SOCKET, SO_SNDBUF, 1024 * 1024);
					socket_set_option($this->socket, SOL_SOCKET, SO_RCVBUF, 1024 * 1024 * 2);
					socket_set_nonblock($this->socket);
					$this->sendInitPackets($port);
					$this->log("Connection created. Logging in.");
					$this->login();
					$this->state++;
					break;
			}
		}
	}
	public function onCommand($cmd, $args){
		switch($cmd){
			case "preset":
				if(!isset($args[1])){
					return "Usage: /preset <key> <value ...>";
				}
				if($this->state !== self::STANDBY){
					return "Client is running; cannot modify settings.";
				}
				switch($key = array_shift($args)){
					case "ip":
						$this->ip = $args[0];
						return "Address set to {$this->ip}:{$this->port}";
					case "port":
						if(!is_numeric($args[0])){
							return "Port must be numeric!";
						}
						$this->port = (int) $args[0];
						return "Address set to {$this->ip}:{$this->port}";
					case "ign":
						$this->ign = $args[0];
						return "IGN set to $args[0]";
					default:
						return "Unknown value: $key";
				}
				break;
			case "start":
				$this->state = self::CONNECTING;
				break;
			default:
				return "Command /$cmd is unknown!";
		}
	}
	public function log($message, $debug = 1){
		console(TextFormat::LIGHT_PURPLE."[$name] $message", $debug);
	}
	public function quit($reason = "No reason given"){
		$this->end();
	}
	public function end($reason = "No reason"){
		$this->state = self::STOPPED;
		exit(0);
	}
	protected function sendInitPackets(&$myPort){
		$startMilli = microtime(true) * 1000;
		$buffer = Protocol::REQ_ADS.Binary::writeLong(microtime(true) * 1000 - $startMilli).Protocol::MAGIC;
		socket_send($this->socket, $buffer, strlen($buffer), 0);
		$buffer = "";
		socket_recv($this->socket, $buffer, 84, 0);
		if($buffer and substr($buffer, 0, 1) === "\x1C"){
			$this->log("Logging in server ".substr($buffer, 35));
		}
		$order = 0;
		$this->log("Connecting to server...");
		do{
			$order++;
			$buffer = Protocol::OPEN_CON_REQ_1.Protocol::MAGIC.Protocol::STRUCTURE.$this->getNullPayload($order);
			socket_send($this->socket, $buffer, strlen($buffer), 0);
			usleep(5000);
		}while(($buffer = $this->receive(4096, Protocol::WRONG_PROTOCOL.Protocol::OPEN_CON_REP_1)) === false and $order < 13)
		if(substr($buffer, 0, 1) === Protocol::WRONG_PROTOCOL){
			$this->log("[ERROR] Incorrect RakLib version: ".ord(Protocol::STRUCTURE)." (client version) <> ".ord(substr($buffer, 1, 1))." (server version)!");
			$this->end("Wrong RakLib version");
			return false;
		}
		$mtu = Binary::readShort(substr($buffer, 26));
		$buffer = Protocol::OPEN_CON_REQ_2.Protocol::MAGIC.Protocol::SECURITY_PROTOCOL.Binary::writeShort($mtu).Binary::writeLong(Protocol::CLIENT_ID);
		do{
			socket_send($this->socket, $buffer, strlen($buffer), 0);
		}while(($buffer = $this->receive(30, Protocol::OPEN_CON_REP_2)) === false)
		$myPort = Binary::readShort(substr($buffer, 25, 2));
		return true;
	}
	protected function login(){
		$buffer = Protocol::LOGIN.chr(strlen($this->ign)).$this->ign.Protocol::PROTOCOL.Protocol::PROTOCOL.Binary::writeInt(Protocol::CLIENT_ID).$realmsData; // what are these... (I notice that PROTOCOL is int not byte... #1 and #2?)
	}
	public function receive($length, $filter){
		$buffer = "";
		socket_recv($this->socket, $buffer, $length, 0);
		if($buffer and (strpos($filter, substr($buffer, 0, 1)))){
			return $buffer;
		}
		return false;
	}
	protected function getNullPayload($order){
		if(1 <= $order and $order <= 4){
			return str_repeat("\x00", 1447);
		}
		if(
	}
}
