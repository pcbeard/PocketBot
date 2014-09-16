package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.github.legendofmcpe.pocketbot.PocketBot;
import com.github.legendofmcpe.pocketbot.utils.Constants;

public class RaknetPacketParser implements Constants{
	private Map<Byte, Class<? extends ReceivedRaknetPacket>> packetTypes;
	private PocketBot bot;
	public RaknetPacketParser(int size, PocketBot bot){
		packetTypes = new HashMap<Byte, Class<? extends ReceivedRaknetPacket>>(size);
		this.bot = bot;
	}
	public boolean registerPacketType(Byte pid, Class<? extends ReceivedRaknetPacket> packet){
		if(packetTypes.containsKey(pid)){
			return false;
		}
		packetTypes.put(pid, packet);
		return true;
	}
	public ReceivedRaknetPacket parsePacket(byte[] buffer){
		ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
		Byte pid = bb.get();
		if(packetTypes.containsKey(pid)){
			try{
				ReceivedRaknetPacket pk = packetTypes.get(pid).newInstance();
				pk.decode(bot, bb);
				return pk;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		bot.getLogger().warning(bot.getLang().unknown_packet(pid, bb));
		UnknownReceivedPacket pack = new UnknownReceivedPacket();
		pack.setPid(pid);
		pack.decode(bot, bb);
		return pack;
	}
}
