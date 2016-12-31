package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by layely on 12/13/16.
 */

public class UtilDAO {

    public static final String COLUMN_KEY = "keyUtil";
    public static final String COLUMN_VALUE = "valueUtil";

    public static final String FIELD_LAST_NUM_SYNCED = "lastNumSynced";
    public static final String FIELD_LAST_NUM_SUP = "lastNumSup";
    public static final String FIELD_NUMBER_OF_LAUNCH = "numberOfLaunch";
    public static final String FIELD_NUMBER_OF_SYNC = "numberOfSync";
    private SQLiteDatabase db;

    public UtilDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
    }

    public int getLastNumSynced() {
        String query = "SELECT " + COLUMN_VALUE + " FROM " + SQLHelper.TABLE_UTIL + " WHERE " + COLUMN_KEY + " ='" + FIELD_LAST_NUM_SYNCED + "'";

        Cursor result = db.rawQuery(query, null);
        if (result == null) {
            return -1;
        }

        result.moveToFirst();
        if (!result.isAfterLast()) {
            return result.getInt(result.getColumnIndex(COLUMN_VALUE));
        }
        return -2;
    }

//    public void incrementLastNumSynced() {
//        incrementLastNumSynced(1);
//    }

    public void setLastNumSynced(int newValue) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY, FIELD_LAST_NUM_SYNCED);
        cv.put(COLUMN_VALUE, newValue);

        db.update(SQLHelper.TABLE_UTIL, cv, COLUMN_KEY + "='" + FIELD_LAST_NUM_SYNCED + "'", null);
    }

    public int getLastNumSup() {
        String query = "SELECT " + COLUMN_VALUE + " FROM " + SQLHelper.TABLE_UTIL + " WHERE " + COLUMN_KEY + " ='" + FIELD_LAST_NUM_SUP + "'";

        Cursor result = db.rawQuery(query, null);
        if (result == null) {
            return -1;
        }

        result.moveToFirst();
        if (!result.isAfterLast()) {
            return result.getInt(result.getColumnIndex(COLUMN_VALUE));
        }
        return -2;
    }

//    public void incrementLastNumSup() {
//        incrementLastNumSup(1);
//    }

    public void setLastNumSup(int newValue) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY, FIELD_LAST_NUM_SUP);
        cv.put(COLUMN_VALUE, newValue);

        db.update(SQLHelper.TABLE_UTIL, cv, COLUMN_KEY + "='" + FIELD_LAST_NUM_SUP + "'", null);
    }

    public void incrementNumberOfSync() {
        incrementNumberOf(FIELD_NUMBER_OF_SYNC);
    }

    public int getNumberOfSync() {
        return getNumberOf(FIELD_NUMBER_OF_SYNC);
    }

    public int getNumberOfLaunch() {
        String query = "SELECT " + COLUMN_VALUE + " FROM " + SQLHelper.TABLE_UTIL + " WHERE " + COLUMN_KEY + " ='" + FIELD_NUMBER_OF_LAUNCH + "'";

        Cursor result = db.rawQuery(query, null);
        if (result == null) {
            return -1;
        }

        result.moveToFirst();
        if (!result.isAfterLast()) {
            return result.getInt(result.getColumnIndex(COLUMN_VALUE));
        }
        return -2;
    }

    public void incrementNumberOfLaunch() {
        incrementNumberOfLaunch(1);
    }

    public void incrementNumberOfLaunch(int increment) {
        incrementNumberOf(FIELD_NUMBER_OF_LAUNCH, increment);
    }

    private int getNumberOf(final String FIELD) {
        String query = "SELECT " + COLUMN_VALUE + " FROM " + SQLHelper.TABLE_UTIL + " WHERE " + COLUMN_KEY + " ='" + FIELD + "'";

        Cursor result = db.rawQuery(query, null);
        if (result == null) {
            return -1;
        }

        result.moveToFirst();
        if (!result.isAfterLast()) {
            return result.getInt(result.getColumnIndex(COLUMN_VALUE));
        }
        return -2;
    }

    private void incrementNumberOf(final String FIELD, int increment) {
        int newValue = getLastNumSup() + increment;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY, FIELD);
        cv.put(COLUMN_VALUE, newValue);

        db.update(SQLHelper.TABLE_UTIL, cv, COLUMN_KEY + "='" + FIELD + "'", null);
    }

    public void incrementNumberOf(final String FIELD) {
        incrementNumberOf(FIELD, 1);
    }
}
