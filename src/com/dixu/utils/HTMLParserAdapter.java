package com.dixu.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author dixu
 * Due to the considering of performance, this class is deprecated and switched to <i>parsepage(String doc)</i> in the HTMLParser.java
 */

@Deprecated
public class HTMLParserAdapter {

	public static List<String> getWords(String html) {
		List<String> ret = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		HTMLParser.parsePage(null, html, sb);
		String[] words = sb.toString().split("\n");
		for (String word : words) {
			String[] segments = word.split(" ");
			ret.add(segments[0]);
		}
		words = null;
		sb = null;
		return ret;
	}

}
