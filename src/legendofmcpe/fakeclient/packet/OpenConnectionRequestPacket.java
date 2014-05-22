package legendofmcpe.fakeclient.packet;

import java.net.DatagramPacket;
import java.net.SocketException;

public class OpenConnectionRequestPacket extends Packet{
	public byte[] pingID;
	public final boolean isFirst;
	public OpenConnectionRequestPacket(long pingID, boolean isFirst){
		for(int i = 0x07; i >= 0; i--){
			byte b = (byte)((pingID >> (i * 8)) & 0xff);
			this.pingID[i] = b;
		}
		this.isFirst = isFirst;
	}
	@Override public DatagramPacket toUDP(){
		byte[] buffer = Utils.mergeArrays(new byte[][]{
				new byte[]{(isFirst ? Utils.O.CONNECTED_PING_OPEN_CONNECTIONS:Utils.O.UNCONNECTED_PING_OPEN_CONNECTIONS)},
				Utils.MAGIC.clone(),
				pingID.clone()
		});
		try{
			DatagramPacket pk = new DatagramPacket(buffer, buffer.length, null);
			return pk;
		}catch(SocketException e){
			err(e);
			return null;
		}
	}
}
