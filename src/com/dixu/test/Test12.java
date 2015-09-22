package com.dixu.test;

import com.dixu.utils.GCThread;

public class Test12 {

	/**
	 * @param args
	 */
	
	
	public static void main(String[] args) {
		GCThread t = new GCThread();
		int dd[] = new int[200*1024*1024];
		dd = null;
		t.start();
		while (true) {
			try {
				Thread.sleep(1000);
				System.gc();
				System.runFinalization();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

	}

}
