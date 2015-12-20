package com.nhs3108.fhrm.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nhs3108 on 18/12/2015.
 */
public class Department {
    private int id = FLAG_NOT_SAVED;
	public static final String TABLE_NAME = "departments";
	public static final String DEPARTMENT_KEY_ID = "id";
    public static final String DEPARTMENT_NAME = "name";
    public static final String DEPARTMENT_DESCRIPTION = "description";
    public static final int FLAG_NOT_SAVED = -1;

	private String name = "";
	private String description = "";

	public Department(String name) {
		this.name = name;
	}

	public Department(String name, String description) {
		this.name = name;
		this.description = description;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
