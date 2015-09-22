package com.dixu.utils;


public class Normal {

	public static byte[] int2bytes(long vv) {
		int v = (int) vv;
		byte b[] = new byte[4];
		//reverse??? why???
		b[3] = (byte) ((v >>> 24) & 0xFF);
		b[2] = (byte) ((v >>> 16) & 0xFF);
		b[1] = (byte) ((v >>> 8) & 0xFF);
		b[0] = (byte) ((v >>> 0) & 0xFF);
		return b;
	}

}
