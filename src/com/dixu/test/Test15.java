package com.dixu.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dixu.utils.SortThread;

public class Test15 {
	public static void main(String[] args) {
		List lists[] = new List[8];
//		Random rand = new Random();
//		for (int i = 0; i < 8; i++) {
//			lists[i] = new ArrayList(10000000);
//			for (int j = 0; j < 10000000; j++) {
//				lists[i].add(rand.nextInt(10000000));
//			}
//		}

		SortThread threads[] = new SortThread[8];
		for (int i = 0; i < 8; i++) {
			threads[i] = new SortThread(lists, 1,1);
			threads[i].start();
		}
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = 0; i < 8; i++)
				if (threads[i].isFinished == true)
					System.out.println("Thread " + i + " is finished");
				else
					flag = true;
		}
	}

}
