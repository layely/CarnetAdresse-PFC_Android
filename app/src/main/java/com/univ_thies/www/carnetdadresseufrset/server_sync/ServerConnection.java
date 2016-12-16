package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.net.Uri;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by layely on 12/16/16.
 */

public class ServerConnection {
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECTION_TIMEOUT = 5000;

    private static final String URLstr = "http://172.20.10.2/CarnetAdresseRestful";

    public static HttpURLConnection getConnection(String phpfile, UtilDAO utilDAO) {

        HttpURLConnection conn = null;
        URL url = null;

        try {
//            url = new URL(URLstr);
            url = new URL(Uri.parse(URLstr)
                    .buildUpon()
                    .appendPath(phpfile)
                    .appendQueryParameter(UtilDAO.FIELD_LAST_NUM_SYNCED, Integer.toString(utilDAO.getLastNumSynced()))
                    .build().toString());
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

            // Append parameters to URL
//            Uri.Builder builder = new Uri.Builder()
//                    .appendQueryParameter(UtilDAO.FIELD_LAST_NUM_SYNCED, Integer.toString(utilDAO.getLastNumSynced()));
//            String query = builder.build().getEncodedQuery();
//            Log.i("tagasync", "::::::::: getencoded query === " + query);
//            Log.i("tagasync", "::::::::: raw query === " + builder.build().toString());

            // Open connection for sending data
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(
//                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(query);
//            writer.flush();
//            writer.close();
//            os.close();

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

}

