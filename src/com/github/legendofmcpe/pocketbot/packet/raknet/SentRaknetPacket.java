package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.github.legendofmcpe.pocketbot.Constants;

public abstract class SentRaknetPacket implements Constants{
	private ByteBuffer bb;
	protected SentRaknetPacket(int malloc){
		bb = ByteBuffer.allocate(malloc);
	}
	public abstract byte getPid();
	public void sendTo(DatagramSocket sk, SocketAddress addr) throws IOException{
		bb.put(getPid());
		encode();
		byte[] buffer = bb.array();
		sk.send(new DatagramPacket(buffer, buffer.length));
	}
	protected abstract void encode();
	protected final ByteBuffer bb(){
		return bb;
	}
}
