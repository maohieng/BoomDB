package com.boombile.boomdb;

import android.content.ContentValues;

public class DatabaseParams {

	public static class Insert {
		/** the table to insert the row into **/
		public String table;
		/**
		 * optional; may be null. SQL doesn't allow inserting a completely empty
		 * row without naming at least one column name. If your provided values
		 * is empty, no column names are known and an empty row can't be
		 * inserted. If not set to null, the nullColumnHack parameter provides
		 * the name of nullable column name to explicitly insert a NULL into in
		 * the case where your values is empty.
		 **/
		public String nullColumnHack;
		/**
		 * this map contains the initial column values for the row. The keys
		 * should be the column names and the values the column values
		 **/
		public ContentValues values;
	}

	public static class Delete {
		/** the table to delete from **/
		public String table;
		/**
		 * the optional WHERE clause to apply when deleting. Passing null will
		 * delete all rows.
		 **/
		public String whereClause;
		/**
		 * You may include ?s in the where clause, which will be replaced by the
		 * values from whereArgs. The values will be bound as Strings.
		 **/
		public String[] whereArgs;
	}

	public static class Select {
		/** true if you want each row to be unique, false otherwise. **/
		public boolean distinct;
		/** The table name to compile the query against. **/
		public String table;
		/**
		 * A list of which columns to return. Passing null will return all
		 * columns, which is discouraged to prevent reading data from storage
		 * that isn't going to be used.
		 **/
		public String[] columns;
		/**
		 * A filter declaring which rows to return, formatted as an SQL WHERE
		 * clause (excluding the WHERE itself). Passing null will return all
		 * rows for the given table.
		 **/
		public String selection;
		/**
		 * You may include ?s in selection, which will be replaced by the values
		 * from selectionArgs, in order that they appear in the selection. The
		 * values will be bound as Strings.
		 **/
		public String[] selectionArgs;
		/**
		 * A filter declaring how to group rows, formatted as an SQL GROUP BY
		 * clause (excluding the GROUP BY itself). Passing null will cause the
		 * rows to not be grouped.
		 **/
		public String groupBy;
		/**
		 * A filter declare which row groups to include in the cursor, if row
		 * grouping is being used, formatted as an SQL HAVING clause (excluding
		 * the HAVING itself). Passing null will cause all row groups to be
		 * included, and is required when row grouping is not being used.
		 **/
		public String having;
		/**
		 * How to order the rows, formatted as an SQL ORDER BY clause (excluding
		 * the ORDER BY itself). Passing null will use the default sort order,
		 * which may be unordered.
		 **/
		public String orderBy;
		/**
		 * Limits the number of rows returned by the query, formatted as LIMIT
		 * clause. Passing null denotes no LIMIT clause.
		 **/
		public String limit;
	}

	public static class Update {
		/** the table to update in **/
		public String table;
		/**
		 * a map from column names to new column values. null is a valid value
		 * that will be translated to NULL.
		 **/
		public ContentValues values;
		/**
		 * the optional WHERE clause to apply when updating. Passing null will
		 * update all rows.
		 **/
		public String whereClause;
		/**
		 * You may include ?s in the where clause, which will be replaced by the
		 * values from whereArgs. The values will be bound as Strings.
		 **/
		public String[] whereArgs;
	}

}
