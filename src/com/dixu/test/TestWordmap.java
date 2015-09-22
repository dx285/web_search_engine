package com.dixu.test;

import com.dixu.datastructures.WordMap;
import com.dixu.utils.DataManager;

public class TestWordmap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WordMap map = DataManager.getInstance().readWordMap();
		String d[] = { "0", "00", "000", "0000", "00000", "000000", "0000000", "000007" };
		for (String s : d) {
			System.out.println(map.getIdByWord(s));
		}
		System.out.println(map.getKeySets().size());

	}

}
