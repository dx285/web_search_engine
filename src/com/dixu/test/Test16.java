package com.dixu.test;

import java.util.Arrays;

public class Test16 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String str[] = {"a 0 1","abw 0 1","ac 21 1","2d 4 1","ak 0 1"};
//		Arrays.sort(str);
//		for(String s: str){
//			System.out.println(s);
//		}
		
		
		Thread thread = new Thread(){

			@Override
			public void run() {
				System.out.println("dd");
			}			
		};
		thread.start();
		//thread.start();
		
	}

}
