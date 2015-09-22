package com.dixu.test;

import java.util.List;

import com.dixu.utils.HTMLParserAdapter;

public class Test4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = HTMLParserAdapter.getWords("<http><body>&nbsp;&nbsp;</body></http>");
		for(String s : list)
			System.out.println(s);
		
	}

}
