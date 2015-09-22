package com.dixu.preparation;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Pattern;

import java.lang.ref.WeakReference;

import com.dixu.datastructures.Heap;
import com.dixu.pojo.Tuple;
import com.dixu.utils.MyProperties;
import com.dixu.utils.Normal;
import com.dixu.utils.SortThread;
import com.dixu.utils.VarByte;

public class MergeSort {

	private int numberOfWays = 10; // This number should be larger than 20 to
									// ensure enough memory space

	private int MAX_NUMBER_OF_TUPLES = 40000000;
	private volatile Tuple[] list = new Tuple[MAX_NUMBER_OF_TUPLES];
	private int count = 0, countOfTempFile = 0;
	private BufferedOutputStream bos;
	private BufferedWriter bw;
	private long countOfBytes = 0;
	private int countOfTuples = 0;
	private int numberOfSortThreads = 16;
	private List<Integer> posList;
	private Pattern p = Pattern.compile(" ");

	public MergeSort(int numberOfWays) {
		this.numberOfWays = numberOfWays;
	}

	public MergeSort() {

	}

	public static void main(String args[]) throws Exception {
		long before = System.currentTimeMillis();
		MergeSort msort = new MergeSort();
		msort.sort();
		long now = System.currentTimeMillis();
		System.out.format("The program splits, sorts, and merges files in %d seconds\n", (now - before) / 1000);
	}

	/**
	 * The <i>sort</i> method should be called before NZAnalyzer finished
	 * running It first split the tuples file into <b>numberOfWays</b> parts
	 * Call the srw() to do multi-threaded sorting this part, then write back to
	 * hard drive Finally it uses a heap to merge all the files with compression
	 */

	public void sort() {
		System.out.format("Start %d way merge sort.\n", numberOfWays);
		// GCThread thread = new GCThread();
		// thread.start();
		split();
		mergeSort();
		// thread.stop();

	}

