package com.github.legendofmcpe.pocketbot.packet.mc;

import java.nio.ByteBuffer;

public abstract class DataPacket{
	public abstract byte[] getBuffer();
	public abstract void decode(ByteBuffer bb);
}
