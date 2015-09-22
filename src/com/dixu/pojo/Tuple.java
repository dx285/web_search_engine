package com.dixu.pojo;

public class Tuple implements Comparable {

	// Word
	private String word;
	//docId: total urls number
	// Doc ID
	private int docId;

	// The occurrence position of this word in the doc
	private int occurPos;

	public Tuple(String word, int docId, int occurPos) {
		super();
		this.word = word;
		this.docId = docId;
		this.occurPos = occurPos;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getOccurPos() {
		return occurPos;
	}

	public void setOccurPos(int occurPos) {
		this.occurPos = occurPos;
	}

	@Override
	public int compareTo(Object obj) {
		Tuple tuple = (Tuple) obj;
		int ret = this.word.compareTo(tuple.word);
		if (ret != 0)
			return ret;
		if (this.docId < tuple.docId)
			return -1;
		else if (this.docId == tuple.docId) {
			if (this.occurPos < tuple.occurPos)
				return -1;
			else
				return 1;
		} else
			return 1;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		word = null;
	}

}
