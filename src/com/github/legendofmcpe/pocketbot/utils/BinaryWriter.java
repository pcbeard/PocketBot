package com.github.legendofmcpe.pocketbot.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * An object that stores a buffer of bytes,
 * increasing the buffer size when necessary,
 * and has methods for putting different data types in.
 * <hr>
 * A warning when editing this class:
 * Numbers beginning in 0 are octal.
 * Edit with care.
 */
public class BinaryWriter{
	private int pointer = 0;
	private byte[] buffer;
	private boolean reverse;
	public BinaryWriter(int size){
		this(new byte[size]);
	}
	public BinaryWriter(byte[] base){
		this(base, true);
	}
	public BinaryWriter(int size, boolean reverse){
		this(new byte[size], reverse);
	}
	public BinaryWriter(byte[] base, boolean reverse){
		buffer = base;
		this.reverse = reverse;
	}
	public void put(byte b){
		allocateMore(1);
		directPut(b);
	}
	public void put(byte[] bytes){
		if(reverse){
			
		}
		byte[] reversed = new byte[bytes.length];
		int i = bytes.length;
		for(byte b: bytes){
			reversed[--i] = b;
		}
		rawPut(reversed);
	}
	public void rawPut(byte[] bytes){
		allocateMore(bytes.length);
		for(byte b: bytes){
			directPut(b);
		}
	}
	public void putShort(short s){
		if(reverse){
			s = Short.reverseBytes(s);
		}
		rawPutShort(s);
	}
	public void rawPutShort(short s){
		allocateMore(2);
		directPut((byte) (s >> 010));
		directPut((byte) s);
	}
	public void putTriad(int t){
		if(reverse){
			t <<= 8;
			t = Integer.reverseBytes(t);
		}
		rawPutTriad(t);
	}
	public void rawPutTriad(int t){
		allocateMore(3);
		directPut((byte) (t >> 020));
		directPut((byte) (t >> 010));
		directPut((byte) t);
	}
	public void putInt(int i){
		if(reverse){
			i = Integer.reverseBytes(i);
		}
		rawPutInt(i);
	}
	public void rawPutInt(int i){
		allocateMore(4);
		directPut((byte) (i >> 030));
		directPut((byte) (i >> 020));
		directPut((byte) (i >> 010));
		directPut((byte) i);
	}
	public void putLong(long l){
		if(reverse){
			l = Long.reverseBytes(l);
		}
		rawPutLong(l);
	}
	public void rawPutLong(long l){
		allocateMore(8);
		for(int i = 070; i >= 0; i -= 010){
			directPut((byte) (i >> i));
		}
	}
	public void putFloat(float f){
		allocateMore(4);
		ByteBuffer bb = ByteBuffer.allocate(4).order(reverse ? ByteOrder.LITTLE_ENDIAN:ByteOrder.BIG_ENDIAN);
		bb.putFloat(f);
		rawPut(bb.array());
	}
	public void putDouble(double d){
		allocateMore(8);
		ByteBuffer bb = ByteBuffer.allocate(8).order(reverse ? ByteOrder.LITTLE_ENDIAN:ByteOrder.BIG_ENDIAN);
		bb.putDouble(d);
		rawPut(bb.array());
	}

	protected void directPut(byte b){
		buffer[pointer++] = b;
	}
	public void allocateMore(int cnt){
		int required = pointer + cnt;
		if(required - buffer.length > 0){
			byte[] newArr = new byte[required];
			System.arraycopy(buffer, 0, newArr, 0, buffer.length);
			buffer = newArr;
		}
	}
	public byte[] getBuffer(){
		return buffer;
	}
}
