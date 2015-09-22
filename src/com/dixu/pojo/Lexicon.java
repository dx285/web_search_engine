package com.dixu.pojo;

public class Lexicon implements Comparable<Lexicon> {

	private String word;

	// This is inclusive
	private long start;

	// This indicates the end length of this lexicon(exclusive)
	private long end;

	//Number of docs which contains this lexicon
	private int numerOfDocs;

	public Lexicon(String word, long start, long end, int numerOfDocs) {
		super();
		this.word = word;
		this.start = start;
		this.end = end;
		this.numerOfDocs = numerOfDocs;
	}

	public Lexicon() {

	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public int getNumerOfDocs() {
		return numerOfDocs;
	}

	public void setNumerOfDocs(int numerOfDocs) {
		this.numerOfDocs = numerOfDocs;
	}

	public int compareTo(Lexicon o) {
		return this.word.compareTo(o.getWord());
	}

}
