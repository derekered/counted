package com.ordinalmethod.counted;

public class Counter {

	long id;
	String name;
	String description;
	int count;
	int order_num;
	String created_at;
	String updated_at;
	int delete_ind;
	int color;

	// constructors
	public Counter() {
	}

	public Counter(String name, String description, int count, int color, int order_num,
			String created_at, String updated_at, int delete_ind) {
		this.name = name;
		this.description = description;
		this.count = count;
		this.color = color;
		this.order_num = order_num;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.delete_ind = delete_ind;
	}

	// setters
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public void setOrder(int order_num) {
		this.order_num = order_num;
	}

	public void setCreated_At(String created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_At(String updated_at) {
		this.updated_at = updated_at;
	}

	public void setDelete(int delete_ind) {
		this.delete_ind = delete_ind;
	}

	// getters
	public long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getCount() {
		return this.count;
	}
	
	public int getColor() {
		return this.color;
	}

	// Used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name + " | " + description + " | " + created_at;
	}
}