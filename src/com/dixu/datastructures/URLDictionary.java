package com.dixu.datastructures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dixu.pojo.URL;



public class URLDictionary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8617093081432457959L;

	private static URLDictionary instance = new URLDictionary();

	private List<URL> urls;

	private double avgDocLen = 0.0;

	private URLDictionary() {
		urls = new ArrayList<URL>(1024);
	}

	public List<URL> getUrls() {
		return urls;
	}

	/**
	 * 
	 * @param id
	 * @return The String whose id is <i>id</i>
	 */
	public URL getURLByID(int id) {
		if (id < urls.size())
			return urls.get(id);
		else
			return null;
	}

	/**
	 * 
	 * @param url
	 *            The url String
	 * @param length
	 *            The number of terms this url contains
	 * @return the id of new url
	 */
	public int addURL(String url, int length) {
		URL newURL = new URL(url, length);
		urls.add(newURL);
		newURL = null;
		return urls.size() - 1;
	}

	public int addURL(URL url) {
		urls.add(url);
		return urls.size() - 1;
	}
	
	public static URLDictionary getInstance() {
		return instance;
	}

	public static void setInstance(URLDictionary instance) {
		URLDictionary.instance = instance;
	}

	public int size() {
		return this.urls.size();
	}

	// notice that you should close it after you save it to hard drive
	public void close() {
		urls.clear();
		urls = null;
	}

	public double getAvgDocLen() {
		return avgDocLen;
	}

	public void setAvgDocLen(double avgDocLen) {
		this.avgDocLen = avgDocLen;
	}

}
