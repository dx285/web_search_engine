package com.dixu.test;

class MyThread extends Thread {

	@Override
	public void run() {
		int i = 0;
		for (;;) {
			i++;
			i--;
			System.out.println("i: "+i);
		}
	}

}

public class PerformanceTest {

	/**
	 * @param args
	 */



	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			MyThread t = new MyThread();
			t.start();
		}

	}

}
