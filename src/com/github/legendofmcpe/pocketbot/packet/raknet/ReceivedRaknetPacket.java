package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.nio.ByteBuffer;

import com.github.legendofmcpe.pocketbot.packet.PacketParser;
import com.github.legendofmcpe.pocketbot.utils.Constants;

public abstract class ReceivedRaknetPacket implements Constants{
	private ByteBuffer bb;
	public abstract byte getPid();
	public void decode(ByteBuffer bb){
		this.bb = bb;
		decode();
	}
	protected abstract void decode();
	protected final ByteBuffer bb(){
		return bb;
	}
	public static void registerTypes(PacketParser parser){
		parser.registerPacketType(OPEN_CONNECTION_REPLY_1, OpenConnectionReply1.class);
	}
}
