package com.boombile.boomdb.example.database;

import com.boombile.boomdb.example.model.User;

/**
 * Created by maohieng on 9/4/15.
 */
public final class DatabaseConstants {
    private DatabaseConstants() {
    }

    public static final String DB_NAME = "boomdbexample.sqlite";
    public static final int DB_VERSION = 1;

    static final String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE_NAME + "(" + User._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + User.COLUMN_NAME + " TEXT)";
    static final String DELETE_TABLE_USER = "DROP TABLE IF EXISTS " + User.TABLE_NAME;
}
