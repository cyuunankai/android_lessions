package com.example.angleplace.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.angleplace.bean.Place;

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
    
	public long addPlace(Place place) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Places.COLUMN_NAME_TITLE, place.getTitle());
    	values.put(WildFishingContract.Places.COLUMN_NAME_DETAIL, place.getDetail());
    	values.put(WildFishingContract.Places.COLUMN_NAME_FILE_NAME, place.getFileName());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.Places.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
	}
	
	public int updatePlace(Place place) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Places.COLUMN_NAME_TITLE, place.getTitle());
    	values.put(WildFishingContract.Places.COLUMN_NAME_DETAIL, place.getDetail());
    	values.put(WildFishingContract.Places.COLUMN_NAME_FILE_NAME, place.getFileName());
    	
    	String selection = WildFishingContract.Places._ID + " = ? ";
		String[] selelectionArgs = { place.getId() };

    	int count = db.update(
    			 WildFishingContract.Places.TABLE_NAME,
    			 values,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}
	
	public int deletePlace(String rowId) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	String selection = WildFishingContract.Places._ID + " = ? ";
		String[] selelectionArgs = { rowId };

    	int count = db.delete(
    			 WildFishingContract.Places.TABLE_NAME,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}

	public Place getPlaceById(String rowId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		String[] projection = { WildFishingContract.Places._ID,
				WildFishingContract.Places.COLUMN_NAME_TITLE,
				WildFishingContract.Places.COLUMN_NAME_DETAIL,
				WildFishingContract.Places.COLUMN_NAME_FILE_NAME };
		String selection = WildFishingContract.Places._ID + " = ? ";
		String[] selelectionArgs = { rowId };

		Cursor c = db.query(WildFishingContract.Places.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selelectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();
		
		Place place = new Place();
		place.setId(c.getString(c.getColumnIndex(WildFishingContract.Places._ID)));
		place.setTitle(c.getString(c.getColumnIndex(WildFishingContract.Places.COLUMN_NAME_TITLE)));
		place.setDetail(c.getString(c.getColumnIndex(WildFishingContract.Places.COLUMN_NAME_DETAIL)));
		place.setFileName(c.getString(c.getColumnIndex(WildFishingContract.Places.COLUMN_NAME_FILE_NAME)));
		return place;
	}
	
	public Cursor getPlaceForPinner() {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		
		String[] projection = { WildFishingContract.Places._ID,
				WildFishingContract.Places.COLUMN_NAME_TITLE};

		Cursor c = db.query(WildFishingContract.Places.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		
		return c;
	}

    /**
     * This creates/opens the database.
     */
    private static class WildFishingOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        private static final String TEXT_TYPE = " TEXT";
        private static final String COMMA_SEP = ",";
        
        // µöµã
        private static final String SQL_CREATE_PLACES =
            "CREATE TABLE " + WildFishingContract.Places.TABLE_NAME + " (" +
            		WildFishingContract.Places._ID + " INTEGER PRIMARY KEY," +
            		WildFishingContract.Places.COLUMN_NAME_TITLE + TEXT_TYPE +  COMMA_SEP +
            		WildFishingContract.Places.COLUMN_NAME_DETAIL + TEXT_TYPE + COMMA_SEP +
		            WildFishingContract.Places.COLUMN_NAME_FILE_NAME + TEXT_TYPE +  
            " )"; 

        private static final String SQL_DELETE_PLACES =
            "DROP TABLE IF EXISTS " + WildFishingContract.Places.TABLE_NAME;
        
        

        WildFishingOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(SQL_CREATE_PLACES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(SQL_DELETE_PLACES);
            onCreate(db);
        }
    }

}
