package com.dixu.datastructures;

public class Heap<T extends Comparable> {

	private Object data[];

	private int capacity = 100;

	private int size = 0;

	private int org[];

	public Heap() {
		data = new Object[capacity];
		org = new int[capacity];
	}

	public Heap(int capacity) {
		this.capacity = capacity;
		data = new Object[capacity];
		org = new int[capacity];
	}

	/**
	 * 
	 * @param t
	 *            The element to add
	 * @param origin
	 *            which array this element is extracted from
	 */
	public void add(T t, int origin) {
		data[size] = t;
		org[size] = origin;
		size++;
		swim();
	}

	/**
	 * This method returns the top element in the heap, or null if it is empty
	 * 
	 * @return T topElement
	 */
	public T getTop() {
		return size > 0 ? (T) data[0] : null;
	}

	/**
	 * This method simply pops the top element and returns with the number
	 * indicates which queue it is from You should call getTop() at first then
	 * call pop() if you want to call routine T pop()
	 * 
	 * @return
	 */
	public int pop() {
		if (size == 0)
			return -1;
		size--;
		data[0] = data[size];
		data[size] = null;

		int ret = org[0];
		org[0] = org[size];
		sink();
		return ret;
	}

	/**
	 * When adding an element, place it in the last position, and call this
	 * method to maintain heap property
	 */
	private void swim() {
		if (size == 0)
			return;
		int sonPtr = size - 1;
		while (sonPtr != 0) {
			int fatherPtr = (sonPtr - 1) >> 1;
			T son = (T) data[sonPtr];
			T father = (T) data[fatherPtr];
			int res = father.compareTo(son);
			if (res <= 0)
				return;
			// swap father and son to keep heap nature
			Object temp = data[sonPtr];
			data[sonPtr] = data[fatherPtr];
			data[fatherPtr] = temp;

			int t = org[sonPtr];
			org[sonPtr] = org[fatherPtr];
			org[fatherPtr] = t;

			sonPtr = fatherPtr;
			son = null;
			father = null;
			temp = null;
		}
	}

	/**
	 * When poping out an element, replace it with the last element, and call
	 * sink() to maintain heap property
	 */
	private void sink() {
		if (size == 0)
			return;
		int fatherPtr = 0;
		T leftSon, rightSon, father;
		while (fatherPtr < size) {
			int rightSonPtr = (fatherPtr + 1) << 1, leftSonPtr = rightSonPtr - 1;
			leftSon = leftSonPtr < size ? (T) data[leftSonPtr] : null;
			rightSon = rightSonPtr < size ? (T) data[rightSonPtr] : null;
			father = (T) data[fatherPtr];
			if (leftSon != null) {
				int sonToSwapPtr = leftSonPtr;
				if (rightSon != null && rightSon.compareTo(leftSon) < 0) {
					sonToSwapPtr = rightSonPtr;
				}
				if (father.compareTo((T) data[sonToSwapPtr]) > 0) {
					Object temp = data[fatherPtr];
					data[fatherPtr] = data[sonToSwapPtr];
					data[sonToSwapPtr] = temp;

					int t = org[sonToSwapPtr];
					org[sonToSwapPtr] = org[fatherPtr];
					org[fatherPtr] = t;

					fatherPtr = sonToSwapPtr;
				} else
					break;
			} else
				break;
		}
		leftSon = null;
		rightSon = null;
		father = null;

	}

	public void clear() {
		if (data != null) {
			for (int i = 0; i < data.length; i++)
				data[i] = null;
		}
		if (org != null) {
			for (int i = 0; i < org.length; i++)
				org[i] = 0;
		}
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public Object[] getData() {
		return data;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

	public int[] getOrg() {
		return org;
	}

	public void setOrg(int[] org) {
		this.org = org;
	}

}
