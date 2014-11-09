<?php

namespace LegendOfMCPE\PocketBot\network\raknet;

class OpenConnectionReply1 extends RaknetPacket{
	/** @var string */
	private $magic;
	/** @var int */
	private $serverId;
	/** @var int */
	private $security;
	/** @var int */
	private $mtu;
	public function getPid(){
		return self::OPEN_CONNECTION_REPLY_1;
	}
	public function decode(){
		$this->magic = $this->read(strlen(self::MAGIC));
		$this->serverId = $this->readLong();
		$this->security = $this->readChar();
		$this->mtu = $this->readShort(false);
	}
	public function getMagic(){
		return $this->magic;
	}
	public function getServerId(){
		return $this->serverId;
	}
	public function getSecurity(){
		return $this->security;
	}
	public function getMtu(){
		return $this->mtu;
	}
}
