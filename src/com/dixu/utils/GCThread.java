package com.dixu.utils;


public class GCThread extends Thread {

	
	/**
	 *  This thread keeps garbage collection in the background to ensure enough memory
	 */
	
	private int interval = 100;
	
	private volatile boolean flag = true;
	
	public synchronized void stopIt(){
		flag = false;
	}

	@Override
	public void run() {

		while (flag) {
			try {
				Thread.sleep(interval);
				System.gc();
				System.runFinalization();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
