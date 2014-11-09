<?php

namespace LegendOfMCPE\PocketBot;

class Enum{
	/** @var string */
	private $array;
	/** @var bool */
	private $lock = false;
	public function __construct(){
		$this->array = serialize([]);
	}
	public function add($item){
		while($this->lock);
		$this->lock = true;
		$array = unserialize($this->array);
		$array[] = $item;
		$this->array = serialize($array);
		$this->lock = false;
	}
	public function next(){
		while($this->lock);
		$this->lock = true;
		$array = unserialize($this->array);
		$ret = array_shift($array);
		$this->array = serialize($array);
		$this->lock = false;
		return $ret;
	}
}
