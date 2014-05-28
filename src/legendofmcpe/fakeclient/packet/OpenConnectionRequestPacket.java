package legendofmcpe.fakeclient.packet;

import java.net.DatagramPacket;
import java.net.SocketAddress;

public class OpenConnectionRequestPacket extends Packet{
	protected int length;
	protected SocketAddress address;
	public OpenConnectionRequestPacket(int nullLength, SocketAddress inetAddress){
		this.length = nullLength;
		this.address = inetAddress;
	}
	@Override public DatagramPacket toUDP(){
		byte[] buffer = new byte[]{};
		try{
			byte[] magic = new byte[]{Utils.O.OPEN_CONNECTION_REQUEST};
			byte[] protocol = new byte[]{Utils.PROTOCOL};
			byte[] payload = new byte[]{};
			for(int i = 0; i < this.length; i++){
				payload[i] = (byte) 0;
			}
			buffer = Utils.mergeArrays(new byte[][]{magic, protocol, payload});
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length, address);
			return pack;
		}catch(Throwable e){
			err(e);
			return null;
		}
	}
}
