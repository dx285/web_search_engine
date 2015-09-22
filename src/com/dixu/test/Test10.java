package com.dixu.test;

import java.util.Random;

import com.dixu.datastructures.Heap;
import com.dixu.pojo.Tuple;

public class Test10 {
	public static void main(String[] args) {
		Heap<Tuple> heap = new Heap<Tuple>();
		for (int i = 1; i <= 10; i++) {
			Tuple t = new Tuple("" + (10 - i), new Random().nextInt(100), new Random().nextInt(10));
			heap.add(t, 0);
		}
		Tuple t = new Tuple("abd", 12, 01);
		heap.add(t,0);
		t = new Tuple("abd", 22, 01);
		heap.add(t,0);
		t = new Tuple("dwq",21,2);
		heap.add(t,0);
		t = new Tuple("xiw",32,1);
		heap.add(t,0);
		t = new Tuple("xiw",2,2);
		heap.add(t,0);
		while ((t = heap.getTop()) != null) {
			System.out.format("%s %d %d\n", t.getWord(), t.getDocId(), t.getOccurPos());
			heap.pop();
		}
	}
}
