package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.nio.ByteBuffer;

import com.github.legendofmcpe.pocketbot.packet.mc.DataPacket;
import com.github.legendofmcpe.pocketbot.utils.Constants;

public class RaknetDataPacket implements Constants{
	private DataPacket pk;

	public RaknetDataPacket(DataPacket pk){
		this.pk = pk;
	}
	public byte[] getBuffer(){
		byte[] buffer = pk.getBuffer();
		ByteBuffer ret = ByteBuffer.allocate(2 + buffer.length);
		ret.putShort((short) (buffer.length << 3));
		ret.put(buffer);
		return ret.array();
	}
}
