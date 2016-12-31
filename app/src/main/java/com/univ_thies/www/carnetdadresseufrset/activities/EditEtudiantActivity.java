package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.univ_thies.www.carnetdadresseufrset.Adapters.SpinnerFiliereAdapter;
import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.FiliereDAO;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.objects.Filiere;
import com.univ_thies.www.carnetdadresseufrset.util.Utilitaire;

import java.util.List;

public class EditEtudiantActivity extends AppCompatActivity {

    EditText editTextNom;
    EditText editTextPrenom;
    EditText editTextINE;
    EditText editTextNumDossier;
    EditText editTextMob1;
    EditText editTextMob2;
    EditText editTextEmail;
    EditText editTextAddr;

    DatePicker datepickerDateNaiss;
    Spinner spinnerSexe;
    Spinner spinnerFiliere;
    Spinner spinnerSpecialite;
    Spinner spinnerNiveau;
    Etudiant etudiant;
    boolean toAdd;
    private EtudiantDAO etudiantDAO;
    private FiliereDAO filiereDAO;

    private ImageButton imageButtonDelete;
    private ImageButton imageButtonValidate;

    private View layoutSpinnerSpecialite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_etudiant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("hello");
        Intent intent = getIntent();
        initDAOs();
        initWidgets();

        if (intent.getExtras() == null) {
            etudiant = null;
            toAdd = true;
            imageButtonDelete.setVisibility(View.GONE);
            getSupportActionBar().setTitle("Ajout d'un étudiant");
        } else {
            getSupportActionBar().setTitle("Modification");
            etudiant = (Etudiant) intent.getSerializableExtra(DisplayEtudiantActivity.KEY_ETU_SER);
            toAdd = intent.getBooleanExtra(DisplayEtudiantActivity.KEY_ADD, true);
            loadEtudiantIntoWidget(etudiant);
        }

        imageButtonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnValidateAction();
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bntSupprimerAction();
            }
        });


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
        fab.setVisibility(View.GONE);
    }


    private void initWidgets() {
        imageButtonDelete = (ImageButton) findViewById(R.id.imagebuttonDelete);
        imageButtonValidate = (ImageButton) findViewById(R.id.imagebuttonValidate);
        editTextNom = (EditText) findViewById(R.id.editTextNomEdit);
        editTextPrenom = (EditText) findViewById(R.id.editTextPrenomEdit);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailEdit);
        editTextINE = (EditText) findViewById(R.id.editTextINEEdit);
        editTextNumDossier = (EditText) findViewById(R.id.editTextNumDossierEdit);
        editTextMob1 = (EditText) findViewById(R.id.editTextMob1Edit);
        editTextMob2 = (EditText) findViewById(R.id.editTextMob2Edit);
        editTextAddr = (EditText) findViewById(R.id.editTextAddrEdit);


        spinnerFiliere = (Spinner) findViewById(R.id.spinnerFilEdit);
        List<Filiere> filieres = filiereDAO.getAllFiliere();
        SpinnerFiliereAdapter filiereAdapter = new SpinnerFiliereAdapter(this, R.layout.item_filiere, R.id.textFil_item, filieres);
        spinnerFiliere.setAdapter(filiereAdapter);

        spinnerNiveau = (Spinner) findViewById(R.id.spinnerNiveau);
        spinnerNiveau.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, Etudiant.niveaux));

        spinnerSexe = (Spinner) findViewById(R.id.spinnerSexeEdit);

        layoutSpinnerSpecialite = findViewById(R.id.layoutSpinnerSpecialiteEdit);
        spinnerSpecialite = (Spinner) findViewById(R.id.spinnerSpecialite);

        spinnerFiliere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("tag_specialite", ":::::: onItemSelected");
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
        Log.i("tag_specialite", "::::::::::::: on Update Spinner Specialite");
        Filiere fil = (Filiere) spinnerFiliere.getSelectedItem();
        Log.i("tag_specialite", "::::::::::::: filiere :** " + fil.getLibelleFiliere());
        if (fil.getSpecialites() != null)
            for (String spec : fil.getSpecialites())
                Log.i("tag_specialite", "::::::::::::: spec :** " + spec);
        List<String> specialites = fil.getSpecialites();

        if (specialites == null || specialites.size() <= 0) {
            Log.i("tag_specialite", "Specialite is null");
            layoutSpinnerSpecialite.setVisibility(View.GONE);
        } else {
            Log.i("tag_specialite", "Speciales: not null");
            spinnerSpecialite.setAdapter(new ArrayAdapter<String>(this, R.layout.item_filiere, R.id.textFil_item, specialites));
            layoutSpinnerSpecialite.setVisibility(View.VISIBLE);
            for (String spec : specialites) {
                Log.i("tag_specialite", "specialite: " + spec);
            }
        }
    }

    private void initDAOs() {
        etudiantDAO = new EtudiantDAO(this);
        filiereDAO = new FiliereDAO(this);
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

        String INE = editTextINE.getText().toString();

        String numDossierStr = editTextNumDossier.getText().toString();
        Long numDossier = Long.valueOf(0);
        if (numDossierStr.isEmpty()) {
            editTextNumDossier.requestFocusFromTouch();
            return null;
        } else {
            try {
                numDossier = Long.parseLong(numDossierStr);
            } catch (Exception e) {
                editTextNumDossier.requestFocus();
                return null;
            }
        }

        long mobile1 = 0;
        if (!editTextMob1.getText().toString().isEmpty())
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

        String dateNaiss = Utilitaire.getDateString(datepickerDateNaiss);

        int sync = 0;
        int modif_sync = 0;
//        if (toAdd)
        sync = 1;
//        else
//            modif_sync = 1;

        Etudiant etu = new Etudiant(sync, modif_sync);
        etu.setNom(nom);
        etu.setPrenom(prenom);
        etu.setFiliere(filiere);
        etu.setIne(INE);
        etu.setNumDossier(numDossier);
        etu.setMobile1(mobile1);
        etu.setMobile2(mobile2);
        etu.setEmail(email);
        etu.setSexe(sexe);
        etu.setAddresse(addresse);
        etu.setDateNais(dateNaiss);
        etu.setNiveau((String) spinnerNiveau.getSelectedItem());

        if (spinnerSpecialite.getVisibility() == View.VISIBLE) {
            etu.setSpecialite((String) spinnerSpecialite.getSelectedItem());
        }

        return etu;
    }

    private void loadEtudiantIntoWidget(Etudiant etudiant) {
        editTextNom.setText(etudiant.getNom().toString());
        editTextPrenom.setText(etudiant.getPrenom().toString());
        editTextMob1.setText(String.valueOf(etudiant.getMobile1()));
        String strMobile2 = String.valueOf(etudiant.getMobile2());
        if (!strMobile2.equalsIgnoreCase("0")) {
            editTextMob2.setText(strMobile2);
        }
        editTextEmail.setText(etudiant.getEmail());
        editTextINE.setText(etudiant.getIne().toString());
        editTextNumDossier.setText(Long.toString(etudiant.getNumDossier()));
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
            Intent i = new Intent(this, EditEtudiantActivity.class);
            i.putExtra(DisplayEtudiantActivity.KEY_ETU_SER, newEtu);
            setResult(DisplayEtudiantActivity.RESPONSE_CODE_RELOAD, i);
        }
        finish();
    }


    private void bntSupprimerAction() {
        if (etudiant != null) {
            Log.i("deleteFromLocal", "buttonDeletePresssed : " + etudiant.getPrenom() + " " + etudiant.getNom());
            Snackbar.make(new CoordinatorLayout(EditEtudiantActivity.this), "deleted: " + etudiant.getPrenom(), Snackbar.LENGTH_INDEFINITE).show();
            etudiantDAO.deleteFromLocal(etudiant);
            setResult(DisplayEtudiantActivity.RESPONSE_CODE_EXIT);
            finish();
        }
    }
}
