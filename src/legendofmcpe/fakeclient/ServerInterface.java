package legendofmcpe.fakeclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import legendofmcpe.fakeclient.android.ConnectServerService;
import legendofmcpe.fakeclient.android.Console;
import legendofmcpe.fakeclient.packet.OpenConnectionRequest2Packet;
import legendofmcpe.fakeclient.packet.OpenConnectionRequestPacket;
import legendofmcpe.fakeclient.packet.Packet;
import legendofmcpe.fakeclient.packet.UnconnectedOpenConnectionRequestPacket;
import legendofmcpe.fakeclient.packet.Utils;
import android.os.Bundle;

public class ServerInterface extends Thread{
	public final InetSocketAddress address;
	public final DatagramSocket socket;
	public final ConnectServerService ctx;
	public final Console console;
	protected long time;
	public final Bundle args;
	public ServerInterface(InetSocketAddress address, ConnectServerService ctx, Console console, Bundle args) throws SocketException{
		this.address = address;
		this.ctx = ctx;
		this.console = console;
		this.args = args;
		socket = new DatagramSocket(address);
	}
	public void run(){
		@SuppressWarnings("unused") long time = System.nanoTime();
		try{
			init();
		}catch(OutdatedServerException e){
			err(e);
		}
	}
	protected void init() throws OutdatedServerException{
		long time = System.nanoTime();
		boolean received = false;
		boolean isFirst = true;
		byte[] advertizer = new byte[8];
		while(!received && System.nanoTime() - time <= 5000000l){
			try{
				socket.send(new UnconnectedOpenConnectionRequestPacket(System.nanoTime() - time, isFirst, socket.getLocalSocketAddress()).toUDP());
				DatagramPacket pack = new DatagramPacket(new byte[128], 128);
				socket.receive(pack);
				byte[] data = pack.getData();
				if(data[0] == Utils.I.UNCONNECTED_PING_OPEN_CONNECTIONS){
					received = true;
					advertizer = data;
					break;
				}
			}catch(Exception e){
				err(e);
				return;
			}
			isFirst = false;
		}
		if(!received){
			err(new Exception("No reponse from server for 5 seconds"));
		}
		@SuppressWarnings("unused") long pingID = Utils.readLong(Utils.arraySlice_SL(advertizer, 0, 8));
		long serverID = Utils.readLong(Utils.arraySlice_SL(advertizer, 8, 8));
		int length = Utils.MAGIC.length;
		byte[] identifier = Utils.arraySlice_SL(advertizer, 8 + 8 + length);
		String name = Utils.readString(identifier);
		name = new String(name.substring(11)); // hello GC
		console.write("Logging in into server of ID " + Long.toString(serverID) + " with identifier " + name + ".");
		
		byte[] buffer = new byte[32];
		byte[] data = new byte[0];
		try{
			socket.setSoTimeout(500);
			for(int i = 0; i < 4; i++){
				socket.send(new OpenConnectionRequestPacket(1447, socket.getLocalSocketAddress()).toUDP());
				DatagramPacket pack = new DatagramPacket(buffer, 32);
				socket.receive(pack);
				buffer = pack.getData();
				if(buffer[0] == Utils.I.INCOMPATIBLE_PROTOCOL){
					throw new OutdatedServerException(buffer[1]);
				}
				if(buffer[0] == Utils.I.OPEN_CONNECTION_REPLY){
					data = buffer;
					break;
				}
			}
			if(data.length == 0){
				for(int i = 0; i < 4; i++){
					socket.send(new OpenConnectionRequestPacket(1155, socket.getLocalSocketAddress()).toUDP());
					DatagramPacket pack = new DatagramPacket(buffer, 32);
					socket.receive(pack);
					buffer = pack.getData();
					if(buffer[0] == Utils.I.INCOMPATIBLE_PROTOCOL){
						throw new OutdatedServerException(buffer[1]);
					}
					if(buffer[0] == Utils.I.OPEN_CONNECTION_REPLY){
						data = buffer;
						break;
					}
				}
			}
			if(data.length == 0){
				for(int i = 0; i < 5; i++){
					socket.send(new OpenConnectionRequestPacket(1155, socket.getLocalSocketAddress()).toUDP());
					DatagramPacket pack = new DatagramPacket(buffer, 32);
					socket.receive(pack);
					buffer = pack.getData();
					if(buffer[0] == Utils.I.INCOMPATIBLE_PROTOCOL){
						throw new OutdatedServerException(buffer[1]);
					}
					if(buffer[0] == Utils.I.OPEN_CONNECTION_REPLY){
						data = buffer;
						break;
					}
				}
			}
			if(data.length == 0){
				throw new Exception("Server not found.");
			}
		}catch(Throwable e){
			err(e);
			return;
		}
		
		short MTU = Utils.readShort(Utils.arraySlice_SE(data, 26));
		try{
			socket.send(new OpenConnectionRequest2Packet(address, MTU, args.getLong("CID")).toUDP());
		}catch(IOException e){
			err(e);
		}
	}
	public DatagramPacket waitPacket(Packet packet, byte[] wait){
		long start = System.nanoTime();
		boolean received = false;
		while(!received && System.nanoTime() - start <= 5000000l){
			try{
				socket.send(packet.toUDP());
				DatagramPacket pack = new DatagramPacket(new byte[128], 128);
				socket.receive(pack);
				for(int i = 0; i < wait.length; i++){
					if(wait[i] == pack.getData()[0]){
						return pack;
					}
				}
			}catch(Exception e){
				err(e);
			}
		}
		return null;
	}
	protected void err(Throwable t){
		ctx.err(t);
	}
}
