<?php

namespace {
	/** @var string[] $AUTOLOAD_ROOTS */
	/** @noinspection PhpUnusedLocalVariableInspection */
	$AUTOLOAD_ROOTS = [];
	/** @var \LegendOfMCPE\PocketBot\Client $connections */
	/** @noinspection PhpUnusedLocalVariableInspection */
	$connections = [];
	function add_autoload_root_path($path){
		global $AUTOLOAD_ROOTS;
		if(!is_dir($path)){
			throw new \InvalidArgumentException("Parameter 1 pased to " . __FUNCTION__ . " is not a directory");
		}
		$AUTOLOAD_ROOTS[] = rtrim(realpath($path), "\\/") . "/";
	}
	add_autoload_root_path("src");
	function autoload_class($class){
		global $AUTOLAOD_ROOTS;
		foreach($AUTOLAOD_ROOTS as $root){
			$path = $root . str_replace("\\", "/", $class) . ".php";
			if(is_file($path)){
				require_once $path;
				return;
			}
		}
	}
	spl_autoload_register("autoload_class", true);
}

namespace LegendOfMCPE\PocketBot{
	$console = new ConsoleReader;
	$console->start();

	function console($message){
		echo date("[H:i:s] ") . $message . PHP_EOL;
	}
}
