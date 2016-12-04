package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.univ_thies.www.carnetdadresseufrset.Adapters.SpinnerFiliereAdapter;
import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.FiliereDAO;
import com.univ_thies.www.carnetdadresseufrset.database.PromoDAO;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;
import com.univ_thies.www.carnetdadresseufrset.objects.Promo;

import java.util.ArrayList;
import java.util.List;

public class EditEtudiantActivity extends AppCompatActivity {

    EditText editTextNom;
    EditText editTextPrenom;
    EditText editTextINE;
    EditText editTextMob1;
    EditText editTextMob2;
    EditText editTextEmail;
    EditText editTextAddr;

    DatePicker datepickerDateNaiss;
    Spinner spinnerSexe;
    Spinner spinnerFiliere;
    AutoCompleteTextView autoCompleteTextViewPromo;

    private EtudiantDAO etudiantDAO;
    private FiliereDAO filiereDAO;
    private PromoDAO promoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Etudiant etudiant = (Etudiant) intent.getSerializableExtra(DisplayEtudiantActivity.KEY_ETU_SER);
        boolean toAdd = intent.getBooleanExtra(DisplayEtudiantActivity.KEY_ADD, true);

        Toast.makeText(this, etudiant.getEmail() + " " + toAdd, Toast.LENGTH_LONG).show();

        initDAOs();
        initWidgets();

        loadEtudiantIntoWidget(etudiant);

//        Log.i("tag", "EditEtudiantActivity:::::::::::>  " + etudiant.getNom());


//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        EditEtudiantFragment fragment = EditEtudiantFragment.newInstance(etudiant, toAdd);
//        ft.replace(R.id.fragment_edit_etu, fragment);
//        ft.commit();

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

    private void initWidgets() {
        editTextNom = (EditText) findViewById(R.id.editTextNomEdit);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenomEdit);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailEdit);
        editTextINE = (EditText) findViewById(R.id.editTextINEEdit);
        editTextMob1 = (EditText) findViewById(R.id.editTextMob1Edit);
        editTextMob2 = (EditText) findViewById(R.id.editTextMob2Edit);
        editTextAddr = (EditText) findViewById(R.id.editTextAddrEdit);

        autoCompleteTextViewPromo = (AutoCompleteTextView) findViewById(R.id.autocpPromoEdit);
        List<Promo> promos = promoDAO.getAllPromo();
        List<String> promosStr = new ArrayList<>();
        for (Promo p : promos) {
            promosStr.add(p.getPromo());
        }
        autoCompleteTextViewPromo.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, promosStr));
        autoCompleteTextViewPromo.setThreshold(0);
        spinnerFiliere = (Spinner) findViewById(R.id.spinnerFilEdit);
//        String[] filieres = {"Mathematique & Informatique", "Informatique", "Eeau et Environnement", "Physique & Chimie"};

        List<Filiere> filieres = filiereDAO.getAllFiliere();

        SpinnerFiliereAdapter filiereAdapter = new SpinnerFiliereAdapter(this, R.layout.item_filiere, R.id.textFil_item, filieres);
        spinnerFiliere.setAdapter(filiereAdapter);

        spinnerSexe = (Spinner) findViewById(R.id.spinnerSexeEdit);
        String[] sexes = {"MASCULIN", "FEMININ"};
        spinnerSexe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, sexes));


        datepickerDateNaiss = (DatePicker) findViewById(R.id.datepickerDateNaisEdit);
    }

    private void initDAOs() {
        etudiantDAO = new EtudiantDAO(this);
        filiereDAO = new FiliereDAO(this);
        promoDAO = new PromoDAO(this);
    }

    private void loadEtudiantIntoWidget(Etudiant etudiant) {
        editTextNom.setText(etudiant.getNom());
        editTextPrenom.setText(etudiant.getPrenom());
        editTextMob1.setText(String.valueOf(etudiant.getMobile1()));
        editTextMob2.setText(String.valueOf(etudiant.getMobile2()));
        editTextEmail.setText(etudiant.getEmail());
        autoCompleteTextViewPromo.setText(etudiant.getPromo().getPromo());
        editTextINE.setText(etudiant.getIne());
        editTextAddr.setText(etudiant.getAddresse());

        int filiereCount = spinnerFiliere.getAdapter().getCount();
        for (int i = 0; i < filiereCount; i++) {
            if (spinnerFiliere.getItemAtPosition(i).equals(etudiant.getFiliere()))
                spinnerFiliere.setSelection(i, true);
        }

    }


}
