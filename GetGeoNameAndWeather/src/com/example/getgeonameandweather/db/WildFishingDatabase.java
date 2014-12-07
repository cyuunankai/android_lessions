package com.example.getgeonameandweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.getgeonameandweather.bean.Weather;
import com.example.getgeonameandweather.bean.WeatherAndLocation;

public class WildFishingDatabase {
    private static final String TAG = "WildFishingDatabase";

    private static final String DATABASE_NAME = "WildFishingDatabase";
    private static final int DATABASE_VERSION = 2;

    private final WildFishingOpenHelper mDatabaseOpenHelper;

    /**
     * Constructor
     * @param context The Context within which to work, used to create the DB
     */
    public WildFishingDatabase(Context context) {
        mDatabaseOpenHelper = new WildFishingOpenHelper(context);
    }
    
    public long addWeather(WeatherAndLocation wal) {
    	// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();

    	Weather weather = wal.getWeatherData();
    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_DATE, weather.getDate());
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_MIN_TEMP_C, weather.getMintempC());
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_MAX_TEMP_C, weather.getMaxtempC());
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_SUNRISE, weather.getAstronomy().getSunrise());
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_SUNSET, weather.getAstronomy().getSunset());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.Weathers.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
    }

    /**
     * This creates/opens the database.
     */
    private static class WildFishingOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        
        // 天气
        private static final String SQL_CREATE_WEATHERS =
            "CREATE TABLE " + WildFishingContract.Weathers.TABLE_NAME + " (" +
            		WildFishingContract.Weathers._ID + " INTEGER PRIMARY KEY," +
            		WildFishingContract.Weathers.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP +
		            WildFishingContract.Weathers.COLUMN_NAME_MIN_TEMP_C + TEXT_TYPE + COMMA_SEP +
		            WildFishingContract.Weathers.COLUMN_NAME_MAX_TEMP_C + TEXT_TYPE + COMMA_SEP +
		            WildFishingContract.Weathers.COLUMN_NAME_SUNRISE + TEXT_TYPE + COMMA_SEP +
		            WildFishingContract.Weathers.COLUMN_NAME_SUNSET + TEXT_TYPE + 
            " )";

        private static final String SQL_DELETE_WEATHERS =
            "DROP TABLE IF EXISTS " + WildFishingContract.Weathers.TABLE_NAME;
        
        // 天气hourly
        private static final String SQL_CREATE_WEATHERS_HOURLY =
                "CREATE TABLE " + WildFishingContract.WeathersHourly.TABLE_NAME + " (" +
                		WildFishingContract.WeathersHourly._ID + " INTEGER PRIMARY KEY," +
                		WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_ID + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_TEMP_C + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_WIND_SPEED_KMPH + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_WIND_DIR_DEGREE + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_PRESSURE + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_CLOUD_COVER + TEXT_TYPE + COMMA_SEP +
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_CODE + TEXT_TYPE +
                " )";

        private static final String SQL_DELETE_WEATHERS_HOURLY =
            "DROP TABLE IF EXISTS " + WildFishingContract.WeathersHourly.TABLE_NAME;


        WildFishingOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(SQL_CREATE_WEATHERS);
            mDatabase.execSQL(SQL_CREATE_WEATHERS_HOURLY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(SQL_DELETE_WEATHERS);
            onCreate(db);
        }
    }

}
