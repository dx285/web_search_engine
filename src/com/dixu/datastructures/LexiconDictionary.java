package com.dixu.datastructures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dixu.pojo.Lexicon;
import com.dixu.utils.MyProperties;

public class LexiconDictionary {

	private static LexiconDictionary instance;
	private List<Lexicon> list;
	//constructor?
	private LexiconDictionary() {
		try {
			//Read lexicons from files
			list = new ArrayList<Lexicon>();
			BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(
					MyProperties.LEXICON_STRUCTURE_TABLE_FILE)));
			File file = new File(MyProperties.INVERTED_INDEX_FILE);
			String line;
			Lexicon last = null;
			while ((line = fin.readLine()) != null) {
				String elem[] = line.split(" ");
				Lexicon l = new Lexicon();
				l.setWord(new String(elem[0]));
				l.setStart(Long.parseLong(elem[1]));
				l.setNumerOfDocs(Integer.parseInt(elem[2]));
				elem = null;
				if (last != null)
					last.setEnd(l.getStart());
				last = l;
				list.add(l);
				l = null;
			}
			last.setEnd(file.length() + 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static LexiconDictionary getInstance() {
		return instance == null ? instance = new LexiconDictionary() : instance;
	}

	public Lexicon getLexicon(String word) {
		Lexicon key = new Lexicon();
		key.setWord(word);
		int res = Collections.binarySearch(list, key);
		if (res < 0)
			return null;
		else
			return list.get(res);
	}

}
