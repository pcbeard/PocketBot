package pemapmodder.fakeclient.packet;

import java.net.DatagramPacket;

public abstract class Packet{
	public abstract DatagramPacket toUDP();
	protected void err(Throwable t){
		// TODO
	}
}
