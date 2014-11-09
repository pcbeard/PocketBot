<?php

namespace LegendOfMCPE\PocketBot;

class ConsoleListener extends \Thread{
	public $running = true;
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
	}
	public function dispatchCommand($line){
		// TODO
	}
}
