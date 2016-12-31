package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;

/**
 * Created by layely on 12/23/16.
 */

public class DeleteEtudiantInLocalTask extends AsyncTask<Void, String, Void> {
    private final String PHP_FILE = "fetch_deleted_student.php";
    private SwipeRefreshLayout swipeRefreshLayout;

    private EtudiantDAO etudiantDAO;
    private UtilDAO utilDAO;

    public DeleteEtudiantInLocalTask(Context context) {
        etudiantDAO = new EtudiantDAO(context);
        utilDAO = new UtilDAO(context);
        Log.i("tagasync", "instaciation over");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tagasync", ":::::::::Fetch doInBackground - start");
        HashMap<String, String> paramsMap = new HashMap<>(1);
        paramsMap.put(UtilDAO.FIELD_LAST_NUM_SUP, Integer.toString(utilDAO.getLastNumSup()));
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
                    publishProgress("Supression des données locales...");
                    Log.i("tagasync", " Suppression des données locales...");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("tagasync", " JsonArrayCreated...");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = null;
                        try {
                            c = jsonArray.getJSONObject(i);
                            String ine = c.getString("INEetuSup");
                            long numDossier = c.getLong("numDossierEtuSup");
                            int lastDeleted = c.getInt("num_sync_etu_sup");
                            etudiantDAO.deleteFromServer(numDossier);
                            utilDAO.setLastNumSup(lastDeleted);
                            Log.i("tagasync", "---//----------etudiant numDossier: " + numDossier);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    publishProgress("Erreur serveur");
                }
                publishProgress("Terminé");
                Log.i("tagasync", "Terminé");
                // Pass data to onPostExecute method
                return null;

            } else {
                publishProgress("Pas de connection internet");
                Log.i("tagasync", "Pas de connection internet");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.i("tagasync", ":::::::::onPostExecute");
//        try {
//            Thread.sleep(1000);
//            snackbar.dismiss();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
    }

    @Override
    protected void onPreExecute() {
        Log.i("tagasync", ":::::::::onPreExecute - start");
    }
}
