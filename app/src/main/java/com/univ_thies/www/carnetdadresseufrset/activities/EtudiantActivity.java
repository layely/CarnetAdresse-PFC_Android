package com.univ_thies.www.carnetdadresseufrset.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

public class EtudiantActivity extends AppCompatActivity implements DisplayEtudiantFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Etudiant etudiant = (Etudiant) getIntent().getSerializableExtra(HomeActivity.SER_KEY_ETU);
//        Toast.makeText(this, etudiant.getPrenom(), Toast.LENGTH_LONG).show();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DisplayEtudiantFragment fragment = DisplayEtudiantFragment.newInstance(etudiant);
        ft.replace(R.id.fragment_display_etu, fragment);
        ft.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
