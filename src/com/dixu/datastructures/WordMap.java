package com.dixu.datastructures;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class WordMap implements Serializable {

	private static final long serialVersionUID = 1185821074258639306L;

	private static int capacity = 1024 * 1024;

	private static WordMap instance = new WordMap();

	private HashMap<String, Integer> map;

	private WordMap() {
		map = new HashMap<String, Integer>(capacity);
	}

	public static WordMap getInstance() {
		return instance;
	}

	public void addWord(String word, int id) {
		map.put(word, Integer.valueOf(id));
	}

	public void addWord(String word) {
		addWord(word, 0);
	}

	public boolean containsWord(String word) {
		return map.containsKey(word);
	}

	public static int getCapacity() {
		return capacity;
	}

	public static void setCapacity(int capacity) {
		WordMap.capacity = capacity;
	}

	public static void setInstance(WordMap instance) {
		WordMap.instance = instance;
	}

	public int size() {
		return this.map.keySet().size();
	}

	public Set<String> getKeySets() {
		return map.keySet();
	}

	public int getIdByWord(String word) {
		return map.get(word);
	}

	public Map<String, Integer> getMap() {
		return map;
	}

	// ensure you close it after you save it to hard drive
	public void close() {
		map.clear();
		map = null;
	}

}
