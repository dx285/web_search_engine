package com.dixu.test;

import java.util.Set;

import com.dixu.datastructures.WordMap;


public class Test7 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		WordMap.getInstance().addWord("ddd");
		WordMap.getInstance().addWord("dddf");
		
		Set<String> set = WordMap.getInstance().getKeySets();
		String s[] = new String[0];
		String str[] = set.toArray(s);
		System.out.println(str.length);

	}

}
