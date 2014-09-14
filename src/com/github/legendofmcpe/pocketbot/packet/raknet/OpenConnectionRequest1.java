package com.github.legendofmcpe.pocketbot.packet.raknet;


public class OpenConnectionRequest1 extends SentRaknetPacket{
	private int mtu = -1;
	public OpenConnectionRequest1(int mtu){
		super(mtu + 18);
	}
	@Override
	public byte getPid(){
		return OPEN_CONNECTION_REQUEST_1;
	}
	@Override
	public void encode(){
		bb().put(MAGIC_BYTES);
		bb().put(RAKNET_VERSION);
		for(int i = 0; i < mtu; i++){
			bb().put((byte) 0x00);
		}
	}
}
