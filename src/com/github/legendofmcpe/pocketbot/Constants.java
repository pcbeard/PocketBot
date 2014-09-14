package com.github.legendofmcpe.pocketbot;

public interface Constants{
	public final static byte RAKNET_VERSION = 5;
	public final static byte MCPE_VERSION = 11;
	public final static byte[] MAGIC_BYTES = {
		0x00,			(byte) 0xFF,	(byte) 0xFF,	0x00,
		(byte) 0xFE,	(byte) 0xFE,	(byte) 0xFE,	(byte) 0xFE,
		(byte) 0xFD,	(byte) 0xFD,	(byte) 0xFD,	(byte) 0xFD,
		0x12,			0x34,			0x56,			0x78
	};
	public final static byte OPEN_CONNECTION_REQUEST_1 = 0x05;
	public final static byte OPEN_CONNECTION_REPLY_1= 0x06;
	public final static byte OPEN_CONNECTION_REQUEST_2 = 0x07;
	public final static byte OPEN_CONNECTION_REPLY_2 = 0x08;
	public final static byte INCOMPATIBLE_PROTOCOL_VERSION = 0x1A;
}
