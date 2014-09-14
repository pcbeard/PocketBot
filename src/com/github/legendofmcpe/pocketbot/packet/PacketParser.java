package com.github.legendofmcpe.pocketbot.packet;

import java.nio.ByteBuffer;
import java.util.Map;

import android.support.v4.util.ArrayMap;

import com.github.legendofmcpe.pocketbot.Constants;
import com.github.legendofmcpe.pocketbot.PocketBot;
import com.github.legendofmcpe.pocketbot.packet.raknet.ReceivedRaknetPacket;

public class PacketParser implements Constants{
	private Map<Byte, Class<? extends ReceivedRaknetPacket>> packetTypes;
	private PocketBot bot;
	public PacketParser(int size, PocketBot bot){
		packetTypes = new ArrayMap<Byte, Class<? extends ReceivedRaknetPacket>>(size);
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
		ByteBuffer bb = ByteBuffer.wrap(buffer);
		Byte pid = bb.get();
		if(packetTypes.containsKey(pid)){
			try{
				ReceivedRaknetPacket pk = packetTypes.get(pid).newInstance();
				pk.decode(bb);
				return pk;
			}
			catch(Exception e){}
		}
		bot.getLogger().warning(bot.getLang().unknown_packet(pid, bb));
		UnknownReceivedPacket pack = new UnknownReceivedPacket();
		pack.setPid(pid);
		pack.decode(bb);
		return pack;
	}
}
