package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.univ_thies.www.carnetdadresseufrset.Adapters.SpinnerFiliereAdapter;
import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.FiliereDAO;
import com.univ_thies.www.carnetdadresseufrset.database.PromoDAO;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;
import com.univ_thies.www.carnetdadresseufrset.objects.Promo;
import com.univ_thies.www.carnetdadresseufrset.util.Utilitaire;

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
    Spinner spinnerSpecialite;
    AutoCompleteTextView autoCompleteTextViewPromo;
    Etudiant etudiant;
    boolean toAdd;
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

        initDAOs();
        initWidgets();

        if (intent.getExtras() == null) {
            etudiant = null;
            toAdd = true;
        } else {
            etudiant = (Etudiant) intent.getSerializableExtra(DisplayEtudiantActivity.KEY_ETU_SER);
            toAdd = intent.getBooleanExtra(DisplayEtudiantActivity.KEY_ADD, true);
            loadEtudiantIntoWidget(etudiant);
        }

//        Toast.makeText(this, etudiant.getEmail() + " " + toAdd, Toast.LENGTH_LONG).show();


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
                btnValidateAction();
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
        List<Filiere> filieres = filiereDAO.getAllFiliere();

        SpinnerFiliereAdapter filiereAdapter = new SpinnerFiliereAdapter(this, R.layout.item_filiere, R.id.textFil_item, filieres);
        spinnerFiliere.setAdapter(filiereAdapter);
        spinnerSexe = (Spinner) findViewById(R.id.spinnerSexeEdit);

        spinnerSpecialite = (Spinner) findViewById(R.id.spinnerSpecialite);

        spinnerFiliere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateSpinnerSpecialite();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updateSpinnerSpecialite();


        String[] sexes = {"MASCULIN", "FEMININ"};
        spinnerSexe.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, sexes));


        datepickerDateNaiss = (DatePicker) findViewById(R.id.datepickerDateNaisEdit);
    }

    private void updateSpinnerSpecialite() {
        Log.i("tag", "::::::::::::: on Update Spinner Specialite");
        Filiere fil = (Filiere) spinnerFiliere.getSelectedItem();
        List<String> specialites = fil.getSpecialites();


        if (specialites == null || specialites.size() <= 0) {
            Log.i("tag", "Specialite is null");
            spinnerSpecialite.setVisibility(View.GONE);
        } else {
            Log.i("tag", "Speciales: not null");
            spinnerSpecialite.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, specialites));
            spinnerSpecialite.setVisibility(View.VISIBLE);
            for (String spec : specialites) {
                Log.i("tag", spec);
            }
        }
    }

    private void initDAOs() {
        etudiantDAO = new EtudiantDAO(this);
        filiereDAO = new FiliereDAO(this);
        promoDAO = new PromoDAO(this);
    }

    private Etudiant createEtudiant() {
        String nom = editTextNom.getText().toString();
        if (!Utilitaire.isValidName(nom)) {
            editTextNom.requestFocus();
            return null;
        }

        String prenom = editTextPrenom.getText().toString();
        if (!Utilitaire.isValidName(nom)) {
            editTextNom.requestFocus();
            return null;
        }

        Filiere filiere = (Filiere) spinnerFiliere.getSelectedItem();

        Promo promo = new Promo(autoCompleteTextViewPromo.getText().toString(), 0, 0);
        String INE = editTextINE.getText().toString();
        if (INE.isEmpty()) {
            editTextINE.requestFocus();
            return null;
        }

        long mobile1;
        try {
            mobile1 = Long.parseLong(editTextMob1.getText().toString());
        } catch (Exception e) {
            editTextMob1.requestFocus();
            return null;
        }

        long mobile2 = 0;
        if (!editTextMob2.getText().toString().isEmpty()) {
            try {
                mobile2 = Long.parseLong(editTextMob2.getText().toString());
            } catch (Exception e) {
                editTextMob2.requestFocus();
                return null;
            }
        }

        String email = editTextEmail.getText().toString();

        if (!Utilitaire.isValidEmail(email)) {
            editTextEmail.requestFocus();
            return null;
        }

        char sexe = ((String) spinnerSexe.getSelectedItem()).charAt(0);

        String addresse = editTextAddr.getText().toString();
        if (addresse.isEmpty()) {
            editTextAddr.requestFocus();
            return null;
        }

        String dateNaiss = Utilitaire.getDateString(datepickerDateNaiss);

        int sync = 0;
        int modif_sync = 0;
        if (toAdd)
            sync = 1;
        else
            modif_sync = 1;

        Etudiant etu = new Etudiant(sync, modif_sync);
        etu.setNom(nom);
        etu.setPrenom(prenom);
        etu.setFiliere(filiere);
        etu.setPromo(promo);
        etu.setIne(INE);
        etu.setMobile1(mobile1);
        etu.setMobile2(mobile2);
        etu.setEmail(email);
        etu.setSexe(sexe);
        etu.setAddresse(addresse);
        etu.setDateNais(dateNaiss);

        if (spinnerSpecialite.getVisibility() == View.VISIBLE) {
            etu.setSpecialite((String) spinnerSpecialite.getSelectedItem());
        }

        return etu;
    }

    private void loadEtudiantIntoWidget(Etudiant etudiant) {
        editTextNom.setText(etudiant.getNom());
        editTextPrenom.setText(etudiant.getPrenom());
        editTextMob1.setText(String.valueOf(etudiant.getMobile1()));
        String strMobile2 = String.valueOf(etudiant.getMobile2());
        if (!strMobile2.equalsIgnoreCase("0")) {
            editTextMob2.setText(strMobile2);
        }
        editTextEmail.setText(etudiant.getEmail());
        autoCompleteTextViewPromo.setText(etudiant.getPromo().getPromo());
        editTextINE.setText(etudiant.getIne());
        editTextAddr.setText(etudiant.getAddresse());

        int filiereCount = spinnerFiliere.getAdapter().getCount();
        for (int i = 0; i < filiereCount; i++) {
            if (spinnerFiliere.getItemAtPosition(i).equals(etudiant.getFiliere()))
                spinnerFiliere.setSelection(i, true);
        }

        Utilitaire.setDatePicker(etudiant.getDateNais(), datepickerDateNaiss);
    }

    public void btnValidateAction() {
        Etudiant newEtu = createEtudiant();
        if (newEtu == null)
            return;

        if (toAdd) {
            etudiantDAO.addEtudiant(newEtu);
        } else {
            etudiantDAO.modify(etudiant, newEtu);
        }
        finish();
    }
}
