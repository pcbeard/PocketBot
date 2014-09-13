package com.github.legendofmcpe.pocketbot.packet;

public abstract class Packet{
	public final static byte RAKNET_VERSION = 5;
	public final static byte MCPE_VERSION = 11;

	public final static byte OPEN_CONNECTION_REQUEST_1 = 0x05;
	public final static byte OPEN_CONNECTION_REPLY_1= 0x06;
	public final static byte OPEN_CONNECTION_REQUEST_2 = 0x07;
	public final static byte OPEN_CONNECTION_REPLY_2 = 0x08;
	public abstract byte getPid();
	public abstract void decode();
	public abstract void encode();
	public abstract byte[] getBuffer();
	public abstract void setBuffer(byte[] buffer);
}
