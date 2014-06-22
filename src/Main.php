<?php

define("DEBUG", 20);

error_reporting(E_ALL & E_STRICT);

require_once(dirname(__FILE__)."/TextFormat.php");

set_error_handler(function($level, $message, $file = "unknown file", $line = "[unknown]"){
	console(TextFormat::RED."A level $level error occurred: ".TextFormat::BLUE.$message.TextFormat::YELLOW." at $file on line $line");
	$ex = new Exception;
	$trace = $ex->getTraceAsString();
	console(TextFormat::GREEN."Stack trace:".PHP_EOL.$trace);
});

require_once(dirname(__FILE__)."/Binary.php");

require_once(dirname(__FILE__)."/Protocol.php");

require_once(dirname(__FILE__)."/Client.php");

require_once(dirname(__FILE__)."/ConsoleLoop.php");
$console = new ConsoleLoop;
$clients = [];
$currentClient = false;
$console->start();

sleep(DEBUG);
exit(2);

function console($message, $debugLevel = 1){
	if(DEBUG < $debugLevel or $message === ""){
		return;
	}
	echo TextFormat::toANSI(TextFormat::AQUA.date("h:m:s ").TextFormat::RESET.$message.PHP_EOL);
}
