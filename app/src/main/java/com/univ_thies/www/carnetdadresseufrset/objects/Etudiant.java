package com.univ_thies.www.carnetdadresseufrset.objects;

import com.univ_thies.www.carnetdadresseufrset.util.Utilitaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by layely on 11/26/16.
 */

public class Etudiant implements Serializable {

    public static final String[] niveaux = {"L1", "L2", "L3", "M1", "M2"};
    String ine;
    long numDossier;
    String nom;
    String prenom;
    String dateNais;
    char sexe;
    long mobile1;
    long mobile2;
    String email;
    String addresse;
    String specialite = "";
    Filiere filiere;
    String niveau;
    int sync;
    int modif_sync;

    public Etudiant(String ine, long numDossier, String nom, String prenom, String dateNais, Character sexe, Long mobile1, Long mobile2, String email, String addresse, String specialite, Filiere filiere, String niveau, int sync, int modif_sync) {

        this.ine = Utilitaire.filterString(ine);
        this.numDossier = numDossier;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = Utilitaire.filterString(dateNais);
        this.sexe = Utilitaire.filterString(sexe.toString()).charAt(0);
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.email = email;
        this.addresse = Utilitaire.filterString(addresse);
        this.specialite = Utilitaire.filterString(specialite);
        this.filiere = filiere;
        this.niveau = niveau;
        this.sync = sync;
        this.modif_sync = modif_sync;
    }

    public Etudiant(int sync, int modif_sync) {
        this.sync = sync;
        this.modif_sync = modif_sync;
    }

    public static List<Etudiant> search(List<Etudiant> etudiants, String toSearch) {
        if (etudiants == null)
            return null;
        ArrayList<Etudiant> found = new ArrayList<>(25);
        if (toSearch.isEmpty()) {
            return found;
        }

        for (Etudiant etu : etudiants) {
            if (etu.getNom().contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (etu.getPrenom().contains(toSearch.toUpperCase()))
                found.add(etu);
            else if ((etu.getPrenom() + " " + etu.getNom()).contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (etu.getIne().contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (etu.getAddresse().contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (String.valueOf(etu.getMobile1()).contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (String.valueOf(etu.getMobile2()).contains(toSearch.toUpperCase()))
                found.add(etu);
            else if (etu.getEmail().contains(toSearch))
                found.add(etu);
        }

        return found;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateNais() {
        return dateNais;
    }

    public void setDateNais(String dateNais) {
        this.dateNais = dateNais;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    public long getMobile1() {
        return mobile1;
    }

    public void setMobile1(long mobile1) {
        this.mobile1 = mobile1;
    }

    public long getMobile2() {
        return mobile2;
    }

    public void setMobile2(long mobile2) {
        this.mobile2 = mobile2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        if (specialite != null && specialite.equalsIgnoreCase("null"))
            this.specialite = specialite;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public int getSync() {
        return sync;
    }

    public int getModifSync() {
        return modif_sync;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public long getNumDossier() {
        return numDossier;
    }

    public void setNumDossier(long numDossier) {
        this.numDossier = numDossier;
    }
}
