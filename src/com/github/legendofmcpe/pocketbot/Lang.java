package com.github.legendofmcpe.pocketbot;

import java.nio.ByteBuffer;

public interface Lang{
	public String socket_connect_error();
	public String unknown_packet(Byte pid, ByteBuffer bb);
}
