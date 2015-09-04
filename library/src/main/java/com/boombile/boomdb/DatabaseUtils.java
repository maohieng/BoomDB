package com.boombile.boomdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;

import com.boombile.boomdb.DatabaseParams.Delete;
import com.boombile.boomdb.DatabaseParams.Insert;
import com.boombile.boomdb.DatabaseParams.Select;
import com.boombile.boomdb.DatabaseParams.Update;

public final class DatabaseUtils {

    // prevent the creation the object
    private DatabaseUtils() {}

    /**
     * Convenience method for deleting rows in the database.
     * 
     * @param database : an {@link SQLiteDatabase} object to work with database
     * @param params : {@link Delete} parameters
     * @return the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1"
     *         as the whereClause.
     * @see SQLiteDatabase#delete(String, String, String[])
     */
    public static int delete(SQLiteDatabase database, DatabaseParams.Delete params) {
        return database.delete(params.table, params.whereClause, params.whereArgs);
    }

    /**
     * Convenience method for inserting a row into the database.
     * 
     * @param database : {@link SQLiteDatabase} object to work with database
     * @param params : {@link Insert} parameters
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     * @see SQLiteDatabase#insert(String, String, android.content.ContentValues)
     */
    public static long insert(SQLiteDatabase database, DatabaseParams.Insert params) {
        return database.insert(params.table, params.nullColumnHack, params.values);
    }

    /**
     * Query the given URL, returning a {@link Cursor} over the result set.
     * 
     * @param params An instance of {@link Select} params.
     * @return A {@link Cursor} object, which is positioned before the first entry. Note that {@link Cursor}s are not
     *         synchronized, see the documentation for more details.
     * @see Cursor ,
     */
    public static Cursor select(SQLiteDatabase database, DatabaseParams.Select params) {
        return database.query(params.distinct, params.table, params.columns, params.selection, params.selectionArgs, params.groupBy, params.having,
                params.orderBy, params.limit);
    }

    /**
     * Convenience method for updating rows in the database.
     * 
     * @param database : an {@link SQLiteDatabase} object to work with database.
     * @param params : {@link Update} parameters
     * @return the number of rows affected
     * @see SQLiteDatabase#update(String, android.content.ContentValues, String, String[])
     */
    public static int update(SQLiteDatabase database, DatabaseParams.Update params) {
        return database.update(params.table, params.values, params.whereClause, params.whereArgs);
    }

    // /**
    // * Runs the provided SQL and returns a Cursor over the result set.
    // *
    // * @param database : an {@link SQLiteDatabase} object to work with
    // database.
    // * @param sql : the SQL query. The SQL string must not be ; terminated
    // * @param selectionArgs : You may include ?s in where clause in the query,
    // which will be replaced by the values from
    // * selectionArgs. The values will be bound as Strings.
    // * @return A Cursor object, which is positioned before the first entry.
    // Note that Cursors are not synchronized, see the
    // * documentation for more details.
    // */
    // public static Cursor rawQuery(SQLiteDatabase database, String sql,
    // String[] selectionArgs) {
    // return database.rawQuery(sql, selectionArgs);
    // }

    /**
     * Creates the place holders ( ? ) for a list or array of a data by providing its size or length.
     * 
     * @param len : the length of a selection arguments array.
     * @return
     */
    public static String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for(int i = 1; i < len; i++) {
                sb.append(",?");
            }

            return sb.toString();
        }
    }

    /**
     * Executes queries task.
     * 
     * @author maohieng
     * 
     */
    public static class ExecQueriesTask extends AsyncTask<String, Integer, Void> {
        private static final String TAG = ExecQueriesTask.class.getSimpleName();

        @Override
        protected Void doInBackground(final String... params) {
            DatabaseManager.getInstance().executeQuery(new QueryExecutor() {
                @Override
                public void run(SQLiteDatabase db) {
                    for(String query : params) {
                        try {
                            db.execSQL(query);
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                            Log.e(TAG, "invalid query: " + query);
                        }
                    }
                }
            });

            return null;
        }
    }
}
