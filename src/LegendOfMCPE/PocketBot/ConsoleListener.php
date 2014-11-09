<?php

namespace LegendOfMCPE\PocketBot;

class ConsoleListener extends \Thread{
	public $running = true;
	private $currentClientId = null;
	public function run(){
		$console = fopen("php://stdin", "r");
		while($this->running){
			$line = trim(fgets($console));
			if(!$line){
				continue;
			}
			$this->dispatchCommand($line);
		}
		fclose($console);
		exec("pause");
	}
	public function dispatchCommand($line){
		$args = explode(" ", $line);
		$cmd = array_shift($args);
		global $clients;
		switch(strtolower($cmd)){
			case "stop":
				foreach($clients as $client){
					$client->forceStop((($implosion = implode(" ", $args)) === "") ? "console shutdown":$implosion);
				}
				$this->running = false;
				break;
			case "connect":
				if(!isset($args[2])){
					console("[CMD] Usage: 'connect <unique name> <ip> <port>");
					break;
				}
				list($id, $ip, $port) = $args;
				if(!isset($clients[$id])){
					console("[CMD] Client '$id' already exists!");
				}
				global $config;
				$client = new Client($id, $ip, $port, $config->username);
				$clients[$id] = $client;
				$client->start();
				console("[CMD] $client started.");
			default:
				if($this->currentClientId === null){
					console("[CMD] Unknown command '$cmd''");
				}
				else{
					$clients[$this->currentClientId]->queueCommand(["cmd" => $cmd, "args" => $args]);
				}
		}
	}
	public function onClientShutdown(Client $client){
		global $clients;
		if(isset($clients[$client->getID()])){
			unset($clients[$client->getID()]);
			if($this->currentClientId === $client->getID()){
				$this->currentClientId = null;
			}
		}
		else{
			console("[WARNING] $client (ID {$client->getID()}) is not in the global \$clients list!");
		}
	}
}
