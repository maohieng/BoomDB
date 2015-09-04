package com.boombile.boomdb.example.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.boombile.boomdb.BaseColumns;

/**
 * Created by maohieng on 9/4/15.
 */
public class User implements BaseColumns {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME = "name";

    private long id;
    private String name;

    public User() {
    }

    public User(Cursor c) {
        id = c.getLong(c.getColumnIndex(_ID));
        name = c.getString(c.getColumnIndex(COLUMN_NAME));
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues(2);
        values.put(_ID, id);
        values.put(COLUMN_NAME, name);

        return values;
    }

    @Override
    public String toString() {
        return name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
