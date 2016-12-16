package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.univ_thies.www.carnetdadresseufrset.objects.Promo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by layely on 11/27/16.
 */

public class PromoDAO {
    public static final String COLUMN_PROMO = "promo";
    public static final String COLUMN_SYNC = "syncProm";
    public static final String COLUMN_MODIF_SYNC = "modifSyncProm";


    private SQLiteDatabase db;

    public PromoDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
    }

    public List<Promo> getAllPromo() {
        List<Promo> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_PROMO;

        Cursor result = db.rawQuery(query, null);

        list = asList(result);
        result.close();
        return list;
    }

    private List<Promo> asList(Cursor result) {
        List<Promo> list = new ArrayList<>();

        if (result == null)
            return list;

        result.moveToFirst();
        while (!result.isAfterLast()) {
            String promo = result.getString(result.getColumnIndex(COLUMN_PROMO));
            int sync = result.getInt(result.getColumnIndex(COLUMN_SYNC));
            int modifSync = result.getInt(result.getColumnIndex(COLUMN_MODIF_SYNC));

            Promo prom = new Promo(promo, sync, modifSync);
            list.add(prom);
            result.moveToNext();
        }
        return list;
    }

    public boolean addPromo(Promo newPromo) {
        ContentValues cv = getPromoContentValues(newPromo);
        if (cv == null)
            return false;

        long result = db.insert(SQLHelper.TABLE_PROMO, null, cv);
        return result != -1;
    }

    public boolean deleteFiliere(String promo) {
        int result = db.delete(SQLHelper.TABLE_PROMO, COLUMN_PROMO + "='" + promo.toUpperCase() + "'", null);
        return result != 0;
    }

    public Promo getPromo(String promo) {

        Cursor result = db.rawQuery("SELECT " +
                COLUMN_PROMO + ", " +
                COLUMN_SYNC + ", " +
                COLUMN_MODIF_SYNC +
                " FROM " + SQLHelper.TABLE_PROMO +
                " WHERE " + COLUMN_PROMO + "='" + promo.toUpperCase() + "'", null);

        List<Promo> list = asList(result);
        if (list.size() > 0)
            return list.get(0);
        return null;
    }

    private ContentValues getPromoContentValues(Promo promo) {
        if (promo == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROMO, promo.getPromo().toUpperCase());
        values.put(COLUMN_SYNC, promo.getSync());
        values.put(COLUMN_MODIF_SYNC, promo.getModifSync());

        return values;
    }
}

