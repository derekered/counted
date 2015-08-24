package com.ordinalmethod.counted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CounterDataSource {

	// Database fields
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	// List all columns in Counter table
	private String[] allColumns = { DatabaseHelper.COLUMN_ID,
			DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION,
			DatabaseHelper.COLUMN_COUNT, DatabaseHelper.COLUMN_ORDER_NUM,
			DatabaseHelper.COLUMN_CREATED_AT, DatabaseHelper.COLUMN_UPDATED_AT,
			DatabaseHelper.COLUMN_DELETE_IND, DatabaseHelper.COLUMN_INTERVAL,
			DatabaseHelper.COLUMN_COLOR, DatabaseHelper.COLUMN_GOAL,
			DatabaseHelper.COLUMN_GOAL_MESSAGE, DatabaseHelper.COLUMN_MAX, DatabaseHelper.COLUMN_MIN };
	

	public CounterDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Counter incrementCounter(Counter counter, int increment) {

		ContentValues values = new ContentValues();

		long counterID = counter.getId();

		Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTER,
				allColumns, DatabaseHelper.COLUMN_ID + " = " + counterID, null,
				null, null, null);
		cursor.moveToFirst();
		counter = cursorToCounter(cursor);

		int newCount = counter.getCount() + increment;

		// Put new increment
		values.put(DatabaseHelper.COLUMN_COUNT, newCount);

		// Get current date
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		values.put(DatabaseHelper.COLUMN_UPDATED_AT, dateFormat.format(date));

		database.update(DatabaseHelper.TABLE_COUNTER, values,
				DatabaseHelper.COLUMN_ID + "=" + counterID, null);

		cursor = database.query(DatabaseHelper.TABLE_COUNTER, allColumns,
				DatabaseHelper.COLUMN_ID + " = " + counterID, null, null, null,
				null);
		cursor.moveToFirst();
		counter = cursorToCounter(cursor);
		cursor.close();

		return counter;

	}

	public void updateCounter(Counter counter) {
		ContentValues values = new ContentValues();

		long counterID = counter.getId();

		// Put updated values
		values.put(DatabaseHelper.COLUMN_NAME, counter.getName());
		values.put(DatabaseHelper.COLUMN_COLOR, counter.getColor());

		// Get current date
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		values.put(DatabaseHelper.COLUMN_UPDATED_AT, dateFormat.format(date));

		database.update(DatabaseHelper.TABLE_COUNTER, values,
				DatabaseHelper.COLUMN_ID + "=" + counterID, null);

	}

	public Counter createCounter(String name, String description, int count,
			int color, int order) {

		ContentValues values = new ContentValues();

		// Store name, description, count, and order number
		values.put(DatabaseHelper.COLUMN_NAME, name);
		values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
		values.put(DatabaseHelper.COLUMN_COUNT, count);
		values.put(DatabaseHelper.COLUMN_COLOR, color);
		values.put(DatabaseHelper.COLUMN_ORDER_NUM, order);

		// Get current date
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		// Store created and updated date
		values.put(DatabaseHelper.COLUMN_CREATED_AT, dateFormat.format(date));
		values.put(DatabaseHelper.COLUMN_UPDATED_AT, dateFormat.format(date));

		// Store deleted indicator
		values.put(DatabaseHelper.COLUMN_DELETE_IND, 0);

		long insertId = database.insert(DatabaseHelper.TABLE_COUNTER, null,
				values);
		Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTER,
				allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Counter newCounter = cursorToCounter(cursor);
		cursor.close();
		return newCounter;
	}

	public void deleteCounter(Counter counter) {
		long id = counter.getId();
		System.out.println("Comment deleted with id: " + id);
		database.delete(DatabaseHelper.TABLE_COUNTER, DatabaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Counter> getAllCounters() {
		List<Counter> counters = new ArrayList<Counter>();

		Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTER,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Counter counter = cursorToCounter(cursor);
			counters.add(counter);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return counters;
	}

	public Counter getCounterByID(long counterID) {
		String[] ID = { Long.toString(counterID) };
		Cursor cursor = database.query(DatabaseHelper.TABLE_COUNTER,
				allColumns, "_id = ?", ID, null, null, null);

		cursor.moveToFirst();
		Counter counter = cursorToCounter(cursor);
		cursor.close();
		return counter;

	}

	private Counter cursorToCounter(Cursor cursor) {
		Counter counter = new Counter();
		counter.setId(cursor.getLong(0));
		counter.setName(cursor.getString(1));
		counter.setDescription(cursor.getString(2));
		counter.setCount(cursor.getInt(3));
		counter.setOrder(cursor.getInt(4));
		counter.setCreated_At(cursor.getString(5));
		counter.setUpdated_At(cursor.getString(6));
		counter.setDelete(cursor.getInt(7));
		counter.setColor(cursor.getInt(9));
		return counter;
	}

}
