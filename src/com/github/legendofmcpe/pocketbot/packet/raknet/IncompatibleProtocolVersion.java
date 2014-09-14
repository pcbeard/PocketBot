package com.github.legendofmcpe.pocketbot.packet.raknet;

public class IncompatibleProtocolVersion extends ReceivedRaknetPacket{
	private byte expected = -1;
	@Override
	public byte getPid(){
		return INCOMPATIBLE_PROTOCOL_VERSION;
	}

	@Override
	protected void decode(){
		expected = bb().get();
	}

	public byte getExpectedProtocol(){
		return expected;
	}
}
