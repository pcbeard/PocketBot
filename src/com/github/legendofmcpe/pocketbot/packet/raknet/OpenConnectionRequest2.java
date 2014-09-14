package com.github.legendofmcpe.pocketbot.packet.raknet;

public class OpenConnectionRequest2 extends SentRaknetPacket{
	private short port, mtu;
	private long clientId;

	public OpenConnectionRequest2(short port, short mtu, long clientId){
		super(34);
		this.port = port;
		this.mtu = mtu;
		this.clientId = clientId;
	}

	@Override
	public byte getPid(){
		return OPEN_CONNECTION_REQUEST_2;
	}

	@Override
	protected void encode(){
		bb().put(MAGIC_BYTES);
		bb().put((byte) 0x04);
		bb().putInt(0x3f57fefd);
		bb().putShort(port);
		bb().putShort(mtu);
		bb().putLong(clientId);
	}
}
