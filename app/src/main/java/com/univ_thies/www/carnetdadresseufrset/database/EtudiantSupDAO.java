package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by layely on 12/18/16.
 */

public class EtudiantSupDAO {

    public static final String COLUMN_INE = "ineEtuSup";
    public static final String COLUMN_NUM_DOSSIER = "numDossierEtuSup";

    private SQLiteDatabase db;


    public EtudiantSupDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
    }


    public List<Long> getAllDeletedNumDossier() {
        List<Long> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT_SUP;

        Cursor result = db.rawQuery(query, null);

        list = asList(result);
        result.close();
        return list;
    }

    protected List<Long> asList(Cursor result) {
        List<Long> list = new LinkedList<>();
        if (result == null)
            return list;

        result.moveToFirst();
        while (!result.isAfterLast()) {
            Long numDossier = result.getLong(result.getColumnIndex(COLUMN_NUM_DOSSIER));
//            long numDossier = result.getLong(result.getColumnIndex(COLUMN_NUM_DOSSIER));

            list.add(numDossier);
            result.moveToNext();
        }

        return list;
    }

    public boolean delete(Long numDossier) {
        if (numDossier != null) {
            int result = db.delete(SQLHelper.TABLE_ETUDIANT_SUP, COLUMN_NUM_DOSSIER + "='" + numDossier + "'", null);
            return true;
        }
        return false;
    }

    public boolean delete(Etudiant etudiant) {
        if (etudiant != null) {
            return delete(etudiant.getNumDossier());
        }
        return false;
    }

    public boolean add(Etudiant etu) {
        ContentValues cv = getEtudiantContentValues(etu);
        if (cv == null)
            return false;

        long result = db.insert(SQLHelper.TABLE_ETUDIANT_SUP, null, cv);
        return result != -1;
    }


    private ContentValues getEtudiantContentValues(Etudiant etudiant) {
        if (etudiant == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_INE, etudiant.getIne().toUpperCase());
        values.put(COLUMN_NUM_DOSSIER, etudiant.getNumDossier());
        return values;
    }
}
