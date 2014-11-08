<?php

class ConsoleLoop{
	public $running = true;
	public function start(){
		console("Starting console loop", 20);
		console("ReadLine extension is ".(extension_loaded("readline") ? "loaded":"not loaded").".", 20);
		$stream = fopen("php://stdin", "r");
		while($this->running){
			$line = trim(fgets($stream));
			if($line !== ""){
				console($this->handleCommand($line), 0);
			}
		}
		console("Stopped...");
		sleep(1);
		exit(0);
	}
	private function handleCommand($line){
		global $clients, $currentClient;
		$output = "";
		$args = preg_split("#[ ]{1,}#", $line);
		console("Command evaluated: ".var_export($args, true), 20);
		switch(trim(strtolower($cmd = array_shift($args)))){
			case "stop":
				foreach($clients as $client){
					$client->end("Stopped by terminal");
				}
				$this->running = false;
				break;
			case "addclient":
				if(!isset($args[0])){
					return "Usage: /addclient <client name>";
				}
				$name = array_shift($args);
				$clients[$name] = new Client($name);
				break;
			case "switchclient":
				if(!isset($args[0])){
					return "Usage: /switchclient <client name>";
				}
				$name = array_shift($args);
				if(isset($clients[$name])){
					$currentClient = $name;
					return "Switched to client $name";
				}
				else{
					return "Client $name not found!";
				}
				break;
			case "gen-help":
				return "/gen-help, /addclient, /stop, /switchclient";
				break;
			default:
				if($currentClient !== false){
					$msg = $clients[$currentClient]->onCommand($cmd, $args);
					$clients[$currentClient]->log($msg);
					return "";
				}
				else{
					return "Client not selected!";
				}
				break;
		}
		return $output;
	}
}
