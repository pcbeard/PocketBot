<?php

define("FILE_PATH", dirname(__FILE__).DIRECTORY_SEPARATOR);
require_dir(FILE_PATH);

$mode = false;

$clients = [];

$console = new Console;

function require_dir($path){ // always suffix with directory separator
	$dir = dir($path);
	$cnt = 0;
	while(($file = $dir->read()) !== false){
		if(is_file($dir.$file) and strtolower(substr($file, -4)) === ".php"){
			require_once($dir.$file);
			$cnt++;
		}
		elseif(is_dir($dir.$file)){
			$cnt += require_dir($dir.$file.DIRECTORY_SEPARATOR);
		}
	}
	return $cnt;
}

function console($msg){
	echo date("H:i:s");
	echo $msg;
	echo PHP_EOL;
}

function evalLine($line){
	global $mode;
	if($mode === false){
		$args = explode(" ", $line);
		$cmd = array_shift($args);
	}
}
