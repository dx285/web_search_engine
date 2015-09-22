package com.dixu.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test8 {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("add");
		list.add("adde");
		list.add("addx");
		list.add("armani");
		list.add("armadillo");
		Collections.sort(list);
		for(String s : list){
			System.out.println(s);
		}

	}

}
