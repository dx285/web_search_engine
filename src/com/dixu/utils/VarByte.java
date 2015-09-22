package com.dixu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class VarByte {

	private static long MAX[] = { 0x80l, 0x4000l, 0x200000l, 0x10000000l, 0x800000000l };

	public static byte[] int2bytes(long num) {
		int length = 0;
		for (int i = 0; i < MAX.length; i++)
			// determine the number of bytes should use
			if (MAX[i] > num) {
				length = i + 1;
				break;
			}
		byte ret[] = new byte[length];
		for (int i = 0; i < length; i++) {
			ret[i] = (byte) (num & 0x7F); // get the lower 7 bits
			ret[i] ^= 0x80; // set the 8th bit to 1
			num >>>= 7;
		}
		ret[length - 1] &= 0x7F; // the 8th bit in the last byte is set to 0 as
									// termination
		return ret;
	}

	public static List<Long> bytes2ints(byte bytes[]) {
		assert bytes != null;
		List<Long> list = new ArrayList<Long>();
		int scale = 0;
		long num = 0l;
		for (int i = 0; i < bytes.length; i++) {
			num += (bytes[i] & 0x7F) << scale;
			scale += 7;
			// Test the highest bit;
			if ((bytes[i] & 0x80) == 0) {
				list.add(Long.valueOf(num));
				num = 0l;
				scale = 0;
			}
		}
		return list;
	}
}
