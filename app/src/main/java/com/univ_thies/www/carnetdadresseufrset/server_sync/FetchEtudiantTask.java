package com.univ_thies.www.carnetdadresseufrset.server_sync;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.univ_thies.www.carnetdadresseufrset.Adapters.ListEtudiantAdapter;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.FiliereDAO;
import com.univ_thies.www.carnetdadresseufrset.database.PromoDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;
import com.univ_thies.www.carnetdadresseufrset.objects.Promo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by layely on 12/13/16.
 */

public class FetchEtudiantTask extends AsyncTask<Void, String, Void> {

    private final String PHP_FILE = "fetch_student.php";
    private List<Etudiant> newEtudiants;
    private Snackbar snackbar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Context context;
    private EtudiantDAO etudiantDAO;
    private UtilDAO utilDAO;
    private ListView listView;
    private HttpURLConnection conn;

    public FetchEtudiantTask(Context context, ListView listView, View view, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        etudiantDAO = new EtudiantDAO(context);
        utilDAO = new UtilDAO(context);
        this.listView = listView;
        snackbar = Snackbar.make(view, "Connection ...", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null);
        Log.i("tagasync", "instaciation over");
        this.swipeRefreshLayout = swipeRefreshLayout;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tagasync", ":::::::::Fetch doInBackground - start");
        HashMap<String, String> paramsMap = new HashMap<>(1);
        paramsMap.put(UtilDAO.FIELD_LAST_NUM_SYNCED, Integer.toString(utilDAO.getLastNumSynced()));
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
                    publishProgress("Récuperation des données ...");
                    Log.i("tagasync", "Récuperation des données ...");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Log.i("tagasync", " JsonArrayCreated...");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = null;
                        try {
                            c = jsonArray.getJSONObject(i);
                            String ine = c.getString("INEetu");
                            long numDossier = c.getLong("numDossierEtu");
                            String nom = c.getString("nomEtu");
                            String prenom = c.getString("prenomEtu");
                            String dateNaiss = c.getString("dateNaissEtu");
                            char sexe = c.getString("sexeEtu").charAt(0);
                            long mobile1 = c.getLong("mobile1Etu");
                            long mobile2 = c.getLong("mobile2Etu");
                            String email = c.getString("emailEtu");
                            String adresse = c.getString("adresseEtu");
                            String specialite = c.getString("specialiteEtu");
                            String niveau = c.getString("niveauEtu");
                            String promo = c.getString("promoEtu");
                            String libeleFiliere = c.getString("libeleFiliereEtu");

                            Promo objectPromo = new PromoDAO(context).getPromo(promo);
                            Filiere objectFiliere = new FiliereDAO(context).getFiliere(libeleFiliere);

                            Etudiant etudiant = new Etudiant(ine, numDossier, nom, prenom, dateNaiss, sexe, mobile1, mobile2, email, adresse, specialite, objectFiliere, objectPromo, niveau, 0, 0);
                            newEtudiants.add(etudiant);
//                            etudiantDAO.addEtudiant(etudiant);
//                            utilDAO.incrementLastNumSynced();
                            Log.i("tagasync", "---//----------etudiant: " + etudiant.getPrenom() + " " + etudiant.getNom() + " " + etudiant.getDateNais() + " " + etudiant.getSpecialite());
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
        Log.i("tagasync", "::::::::: ListEtudiants: " + newEtudiants.size());
        for (Etudiant etudiant : newEtudiants) {
            Log.i("tagasync", "about to add etudiant :::: " + etudiant.getPrenom() + " " + etudiant.getNom());
            etudiantDAO.addEtudiant(etudiant);
            utilDAO.incrementLastNumSynced();
            Log.i("tagasync", "etudiant added + lastNumSynced updated");
        }
//        try {
//            Thread.sleep(1000);
//            snackbar.dismiss();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        if (listView != null && listView.getAdapter() instanceof ListEtudiantAdapter) {
            ListEtudiantAdapter adapter = ((ListEtudiantAdapter) listView.getAdapter());
            adapter.clear();
            adapter.addAll(etudiantDAO.getAllEtudiants());
        }

        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (snackbar != null)
            snackbar.setText(values[0]);
    }

    @Override
    protected void onPreExecute() {
        newEtudiants = new LinkedList<>();
        Log.i("tagasync", ":::::::::onPreExecute - start");
        snackbar.show();
        Log.i("tagasync", ":::::::::onPreExecute - end");
    }

    @Override
    protected void onCancelled() {
        Log.i("tagasync", ":::::::::onCancelled");
//        snackbar.dismiss();
    }
}
