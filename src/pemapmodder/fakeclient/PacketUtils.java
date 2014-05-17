package pemapmodder.fakeclient;

public class PacketUtils{
	public final static byte
			 CONNECTED_PING_OPEN_CONNECTIONS	= 0x01
			,UNCONNECTED_PING_OPEN_CONNECTIONS	= 0x02
			,OPEN_CONNECTION_REQUEST			= 0x05
			,OPEN_CONNECTION_REQUEST_2			= 0x07
			;
	public final static byte
			 OPEN_CONNECTION_REPLY				= 0x06
			,OPEN_CONNECTION_REPLY_2			= 0x08
			,INCOMPATIBLE_PROTOCOL				= 0x1a
			;
}
