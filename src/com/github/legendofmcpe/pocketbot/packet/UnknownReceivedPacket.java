package com.github.legendofmcpe.pocketbot.packet;

import com.github.legendofmcpe.pocketbot.packet.raknet.ReceivedRaknetPacket;

public class UnknownReceivedPacket extends ReceivedRaknetPacket{
	private byte pid;
	private byte[] buffer;

	public void setPid(byte pid){
		this.pid = pid;
	}

	@Override
	public byte getPid(){
		return pid;
	}

	@Override
	protected void decode(){
		buffer = new byte[bb().remaining()];
		bb().get(buffer);
	}

	public byte[] getBuffer(){
		return buffer;
	}
}
