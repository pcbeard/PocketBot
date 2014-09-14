package com.github.legendofmcpe.pocketbot.runner;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.github.legendofmcpe.pocketbot.Lang;

public class PropertiesLang implements Lang{
	private PropertiesConfig config;
	public PropertiesLang(File f){
		Map<String, String> d = new HashMap<String, String>(6); // TODO update this count
		d.put("socket_connect_error", "Cannot connect to socket!");
		d.put("unknown_packet", "Unknwon packet $pid$ (0x$hex_pid$) received! Buffer: $buffer$");
		d.put("server_no_response", "No response from the server! Is the server offline?");
		d.put("stopping", "Stopping connection...");
		d.put("incorrect_raknet_protocol", "The RakNet protocol doesn't match: server expected $expected$!");
		d.put("connect_packet_error_abort", "A protocol error occurred during creating connection; cannot connect to the server.");
		config = new PropertiesConfig(f, d);
	}
	public String socket_connect_error(){
		return config.get("socket_connect_error");
	}
	public String unknown_packet(Byte pid, ByteBuffer bb){
		return config.get("unknown_packet")
				.replace("$pid$", String.valueOf(pid))
				.replace("$hex_pid$", Integer.toHexString(pid))
				.replace("$buffer$", new String(bb.array()));
	}
	public String server_no_response(){
		return config.get("server_no_response");
	}
	public String stopping(){
		return config.get("stopping");
	}
	public String incorrect_raknet_protocol(byte expected){
		return config.get("incorrect_raknet_protocol")
				.replace("$expected$", String.valueOf((int) expected));
	}
	public String connect_packet_error_abort(){
		return config.get("connect_packet_error_abort");
	}
}
