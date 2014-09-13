package com.github.legendofmcpe.pocketbot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

import com.github.legendofmcpe.pocketbot.packet.Packet;

public class PocketBot extends Thread{
	private DatagramSocket socket;
	private final InetSocketAddress address;
	private Logger logger;
	private Lang lang;
	public PocketBot(String name, InetSocketAddress address, Logger logger, Lang lang){
		super("PocketBot_".concat(name));
		this.address = address;
		this.logger = logger;
	}
	@Override
	public void run(){
		try{
			socket = new DatagramSocket();
		}
		catch(Exception e){
			logger.critical(lang.socket_connect_error());
		}
		connect();
	}
	private void connect(){
		// TODO
	}
	public DatagramSocket getSocket(){
		return socket;
	}
	public synchronized void sendPacket(Packet pk) throws IOException{
		DatagramPacket packet = new DatagramPacket(
				pk.getBuffer(), pk.getBuffer().length, address);
		socket.send(packet);
	}
	public InetSocketAddress getAddress(){
		return address;
	}
	public Logger getLogger(){
		return logger;
	}
	public Lang getLang(){
		return lang;
	}
}
