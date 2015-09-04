package com.boombile.boomdb;

import android.database.sqlite.SQLiteDatabase;

import com.boombile.boomdb.DatabaseParams.Select;

/**
 * Data Access Object, a DAO class type provides the default methods for working
 * with database and model.
 * 
 * @author maohieng
 * 
 */
public interface DAO<E> {

	public E selectById(SQLiteDatabase database, String id);

	/**
	 * Queries to select a row.
	 * 
	 * @param database
	 * @param selection
	 * @param selectionArgs
	 * @return
	 */
	public E selectRow(SQLiteDatabase database, String selection,
					   String[] selectionArgs);

	/**
	 * Queries to select the rows by the {@link Select} parameter. Provide
	 * <code>null</code> for <code>selectParams</code>, it will query all data
	 * from the database table.
	 * 
	 * @param database
	 * @param selectParams
	 * @return
	 */
	public E[] select(SQLiteDatabase database, Select selectParams);

	/**
	 * Queries to insert the object <code>E</code> as a row of a database table.
	 * 
	 * @param database
	 *            : Currently opened database.
	 * @param object
	 * @return
	 */
	public long insert(SQLiteDatabase database, E object);

	public int update(SQLiteDatabase database, String id, E object);

	public int delete(SQLiteDatabase database, String id);

	public void deleteAll(SQLiteDatabase database);

}
