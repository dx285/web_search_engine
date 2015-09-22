package com.dixu.query;

import com.dixu.datastructures.LexiconDictionary;
import com.dixu.datastructures.URLDictionary;
import com.dixu.pojo.Lexicon;
import com.dixu.pojo.URL;
import com.dixu.utils.DataManager;

public class QueryProcessor {

	public static QueryProcessor instance;

	private DataManager dm;
	private URLDictionary uDict;
	private LexiconDictionary lDict;

	private QueryProcessor() {
		dm = DataManager.getInstance();
		uDict = dm.readURLDict();
		lDict = LexiconDictionary.getInstance();
	}

	public static QueryProcessor getInstance() {
		if (instance == null)
			instance = new QueryProcessor();
		return instance;
	}

	public URL getURLById(int id) {
		return uDict.getURLByID(id);
	}

	public Lexicon getLexiconByWord(String word) {
		return lDict.getLexicon(word);
	}

}
