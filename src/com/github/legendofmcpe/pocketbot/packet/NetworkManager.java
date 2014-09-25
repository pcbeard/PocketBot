package com.github.legendofmcpe.pocketbot.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import com.github.legendofmcpe.pocketbot.PocketBot;
import com.github.legendofmcpe.pocketbot.packet.mc.DataPacket;
import com.github.legendofmcpe.pocketbot.packet.mc.DataPacketParser;
import com.github.legendofmcpe.pocketbot.packet.raknet.IncompatibleProtocolVersion;
import com.github.legendofmcpe.pocketbot.packet.raknet.OpenConnectionReply1;
import com.github.legendofmcpe.pocketbot.packet.raknet.OpenConnectionReply2;
import com.github.legendofmcpe.pocketbot.packet.raknet.OpenConnectionRequest1;
import com.github.legendofmcpe.pocketbot.packet.raknet.OpenConnectionRequest2;
import com.github.legendofmcpe.pocketbot.packet.raknet.RaknetDataPacket;
import com.github.legendofmcpe.pocketbot.packet.raknet.RaknetPacketParser;
import com.github.legendofmcpe.pocketbot.packet.raknet.ReceivedRaknetPacket;
import com.github.legendofmcpe.pocketbot.packet.raknet.SentCustomPacket;

public class NetworkManager{
	private PocketBot bot;
	private DatagramSocket sk;
	private InetSocketAddress addr;
	private RaknetPacketParser rpParser;
	private DataPacketParser dpParser;
	private int seqNumber;
	private List<RaknetDataPacket> dataPacketQueue = new ArrayList<RaknetDataPacket>(4);

	public NetworkManager(PocketBot bot){
		this.bot = bot;
		addr = bot.getAddress();
		rpParser = new RaknetPacketParser(32, bot);
		dpParser = new DataPacketParser(bot);
		ReceivedRaknetPacket.registerTypes(rpParser);
	}
	public void start(){
		connect();
		if(!bot.isRunning()){
			return;
		}
		login();
	}
	private void connect(){
		try{
			sk = new DatagramSocket();
		}
		catch(SocketException e1){
			bot.getLogger().critical(bot.getLang().socket_connect_error());
			bot.getLogger().exception(e1);
		}
		// send request 1
		List<Integer> nextMtu = getMtuList();
		try{
			ReceivedRaknetPacket rpk = null;
			do{
				sk.setSoTimeout(500);
				OpenConnectionRequest1 pk = new OpenConnectionRequest1(nextMtu.remove(0));
				pk.sendTo(sk, addr);
				rpk = receive(28);
			}
			while(rpk == null && !nextMtu.isEmpty());
			// handle reply 1
			if(rpk == null){
				bot.getLogger().critical(bot.getLang().server_no_response());
				bot.end();
				return;
			}
			sk.setSoTimeout(15000);
			short mtu = 0;
			if(rpk instanceof OpenConnectionReply1){
				mtu = ((OpenConnectionReply1) rpk).getMtuCnt();
			}
			else if(rpk instanceof IncompatibleProtocolVersion){
				byte expected = ((IncompatibleProtocolVersion) rpk).getExpectedProtocol();
				bot.getLogger().critical(bot.getLang().incorrect_raknet_protocol(expected));
				bot.end();
				return;
			}
			else{
				bot.getLogger().critical(bot.getLang().connect_packet_error_abort());
				bot.end();
				return;
			}
			// send request 2
			OpenConnectionRequest2 pk = new OpenConnectionRequest2(mtu, (short) addr.getPort(), bot.getClientId());
			pk.sendTo(sk, addr);
			OpenConnectionReply2 reply2 = (OpenConnectionReply2) receive(30);
			bot.setMtu(reply2.getMtu());
		}
		catch(Exception e){
			bot.getLogger().exception(e);
		}
	}
	private void login(){
		
	}

	private ReceivedRaknetPacket receive(int size) throws IOException{
		DatagramPacket pk = new DatagramPacket(new byte[size], size);
		try{
			sk.receive(pk);
			byte[] buffer = pk.getData();
			return rpParser.parsePacket(buffer);
		}
		catch(SocketTimeoutException e){
			return null;
		}
	}

	public void addToQueue(RaknetDataPacket pk){
		dataPacketQueue.add(pk);
	}
	public void sendDataPackets(){
		SentCustomPacket pk = new SentCustomPacket(
				new ArrayList<RaknetDataPacket>(dataPacketQueue), seqNumber++);
		dataPacketQueue.clear();
		try{
			pk.sendTo(sk, addr);
			dataPacketQueue.clear();
		}
		catch(IOException e){
			e.printStackTrace(bot.getLogger().getPrinter());
		}
		
	}
	public void sendDataPacketDirect(DataPacket pk){
		List<RaknetDataPacket> pks = new ArrayList<RaknetDataPacket>(1);
		pks.add(new RaknetDataPacket(pk));
		SentCustomPacket scp = new SentCustomPacket((byte) 0x80, pks, seqNumber++);
		try{
			scp.sendTo(sk, addr);
		}
		catch(IOException e){
			e.printStackTrace(bot.getLogger().getPrinter());
		}
	}

	public DataPacketParser getDataPacketParser(){
		return dpParser;
	}

	public void handlePacket(DataPacket parsePacket){
		// TODO Auto-generated method stub
	}

	public static List<Integer> getMtuList(){
		List<Integer> l = new ArrayList<Integer>(13);
		for(int i = 0; i < 4; i++){
			l.add(1447);
		}
		for(int i = 0; i < 4; i++){
			l.add(1155);
		}
		for(int i = 0; i < 5; i++){
			l.add(531);
		}
		return l;
	}
}
