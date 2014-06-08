<?php

define("FILE_PATH", dirname(__FILE__).DIRECTORY_SEPARATOR);
$cnt = require_dir(FILE_PATH) + 1;
console("Loaded $cnt PHP files");

readLine();

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
	echo $msg;
	echo PHP_EOL;
}
