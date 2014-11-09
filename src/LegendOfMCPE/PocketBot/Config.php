<?php

namespace LegendOfMCPE\PocketBot;

const CONFIG_PATH = "data/settings.yml";

class Config{
	/** @var string */
	public $username;
	/** @var string[] */
	public $autorun;
	/** @var string[][] */
	public $autojoin;
	public function __construct(){
		$this->touch() or $this->reload();
	}
	public function touch(){
		if(!is_file(CONFIG_PATH)){
			$this->username = "PocketBot_";
			$this->username .= Utils::getRandomString(16 - strlen($this->username));
			$this->autorun = serialize([]);
			$this->autojoin = serialize([
				["name" => "localhost", "ip" => "127.0.0.1", "port" => 19132]
			]);
			$this->save();
			return true;
		}
		return false;
	}
	public function reload(){
		$data = yaml_parse(file_get_contents(CONFIG_PATH));
		$this->autojoin = serialize($data["autojoin"]);
		$this->username = $data["connection details"]["username"];
		$this->autorun = $data["connection details"]["autorun"];
	}
	public function save(){
		$data = [
			"connection details" => [
				"username" => $this->username,
				"autorun" => unserialize($this->autorun),
			],
			"autojoin" => unserialize($this->autojoin),
		];
		file_put_contents(CONFIG_PATH, yaml_emit($data));
	}
}
