package com.example.angleplace.db;

import android.provider.BaseColumns;

public class WildFishingContract {

	// µöµã
	public static abstract class Places implements BaseColumns {
        public static final String TABLE_NAME = "places";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DETAIL = "detail";
        public static final String COLUMN_NAME_FILE_NAME = "file_path";
    }
}
