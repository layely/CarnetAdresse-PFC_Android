package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.metier.Filiere;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by layely on 11/26/16.
 */

public class EtudiantDAO {
    public static final String COLUMN_INE = "ineEtu";
    public static final String COLUMN_NUM_DOSSIER = "numDossierEtu";
    public static final String COLUMN_NOM = "nomEtu";
    public static final String COLUMN_PRENOM = "prenomEtu";
    public static final String COLUMN_DATE_NAISS = "dateNaisEtu";
    public static final String COLUMN_SEXE = "sexeEtu";
    public static final String COLUMN_MOBILE_1 = "mobile1Etu";
    public static final String COLUMN_MOBILE_2 = "mobile2Etu";
    public static final String COLUMN_EMAIL = "emailEtu";
    public static final String COLUMN_ADRESSE = "adresseEtu";
    public static final String COLUMN_FILIERE = "filiereEtu";
    public static final String COLUMN_SPECIALITE = "specialiteEtu";

    public static final String COLUMN_SYNC = "syncEtu";
    public static final String COLUMN_MODIF_SYNC = "modifSyncEtu";
    private static final String COLUMN_NIVEAU = "niveauEtu";
    private SQLiteDatabase db;
    private FiliereDAO filiereDAO;
    private EtudiantSupDAO etudiantSupDAO;


    public EtudiantDAO(Context context) {
        db = new SQLHelper(context).getWritableDatabase();
        filiereDAO = new FiliereDAO(context);
        etudiantSupDAO = new EtudiantSupDAO(context);
    }

    public List<Etudiant> getAllEtudiants() {
        List<Etudiant> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT + " ORDER BY " + COLUMN_PRENOM;

        Cursor result = db.rawQuery(query, null);

        list = asList(result);
        result.close();
        return list;
    }

    protected List<Etudiant> asList(Cursor result) {
        List<Etudiant> list = new LinkedList<>();
        if (result == null)
            return list;

        result.moveToFirst();
        while (!result.isAfterLast()) {
            String ine = result.getString(result.getColumnIndex(COLUMN_INE));
            long numDossier = result.getLong(result.getColumnIndex(COLUMN_NUM_DOSSIER));
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
            String niveau = result.getString(result.getColumnIndex(COLUMN_NIVEAU));
            int sync = result.getInt(result.getColumnIndex(COLUMN_SYNC));
            int modif_sync = result.getInt(result.getColumnIndex(COLUMN_MODIF_SYNC));

            Log.i("tag", "on Get All Etudiant ::::: filiereStr : " + filiereStr);
            Filiere filiere = filiereDAO.getFiliere(filiereStr);
            if (filiere.getSpecialites() != null)
                for (String spec : filiere.getSpecialites())
                    Log.i("tag", "on Get All Etudiant ::::: specilite ## : " + spec);

            Etudiant etu = new Etudiant(ine, numDossier, nom, prenom, dateNais, sexe, mobile1, mobile2, email, addresse, specialite, filiere, niveau, sync, modif_sync);
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
        return result != -1;
    }


    private ContentValues getEtudiantContentValues(Etudiant etudiant) {
        if (etudiant == null) {
            return null;
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_INE, etudiant.getIne().toUpperCase());
        values.put(COLUMN_NUM_DOSSIER, etudiant.getNumDossier());
        values.put(COLUMN_NOM, etudiant.getNom().toUpperCase());
        values.put(COLUMN_PRENOM, etudiant.getPrenom().toUpperCase());
        values.put(COLUMN_DATE_NAISS, etudiant.getDateNais().toUpperCase());
        values.put(COLUMN_SEXE, String.valueOf(etudiant.getSexe()).toUpperCase());
        values.put(COLUMN_MOBILE_1, etudiant.getMobile1());
        values.put(COLUMN_MOBILE_2, etudiant.getMobile2());
        values.put(COLUMN_EMAIL, etudiant.getEmail());
        values.put(COLUMN_ADRESSE, etudiant.getAddresse());
        if (etudiant.getSpecialite() != null)
            values.put(COLUMN_SPECIALITE, etudiant.getSpecialite().toUpperCase());
        values.put(COLUMN_FILIERE, etudiant.getFiliere().getLibelleFiliere().toUpperCase());
        values.put(COLUMN_NIVEAU, etudiant.getNiveau());
        values.put(COLUMN_SYNC, etudiant.getSync());
        values.put(COLUMN_MODIF_SYNC, etudiant.getModifSync());
        return values;
    }

    public void modify(Etudiant etudiant, Etudiant newEtu) {
        etudiantSupDAO.add(etudiant);
        deleteFromLocal(etudiant);
        addEtudiant(newEtu);
    }

    public List<Etudiant> getNewEtudiantsUnsynchronizedToServer() {
        List<Etudiant> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT + " WHERE " + COLUMN_SYNC + "='" + "1' ORDER BY " + COLUMN_PRENOM;

        Cursor result = db.rawQuery(query, null);
        list = asList(result);
        result.close();
        return list;
    }

    public void setSynced(Etudiant etu) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SYNC, 0);

        db.update(SQLHelper.TABLE_ETUDIANT, cv, COLUMN_INE + "='" + etu.getIne().toUpperCase() + "'", null);
    }

    public Etudiant get(String ine) {
        List<Etudiant> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT + " WHERE " + COLUMN_INE + "='" + ine.toUpperCase() + "' ORDER BY " + COLUMN_PRENOM;

        Cursor result = db.rawQuery(query, null);
        list = asList(result);
        result.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public Etudiant get(int numDossier) {
        List<Etudiant> list = null;

        String query = "SELECT * FROM " + SQLHelper.TABLE_ETUDIANT + " WHERE " + COLUMN_INE + "='" + numDossier + "' ORDER BY " + COLUMN_PRENOM;

        Cursor result = db.rawQuery(query, null);
        list = asList(result);
        result.close();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public boolean deleteFromLocal(Etudiant etu) {
        if (etu == null || etu.getNumDossier() == 0) return false;
        if (etu.getSync() == 0) {
            etudiantSupDAO.add(etu);
        }
        int result = db.delete(SQLHelper.TABLE_ETUDIANT, COLUMN_NUM_DOSSIER + "='" + etu.getNumDossier() + "'", null);
        return true;
    }

    public boolean deleteFromServer(long numDossier) {
        int result = db.delete(SQLHelper.TABLE_ETUDIANT, COLUMN_NUM_DOSSIER + "='" + numDossier + "'", null);
        return true;
    }
}
