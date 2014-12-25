package com.example.anglepoint.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.anglepoint.bean.Bait;
import com.example.anglepoint.bean.LureMethod;
import com.example.anglepoint.bean.Point;
import com.example.anglepoint.bean.RodLength;

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
    
    // 竿长
	public long addRodLength(RodLength rl) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.RodLengths.COLUMN_NAME_NAME, rl.getName());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.RodLengths.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
	}
	
	public int updateRodLength(RodLength rl) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.RodLengths.COLUMN_NAME_NAME, rl.getName());
    	
    	String selection = WildFishingContract.RodLengths._ID + " = ? ";
		String[] selelectionArgs = { rl.getId() };

    	int count = db.update(
    			 WildFishingContract.RodLengths.TABLE_NAME,
    			 values,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}
	
	public int deleteRodLength(String rowId) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	String selection = WildFishingContract.RodLengths._ID + " = ? ";
		String[] selelectionArgs = { rowId };

    	int count = db.delete(
    			 WildFishingContract.RodLengths.TABLE_NAME,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}

	public RodLength getRodLengthById(String rowId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		String[] projection = { WildFishingContract.RodLengths._ID,
				WildFishingContract.RodLengths.COLUMN_NAME_NAME};
		String selection = WildFishingContract.RodLengths._ID + " = ? ";
		String[] selelectionArgs = { rowId };

		Cursor c = db.query(WildFishingContract.RodLengths.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selelectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();
		
		RodLength rl = new RodLength();
		rl.setId(c.getString(c.getColumnIndex(WildFishingContract.RodLengths._ID)));
		rl.setName(c.getString(c.getColumnIndex(WildFishingContract.RodLengths.COLUMN_NAME_NAME)));
		
		return rl;
	}
	
	public Cursor getRodLengthForPinner() {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		
		String[] projection = { WildFishingContract.RodLengths._ID,
				WildFishingContract.RodLengths.COLUMN_NAME_NAME};

		Cursor c = db.query(WildFishingContract.RodLengths.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		
		return c;
	}
	
	// 打窝
	public long addLureMethod(LureMethod lm) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.LureMethods.COLUMN_NAME_NAME, lm.getName());
    	values.put(WildFishingContract.LureMethods.COLUMN_NAME_DETAIL, lm.getDetail());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.LureMethods.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
	}
	
	public int updateLureMethod(LureMethod rl) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.LureMethods.COLUMN_NAME_NAME, rl.getName());
    	values.put(WildFishingContract.LureMethods.COLUMN_NAME_DETAIL, rl.getDetail());
    	
    	String selection = WildFishingContract.LureMethods._ID + " = ? ";
		String[] selelectionArgs = { rl.getId() };

    	int count = db.update(
    			 WildFishingContract.LureMethods.TABLE_NAME,
    			 values,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}
	
	public int deleteLureMethod(String rowId) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	String selection = WildFishingContract.LureMethods._ID + " = ? ";
		String[] selelectionArgs = { rowId };

    	int count = db.delete(
    			 WildFishingContract.LureMethods.TABLE_NAME,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}

	public LureMethod getLureMethodById(String rowId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		String[] projection = { WildFishingContract.LureMethods._ID,
				WildFishingContract.LureMethods.COLUMN_NAME_NAME,
				WildFishingContract.LureMethods.COLUMN_NAME_DETAIL};
		String selection = WildFishingContract.LureMethods._ID + " = ? ";
		String[] selelectionArgs = { rowId };

		Cursor c = db.query(WildFishingContract.LureMethods.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selelectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();
		
		LureMethod lm = new LureMethod();
		lm.setId(c.getString(c.getColumnIndex(WildFishingContract.LureMethods._ID)));
		lm.setName(c.getString(c.getColumnIndex(WildFishingContract.LureMethods.COLUMN_NAME_NAME)));
		lm.setDetail(c.getString(c.getColumnIndex(WildFishingContract.LureMethods.COLUMN_NAME_DETAIL)));
		
		return lm;
	}
	
	public Cursor getLureMethodForPinner() {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		
		String[] projection = { WildFishingContract.LureMethods._ID,
				WildFishingContract.LureMethods.COLUMN_NAME_NAME};

		Cursor c = db.query(WildFishingContract.LureMethods.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		
		return c;
	}
	
	// 饵料
	public long addBait(Bait bait) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Baits.COLUMN_NAME_NAME, bait.getName());
    	values.put(WildFishingContract.Baits.COLUMN_NAME_DETAIL, bait.getDetail());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.Baits.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
	}
	
	public int updateBait(Bait bait) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Baits.COLUMN_NAME_NAME, bait.getName());
    	values.put(WildFishingContract.Baits.COLUMN_NAME_DETAIL, bait.getDetail());
    	
    	String selection = WildFishingContract.Baits._ID + " = ? ";
		String[] selelectionArgs = { bait.getId() };

    	int count = db.update(
    			 WildFishingContract.Baits.TABLE_NAME,
    			 values,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}
	
	public int deleteBait(String rowId) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	String selection = WildFishingContract.Baits._ID + " = ? ";
		String[] selelectionArgs = { rowId };

    	int count = db.delete(
    			 WildFishingContract.Baits.TABLE_NAME,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}

	public Bait getBaitById(String rowId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		String[] projection = { WildFishingContract.Baits._ID,
				WildFishingContract.Baits.COLUMN_NAME_NAME,
				WildFishingContract.Baits.COLUMN_NAME_DETAIL};
		String selection = WildFishingContract.Baits._ID + " = ? ";
		String[] selelectionArgs = { rowId };

		Cursor c = db.query(WildFishingContract.Baits.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selelectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();
		
		Bait bait = new Bait();
		bait.setId(c.getString(c.getColumnIndex(WildFishingContract.Baits._ID)));
		bait.setName(c.getString(c.getColumnIndex(WildFishingContract.Baits.COLUMN_NAME_NAME)));
		bait.setDetail(c.getString(c.getColumnIndex(WildFishingContract.Baits.COLUMN_NAME_DETAIL)));
		
		return bait;
	}
	
	public Cursor getBaitForPinner() {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		
		String[] projection = { WildFishingContract.Baits._ID,
				WildFishingContract.Baits.COLUMN_NAME_NAME};

		Cursor c = db.query(WildFishingContract.Baits.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);
		
		return c;
	}
	
	// 钓点
	public long addPoint(Point point) {
		// Gets the data repository in write mode
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Points.COLUMN_NAME_PLACE_ID, point.getPlaceId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_ROD_LENGTH_ID, point.getRodLengthId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_DEPTH, point.getDepth());
    	values.put(WildFishingContract.Points.COLUMN_NAME_LURE_METHOD_ID, point.getLureMethodId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_BAIT_ID, point.getBaitId());

    	// Insert the new row, returning the primary key value of the new row
    	long newRowId;
    	newRowId = db.insert(
    			 WildFishingContract.Points.TABLE_NAME,
    	         null,
    	         values);
    	
    	return newRowId;
	}
	
	public int updatePoint(Point point) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(WildFishingContract.Points.COLUMN_NAME_PLACE_ID, point.getPlaceId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_ROD_LENGTH_ID, point.getRodLengthId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_DEPTH, point.getDepth());
    	values.put(WildFishingContract.Points.COLUMN_NAME_LURE_METHOD_ID, point.getLureMethodId());
    	values.put(WildFishingContract.Points.COLUMN_NAME_BAIT_ID, point.getBaitId());
    	
    	String selection = WildFishingContract.Points._ID + " = ? ";
		String[] selelectionArgs = { point.getId() };

    	int count = db.update(
    			 WildFishingContract.Points.TABLE_NAME,
    			 values,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}
	
	public int deletePoint(String rowId) {
    	SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
    	
    	String selection = WildFishingContract.Points._ID + " = ? ";
		String[] selelectionArgs = { rowId };

    	int count = db.delete(
    			 WildFishingContract.Points.TABLE_NAME,
    			 selection,
    			 selelectionArgs);
    	
    	return count;
	}

	public Point getPointById(String rowId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		String[] projection = { WildFishingContract.Points._ID,
				WildFishingContract.Points.COLUMN_NAME_PLACE_ID,
				WildFishingContract.Points.COLUMN_NAME_ROD_LENGTH_ID,
				WildFishingContract.Points.COLUMN_NAME_DEPTH,
				WildFishingContract.Points.COLUMN_NAME_LURE_METHOD_ID,
				WildFishingContract.Points.COLUMN_NAME_BAIT_ID};
		String selection = WildFishingContract.Points._ID + " = ? ";
		String[] selelectionArgs = { rowId };

		Cursor c = db.query(WildFishingContract.Points.TABLE_NAME, // The table to query
				projection, // The columns to return
				selection, // The columns for the WHERE clause
				selelectionArgs, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				null // The sort order
				);

		c.moveToFirst();
		
		Point point = new Point();
		point.setId(c.getString(c.getColumnIndex(WildFishingContract.Points._ID)));
		point.setPlaceId(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_PLACE_ID)));
		point.setRodLengthId(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_ROD_LENGTH_ID)));
		point.setDepth(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_DEPTH)));
		point.setLureMethodId(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_LURE_METHOD_ID)));
		point.setBaitId(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_BAIT_ID)));
		
		return point;
	}
	
	public List<Point> getPointsForList(String placeId) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		
		StringBuffer  sb = new StringBuffer();
		sb.append("SELECT p._id,p.depth,rl.name AS rod_length_name,lm.name AS lure_method_name,b.name AS bait_name ");
		sb.append("FROM points p  ");
		sb.append("INNER JOIN rod_lengths rl ON p.rod_length_id=rl._id ");
		sb.append("INNER JOIN lure_methods lm ON p.lure_method_id=lm._id ");
		sb.append("INNER JOIN baits b ON p.bait_id=b._id ");
		sb.append(" WHERE p.place_id=?");

		
		Cursor c = db.rawQuery(sb.toString(), new String[]{String.valueOf(placeId)});
		
		List<Point> list = new ArrayList<Point>();
		while(c.moveToNext()){
			Point point = new Point();
			point.setId(c.getString(c.getColumnIndex(WildFishingContract.Points._ID)));
			point.setDepth(c.getString(c.getColumnIndex(WildFishingContract.Points.COLUMN_NAME_DEPTH)));
			point.setRodLengthName(c.getString(c.getColumnIndex("rod_length_name")));
			point.setLureMethodName(c.getString(c.getColumnIndex("lure_method_name")));
			point.setBaitName(c.getString(c.getColumnIndex("bait_name")));
		
			list.add(point);
		}
		
		return list;
	}

    /**
     * This creates/opens the database.
     */
    private static class WildFishingOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String REAL_TYPE = " REAL";
        private static final String COMMA_SEP = ",";
        
        // 钓点
        private static final String SQL_CREATE_POINTS =
            "CREATE TABLE " + WildFishingContract.Points.TABLE_NAME + " (" +
            		WildFishingContract.Points._ID + " INTEGER PRIMARY KEY," +
            		WildFishingContract.Points.COLUMN_NAME_PLACE_ID + INTEGER_TYPE +  COMMA_SEP +
            		WildFishingContract.Points.COLUMN_NAME_ROD_LENGTH_ID + INTEGER_TYPE +  COMMA_SEP +
            		WildFishingContract.Points.COLUMN_NAME_DEPTH + REAL_TYPE + COMMA_SEP +
		            WildFishingContract.Points.COLUMN_NAME_LURE_METHOD_ID + INTEGER_TYPE +  COMMA_SEP +
		            WildFishingContract.Points.COLUMN_NAME_BAIT_ID + INTEGER_TYPE +  
            " )";
        
        // 竿长
        private static final String SQL_CREATE_ROD_LENGTHS =
                "CREATE TABLE " + WildFishingContract.RodLengths.TABLE_NAME + " (" +
                		WildFishingContract.RodLengths._ID + " INTEGER PRIMARY KEY," +
                		WildFishingContract.RodLengths.COLUMN_NAME_NAME + TEXT_TYPE +  
                " )";
        // 打窝
        private static final String SQL_CREATE_LURE_METHODS =
                "CREATE TABLE " + WildFishingContract.LureMethods.TABLE_NAME + " (" +
                		WildFishingContract.LureMethods._ID + " INTEGER PRIMARY KEY," +
                		WildFishingContract.LureMethods.COLUMN_NAME_NAME + TEXT_TYPE +  COMMA_SEP +
                		WildFishingContract.LureMethods.COLUMN_NAME_DETAIL + TEXT_TYPE +  
                " )";
        
        // 饵料
        private static final String SQL_CREATE_BAITS =
                "CREATE TABLE " + WildFishingContract.Baits.TABLE_NAME + " (" +
                		WildFishingContract.Baits._ID + " INTEGER PRIMARY KEY," +
                		WildFishingContract.Baits.COLUMN_NAME_NAME + TEXT_TYPE +  COMMA_SEP +
                		WildFishingContract.Baits.COLUMN_NAME_DETAIL + TEXT_TYPE +  
                " )";

        private static final String SQL_DELETE_POINTS =
            "DROP TABLE IF EXISTS " + WildFishingContract.Points.TABLE_NAME;
        private static final String SQL_DELETE_ROD_LENGTHS =
                "DROP TABLE IF EXISTS " + WildFishingContract.RodLengths.TABLE_NAME;
        private static final String SQL_DELETE_LURE_METHODS =
                "DROP TABLE IF EXISTS " + WildFishingContract.LureMethods.TABLE_NAME;
        private static final String SQL_DELETE_BAITS =
                "DROP TABLE IF EXISTS " + WildFishingContract.Baits.TABLE_NAME;
        
        

        WildFishingOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(SQL_CREATE_POINTS);
            mDatabase.execSQL(SQL_CREATE_ROD_LENGTHS);
            mDatabase.execSQL(SQL_CREATE_LURE_METHODS);
            mDatabase.execSQL(SQL_CREATE_BAITS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            mDatabase.execSQL(SQL_DELETE_POINTS);
            mDatabase.execSQL(SQL_DELETE_ROD_LENGTHS);
            mDatabase.execSQL(SQL_DELETE_LURE_METHODS);
            mDatabase.execSQL(SQL_DELETE_BAITS);
            onCreate(db);
        }
    }

}
