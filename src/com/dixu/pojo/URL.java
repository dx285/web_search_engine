package com.dixu.pojo;

import java.io.Serializable;



public class URL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7469268697932748554L;

	private String url;

	private long startFrom; // The starting length of terms in the url in the
							// snippets file

	private long endwith; // The end length of terms but exclusive

	private int length; // number of terms in the page

	public URL(String url, int length) {
		this.url = url;
		this.length = length;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getStartFrom() {
		return startFrom;
	}

	public void setStartFrom(long startFrom) {
		this.startFrom = startFrom;
	}

	public long getEndwith() {
		return endwith;
	}

	public void setEndwith(long endwith) {
		this.endwith = endwith;
	}

}
