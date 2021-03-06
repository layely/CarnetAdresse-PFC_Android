package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;

public class DisplayEtudiantActivity extends AppCompatActivity implements DisplayEtudiantFragment.OnFragmentInteractionListener {

    public static final String KEY_ETU_SER = "com.univ_thies.www.carnetdadresseufrset.activities.serkey";
    public static final String KEY_ADD = "add";
    public static final int REQUEST_CODE = 1;
    public static final int RESPONSE_CODE_EXIT = 1;
    public static final int RESPONSE_CODE_RELOAD = 2;
    DisplayEtudiantFragment fragment;
    private Etudiant etudiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        etudiant = (Etudiant) getIntent().getSerializableExtra(HomeActivity.SER_KEY_ETU);
//        Toast.makeText(this, etudiant.getPrenom(), Toast.LENGTH_LONG).show();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragment = DisplayEtudiantFragment.newInstance(etudiant);
        ft.replace(R.id.fragment_display_etu, fragment);
        ft.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESPONSE_CODE_EXIT) {
                finish();
            } else if (resultCode == RESPONSE_CODE_RELOAD) {
                etudiant = (Etudiant) data.getSerializableExtra(DisplayEtudiantActivity.KEY_ETU_SER);
                Log.i("displayactivity", "nom et prenom: " + etudiant.getNom() + " " + etudiant.getPrenom());
                if (fragment != null) {
                    fragment.reload(etudiant);
                }
            }
        }
    }
}
