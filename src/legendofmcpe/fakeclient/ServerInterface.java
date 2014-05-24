package legendofmcpe.fakeclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import legendofmcpe.fakeclient.android.ConnectServerService;
import legendofmcpe.fakeclient.android.Console;
import legendofmcpe.fakeclient.packet.OpenConnectionRequestPacket;
import legendofmcpe.fakeclient.packet.Packet;
import legendofmcpe.fakeclient.packet.Utils;

public class ServerInterface extends Thread{
	public final InetSocketAddress address;
	public final DatagramSocket socket;
	public final ConnectServerService ctx;
	public final Console console;
	protected long time;
	public ServerInterface(InetSocketAddress address, ConnectServerService ctx, Console console) throws SocketException{
		this.address = address;
		this.ctx = ctx;
		this.console = console;
		socket = new DatagramSocket(address);
	}
	public void run(){
		long time = System.nanoTime();
		boolean received = false;
		boolean isFirst = true;
		byte[] advertizer = new byte[8];
		while(!received && System.nanoTime() - time <= 5000000l){
			try{
				socket.send(new OpenConnectionRequestPacket(System.nanoTime() - time, isFirst).toUDP());
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
			}
			isFirst = false;
		}
		if(!received){
			err(new Exception("No reponse from server for 5 seconds"));
		}
		@SuppressWarnings("unused") long pingID = Utils.readLong(Utils.arraySlice_SL(advertizer, 0, 8));
		long serverID = Utils.readLong(Utils.arraySlice_SL(advertizer, 8, 8));
		
		console.write("Logging in into server of ID " + Long.toString(serverID));
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
