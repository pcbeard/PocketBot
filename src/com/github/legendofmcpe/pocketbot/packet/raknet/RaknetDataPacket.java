package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.nio.ByteBuffer;

import com.github.legendofmcpe.pocketbot.packet.mc.DataPacket;

public class RaknetDataPacket{
	private DataPacket pk;

	public RaknetDataPacket(DataPacket pk){
		this.pk = pk;
	}
	public byte[] getBuffer(){
		byte[] buffer = pk.getBuffer();
		ByteBuffer ret = ByteBuffer.allocate(2 + buffer.length);
		ret.putShort((short) (buffer.length * 8));
		ret.put(buffer);
		return ret.array();
	}
}
