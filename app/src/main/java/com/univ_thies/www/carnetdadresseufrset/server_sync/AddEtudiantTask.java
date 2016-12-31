package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by layely on 12/16/16.
 */

public class AddEtudiantTask extends AsyncTask<Void, String, Void> {
    private final String PHP_FILE = "add_student.php";
    List<Etudiant> etudiants;
    EtudiantDAO etudiantDAO;
    UtilDAO utilDAO;
    Context context;

    public AddEtudiantTask(Context context) {
        this.context = context;
        etudiantDAO = new EtudiantDAO(context);
        utilDAO = new UtilDAO(context);
        etudiants = etudiantDAO.getNewEtudiantsUnsynchronizedToServer();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tagasync", "::::::::: doInBackground");
        if (etudiants == null)
            return null;
        Log.i("tagasync", ":::::::: list_size: " + etudiants.size());
        for (Etudiant etu : etudiants) {
            HashMap<String, String> paramsMap = new HashMap<>(15);
            paramsMap.put("ine", etu.getIne());
            paramsMap.put("numDossier", Long.toString(etu.getNumDossier()));
            paramsMap.put("nom", etu.getNom());
            paramsMap.put("prenom", etu.getPrenom());
            paramsMap.put("adresse", etu.getAddresse());
            paramsMap.put("mobile1", Long.toString(etu.getMobile1()));
            paramsMap.put("mobile2", Long.toString(etu.getMobile2()));
            paramsMap.put("email", etu.getEmail());
            paramsMap.put("sexe", String.valueOf(etu.getSexe()));
            paramsMap.put("dateNaissance", etu.getDateNais());
            paramsMap.put("filiere", etu.getFiliere().getLibelleFiliere());
            paramsMap.put("specialite", etu.getSpecialite());
            paramsMap.put("niveau", etu.getNiveau());

            HttpURLConnection conn = ServerConnection.getConnection(PHP_FILE, paramsMap);
            Log.i("tagasync", "::::::URL:::::::::" + conn.getURL().toString());
            Log.i("tagasync", "::::::::: connect fired");
            try {
                conn.connect();
                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    publishProgress("Connection établie ...");
                    Log.i("tagasync", "Connection établie ...");

                    JSONObject jsonObject = new JSONObject(ServerConnection.getResultString(conn));
                    if (jsonObject != null && jsonObject.getInt("result") == 0) {
                        publishProgress(" envoi des données ...");
                        Log.i("tagasync", "envoi des données ...");
                        int numLastSynced = jsonObject.getInt("data");
                        utilDAO.setLastNumSynced(utilDAO.getLastNumSynced() + 1);
                        etudiantDAO.setSynced(etu);
                        Log.i("tagasync", " JsonArrayCreated...");
                    } else {
                        publishProgress("Erreur serveur");
                    }

                    // Pass data to onPostExecute method

                } else {
                    publishProgress("Pas de connection internet");
                    Log.i("tagasync", "Pas de connection internet");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
                publishProgress("Terminé");
                Log.i("tagasync", "Terminé");
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
