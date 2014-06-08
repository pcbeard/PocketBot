<?php

define("PRECONNECT_0", 0x01);
define("PRECONNECT_1", 0x02);
define("OPEN_CONNECTION_REQUEST", 0x05);
define("OPEN_CONNECTION_REPLY", 0x06);
define("OPEN_CONNECTION_REQUEST_2", 0x07);
define("OPEN_CONENCITON_REPLY_2", 0x08);
define("INCOMPATIBLE_PROTOCOL", 0x1A);
define("SERVER_INFO", 0x1D);
define("MAGIC", "\x00\xff\xff\x00\xfe\xfe\xfe\xfe\xfd\xfd\xfd\xfd\x12\x34\x56\x78");

class Protocol{
	/**
	 * @param int $num
	 * @return string
	 */
	public static function writeBinary($num){
		$out = "";
		while($num > 0){
			$end = $num & 0xFF;
			$out = chr($end) . $out;
			$num >>= 8;
		}
		return $out;
	}
	/**
	 * @param string $str
	 * @return int
	 */
	public static function readBinary($str){
		$out = 0;
		for($i = 0; $i < strlen($str); $i++){
			$out <<= 8;
			$out |= ord($str{$i});
		}
		return $out;
	}
}
