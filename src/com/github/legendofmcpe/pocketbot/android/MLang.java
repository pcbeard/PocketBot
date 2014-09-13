package com.github.legendofmcpe.pocketbot.android;

import java.nio.ByteBuffer;

import android.content.Context;

import com.github.legendofmcpe.pocketbot.Lang;

public class MLang implements Lang{
	private Context ctx;
	public MLang(Context ctx){
		this.ctx = ctx;
	}

	@Override
	public String socket_connect_error(){
		return ctx.getString(R.string.bot_socket_connect_error);
	}
	@Override
	public String unknown_packet(Byte pid, ByteBuffer bb){
		StringBuilder buffer = new StringBuilder("0x");
		while(bb.hasRemaining()){
			buffer.append(Integer.toHexString((int) (byte) bb.get()));
		}
		return ctx.getString(R.string.bot_unknown_packet)
				.replace("%hexpid%", "0x".concat(Integer.toHexString((int) (byte) pid)))
				.replace("%pid%", String.valueOf((int) (byte) pid))
				.replace("%buffer%", new String(bb.array()))
				.replace("%hexbuffer%", buffer.toString());
	}
}
