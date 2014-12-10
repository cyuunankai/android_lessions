package com.example.getgeonameandweather.db;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.getgeonameandweather.bean.Hourly;
import com.example.getgeonameandweather.bean.LocationData;
import com.example.getgeonameandweather.bean.Weather;
import com.example.getgeonameandweather.bean.WeatherAndLocation;

public class WildFishingDatabase {
    private static final String TAG = "WildFishingDatabase";

    private static final String DATABASE_NAME = "WildFishingDatabase.db";
    private static final int DATABASE_VERSION = 2;

    private final WildFishingOpenHelper mDatabaseOpenHelper;

    /**
     * Constructor
     * @param context The Context within which to work, used to create the DB
     */
    public WildFishingDatabase(Context context) {
        mDatabaseOpenHelper = new WildFishingOpenHelper(context);
    }
    
	public void addWeatherData(WeatherAndLocation wal) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
		long weatherId = addWeather(wal, db);
		addWeatherHourly(wal.getWeatherData(), weatherId, db);
	}
    
    public long addWeather(WeatherAndLocation wal, SQLiteDatabase db) {

    	Weather weather = wal.getWeatherData();
    	LocationData location = wal.getLocationData();
    	
    	// Create a new map of values, where column names are the keys
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_DATE, weather.getDate());
    	values.put(WildFishingContract.Weathers.COLUMN_NAME_REGION, location.getRegion());
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
    
    public void addWeatherHourly(Weather weather, long weatherId, SQLiteDatabase db) {

    	List<Hourly> hList = weather.getHourlyList();
		for (Hourly h : hList) {
	    	// Create a new map of values, where column names are the keys
	    	ContentValues values = new ContentValues();
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_ID, weatherId);
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_TIME, h.getTime());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_TEMP_C, h.getTempC());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_WIND_SPEED_KMPH, h.getWindspeedKmph());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_WIND_DIR_DEGREE, h.getWinddirDegree());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_PRESSURE, h.getPressure());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_CLOUD_COVER, h.getCloudcover());
	    	values.put(WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_CODE, h.getWeatherCode());
	    	
	    	db.insert(
	    			 WildFishingContract.WeathersHourly.TABLE_NAME,
	    	         null,
	    	         values);
    	}
    }
    
    public String getWeathers(){
    	SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
    	String[] projection = {
    			WildFishingContract.Weathers._ID,
    			WildFishingContract.Weathers.COLUMN_NAME_DATE
    		    };

    	String sortOrder =
    			WildFishingContract.Weathers._ID + " DESC";

    		Cursor c = db.query(
    				WildFishingContract.Weathers.TABLE_NAME,  // The table to query
    		    projection,                               // The columns to return
    		    null,                                // The columns for the WHERE clause
    		    null,                            // The values for the WHERE clause
    		    null,                                     // don't group the rows
    		    null,                                     // don't filter by row groups
    		    sortOrder                                 // The sort order
    		    );
    		
    		c.moveToFirst();
    		return c.getString(0) + " : " + c.getString(1);
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
            		WildFishingContract.Weathers.COLUMN_NAME_DATE + TEXT_TYPE + " UNIQUE" + COMMA_SEP +
            		WildFishingContract.Weathers.COLUMN_NAME_REGION + TEXT_TYPE + COMMA_SEP +
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
    		            WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_CODE + TEXT_TYPE + COMMA_SEP +
    		            "FOREIGN KEY("
						+ WildFishingContract.WeathersHourly.COLUMN_NAME_WEATHER_ID
						+ ") REFERENCES "
						+ WildFishingContract.Weathers.TABLE_NAME
						+ "(" + WildFishingContract.Weathers._ID + ")" +
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
