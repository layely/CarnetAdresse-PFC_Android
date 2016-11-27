package com.univ_thies.www.carnetdadresseufrset.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by layely on 11/26/16.
 */

public class SQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_ETUDIANT = "Etudiant";
    public static final String TABLE_FILIERE = "Filiere";
    public static final String TABLE_PROMO = "Promo";
    public static final String TABLE_ETUDIANT_SUP = "Etudiant_supprime";
    public static final String TABLE_FILIERE_SUP = "Filiere_supprimee";
    public static final String TABLE_PROMO_SUP = "Promo_supprimee";

    private final static String databaseName = "carnetadresse.db";//name of database
    private final static int VersionDataBase = 2;//Verion of database
    private Context context = null;

    public SQLHelper(Context context) {
        super(context, databaseName, null, VersionDataBase);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            createTablesFromFile(context, db);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ETUDIANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ETUDIANT_SUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILIERE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILIERE_SUP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROMO_SUP);

        onCreate(db);
    }


    public int createTablesFromFile(Context context, SQLiteDatabase db) throws IOException {
        // Reseting Counter
        int result = 0;

        // Open the resource
        InputStream ins = context.getResources().openRawResource(
                context.getResources().getIdentifier("db",
                        "raw", context.getPackageName()));
        BufferedReader insertReader = new BufferedReader(new InputStreamReader(ins));

        String txtFromFile = "";
        while (insertReader.ready()) {
            txtFromFile += insertReader.readLine();
        }

        String[] statements = txtFromFile.split(";");
        for (String stmt : statements) {
            db.execSQL(stmt);
            result++;
        }
        insertReader.close();

        // returning number of inserted rows
        return result;
    }
}
