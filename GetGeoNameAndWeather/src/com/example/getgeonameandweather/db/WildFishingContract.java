package com.example.getgeonameandweather.db;

import android.provider.BaseColumns;

public class WildFishingContract {

	public static abstract class Weathers implements BaseColumns {
        public static final String TABLE_NAME = "weathers";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_REGION = "region";
        public static final String COLUMN_NAME_MIN_TEMP_C = "min_temp_c";
        public static final String COLUMN_NAME_MAX_TEMP_C = "max_temp_c";
        public static final String COLUMN_NAME_SUNRISE = "sunrise";
        public static final String COLUMN_NAME_SUNSET = "sunset";
    }
    
    public static abstract class WeathersHourly implements BaseColumns {
        public static final String TABLE_NAME = "weathers_hourly";
        public static final String COLUMN_NAME_WEATHER_ID = "weather_id";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_TEMP_C = "temp_c";
        public static final String COLUMN_NAME_WIND_SPEED_KMPH = "wind_speed_kmph";
        public static final String COLUMN_NAME_WIND_DIR_DEGREE = "wind_dir_degree";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_CLOUD_COVER = "cloud_cover";
        public static final String COLUMN_NAME_WEATHER_CODE = "weather_code";
    }
}
