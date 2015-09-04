package com.boombile.boomdb;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.boombile.boomdb.DatabaseParams.Delete;
import com.boombile.boomdb.DatabaseParams.Insert;
import com.boombile.boomdb.DatabaseParams.Select;
import com.boombile.boomdb.DatabaseParams.Update;

import java.lang.reflect.Array;

public abstract class AbstractDAO<E> implements DAO<E> {

    protected final String mTableName;
    private final Class<E> mClass;

    public AbstractDAO(String tableName, Class<E> clazz) {
        mTableName = tableName;
        mClass = clazz;
    }

    /**
     * Extracts from cursor to the object <code>E</code>.
     * 
     * @param cursor
     * @return
     */
    protected abstract E cursorToObject(Cursor cursor);

    /**
     * Returns a {@link ContentValues} object from object <code>E</code>. It's used to insert or update operation.
     * 
     * @param object
     * @return
     */
    protected abstract ContentValues getContentValues(E object);

    public Select newSelectParamsInstance() {
        Select select = new Select();
        select.table = mTableName;

        return select;
    }

    /**
     * Returns an object of {@link Insert} parameter which contains the table name and the values from method
     * {@link #getContentValues(Object)}.
     * 
     * @param object
     * @return
     */
    public Insert newInsertParamsInstance(E object) {
        Insert insert = new Insert();
        insert.table = mTableName;
        insert.values = getContentValues(object);

        return insert;
    }

    public Update newUpdateParamsInstance(E object) {
        Update update = new Update();
        update.table = mTableName;
        update.values = getContentValues(object);

        return update;
    }

    public Delete newDeleteParamsInstace() {
        Delete delete = new Delete();
        delete.table = mTableName;

        return delete;
    }

    /**
     * Extracts a {@link Cursor} object and returns an array of <code>E</code>. <br/>
     * <b>Note : </b> You must close the cursor after using this method by calling method {@link #closeCursor(Cursor)}.
     * 
     * @param cursor
     * @return an array of <code>E</code>
     */
    @SuppressWarnings("unchecked")
    public E[] cursorToArrayObject(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return (E[]) Array.newInstance(mClass, 0);
        }

        E[] objects = (E[]) Array.newInstance(mClass, cursor.getCount());

        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                E object = cursorToObject(cursor);
                objects[i] = object;
                i++;
            } while (cursor.moveToNext());
        }

        return objects;
    }

    public static void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public E selectById(SQLiteDatabase database, String id) {
        // Get an instance of Select Parameters.
        // The selection is "id = ?" which "?" is the holder
        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = new String[]{id};

        E row = selectRow(database, selection, selectionArgs);

        return row;
    }

    @Override
    public E selectRow(SQLiteDatabase database, String selection, String[] selectionArgs) {
        Select select = newSelectParamsInstance();
        select.selection = selection;
        select.selectionArgs = selectionArgs;

        // Select a row from database
        Cursor cursor = DatabaseUtils.select(database, select);

        E row = null;
        // from cursor to E
        if (cursor.moveToFirst()) {
            row = cursorToObject(cursor);
        }

        // Close the cursor
        closeCursor(cursor);

        return row;
    }

    /**
     * {@inheritDoc} <br>
     * If you select all by providing <code>null</code> to <code>selectParams</code>, use ({@link Select} ) <code>null</code>
     * instead of <code>null</code>.
     */
    @Override
    public E[] select(SQLiteDatabase database, Select selectParams) {
        if (selectParams == null) {
            // Create an instance of Select Parameters to select all
            selectParams = newSelectParamsInstance();
        }

        // Select all data from database table
        Cursor cursor = DatabaseUtils.select(database, selectParams);

        // Extract the cursor to get an array of E
        E[] data = cursorToArrayObject(cursor);

        // Close the cursor
        closeCursor(cursor);

        return data;
    }

    /**
     * Uses {@link SQLiteDatabase#rawQuery(String, String[])}
     * 
     * @param database
     * @param sql SQL query
     * @param selectionArgs
     * @return
     */
    public E[] select(SQLiteDatabase database, String sql, String[] selectionArgs) {
        Cursor cursor = database.rawQuery(sql, selectionArgs);

        E[] data = cursorToArrayObject(cursor);

        closeCursor(cursor);

        return data;
    }

    @Override
    public long insert(SQLiteDatabase database, E object) {
        // Get an instance of Insert Parameters
        Insert insert = newInsertParamsInstance(object);

        // Insert into database which return the id of inserted row
        return DatabaseUtils.insert(database, insert);
    }

    @Override
    public int update(SQLiteDatabase database, String id, E object) {
        // Get an instance of Update parameters
        // Set the clause and argument parameters
        Update update = newUpdateParamsInstance(object);
        update.whereClause = BaseColumns._ID + " = ?";
        update.whereArgs = new String[]{id};

        // Update into database which return the number of rows affected
        return DatabaseUtils.update(database, update);
    }

    @Override
    public int delete(SQLiteDatabase database, String id) {
        // Get an instance of Delete Parameters
        // Set the clause and argument parameters
        Delete delete = newDeleteParamsInstace();
        delete.whereClause = BaseColumns._ID + " = ?";
        delete.whereArgs = new String[]{id};

        return DatabaseUtils.delete(database, delete);
    }

    @Override
    public void deleteAll(SQLiteDatabase database) {
        Delete delete = newDeleteParamsInstace();
        DatabaseUtils.delete(database, delete);
    }
}
