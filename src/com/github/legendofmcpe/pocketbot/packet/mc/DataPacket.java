package com.github.legendofmcpe.pocketbot.packet.mc;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.github.legendofmcpe.pocketbot.PocketBot;
import com.github.legendofmcpe.pocketbot.packet.raknet.RaknetDataPacket;
import com.github.legendofmcpe.pocketbot.utils.Constants;

public abstract class DataPacket implements Constants{
	public void send(PocketBot bot){
		bot.getNetworkMgr().addToQueue(new RaknetDataPacket(this));
	}
	public void sendDirect(PocketBot bot){
		bot.getNetworkMgr().sendDataPacketDirect(this);
	}
	public byte[] getBuffer() throws UnsupportedOperationException{
		ByteBuffer bb = ByteBuffer.allocate(getSize()).order(ByteOrder.LITTLE_ENDIAN);
		encode(bb);
		return bb.array();
	}
	protected abstract int getSize();
	protected void encode(ByteBuffer bb){
		throw new UnsupportedOperationException(getClass().getSimpleName()
				.concat(" cannot be encoded."));
	}

	public void decode(ByteBuffer bb){
		throw new UnsupportedOperationException(getClass().getSimpleName()
				.concat(" cannot be decoded."));
	}

	protected void putString(ByteBuffer bb, String string){
		bb.putShort((short) string.length());
		try{
			bb.put(string.getBytes("UTF-8"));
		}
		catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
}
