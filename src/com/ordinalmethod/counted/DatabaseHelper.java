package com.ordinalmethod.counted;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 5;

	// Database Name
	private static final String DATABASE_NAME = "counterManager";

	// Table Names
	public static final String TABLE_COUNTER = "counter";
	public static final String TABLE_COUNT = "count";

	// Common column names
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_CREATED_AT = "created_at";
	public static final String COLUMN_INTERVAL = "interval";

	// COUNTER Table - column names
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_COUNT = "count";
	public static final String COLUMN_ORDER_NUM = "order_num";
	public static final String COLUMN_UPDATED_AT = "updated_at";
	public static final String COLUMN_DELETE_IND = "delete_ind";
	public static final String COLUMN_COLOR = "color";
	public static final String COLUMN_GOAL = "goal";
	public static final String COLUMN_GOAL_MESSAGE = "goal_message";
	public static final String COLUMN_MAX = "max";
	public static final String COLUMN_MIN = "min";
	

	// COUNT Table - column names
	public static final String COLUMN_COUNTER_ID = "counter_id";
	

	// Table Create Statements
	// COUNTER table create statement
	private static final String CREATE_TABLE_COUNTER = "CREATE TABLE "
			+ TABLE_COUNTER + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
			+ COLUMN_DESCRIPTION + " TEXT," + COLUMN_COUNT + " INTEGER,"
			+ COLUMN_ORDER_NUM + " INTEGER," + COLUMN_CREATED_AT + " DATETIME,"
			+ COLUMN_UPDATED_AT + " DATETIME," + COLUMN_DELETE_IND
			+ " INTEGER," + COLUMN_INTERVAL + " INTEGER," + COLUMN_COLOR
			+ " INTEGER," + COLUMN_GOAL + " INTEGER," + COLUMN_GOAL_MESSAGE
			+ " TEXT," + COLUMN_MAX + " INTEGER," + COLUMN_MIN
			+ " INTEGER" + ");";

	// COUNT table create statement
	private static final String CREATE_TABLE_COUNT = "CREATE TABLE "
			+ TABLE_COUNT + "(" + COLUMN_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_COUNTER_ID
			+ " INTEGER," + COLUMN_INTERVAL + " INTEGER, " + COLUMN_CREATED_AT
			+ " DATETIME" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		

	}
	
	

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_COUNTER);
		db.execSQL(CREATE_TABLE_COUNT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNT);

		// create new tables
		onCreate(db);
	}
}