package com.github.legendofmcpe.pocketbot.packet.raknet;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class ReceivedCustomPacket extends ReceivedRaknetPacket{
	private int seqNumber;
	@Override
	public void decode(PocketBot bot){
		seqNumber = getTriad();
		byte ecId = bb().get();
		while(bb().remaining() > 0){
			/*short length =*/ bb().getShort();
			switch(ecId){
				case 0x60:
					bb().get(new byte[4]);
				case 0x40:
					bb().get(new byte[3]);
					break;
			}
			bot.getNetworkMgr().handlePacket(bot.getNetworkMgr().getDataPacketParser().parsePacket(bb()));
		}
	}
	public int getSeqNumber(){
		return seqNumber;
	}
}
