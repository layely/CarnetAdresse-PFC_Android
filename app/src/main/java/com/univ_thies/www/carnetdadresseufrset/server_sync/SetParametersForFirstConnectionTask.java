package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by layely on 12/28/16.
 */

public class SetParametersForFirstConnectionTask extends AsyncTask<Void, String, Void> {

    private final String PHP_FILE = "fetch_initial_params.php";
    private SwipeRefreshLayout swipeRefreshLayout;

    private EtudiantDAO etudiantDAO;
    private UtilDAO utilDAO;

    public SetParametersForFirstConnectionTask(Context context) {
        etudiantDAO = new EtudiantDAO(context);
        utilDAO = new UtilDAO(context);
        Log.i("tagasync", "instaciation over");
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tagasync", ":::::::::setParam doInBackground - start");
//        HashMap<String, String> paramsMap = new HashMap<>(1);
//        paramsMap.put(UtilDAO.FIELD_LAST_NUM_SUP, Integer.toString(utilDAO.getLastNumSup()));
        HttpURLConnection conn = ServerConnection.getConnection(PHP_FILE, null);
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
                    publishProgress("set params ...");
                    Log.i("tagasync", " set params...");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("tagasync", " JsonArrayCreated...");
                    JSONObject c = null;
                    try {
                        if (jsonArray.length() > 0) {
                            c = jsonArray.getJSONObject(0);
                            int lastDeleted = c.getInt("num_sync_etu_sup");
                            utilDAO.setLastNumSup(lastDeleted);
                            Log.i("tagasync", "---//---------- Last Deleted first time launch: " + lastDeleted);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
}
