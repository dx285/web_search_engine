package com.dixu.preparation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dixu.datastructures.URLDictionary;
import com.dixu.datastructures.WordMap;
import com.dixu.pojo.URL;
import com.dixu.utils.DataManager;
import com.dixu.utils.GZipHelper;
import com.dixu.utils.HTMLParser;
import com.dixu.utils.MyProperties;


public class NZAnalyzer {

	public static String sep = "\r\n\r\n";

	// The number of status column in different data sets
	public static byte STATUS_POS[] = { 4, 6, 6, 6 };

	// The data sets used as data inputs
	public static byte mode = GZipHelper.NZ2;

	// The size of input buffer
	public static int buffer_size = 16384;

	public static int numberOfTuples = 0;

	public static int numberOfPages = 0;

	public static void main(String[] args) throws Exception {
		PrintStream log = System.out;
		// BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new
		// FileOutputStream(MyProperties.LOG_FILE)));
		
		//initialize part start
		long before = System.currentTimeMillis();
		GZipHelper helper = new GZipHelper(mode, MyProperties.NZ[mode]);
		byte buffer[] = new byte[20 * 1024 * 1024];
		List<String> wordList = new ArrayList<String>(1000);
		WordMap wordMap = WordMap.getInstance();
		URLDictionary urlDict = URLDictionary.getInstance();
		// GCThread gc_thread = new GCThread();
		// gc_thread.start();
		long countOfSnippetLength = 0;
		InputStream[] iss;
		BufferedInputStream data;
		BufferedReader index;
		String line;
		String[] elem;
		StringBuilder url;
		String contents = null;
		String html = null;
		BufferedWriter tuple_out = new BufferedWriter(new FileWriter(MyProperties.TUPLES_FILE));
		BufferedWriter snippet_out = new BufferedWriter(new FileWriter(MyProperties.SNIPPETS_FILE));
		//initialize end
		
		//read from unpack file from memory
		outer: while (helper.hasNext()) {
			log.print("Processing File " + helper.count() + "\n");
			iss = helper.next();
			data = new BufferedInputStream(iss[0], buffer_size);
			index = new BufferedReader(new InputStreamReader(iss[1]), buffer_size);
			inner: while (true) {
				// Processing indexes
				//URL link indexes
				line = index.readLine();
				if (line == null || "".equals(line))
					break inner;
				elem = line.split(" ");
				url = new StringBuilder();
				int length = 0;
				switch (mode) {
				case GZipHelper.SMALLSET:
					// Url info in smallset consist of two parts: base_url in
					// the 0 place and others in the 3 place
					url.append(elem[0]);
					//why not 2????
					url.append(elem[3]);
					// Page length in the 5 place
					length = Integer.parseInt(elem[5]);
					if (length == 0)
						continue;
					break;
				case GZipHelper.NZ2:
				case GZipHelper.NZ10:
				case GZipHelper.NZALL:
					url.append(elem[0]);
					try {
						length = Integer.parseInt(elem[3]);
					} catch (Exception e) {
						continue outer;
					}
					break;
				}
				// Parser header and html codes
				//processing html
				try {
					//length = elem[3]
					data.read(buffer, 0, length);
					if (!"ok".equals(elem[STATUS_POS[mode]]))
						continue inner;
					contents = new String(buffer, 0, length);
					int pos = contents.indexOf(sep) + sep.length();
					String http_code = contents.substring(0, 12);
					if (!http_code.endsWith("200"))
						continue inner;
					html = contents.substring(pos, contents.length());
					if (html.length() < 10)
						continue inner;
					// Get words from parser
					HTMLParser.parsePage(html, wordList);
					http_code = null;
					line = null;
					elem = null;
					contents = null;
					html = null;
				} catch (Exception e) {
					e.printStackTrace();
					// System.out.println("Length: " + length);
					continue inner;
				}

				// for test
				/*
				 * System.out.println(url.toString()); for (String s : wordList)
				 * System.out.print(s + " "); System.out.println();
				 * Thread.sleep(10000);
				 */
				// end test

				// Save words and url to container
				URL u = new URL(url.toString(), wordList.size());
				u.setStartFrom(countOfSnippetLength);
				// Writing words in this url to snippets data file
				for (String wd : wordList) {
					snippet_out.write(wd);
					snippet_out.write(' ');
					countOfSnippetLength += wd.length() + 1;
				}
				u.setEndwith(countOfSnippetLength);
				
				//length of urlDict
				int docId = urlDict.addURL(u);
				// if(docId==18243){
				// System.out.println();
				// }
				numberOfPages++;
				
				for (int i = 0; i < wordList.size(); i++) {
					String word = wordList.get(i);
					if (!wordMap.containsWord(word))
						wordMap.addWord(word);
					// Write out to tuples file
					//docId: all docs length
					//i: docs pos where word appears
					tuple_out.write(String.format("%s %d %d\n", word, docId, i));
					numberOfTuples++;
					word = null;
				}
				wordList.clear();
				url = null;
			}
			data.close();
			index.close();
			data = null;
			index = null;
			iss = null;
		}

		helper = null;
		buffer = null;
		// Calculating time elapsed
		long now = System.currentTimeMillis();
		int seconds = (int) ((now - before) / 1000);
		float speed = numberOfPages / (float) (seconds);
		tuple_out.close();
		snippet_out.close();

		// Save word dict and url dict to disk
		DataManager manager = DataManager.getInstance();
		manager.calculateAverageLength();
		//this part is confusing
		log.print("Succesfully saved URL Dict?:" + manager.saveURLDict() + "\n");
		log.print("URL Dict size: " + URLDictionary.getInstance().size() + "\n");

		// Sort words
		//overwrite wordMap above
		Set<String> set = wordMap.getKeySets();
		wordList = new ArrayList<String>(set);
		java.util.Collections.sort(wordList);
		for (int i = 0; i < wordList.size(); i++) {
			wordMap.addWord(wordList.get(i), i);
		}
		set = null;
		log.print("Succesfully saved WordMap?:" + manager.saveWordMap() + "\n");
		log.print("WordMap size: " + WordMap.getInstance().size() + "\n");
		log.print(String.format("Writeout tuples: %d\n", numberOfTuples));
		log.print(String.format("Parsed %d pages in %d seconds, speed is %.2f pages/sec\n", numberOfPages, seconds,
				speed));

		// gc_thread.stopIt();
		wordMap.close();
		urlDict.close();
	}
}
