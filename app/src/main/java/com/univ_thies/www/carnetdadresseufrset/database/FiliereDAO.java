package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by layely on 11/27/16.
 */

public class FiliereDAO {

    public static final String COLUMN_LIBELE = "libeleFil";
    public static final String COLUMN_SPECIALITES = "specialitesFil";
    public static final String COLUMN_SYNC = "syncFil";
    public static final String COLUMN_MODIF_SYNC = "modifSyncFil";

    private SQLiteDatabase db;

    public FiliereDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
    }

    public List<Filiere> getAllFiliere() {
        List<Filiere> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_FILIERE;

        Cursor result = db.rawQuery(query, null);

        list = asList(result);
        result.close();
        return list;
    }

    private List<Filiere> asList(Cursor result) {
        List<Filiere> list = new ArrayList<>();

        if (result == null)
            return list;

        result.moveToFirst();
        while (!result.isAfterLast()) {
            String libelefil = result.getString(result.getColumnIndex(COLUMN_LIBELE));
            String specialitesStr = result.getString(result.getColumnIndex(COLUMN_SPECIALITES));
            Log.i("tag_specialite", "as list ::: filiere::: " + libelefil + "raw specialite :::: " + specialitesStr);
            ArrayList<String> specialites = Filiere.getListOfSpecialites(specialitesStr);
            int sync = result.getInt(result.getColumnIndex(COLUMN_SYNC));
            int modifSync = result.getInt(result.getColumnIndex(COLUMN_MODIF_SYNC));

            Filiere filiere = new Filiere(libelefil, specialites, sync, modifSync);
            list.add(filiere);
            result.moveToNext();
        }
        return list;
    }


    public boolean addFiliere(Filiere newFiliere) {
        ContentValues cv = getFiliereContentValues(newFiliere);
        if (cv == null)
            return false;

        long result = db.insert(SQLHelper.TABLE_FILIERE, null, cv);
        return result != -1;
    }

    public boolean deleteFiliere(String libeleFiliere) {
        int result = db.delete(SQLHelper.TABLE_FILIERE, COLUMN_LIBELE + "='" + libeleFiliere.toUpperCase() + "'", null);
        return result != 0;
    }

    public Filiere getFiliere(String libele) {
        String query = "SELECT " +
                COLUMN_LIBELE + ", " +
                COLUMN_SPECIALITES + ", " +
                COLUMN_SYNC + ", " +
                COLUMN_MODIF_SYNC +
                " FROM " + SQLHelper.TABLE_FILIERE +
                " WHERE " + COLUMN_LIBELE + " like '" + libele.toUpperCase() + "'";
        Cursor result = db.rawQuery(query, null);

        List<Filiere> list = asList(result);
        Log.i("tag", "on Get Filiere:::::  list size : " + list.size());
        if (list.size() > 0) {
//            Log.i("tag_specialite", "specialites raw::::: )_
            return list.get(0);
        }
        return null;
    }

    private ContentValues getFiliereContentValues(Filiere filiere) {
        if (filiere == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIBELE, filiere.getLibelleFiliere().toUpperCase());
        values.put(COLUMN_SPECIALITES, Filiere.getStringOfSpecialites(filiere.getSpecialites()).toUpperCase());
        values.put(COLUMN_SYNC, filiere.getSync());
        values.put(COLUMN_MODIF_SYNC, filiere.getModifSync());

        return values;
    }
}
