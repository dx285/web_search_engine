package com.dixu.utils;



public class MyProperties {
	/**
	 * This is the collections of constants used in the program You should
	 * carefully change them
	 */

	public static final String DATASETS_BASE_PATH = "/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/";

	public static final String DATA_BASE_PATH = "/Users/xdrm/Documents/Java101/Pro2_Inverted_Indexing/";

	public static final String NZ[] = { DATASETS_BASE_PATH + "smallset/datset", DATASETS_BASE_PATH + "nz2/nz2_merged",
			DATASETS_BASE_PATH + "nz10/nz10_merged", DATASETS_BASE_PATH + "nz/" };

	public static final String TUPLES_FILE = DATA_BASE_PATH + "TUPLES.DAT";

	public static final String WORD_MAP_FILE = DATA_BASE_PATH + "WORD_MAP.DAT";

	public static final String URL_DICTIONARY_FILE = DATA_BASE_PATH + "URL_DICT.DAT";

	public static final String TUPLES_SORT_TEMP_FILE_PREFIX = DATA_BASE_PATH + "TUPLES_TEMP_";

	public static final String TUPLES_COMBINED_FILE = DATA_BASE_PATH + "TUPLES_TOTAL.DAT";

	public static final String LOG_FILE = DATA_BASE_PATH + "LOG_FILE.DAT";

	public static final String LEXICON_STRUCTURE_TABLE_FILE = DATA_BASE_PATH + "LEXICON_FILE.DAT";

	public static final String INVERTED_INDEX_FILE = DATA_BASE_PATH + "INVERTED_INDEX_FILE.DAT";

	public static final String SNIPPETS_FILE = DATA_BASE_PATH + "SNIPPETS_FILE.DAT";

}
