package com.dixu.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;


public class Test3 {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(
				"/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/WORD_MAP.DAT.zip");
		//GZIPInputStream gis = new GZIPInputStream(fis);
		ZipInputStream zis = new ZipInputStream(fis);
		BufferedInputStream fin = new BufferedInputStream(zis);
		// InputStreamReader reader = new InputStreamReader(gis);
		// BufferedReader fin = new BufferedReader(reader);
		byte buffer[] = new byte[1024 * 1024];
		int count[] = { 4536, 9955, 47155, 1600, 620, 317, 425, 0, 547, 5205,
				3387, 6560, 378, 788, 384 };
		for (int i : count) {
			int bytes = fin.read(buffer, 0, i);
			System.out.println("bytes:"+bytes);
			System.out.println(new String(buffer, 0, 300));
			System.out
					.println("------------------------------------------------------------");
		}
	}
}
