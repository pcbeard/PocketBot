<?php

class Console extends Thread{
	public function __construct(){
		$this->running = true;
		$this->start();
	}
	public function run(){
		if(!extension_loaded("readline")){
			$res = fopen("php://stdin", "r");
		}
		while($this->running){
			if(!isset($res)){
				$line = trim(readline(""));
				if($line != ""){
					readline_add_history($line);
				}
			}
			else{
				$line = trim(fgets($res));
			}
			if(strlen($line) > 0){
				evalLine($line);
			}
		}
		if(isset($res)){

		}
	}
}
