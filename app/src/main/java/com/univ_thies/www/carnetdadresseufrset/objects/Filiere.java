package com.univ_thies.www.carnetdadresseufrset.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by layely on 11/26/16.
 */

public class Filiere implements Serializable {

    private ArrayList<String> specialites;
    private String libelleFiliere;
    private int sync;
    private int modifSync;

//    public Filiere(String libelleFiliere) {
//        this.libelleFiliere = libelleFiliere;
//    }

    public Filiere(String libelleFiliere, ArrayList<String> specialites, int sync, int modifsync) {
        this.libelleFiliere = libelleFiliere;
        this.specialites = specialites;
        this.sync = sync;
        this.modifSync = modifsync;
    }

    public Filiere(String libelleFiliere, int sync, int modifSync, String... specialites) {
        this(libelleFiliere, new ArrayList(Arrays.asList(specialites)), sync, modifSync);
    }

    public static ArrayList<String> getListOfSpecialites(String specialitesStr) {
        if (specialitesStr == null || specialitesStr.isEmpty())
            return null;
        return new ArrayList<String>(Arrays.asList(specialitesStr.split("\\|")));
    }

    public static String getStringOfSpecialites(List<String> specialites) {
        StringBuilder strBuilder = new StringBuilder("");
        for (String sp : specialites) {
            strBuilder.append(sp + "|");
        }
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Filiere))
            return false;

        Filiere a = (Filiere) o;

        return this.getLibelleFiliere().equalsIgnoreCase(a.getLibelleFiliere());

    }

    public String getLibelleFiliere() {
        return libelleFiliere;
    }

    public void setLibelleFiliere(String libelleFiliere) {
        this.libelleFiliere = libelleFiliere;
    }

    public ArrayList<String> getSpecialites() {
        return specialites;
    }

    public void setSpecialites(ArrayList<String> specialites) {
        this.specialites = specialites;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getModifSync() {
        return modifSync;
    }

    public void setModifSync(int modifSync) {
        this.modifSync = modifSync;
    }
}
