package com.dixu.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.dixu.utils.MyProperties;

public class OrderedFileEnsure {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(
				MyProperties.TUPLES_COMBINED_FILE)));
		String line;
		int oldId = -10000, oldDocId = -10000;
		while ((line = fin.readLine()) != null) {
			String e[] = line.split(" ");
			int id = Integer.parseInt(e[0]), docId = Integer.parseInt(e[1]);
			assert (id > oldId || (id == oldId && docId >= oldDocId));
			System.out.println("id:"+id+"  docid: "+docId);
			oldId = id;
			oldDocId = docId;
		}
		fin.close();

	}

}
