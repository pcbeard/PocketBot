package legendofmcpe.fakeclient.packet;

import java.net.DatagramPacket;

public class Utils{
	public final static byte[] MAGIC = {
		0x00, (byte)0xff, (byte)0xff, 0x00,
		(byte)0xfe, (byte)0xfe, (byte)0xfe, (byte)0xfe,
		(byte)0xfd, (byte)0xfd, (byte)0xfd, (byte)0xfd,
		0x12, 0x34, 0x56, 0x78
	};
	public final static byte[] SECURITY_COKKIE = { // read security + cookie from wiki. No idea what the it is]
		0x04, 0x3f, 0x57, (byte) 0xfe, (byte) 0xfd
	};
	public final static byte PROTOCOL = 5;
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
	public static byte[] arraySlice_SE(byte[] in, int start) throws IndexOutOfBoundsException{
		return arraySlice_SE(in, start, in.length);
	}
	public static byte[] arraySlice_SE(byte[] in, int start, int end) throws IndexOutOfBoundsException{
		byte[] result = {};
		for(int i = start; i < end; i++){
			result[result.length] = in[i];
		}
		return result;
	}
	public static byte[] arraySlice_SL(byte[] in, int start) throws IndexOutOfBoundsException{
		return arraySlice_SL(in, start, in.length - start);
	}
	public static byte[] arraySlice_SL(byte[] in, int start, int length) throws IndexOutOfBoundsException{
		byte[] result = {};
		for(int i = 0; i < length; i++){
			result[i] = in[start + i];
		}
		return result;
	}
	public static long readLong(byte[] bytes) throws IndexOutOfBoundsException{
		long output = 0;
		for(int i = 0; i < 8; i++){
			output += (long) bytes[i];
			output <<= 8;
		}
		return output;
	}
	public static byte[] readLong(long in){
		byte[] out = {};
		for(int i = 1; i <= 8; i++){
			out[i] = (byte) ((in >> ((8 - i) * 8)) & 0xFF);
		}
		return out;
	}
	public static int readInt(byte[] bytes) throws IndexOutOfBoundsException{
		int output = 0;
		for(int i = 0; i < 4; i++){
			output += (int) bytes[i];
			output <<= 8;
		}
		return output + 0x80000000;
	}
	public static byte[] writeInt(int in){
		byte[] out = {};
		for(int i = 1; i <= 4; i++){
			out[i] = (byte) ((in >> ((4 - i) * 8)) & 0xFF);
		}
		return out;
	}
	public static short readShort(byte[] bytes) throws IndexOutOfBoundsException{
		short output = 0;
		for(int i = 0; i < 2; i++){
			output += (short) bytes[i];
			output <<= 8;
		}
		return (short) (output + (short) 0x8000);
	}
	public static byte[] writeShort(short in){
		byte[] out = {};
		for(int i = 1; i <= 2; i++){
			out[i] = (byte) ((in >> ((2 - i) * 8)) & 0xFF);
		}
		return out;
	}
	public static String readString(byte[] in){
		return (String) readString(in, 0)[0];
	}
	public static Object[] readString(byte[] in, int pointer){
		String output = "";
		byte[] bytes = {};
		for(int i = pointer; i < in.length; i++){
			bytes[bytes.length] = in[i];
		}
		short length = readShort(bytes);
		for(int i = 0; i < length; i++){
			output += (char) bytes[i + 2];
		}
		return new Object[]{output, length + 2};
	}
	public static byte[][] arrayShift(byte[] bytes){
		return arrayShift(bytes, 1);
	}
	public static byte[][] arrayShift(byte[] bytes, int shifts){
		bytes = bytes.clone();
		byte[][] result = new byte[][]{new byte[]{}, new byte[]{}};
		for(int i = 0; i < shifts; i++){
			byte pulled = bytes[0];
			byte[] newBytes = new byte[]{};
			for(int j = 1; j < bytes.length; j++){
				newBytes[newBytes.length] = bytes[j];
			}
			bytes = newBytes;
			result[0][result[0].length] = pulled;
		}
		result[1] = bytes;
		return result;
	}
}
