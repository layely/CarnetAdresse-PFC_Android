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

    public void incrementLastNumSynced() {
        incrementLastNumSynced(1);
    }

    public void incrementLastNumSynced(int increment) {
        int newValue = getLastNumSynced() + increment;
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KEY, FIELD_LAST_NUM_SYNCED);
        cv.put(COLUMN_VALUE, newValue);

        db.update(SQLHelper.TABLE_UTIL, cv, COLUMN_KEY + "='" + FIELD_LAST_NUM_SYNCED + "'", null);
    }
}
