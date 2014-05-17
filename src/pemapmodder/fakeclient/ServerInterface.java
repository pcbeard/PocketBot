package pemapmodder.fakeclient;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ServerInterface extends Thread{
	public final InetSocketAddress address;
	public DatagramSocket socket;
	public ServerInterface(InetSocketAddress address) throws SocketException{
		this.address = address;
		socket = new DatagramSocket(address);
	}
	public void run(){
		
	}
}
