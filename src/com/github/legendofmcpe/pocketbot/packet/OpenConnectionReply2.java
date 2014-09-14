package com.github.legendofmcpe.pocketbot.packet;

import com.github.legendofmcpe.pocketbot.packet.raknet.ReceivedRaknetPacket;

public class OpenConnectionReply2 extends ReceivedRaknetPacket{
	private short mtu;

	@Override
	public byte getPid(){
		return OPEN_CONNECTION_REPLY_2;
	}

	@Override
	protected void decode(){
		bb().get(new byte[MAGIC_BYTES.length]); // magic // don't pass MAGIC_BYTES directly, or it will get overwritten!
		bb().getLong(); // server ID
		bb().getShort(); // my port
		mtu = bb().getShort();
		bb().get(); // security
	}

	public short getMtu(){
		return mtu;
	}
}
