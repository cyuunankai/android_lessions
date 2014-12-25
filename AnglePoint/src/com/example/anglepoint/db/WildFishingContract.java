package com.example.anglepoint.db;

import android.provider.BaseColumns;

public class WildFishingContract {

	// ����
	public static abstract class Points implements BaseColumns {
        public static final String TABLE_NAME = "points";
        // ��λID
        public static final String COLUMN_NAME_PLACE_ID = "place_id";
        // �ͳ�ID
        public static final String COLUMN_NAME_ROD_LENGTH_ID = "rod_length_id";
        // ˮ��
        public static final String COLUMN_NAME_DEPTH = "depth";
        // ����ID
        public static final String COLUMN_NAME_LURE_METHOD_ID = "lure_method_id";
        // ����ID
        public static final String COLUMN_NAME_BAIT_ID = "bait_id";
    }
	
	// �ͳ�
	public static abstract class RodLengths implements BaseColumns {
        public static final String TABLE_NAME = "rod_lengths";
        public static final String COLUMN_NAME_NAME = "name";
    }
	
	// ����
	public static abstract class LureMethods implements BaseColumns {
        public static final String TABLE_NAME = "lure_methods";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DETAIL = "detail";
    }
	
	// ����
	public static abstract class Baits implements BaseColumns {
        public static final String TABLE_NAME = "baits";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DETAIL = "detail";
    }
}
