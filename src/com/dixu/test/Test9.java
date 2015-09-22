package com.dixu.test;

import java.io.File;
import java.io.FileInputStream;

public class Test9 {

	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream("/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/WORD_MAP.DAT");
		byte[] buffer = new byte[1000];
		System.out.println(fis.read(buffer));

		fis.close();
		File file = new File("/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/WORD_MAP.DAT");
		System.out.println(file.length());

	}

}
