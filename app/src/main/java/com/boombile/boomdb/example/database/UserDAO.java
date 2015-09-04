package com.boombile.boomdb.example.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.boombile.boomdb.AbstractDAO;
import com.boombile.boomdb.DatabaseParams;
import com.boombile.boomdb.example.model.User;

/**
 * Created by maohieng on 9/4/15.
 */
public class UserDAO extends AbstractDAO<User> {

    public UserDAO() {
        super(User.TABLE_NAME, User.class);
    }

    @Override
    protected User cursorToObject(Cursor cursor) {
        return new User(cursor);
    }

    @Override
    protected ContentValues getContentValues(User object) {
        return object.getContentValues();
    }

    public User[] selectOrderByName(SQLiteDatabase db) {
        DatabaseParams.Select select = newSelectParamsInstance();
        select.orderBy = "name";

        return select(db, select);
    }
}
