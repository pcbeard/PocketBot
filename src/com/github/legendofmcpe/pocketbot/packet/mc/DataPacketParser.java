package com.github.legendofmcpe.pocketbot.packet.mc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.github.legendofmcpe.pocketbot.utils.Constants;

public class DataPacketParser implements Constants{
	private Map<Byte, Class<? extends DataPacket>> types;

	public DataPacketParser(){
		this(new HashMap<Byte, Class<? extends DataPacket>>());
		registerDefaults();
	}
	public DataPacketParser(Map<Byte, Class<? extends DataPacket>> list){
		this.types = list;
	}
	private void registerDefaults(){
		
	}

	public void registerDataPacketType(Byte pid, Class<? extends DataPacket> type){
		types.put(pid, type);
	}

	public void parsePacket(byte[] buffer){
		parsePacket(ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN));
	}
	public DataPacket parsePacket(ByteBuffer buffer){
		Class<? extends DataPacket> type = types.get((Byte) buffer.get());
		try{
			DataPacket instance = type.newInstance();
			instance.decode(buffer);
			return instance;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
