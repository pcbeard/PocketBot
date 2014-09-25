package com.github.legendofmcpe.pocketbot.packet.raknet;

import java.util.List;

import com.github.legendofmcpe.pocketbot.utils.Gettable;

public class SentCustomPacket extends SentRaknetPacket{
	public static class ListCounter implements Gettable<Integer>{
		private List<RaknetDataPacket> packets;
		private int initial;
		public ListCounter(List<RaknetDataPacket> packets, int initial){
			this.packets = packets;
			this.initial = initial;
		}
		public Integer run(){
			Integer total = initial;
			for(RaknetDataPacket pk: packets){
				total += pk.getBuffer().length;
			}
			return total;
		}
	}
	private byte pid;
	private List<RaknetDataPacket> packets;
	private int seqNumber;

	public SentCustomPacket(List<RaknetDataPacket> packets, int seqNumber){
		this((byte) 0x84, packets, seqNumber);
	}
	public SentCustomPacket(byte pid, List<RaknetDataPacket> packets, int sequenceNumber){
		super(new ListCounter(packets, 4));
		this.pid = pid;
		this.packets = packets;
		seqNumber = sequenceNumber;
	}
	@Override
	public byte getPid(){
		return pid;
	}
	@Override
	protected void encode(){
		bb().put(pid);
		putTriad(seqNumber);
		for(RaknetDataPacket pk: packets){
			bb().put(pk.getBuffer());
		}
	}
	public List<RaknetDataPacket> getPackets(){
		return packets;
	}
}
