<?php

namespace LegendOfMCPE\PocketBot;

use LegendOfMCPE\PocketBot\network\UDPSocket;

class Client extends \Thread{
	private $socket;
	public function __construct($ip, $port){
		$this->socket = new UDPSocket($ip, $port);
	}
}
