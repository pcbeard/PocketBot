package legendofmcpe.fakeclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import legendofmcpe.fakeclient.android.ConnectServerService;
import legendofmcpe.fakeclient.packet.OpenConnectionRequestPacket;
import legendofmcpe.fakeclient.packet.Utils;
import android.content.Context;

public class ServerInterface extends Thread{
	public final InetSocketAddress address;
	public final DatagramSocket socket;
	public final ConnectServerService ctx;
	protected long time;
	public ServerInterface(InetSocketAddress address, ConnectServerService ctx) throws SocketException{
		this.address = address;
		this.ctx = ctx;
		socket = new DatagramSocket(address);
	}
	public void run(){
		long time = System.nanoTime();
		boolean received = false;
		boolean isFirst = true;
		byte[] advertizer;
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
		
	}
	protected void err(Throwable t){
		ctx.err(t);
	}
}
