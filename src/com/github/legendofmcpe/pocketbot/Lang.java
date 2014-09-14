package com.github.legendofmcpe.pocketbot;

import java.nio.ByteBuffer;

public interface Lang{
	public String socket_connect_error();
	public String unknown_packet(Byte pid, ByteBuffer bb);
	public String server_no_response();
	public String stopping();
	public String incorrect_raknet_protocol(byte expected);
	public String connect_packet_error_abort();
}
