
package com.dixu.utils;

import java.util.Arrays;

import com.dixu.pojo.Tuple;

public class SortThread extends Thread {

	private Tuple list[];
	private int start, end;
	private int intList[];

	/**
	 * This variable is used for testing validity of the thread and should be
	 * set to false normally
	 */

	private boolean isTested = false;

	public volatile boolean isFinished;

	public SortThread(Tuple list[], int start, int end) {
		super();
		this.list = list;
		this.start = start;
		this.end = end;
	}

//	// for test
//	public SortThread(int list[], int start, int end) {
//		super();
//		this.intList = list;
//		this.start = start;
//		this.end = end;
//	}

	/**
	 * It sorts the array from the <i>start</i> to <i>end</i>, you should notice
	 * that the <i>end</i> position is exclusive.
	 */
	@Override
	public void run() {
		isFinished = false;
		if (!isTested)
			Arrays.sort(list, start, end);
		else
			Arrays.sort(intList, start, end);
		isFinished = true;
		
		//why set null after sort
		list = null;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public Tuple[] getList() {
		return list;
	}

	public void setList(Tuple[] list) {
		this.list = list;
	}
}
