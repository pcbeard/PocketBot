package pemapmodder.fakeclient.packet;

import java.net.DatagramPacket;

public class Utils{
	public final static byte[] MAGIC = {
		0x00, (byte)0xff, (byte)0xff, 0x00,
		(byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfe,
		(byte)0xfd, (byte)0xfd, (byte)0xfd, (byte)0xfd,
		0x12, 0x34, 0x56, 0x78
	};
	public final static class O{
		public final static byte
				 CONNECTED_PING_OPEN_CONNECTIONS	= 0x01
				,UNCONNECTED_PING_OPEN_CONNECTIONS	= 0x02
				,OPEN_CONNECTION_REQUEST			= 0x05
				,OPEN_CONNECTION_REQUEST_2			= 0x07
				;
	}
	public final static class I{
		public final static byte
				 OPEN_CONNECTION_REPLY				= 0x06
				,OPEN_CONNECTION_REPLY_2			= 0x08
				,INCOMPATIBLE_PROTOCOL				= 0x1a
				,UNCONNECTED_PING_OPEN_CONNECTIONS	= 0x1c
				,ADVERTIZE_SYSTEM					= 0x1d
				;
	}
	public static byte[] mergeArrays(byte[][] a){
		byte[] r = {};
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[i].length; j++){
				r[r.length] = a[i][j];
			}
		}
		return r;
	}
	public static Packet evalPacket(DatagramPacket pk){
		return null;
	}
}
