package com.github.legendofmcpe.pocketbot.packet.raknet;

import com.github.legendofmcpe.pocketbot.PocketBot;


public class UnknownReceivedPacket extends ReceivedRaknetPacket{
	private byte pid;
	private byte[] buffer;

	public void setPid(byte pid){
		this.pid = pid;
	}

	public byte getPid(){
		return pid;
	}

	@Override
	protected void decode(PocketBot bot){
		buffer = new byte[bb().remaining()];
		bb().get(buffer);
	}

	public byte[] getBuffer(){
		return buffer;
	}
}
