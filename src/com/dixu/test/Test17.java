package com.dixu.test;

import java.io.IOException;

import com.dixu.utils.DataManager;

public class Test17 {


	public static void main(String args[]) throws IOException{
		
		DataManager dm = DataManager.getInstance();
		dm.readURLDict();
		System.in.read();

		
	}
	
}


