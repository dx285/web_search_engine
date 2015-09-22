package com.dixu.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.dixu.datastructures.URLDictionary;
import com.dixu.datastructures.WordMap;
import com.dixu.pojo.URL;


public class DataManager {

	private DataManager() {

	}

	private static DataManager instance = new DataManager();

	public static DataManager getInstance() {
		return instance;
	}

	/**
	 * This method calculates the average doc length of all docs should be
	 * called after analyzed all docs
	 */
	public void calculateAverageLength() {
		URLDictionary uDict = URLDictionary.getInstance();
		long sum = 0;
		for (URL u : uDict.getUrls()) {
			sum += u.getLength();
		}
		double avg = sum;
		avg /= (double) uDict.size();
		uDict.setAvgDocLen(avg);
		System.out.format("The average document length is %.2f\n", avg);
	}

	// Save an object to file <i>path</i>
	private boolean saveObject2File(String path, Object obj) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			oos = null;
			fos = null;
			bos = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Read an object from file <i>path</i>
	private Object readObjectFromFile(String path) {
		Object ret = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			ObjectInputStream ois = new ObjectInputStream(bis);
			ret = ois.readObject();
			fis.close();
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ret;
	}

	// Save the URL Dictionary to hard drive
	public boolean saveURLDict() {
		return this.saveObject2File(MyProperties.URL_DICTIONARY_FILE, URLDictionary.getInstance());
	}
	
	public boolean saveWordMap() {
		return this.saveObject2File(MyProperties.WORD_MAP_FILE, WordMap.getInstance());
	}

	// Read the URL Dictionary from hard drive
	public URLDictionary readURLDict() {
		URLDictionary ret = null;
		ret = (URLDictionary) this.readObjectFromFile(MyProperties.URL_DICTIONARY_FILE);
		return ret;
	}

	public WordMap readWordMap() {
		return (WordMap) this.readObjectFromFile(MyProperties.WORD_MAP_FILE);
	}

}
