package com.dixu.test;

import java.io.IOException;
import java.util.HashMap;


public class Test2 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < 100; i++) {
			map.put(new String("apple"), i);
			System.out.println(map.get("apple"));
		}
		
		System.out.println("Done");
		System.in.read();
	}

}
