<?php

namespace LegendOfMCPE\PocketBot\network\raknet;

abstract class RaknetIDs{
	const CONNECTED_PING_OPEN_CONNECTION    = 0x01;
	const UNCONNECTED_PING_OPEN_CONENCTIONS = 0x02;
	const OPEN_CONNECTION_REQUEST_1         = 0x05;
	const OPEN_CONNECTION_REPLY_1           = 0x06;
	const OPEN_CONNECTION_REQUEST_2         = 0x07;
	const OPEN_CONNECTION_REPLY_2           = 0x08;
	const RAKNET_PROTOCOL_VERSION = 5;
	const MAGIC = "\x00\xff\xff\x00\xfe\xfe\xfe\xfe\xfd\xfd\xfd\xfd\x12\x34\x56\x78";
	public static function getPacketClassByPid($pid){
		$classes = [
//			classes that cannot be instantiated by a nullary constructor are commented out.
//			self::CONNECTED_PING_OPEN_CONNECTION    => UnconnectedPingPacket::class,
//			self::UNCONNECTED_PING_OPEN_CONENCTIONS => UnconnectedPingPacket::class,
//			self::OPEN_CONNECTION_REQUEST_1         => OpenConnectionRequest1::class
			self::OPEN_CONNECTION_REPLY_1           => OpenConnectionReply1::class,
		];
		if(!isset($classes[$pid])){
			throw new \RuntimeException("Unknown PID: $pid");
		}
		return $classes[$pid];
	}
}
