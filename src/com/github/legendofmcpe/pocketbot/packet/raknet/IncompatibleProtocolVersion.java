package com.github.legendofmcpe.pocketbot.packet.raknet;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class IncompatibleProtocolVersion extends ReceivedRaknetPacket{
	private byte expected = -1;
	public byte getPid(){
		return INCOMPATIBLE_PROTOCOL_VERSION;
	}

	@Override
	protected void decode(PocketBot bot){
		expected = bb().get();
	}

	public byte getExpectedProtocol(){
		return expected;
	}
}