	public void mergeSort() {
		System.out.println("Merging");
		Heap<Tuple> heap = new Heap<Tuple>(numberOfWays);
		List<Tuple> tempList = new ArrayList<Tuple>();
		posList = new ArrayList<Integer>();
		try {
			BufferedReader fin[] = new BufferedReader[numberOfWays];
			//what's the purpose of two file output???
			// Inverted index file output
			bos = new BufferedOutputStream(new FileOutputStream(MyProperties.INVERTED_INDEX_FILE));
			// Lexicon Structure table file output
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
					MyProperties.LEXICON_STRUCTURE_TABLE_FILE)));
			// Initiating input temp data parts and heap
			for (int i = 0; i < numberOfWays; i++) {
				fin[i] = new BufferedReader(new InputStreamReader(new FileInputStream(
						MyProperties.TUPLES_SORT_TEMP_FILE_PREFIX + i)));
				String line = fin[i].readLine();
				String[] elem = p.split(line);
				Tuple t = new Tuple(new String(elem[0]), Integer.parseInt(elem[1]), Integer.parseInt(elem[2]));
				elem = null;
				line = null;
				heap.add(t, i);
			}
			/**
			 * This iteration keeps popping one element from the heap and insert
			 * it into tempList if tuples having the same word is collected,
			 * compress and save them by calling <i>compressAndSave()</i>
			 */
			Tuple last = null;
			while (true) {
				Tuple top = heap.getTop();
				if (top == null)
					break;
				if (last != null && !top.getWord().equals(last.getWord())) {
					compressAndSave(tempList);
				}

				tempList.add(top);
				last = top;
				int queueId = heap.pop();
				String line = fin[queueId].readLine();
				if (line == null || "".equals(line)) {
					System.out.format("Temp File %d is merged\n", queueId);
					continue;
				}
				// Add another one into heap
				
				//if you don't use heap after this code, why add elem into heap?????
				String[] elem = p.split(line);
				Tuple t = new Tuple(new String(elem[0]), Integer.parseInt(elem[1]), Integer.parseInt(elem[2]));
				heap.add(t, queueId);
				top = null;
				t = null;
				elem = null;
				line = null;

			}//end while
			last = null;
			// Ensure the remains saved
			if (tempList.size() != 0)
				compressAndSave(tempList);
			// Disposing objects
			heap = null;
			bos.close();
			bos = null;
			bw.close();
			bw = null;
			for (int i = 0; i < numberOfWays; i++) {
				fin[i].close();
				fin[i] = null;
			}
			System.out.println("Inverted index file length: " + countOfBytes);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	public void compressAndSave(List<Tuple> tempList) throws IOException {
//		long countOfBytesBefore = countOfBytes;
//		int countOfDocs = 0;
//		int lastDocId = -99999, docIdB4 = -99999;
//		String word = new String(tempList.get(0).getWord());
//		if (tempList.size() == 0)
//			return;
//		/**
//		 * This iteration keeps collecting tuples having the same word as
//		 * ensured before its been called, and the same docId, when collecting
//		 * those having same docId is finished, compress and save them, then
//		 * keep collecting the remains.
//		 */
//		for (Tuple t : tempList) {
//			// Met with different docId, compress and save
//			if (t.getDocId() != lastDocId) {
//				if (lastDocId != -99999) {
//					// If it is the first one, write it out, or write out the
//					// difference.
//					byte temp[];
//					if (docIdB4 == -99999) {
//						temp = VarByte.int2bytes(lastDocId);// DocId
//					} else {
//						temp = VarByte.int2bytes(lastDocId - docIdB4);
//					}
//					bos.write(temp);
//					countOfBytes += temp.length;
//					temp = null;
//					docIdB4 = lastDocId;
//
//					// Frequency is saved only simply by VarByte
//					temp = VarByte.int2bytes(posList.size());// Frequency
//					bos.write(temp);
//					countOfBytes += temp.length;
//					temp = null;
//
//					// Positions are starting an original one and following the
//					// differences
//					temp = VarByte.int2bytes(posList.get(0));
//					bos.write(temp);
//					countOfBytes += temp.length;
//					temp = null;
//					for (int i = 1; i < posList.size(); i++) {
//						temp = VarByte.int2bytes(posList.get(i));
//						bos.write(temp);
//						countOfBytes += temp.length;
//						temp = null;
//					}
//
//				}
//				countOfDocs++;
//				lastDocId = t.getDocId();
//				posList.clear();
//			}
//			posList.add(Integer.valueOf(t.getOccurPos()));
//		}
//		// Ensure remains safely saved
//		byte temp[];
//		if (docIdB4 == -99999) {
//			temp = VarByte.int2bytes(lastDocId);// DocId
//		} else {
//			temp = VarByte.int2bytes(lastDocId - docIdB4);
//		}
//		bos.write(temp);
//		countOfBytes += temp.length;
//		temp = null;
//		docIdB4 = lastDocId;
//
//		// Frequency is saved only simply by VarByte
//		temp = VarByte.int2bytes(posList.size());// Frequency
//		bos.write(temp);
//		countOfBytes += temp.length;
//		temp = null;
//
//		// Positions are starting an original one and following the
//		// differences
//		temp = VarByte.int2bytes(posList.get(0));
//		bos.write(temp);
//		countOfBytes += temp.length;
//		temp = null;
//		for (int i = 1; i < posList.size(); i++) {
//			temp = VarByte.int2bytes(posList.get(i));
//			bos.write(temp);
//			countOfBytes += temp.length;
//			temp = null;
//		}
//		bw.write(word);
//		bw.write(' ');
//		bw.write(String.format("%d %d", countOfBytesBefore, countOfDocs));
//		bw.write('\n');
//		tempList.clear();
//		posList.clear();
//		word = null;
//	}

	/**
	 * This method splits the tuples big data file into smaller part files and
	 * sorts them.
	 */
	public void split() {
		System.out.format("Spliting large data file to %d piece of files.\n", numberOfWays);
		File tuplesFile = new File(MyProperties.TUPLES_FILE);
		long length = tuplesFile.length();
		long lengthPerWay = length / numberOfWays;
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader fin;

		try {
			// Initiate data input
			fis = new FileInputStream(tuplesFile);
			isr = new InputStreamReader(fis);
			fin = new BufferedReader(isr);

			while (true) {
				// Reading part of file from hard drive allocated to each way
				String line = fin.readLine();
				if (line == null)
					break;
				//why +1 ? blank?
				count += line.length() + 1;
				String elem[] = p.split(line);
				Tuple t = new Tuple(new String(elem[0]), Integer.parseInt(elem[1]), Integer.parseInt(elem[2]));
				list[countOfTuples++] = t;
				if (count >= lengthPerWay) {
					// This part reading is done, sort the array, reset
					// variables and write out array to a temp data file for
					// multi-way sorting
					srw();
					System.gc();
				}

				t = null;
				elem = null;
				line = null;
			}
			srw();
			fis = null;
			isr = null;
			fin.close();
			fin = null;
			list = null;
			countOfTuples = 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void srw() throws FileNotFoundException, IOException {
		// for test case
		// System.out.println("SRW");
		// Heap<Tuple> h = new Heap<Tuple>(countOfTuples);
		// for (int i = 0; i < countOfTuples; i++)
		// h.add(list[i], 0);
		// Tuple last = null;
		// while (h.getTop() != null) {
		// Tuple t = h.getTop();
		// h.pop();
		// if (last == null || t.compareTo(last) >= 0) {
		// last = t;
		// } else {
		// System.out.println("Something wrong here: " + last.getWord() + " " +
		// t.getWord());
		// }
		// }

		// Distribute different part to each sort thread
		int interval = countOfTuples / numberOfSortThreads;
		SortThread threads[] = new SortThread[numberOfSortThreads];
		for (int i = 0; i < numberOfSortThreads - 1; i++)
			threads[i] = new SortThread(list, interval * i, interval * (i + 1));
		threads[numberOfSortThreads - 1] = new SortThread(list, interval * (numberOfSortThreads - 1), countOfTuples);
		// Starting sort threads
		startAll(threads);
		
		//after multiple sorting
		int ptr[] = new int[numberOfSortThreads];
		int maxPtr[] = new int[numberOfSortThreads];
		for (int i = 0; i < numberOfSortThreads; i++) {
			ptr[i] = interval * i + 1;
			maxPtr[i] = interval * (i + 1) - 1;
		}
		maxPtr[numberOfSortThreads - 1] = countOfTuples - 1;

		// Start merging and outputting
		FileOutputStream fos = new FileOutputStream(MyProperties.TUPLES_SORT_TEMP_FILE_PREFIX + countOfTempFile);
		OutputStreamWriter osr = new OutputStreamWriter(fos);
		BufferedWriter fout = new BufferedWriter(osr);
		countOfTempFile++;
		Heap<Tuple> heap = new Heap<Tuple>(numberOfSortThreads);
		for (int i = 0; i < numberOfSortThreads; i++) {
			heap.add(list[interval * i], i);
		}
		
		// int cnt = 0;
		while (!heap.isEmpty()) {
			Tuple tuple = heap.getTop();
			int from = heap.pop();
			if (ptr[from] <= maxPtr[from]) {
				try {
					heap.add(list[ptr[from]], from);
					list[ptr[from]] = null;
					ptr[from]++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// for test
			// if (cnt==758)
			// System.out.print(" ");
			fout.write(tuple.getWord());
			fout.write(' ');
			String o = String.format("%d %d", tuple.getDocId(), tuple.getOccurPos());
			fout.write(o);
			fout.write('\n');
			// System.out.print(o);
			o = null;
			tuple = null;
			// cnt++;
		}

		// Disposing objects
		heap.clear();
		ptr = null;
		maxPtr = null;
		for (int i = 0; i < numberOfSortThreads; i++)
			threads[i] = null;
		countOfTuples = 0;
		fout.close();
		fos = null;
		fout = null;
		System.out.format("Part %d/%d is sorted and written out, total %d bytes\n", countOfTempFile, numberOfWays,
				count);
		count = 0;
	}

	/**
	 * <i>startAll()</i> starts all sort thread and keeps asking their sorting
	 * status until all threads finished sorting
	 */
	public void startAll(SortThread[] threads) {
		boolean flag = true;
		for (SortThread t : threads)
			t.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while (flag) {
			flag = false;
			for (SortThread t : threads)
				if (!t.isFinished)
					flag = true;
		}

		// ensure ordered for test
		// System.out.println("StartAll");
		// for (SortThread s : threads) {
		//
		// for (int i = s.getStart() + 1; i < s.getEnd(); i++)
		// if (tt[i - 1].getWord().compareTo(tt[i].getWord()) > 0) {
		// System.out.println("Something is wrong");
		// System.out.format("%s %s\n", tt[i - 1].getWord(), tt[i].getWord());
		// }
		// }

	}

	public void compressAndSave(List<Tuple> tempList) throws IOException {
		long countOfBytesBefore = countOfBytes;
		int countOfDocs = 0;
		//what's the meaning of those numbers??????
		int lastDocId = -99999, docIdB4 = -99999;
		String word = new String(tempList.get(0).getWord());
		if (tempList.size() == 0)
			return;
		/**
		 * This iteration keeps collecting tuples having the same word as
		 * ensured before its been called, and the same docId, when collecting
		 * those having same docId is finished, compress and save them, then
		 * keep collecting the remains.
		 */
		for (Tuple t : tempList) {
			// Met with different docId, compress and save
			if (t.getDocId() != lastDocId) {
				if (lastDocId != -99999) {
					// If it is the first one, write it out, or write out the
					// difference.
					byte temp[];
					if (docIdB4 == -99999) {
						temp = Normal.int2bytes(lastDocId);// DocId
					} else {
						temp = Normal.int2bytes(lastDocId - docIdB4);
					}
//					bos.write(temp);
					countOfBytes += temp.length;
					temp = null;
					docIdB4 = lastDocId;

					// Frequency is saved only simply by VarByte
					//really???
					temp = Normal.int2bytes(posList.size());// Frequency
					bos.write(temp);
					countOfBytes += temp.length;
					temp = null;

					// Positions are starting an original one and following the
					// differences
					temp = Normal.int2bytes(posList.get(0));
					bos.write(temp);
					countOfBytes += temp.length;
					temp = null;
					for (int i = 1; i < posList.size(); i++) {
						temp = Normal.int2bytes(posList.get(i));
						bos.write(temp);
						countOfBytes += temp.length;
						temp = null;
					}

				}
				countOfDocs++;
				lastDocId = t.getDocId();
				posList.clear();
			}
			posList.add(Integer.valueOf(t.getOccurPos()));
		}//end for
		// Ensure remains safely saved
		byte temp[];
		if (docIdB4 == -99999) {
			temp = Normal.int2bytes(lastDocId);// DocId
		} else {
			temp = Normal.int2bytes(lastDocId - docIdB4);
		}
		//bos.write(temp);
		countOfBytes += temp.length;
		temp = null;
		docIdB4 = lastDocId;

		// Frequency is saved only simply by VarByte
		temp = Normal.int2bytes(posList.size());// Frequency
		bos.write(temp);
		countOfBytes += temp.length;
		temp = null;

		// Positions are starting an original one and following the
		// differences
		temp = Normal.int2bytes(posList.get(0));
		bos.write(temp);
		countOfBytes += temp.length;
		temp = null;
		for (int i = 1; i < posList.size(); i++) {
			temp = Normal.int2bytes(posList.get(i));
			bos.write(temp);
			countOfBytes += temp.length;
			temp = null;
		}
		bw.write(word);
		bw.write(' ');
		bw.write(String.format("%d %d", countOfBytesBefore, countOfDocs));
		bw.write('\n');
		tempList.clear();
		posList.clear();
		word = null;
	}

	
}
