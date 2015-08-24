package com.ordinalmethod.counted;

public class Count {

	long id;
	long counter_id;
	int interval;
	String created_at;

	// constructors
	public Count() {
	}

	public Count(long id, long counter_id, int interval, String created_at) {
		this.id = id;
		this.counter_id = counter_id;
		this.interval = interval;
		this.created_at = created_at;

	}

	// setters
	public void setId(long id) {
		this.id = id;
	}

	public void setCounterId(long counter_id) {
		this.counter_id = counter_id;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	// getters
	public long getId() {
		return this.id;
	}

	public long getCounterId() {
		return this.counter_id;
	}

	public int getInterval() {
		return this.interval;
	}

	public String getCreatedAt() {
		return this.created_at;
	}

	// Used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id + " | " + counter_id + " | " + interval + " | " + created_at;
	}

}
