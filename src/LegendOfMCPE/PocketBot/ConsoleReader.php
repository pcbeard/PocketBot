<?php

namespace LegendOfMCPE\PocketBot;

class ConsoleReader extends \Thread{
	public $running = true;
	public function run(){
		while($this->running){
			$line = trim(fgets(STDIN));
			if(!$line){
				continue;
			}
			$this->dispatchCommand($line);
		}
	}
	public function dispatchCommand($line){
		// TODO
	}
}
