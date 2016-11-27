package com.univ_thies.www.carnetdadresseufrset.objects;

/**
 * Created by layely on 11/26/16.
 */

public class Etudiant {

    String ine;
    String nom;
    String prenom;
    String dateNais;
    char sexe;
    long mobile1;
    long mobile2;
    String email;
    String addresse;
    String specialite;
    Filiere filiere;
    Promo promo;
    int sync;
    int modif_sync;

    public Etudiant(String ine, String nom, String dateNais, char sexe, String email, String addresse, String specialite, long mobile1, Filiere filiere, Promo promo, int sync, int modif_sync) {
        this.ine = ine;
        this.nom = nom;
        this.dateNais = dateNais;
        this.sexe = sexe;
        this.email = email;
        this.addresse = addresse;
        this.specialite = specialite;
        this.mobile1 = mobile1;
        this.promo = promo;
        this.filiere = filiere;
        this.sync = sync;
        this.modif_sync = modif_sync;
    }

    public Etudiant(String ine, String nom, String prenom, String dateNais, char sexe, long mobile1, long mobile2, String email, String addresse, String specialite, Filiere filiere, Promo promo, int sync, int modif_sync) {
        this.ine = ine;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNais = dateNais;
        this.sexe = sexe;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.email = email;
        this.addresse = addresse;
        this.specialite = specialite;
        this.filiere = filiere;
        this.promo = promo;
        this.sync = sync;
        this.modif_sync = modif_sync;
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
        this.specialite = specialite;
    }

    public String getPrenom() {
        return prenom;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public Promo getPromo() {
        return promo;
    }

    public int getSync() {
        return sync;
    }

    public int getModifSync() {
        return modif_sync;
    }
}
