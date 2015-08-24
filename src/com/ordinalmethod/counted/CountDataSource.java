package com.ordinalmethod.counted;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CountDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	// List all columns in Counter table
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_COUNTER_ID, DatabaseHelper.COLUMN_INTERVAL,
			DatabaseHelper.COLUMN_CREATED_AT };

	public CountDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Count createCount(long counterId, int interval) {

		ContentValues values = new ContentValues();

		// Store associated counter and interval
		values.put(DatabaseHelper.COLUMN_COUNTER_ID, counterId);
		values.put(DatabaseHelper.COLUMN_INTERVAL, interval);

		// Get current date
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		// Store created and updated date
		values.put(DatabaseHelper.COLUMN_CREATED_AT, dateFormat.format(date));

		long insertId = database.insert(DatabaseHelper.TABLE_COUNT, null,
				values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_COUNT, allColumns,
				DatabaseHelper.COLUMN_ID + " = " + insertId, null, null, null,
				null);
		cursor.moveToFirst();
		Count newCount = cursorToCount(cursor);
		cursor.close();
		return newCount;
	}

	public Integer getCountTotalByCounterId(long counterId) {
		return 1;

		// FIX THIS METHOD
	}

	// public Count getCountByID(long counterID) {
	// String[] ID = { Long.toString(counterID) };
	// Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTER,
	// allColumns, "_id = ?", ID, null, null, null);
	//
	// cursor.moveToFirst();
	// Counter counter = cursorToCounter(cursor);
	// cursor.close();
	// return counter;
	//
	// }

	private Count cursorToCount(Cursor cursor) {
		Count count = new Count();
		count.setId(cursor.getLong(0));
		count.setCounterId(cursor.getLong(1));
		count.setInterval(cursor.getInt(2));
		count.setCreatedAt(cursor.getString(3));
		return count;
	}

}
