<?php

namespace LegendOfMCPE\PocketBot;

class Utils{
	public static function getRandomChar(){
		$key = mt_rand(0, 62);
		if($key < 26){
			return chr(ord("A") + $key);
		}
		if($key < 52){
			return chr(ord("a") + $key - 26);
		}
		if($key < 62){
			return chr(ord("0") + $key - 52);
		}
		return "_";
	}
	public static function getRandomString($length){
		$str = "";
		for($i = 0; $i < $length; $i++){
			$str .= self::getRandomChar();
		}
		return $str;
	}
}
