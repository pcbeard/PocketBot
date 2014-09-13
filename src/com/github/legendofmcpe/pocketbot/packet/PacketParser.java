package com.github.legendofmcpe.pocketbot.packet;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class PacketParser{
	private Map<Byte, Class<? extends Packet>> packetTypes;
	private PocketBot bot;
	public PacketParser(int size, PocketBot bot){
		packetTypes = new HashMap<Byte, Class<? extends Packet>>();
		this.bot = bot;
	}
	public boolean registerPacketType(Byte pid, Class<? extends Packet> packet){
		if(packetTypes.containsKey(pid)){
			return false;
		}
		packetTypes.put(pid, packet);
		return true;
	}
	public Packet parsePacket(byte[] buffer){
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		Byte pid = bb.get();
		if(packetTypes.containsKey(pid)){
			try{
				Packet pk = packetTypes.get(pid).newInstance();
				pk.setBuffer(buffer);
				pk.decode();
				return pk;
			}
			catch(Exception e){}
		}
		bot.getLogger().warning(bot.getLang().unknown_packet(pid, bb));
		return null;
	}
}
