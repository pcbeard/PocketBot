package com.github.legendofmcpe.pocketbot.packet.raknet;

public class OpenConnectionReply1 extends ReceivedRaknetPacket{
	private short mtu;
	@Override
	public byte getPid(){
		return OPEN_CONNECTION_REPLY_1;
	}

	@Override
	protected void decode(){
		bb().get(new byte[MAGIC_BYTES.length]); // magic
		bb().getLong(); // server ID
		bb().get(); // security
		mtu = bb().getShort();
	}

	public short getMtuCnt(){
		return mtu;
	}
}
