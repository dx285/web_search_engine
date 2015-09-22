package com.dixu.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;



public class Test1 {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */

	static byte[] buffer = new byte[1024 * 1024];
	static File d_disk = new File("/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/data"), 
			e_disk = new File("/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/data");
	static ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);

	public static void main(String[] args) throws Exception {

		System.out.println("Normal IO on Spinning Hard Drive: ");
		for (int i = 0; i < 5; i++) {
			long before = System.currentTimeMillis();
			normalIO(e_disk, 1);
			long now = System.currentTimeMillis();
			System.out.println("Test " + i + ":" + ((now - before) / 1000.0)
					+ "s");
		}

		System.out.println("Normal IO on Solid State Drive: ");
		for (int i = 0; i < 5; i++) {
			long before = System.currentTimeMillis();
			normalIO(d_disk, 10);
			long now = System.currentTimeMillis();
			System.out.println("Test " + i + ":" + ((now - before) / 1000.0)
					+ "s");
		}

		System.out.println("NIO on Spinning Hard Drive: ");
		for (int i = 0; i < 5; i++) {
			long before = System.currentTimeMillis();
			NIO(e_disk, 1);
			long now = System.currentTimeMillis();
			System.out.println("Test " + i + ":" + ((now - before) / 1000.0)
					+ "s");
		}

		System.out.println("NIO on Solid State Drive: ");
		for (int i = 0; i < 5; i++) {
			long before = System.currentTimeMillis();
			NIO(d_disk, 10);
			long now = System.currentTimeMillis();
			System.out.println("Test " + i + ":" + ((now - before) / 1000.0)
					+ "s");
		}

		// ByteBuffer b = ByteBuffer.allocate(1024 * 1024);
		// // b.put(new byte[1024 * 1024]);
		// b.put(buffer);
	}

	public static void NIO(File f, int mode) throws Exception {
		if (f.exists())
			f.delete();
		FileOutputStream fout = new FileOutputStream(f);
		FileChannel fc = fout.getChannel();
		for (int i = 0; i < 1000 * mode; i++) {
			bb.clear();
			bb.put(buffer);
			bb.flip();
			fc.write(bb);
		}
		fc.close();
		fout.flush();
		fout.close();
	}

	public static void normalIO(File f, int mode) throws Exception {
		if (f.exists())
			f.delete();
		DataOutputStream fout = new DataOutputStream(new FileOutputStream(f));
		for (int i = 0; i < 1000 * mode; i++)
			System.out.println("here "+i);
			fout.write(buffer);
		fout.flush();
		fout.close();
	}

}
