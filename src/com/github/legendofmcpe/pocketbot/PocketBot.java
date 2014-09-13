package com.github.legendofmcpe.pocketbot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.github.legendofmcpe.pocketbot.packet.Packet;

public class PocketBot extends Thread{
	private DatagramSocket socket;
	private final InetSocketAddress address;
	private Logger logger;
	private Lang lang;
	private List<String> msgQueue = new ArrayList<String>();
	public PocketBot(String name, InetSocketAddress address, Logger logger, Lang lang){
		super("PocketBot_".concat(name));
		this.address = address;
		this.logger = logger;
	}

	private void tick(){
		for(String msg: msgQueue){
			
			// TODO proceed message
		}
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
	public void queueMessage(String str){
		if(str.length() > 0xFFFF){
			throw new StringIndexOutOfBoundsException("The string is too long (more than 65535 bytes)!");
		}
	}
}
