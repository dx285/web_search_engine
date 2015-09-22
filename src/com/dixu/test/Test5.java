package com.dixu.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;

import com.dixu.utils.GZipHelper;
import com.dixu.utils.MyProperties;


public class Test5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(MyProperties.NZ[GZipHelper.NZ2] + "/0_data");
		GZIPInputStream gis = new GZIPInputStream(fis);
		BufferedInputStream fin = new BufferedInputStream(gis);
		// InputStreamReader reader = new InputStreamReader(gis);
		// BufferedReader fin = new BufferedReader(reader);
		byte buffer[] = new byte[1024 * 1024];
		int count[] = { 2161, 12660, 12988, 13541, 14465, 15634, 13273, 11353 };
		for (int i : count) {
			int bytes = fin.read(buffer, 0, i);
			System.out.println(new String(buffer, 0, 500));
			System.out
					.println("------------------------------------------------------------");
		}
	}

}
