package pemapmodder.fakeclient;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import pemapmodder.fakeclient.packet.OpenConnectionRequestPacket;

public class ServerInterface extends Thread{
	public final InetSocketAddress address;
	public final DatagramSocket socket;
	protected long time;
	public ServerInterface(InetSocketAddress address) throws SocketException{
		this.address = address;
		socket = new DatagramSocket(address);
	}
	public void run(){
		long time = System.nanoTime();
		boolean received = false;
		while(!received && System.nanoTime() - time <= 5000000l){
			try{
				socket.send(new OpenConnectionRequestPacket(System.nanoTime() - time).toUDP());
				DatagramPacket pack = new DatagramPacket(new byte[128], 128);
				socket.receive(pack);
			}catch(Exception e){
				err(e);
			}
		}
		if(!received){
			err(new Exception("No reponse from server for 5 seconds"));
		}
	}
	protected void err(Throwable t){
		// TODO
	}
}
