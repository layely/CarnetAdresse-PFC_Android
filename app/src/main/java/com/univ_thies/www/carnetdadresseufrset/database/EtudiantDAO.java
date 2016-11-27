package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;
import com.univ_thies.www.carnetdadresseufrset.objects.Promo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by layely on 11/26/16.
 */

public class EtudiantDAO {
    public static final String COLUMN_INE = "ineEtu";
    public static final String COLUMN_NOM = "nomEtu";
    public static final String COLUMN_PRENOM = "prenomEtu";
    public static final String COLUMN_DATE_NAISS = "dateNaisEtu";
    public static final String COLUMN_SEXE = "sexeEtu";
    public static final String COLUMN_MOBILE_1 = "mobile1Etu";
    public static final String COLUMN_MOBILE_2 = "mobile2Etu";
    public static final String COLUMN_EMAIL = "emailEtu";
    public static final String COLUMN_ADRESSE = "adresseEtu";
    public static final String COLUMN_FILIERE = "filiereEtu";
    public static final String COLUMN_PROMO = "promoEtu";
    public static final String COLUMN_SPECIALITE = "specialiteEtu";
    public static final String COLUMN_SYNC = "syncEtu";
    public static final String COLUMN_MODIF_SYNC = "modifSyncEtu";
    private SQLiteDatabase db;

    public EtudiantDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
    }

    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT;

        Cursor result = db.rawQuery(query, null);

        list = asList(result);
        result.close();
        return list;
    }

    private List<Etudiant> asList(Cursor result) {
        List<Etudiant> list = new ArrayList<>();
        if (result == null)
            return list;

        result.moveToFirst();
        while (!result.isAfterLast()) {
            String ine = result.getString(result.getColumnIndex(COLUMN_INE));
            String nom = result.getString(result.getColumnIndex(COLUMN_NOM));
            String prenom = result.getString(result.getColumnIndex(COLUMN_PRENOM));
            char sexe = result.getString(result.getColumnIndex(COLUMN_SEXE)).charAt(0);
            String dateNais = result.getString(result.getColumnIndex(COLUMN_DATE_NAISS));
            long mobile1 = result.getLong(result.getColumnIndex(COLUMN_MOBILE_1));
            long mobile2 = result.getLong(result.getColumnIndex(COLUMN_MOBILE_2));
            String email = result.getString(result.getColumnIndex(COLUMN_EMAIL));
            String addresse = result.getString(result.getColumnIndex(COLUMN_ADRESSE));
            String specialite = result.getString(result.getColumnIndex(COLUMN_SPECIALITE));
            String filiereStr = result.getString(result.getColumnIndex(COLUMN_FILIERE));
            String promoStr = result.getString(result.getColumnIndex(COLUMN_PROMO));
            int sync = result.getInt(result.getColumnIndex(COLUMN_SYNC));
            int modif_sync = result.getInt(result.getColumnIndex(COLUMN_MODIF_SYNC));
            Promo promo = Promo.getInstance(promoStr);
            Filiere filiere = Filiere.getInstance(filiereStr);

            Etudiant etu = new Etudiant(ine, nom, prenom, dateNais, sexe, mobile1, mobile2, email, addresse, specialite, filiere, promo, sync, modif_sync);
            list.add(etu);
            result.moveToNext();
        }

        return list;
    }

    public boolean addEtudiant(Etudiant newEtudiant) {
        ContentValues cv = getEtudiantContentValues(newEtudiant);
        if (cv == null)
            return false;

        long result = db.insert(SQLHelper.TABLE_ETUDIANT, null, cv);
        if (result == -1)
            return false;
        return true;
    }


    private ContentValues getEtudiantContentValues(Etudiant etudiant) {
        if (etudiant == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_INE, etudiant.getIne());
        values.put(COLUMN_NOM, etudiant.getNom());
        values.put(COLUMN_PRENOM, etudiant.getPrenom());
        values.put(COLUMN_DATE_NAISS, etudiant.getDateNais());
        values.put(COLUMN_SEXE, String.valueOf(etudiant.getSexe()));
        values.put(COLUMN_MOBILE_1, etudiant.getMobile1());
        values.put(COLUMN_MOBILE_2, etudiant.getMobile2());
        values.put(COLUMN_EMAIL, etudiant.getEmail());
        values.put(COLUMN_ADRESSE, etudiant.getAddresse());
        values.put(COLUMN_SPECIALITE, etudiant.getSpecialite());
        values.put(COLUMN_FILIERE, etudiant.getFiliere().getLibelleFiliere());
        values.put(COLUMN_PROMO, etudiant.getPromo().getPromo());
        values.put(COLUMN_SYNC, etudiant.getSync());
        values.put(COLUMN_MODIF_SYNC, etudiant.getModifSync());
        return values;
    }

}