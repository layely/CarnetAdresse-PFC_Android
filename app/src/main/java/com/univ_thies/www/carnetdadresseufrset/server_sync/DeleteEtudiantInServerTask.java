package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantSupDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by layely on 12/19/16.
 */

public class DeleteEtudiantInServerTask extends AsyncTask<Void, String, Void> {
    private final String PHP_FILE = "delete_student.php";
    List<Long> numDossierEtudiants;
    EtudiantDAO etudiantDAO;
    UtilDAO utilDAO;
    Context context;
    EtudiantSupDAO etudiantSupDAO;

    public DeleteEtudiantInServerTask(Context context) {
        this.context = context;
        etudiantDAO = new EtudiantDAO(context);
        utilDAO = new UtilDAO(context);
        etudiantSupDAO = new EtudiantSupDAO(context);
        numDossierEtudiants = etudiantSupDAO.getAllDeletedNumDossier();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tagasync", "::::::::: DeleteEtudiant::doInBackground");
        if (numDossierEtudiants == null)
            return null;
        Log.i("tagasync", ":::::::: list_size: " + numDossierEtudiants.size());
        for (Long numDossierEtudiant : numDossierEtudiants) {
            HashMap<String, String> paramsMap = new HashMap<>(15);
            paramsMap.put("numDossier", numDossierEtudiant.toString());

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
                    if (jsonObject != null && jsonObject.getInt(ServerConnection.RESULT_TAG) == 0) {
                        publishProgress(" Données supprimées ...");
                        Log.i("tagasync", "Données suprimées ...");
                        etudiantSupDAO.delete(numDossierEtudiant);
                        utilDAO.setLastNumSup(utilDAO.getLastNumSup() + 1);
                    } else {
                        if (jsonObject != null)
                            Log.i("tagasync", "Erreur Server: " + jsonObject.getString(ServerConnection.MESSAGE_TAG));
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
}
