<?php

namespace LegendOfMCPE\PocketBot;

class ConsoleReader extends \Thread{
	public $running;
	private $lines;
	private $LOCK = false;
	public function __construct(){
		$this->lines = serialize([]);
	}
	public function run(){
		$is = fopen("php://stdin", "r");
		while($this->running){
			$line = trim(fgets($is));
			if(!$line){
				continue;
			}
			while($this->LOCK);
			$this->LOCK = true;
			$lines = unserialize($this->lines);
			$lines[] = $line;
			$this->lines = serialize($lines);
			$this->LOCK = false;
		}
	}
	public function nextLine(){
		while($this->LOCK);
		$this->LOCK = true;
		$lines = unserialize($this->lines);
		$ret = array_shift($lines);
		$this->lines = serialize($lines);
		$this->LOCK = false;
		return $ret;
	}
}
