package com.dixu.utils;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class GZipHelper {

	private int mode = 0;
	public final static int SMALLSET = 0, NZ2 = 1, NZ10 = 2, NZALL = 3;
	private String dir;
	private String DATAFILE_PATTERN[] = { "%s/down1_%d.gz", "%s/%d_data", "%s/%d_data", "%s/%d_data" };
	private String INDEXFILE_PATTERN[] = { "%s/down1_%d_index.gz", "%s/%d_index", "%s/%d_index", "%s/%d_index" };

	// The start and end number of gzip files of each data sets
	private int pos[][] = { { 1800, 1900 }, { 0, 83 }, { 0, 414 }, { 0, 4179 } };

	private int cur_pos = 0;

	public GZipHelper(int mode, String dir) {
		this.mode = mode;
		this.dir = dir;
		cur_pos = pos[mode][0];
	}

	//Returns whether next data file input is existed
	public boolean hasNext() {
		return cur_pos < pos[mode][1];
	}

	// This method returns an array containing next data input stream in the first
	// place and following its relative index input stream
	public InputStream[] next() {
		InputStream[] ret = new InputStream[2];
		String data_path = String.format(DATAFILE_PATTERN[mode], dir, cur_pos);
		String index_path = String.format(INDEXFILE_PATTERN[mode], dir, cur_pos);
		try {
			GZIPInputStream data_in = new GZIPInputStream(new FileInputStream(data_path));
			GZIPInputStream index_in = new GZIPInputStream(new FileInputStream(index_path));
			ret[0] = data_in;
			ret[1] = index_in;
			cur_pos++;
			data_path = null;
			index_path = null;
			data_in = null;
			index_in = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return ret;
		}
	}

	
	public int count() {
		return cur_pos;
	}

	public int getCur_pos() {
		return cur_pos;
	}

	public void setCur_pos(int cur_pos) {
		this.cur_pos = cur_pos;
	}

}
