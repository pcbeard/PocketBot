<?php

abstract class Protocol{
	// raklib
	const REQ_ADS = "\x01";
	const ADS = "\x1C";
	const OPEN_CON_REQ_1 = "\x05";
	const OPEN_CON_REP_1 = "\x06";
	const OPEN_CON_REQ_2 = "\x07";
	const OPEN_CON_REP_2 = "\x08";
	const WRONG_PROTOCOL = "\x1A";
	// data packets
	// sent
	const LOGIN = "\x82";
	// received
	const LOGIN_STATUS = "\x83";
	// mutual
	
	// constants
	const SECURITY_COOKIE = "\x04\x3f\x57\xfe\xfd";
	const STRUCTURE = "\x05";
	const PROTOCOL = "\x15";
	const MAGIC = "\x00\xff\xff\x00\xfe\xfe\xfe\xfe\xfd\xfd\xfd\xfd\x12\x34\x56\x78";
	const CLIENT_ID = 0x13b07b07; // leet for Im Bot Bot
}
