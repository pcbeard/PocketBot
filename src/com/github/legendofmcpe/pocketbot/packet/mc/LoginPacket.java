package com.github.legendofmcpe.pocketbot.packet.mc;

import java.nio.ByteBuffer;

public class LoginPacket extends DataPacket{
	private String username;

	public LoginPacket(String username){
		this.username = username;
	}

	@Override
	protected int getSize(){
		return 17 + username.length(); // TODO
	}

	@Override
	protected void encode(ByteBuffer bb){
		bb.put(LOGIN_PACKET);
		putString(bb, username);
		bb.putInt(MCPE_PROTOCOL);
		bb.putInt(MCPE_PROTOCOL);
		bb.putInt(0);
		putString(bb, "");
	}
}
