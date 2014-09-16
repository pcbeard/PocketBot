package com.github.legendofmcpe.pocketbot.packet.raknet;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class OpenConnectionReply1 extends ReceivedRaknetPacket{
	private short mtu;

	public byte getPid(){
		return OPEN_CONNECTION_REPLY_1;
	}

	@Override
	protected void decode(PocketBot bot){
		bb().get(new byte[MAGIC_BYTES.length]); // magic
		bb().getLong(); // server ID
		bb().get(); // security
		mtu = bb().getShort();
	}

	public short getMtuCnt(){
		return mtu;
	}
}
