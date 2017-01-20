package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.univ_thies.www.carnetdadresseufrset.Adapters.ListEtudiantAdapter;
import com.univ_thies.www.carnetdadresseufrset.activities.HomeActivity;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * Created by layely on 12/16/16.
 */

public class ServerConnection {
    public static final String RESULT_TAG = "result";
    public static final String DATA_TAG = "data";
    public static final String MESSAGE_TAG = "message";
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECTION_TIMEOUT = 5000;
    //    private static final String URLstr = "http://172.20.10.2/CarnetAdresseRestful";
    private static final String URLstr = "http://192.168.1.38/CarnetAdresseRestful";

    public static HttpURLConnection getConnection(String phpfile, HashMap<String, String> params) {
        HttpURLConnection conn = null;
        URL url = null;

        try {
//            url = new URL(URLstr);
            Uri.Builder urlBuider = Uri.parse(URLstr).buildUpon().appendPath(phpfile);
            if (params != null)
                for (String key : params.keySet()) {
                    urlBuider.appendQueryParameter(key, params.get(key));
                }
//                    .appendQueryParameter(UtilDAO.FIELD_LAST_NUM_SYNCED, Integer.toString(utilDAO.getLastNumSynced()))

            url = new URL(urlBuider.build().toString());
            Log.i("tagasync", ":::::::::url instance");

            Log.i("tagasync", ":::::::::open connection start");
            // Setup HttpURLConnection class to send and receive data from php and mysql
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("GET");


            // setDoInput and setDoOutput method depict handling of both send and receive
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.i("tagasync", ":::::::::open connection end");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static String getResultString(HttpURLConnection conn) throws IOException {
        InputStream input = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder result = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        Log.i("tagasync", "::::::::::::raw json string ::::::: " + result.toString());
        return result.toString();
    }

    public static void executeUpdate(final Context context, final ListView listView, View fab, final SwipeRefreshLayout swipeRefreshLayout) throws ExecutionException, InterruptedException {
        final Snackbar snackbar = Snackbar.make(HomeActivity.coordinatorLayout, "Synchronisation en cours ...", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null);
        snackbar.show();

        if (swipeRefreshLayout != null && !swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }

        final UtilDAO utilDAO = new UtilDAO(context);
        final DeleteEtudiantInServerTask deleteEtudiantInServerTask = new DeleteEtudiantInServerTask(context);
        final FetchEtudiantTask fetchEtudiantTask = new FetchEtudiantTask(context);
        final DeleteEtudiantInLocalTask deleteEtudiantInLocalTask = new DeleteEtudiantInLocalTask(context);
        final AddEtudiantTask addEtudiantTask = new AddEtudiantTask(context);
        final SetParametersForFirstConnectionTask setinitialParamsTask = new SetParametersForFirstConnectionTask(context);
        Log.i("tagasync", "number of sync; " + utilDAO.getNumberOfSync());

        deleteEtudiantInServerTask.execute();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (utilDAO.getNumberOfSync() == 0) {
                        setinitialParamsTask.execute();
                        setinitialParamsTask.get();
                    }
                    deleteEtudiantInServerTask.get();
                    deleteEtudiantInLocalTask.execute();
                    deleteEtudiantInLocalTask.get();
                    fetchEtudiantTask.execute();
                    fetchEtudiantTask.get();
                    addEtudiantTask.execute();
                    addEtudiantTask.get();
                    utilDAO.incrementNumberOfSync();

                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (listView != null && listView.getAdapter() instanceof ListEtudiantAdapter) {
                                ListEtudiantAdapter adapter = ((ListEtudiantAdapter) listView.getAdapter());
                                adapter.clear();
                                adapter.addAll(new EtudiantDAO(context).getAllEtudiants());
                            }
                            if (swipeRefreshLayout != null)
                                swipeRefreshLayout.setRefreshing(false);

                            snackbar.setText("Synchronisation Términé");
                            snackbar.dismiss();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }

}

