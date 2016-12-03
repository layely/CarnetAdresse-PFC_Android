package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

public class EditEtudiantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Etudiant etudiant = (Etudiant) intent.getSerializableExtra(DisplayEtudiantActivity.KEY_ETU_SER);
        boolean toAdd = (boolean) intent.getBooleanExtra(DisplayEtudiantActivity.KEY_ADD, true);

        Toast.makeText(this, etudiant.getEmail() + " " + toAdd, Toast.LENGTH_LONG).show();
//        Log.i("tag", "EditEtudiantActivity:::::::::::>  " + etudiant.getNom());


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        EditEtudiantFragment fragment = EditEtudiantFragment.newInstance(etudiant, toAdd);
        ft.replace(R.id.fragment_edit_etu, fragment);
        ft.commit();

//        DisplayEtudiantFragment fragment = DisplayEtudiantFragment.newInstance(etudiant);
//        ft.replace(R.id.fragment_display_etu, fragment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
