package com.dixu.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.dixu.utils.MyProperties;

public class Test13 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Reader fin[] = new Reader[10];
		try {

			for (int i = 0; i < 10; i++) {
				fin[i] = new BufferedReader(new InputStreamReader(new FileInputStream(
						MyProperties.TUPLES_SORT_TEMP_FILE_PREFIX + i)));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
