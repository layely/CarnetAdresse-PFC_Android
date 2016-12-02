package com.univ_thies.www.carnetdadresseufrset.objects;

import java.io.Serializable;

/**
 * Created by layely on 11/26/16.
 */

public class Promo implements Serializable {

    int annee1;
    int annee2;
    int sync;
    int modifSync;

    public Promo(String promoStr, int sync, int modifSync) {

        String[] promos = promoStr.split("-");
        int annee1 = Integer.parseInt(promos[0].trim());
        int annee2 = Integer.parseInt(promos[1].trim());

        this.annee1 = annee1;
        this.annee2 = annee2;
        this.sync = sync;
        this.modifSync = modifSync;
    }

    public Promo(int annee1, int annee2, int sync, int modifSync) {
        this.annee1 = annee1;
        this.annee2 = annee2;
        this.sync = sync;
        this.modifSync = modifSync;

    }

    public String getPromo() {
        return annee1 + "-" + annee2;
    }

    public void setPromo(int annee1, int annee2) {
        this.annee1 = annee1;
        this.annee2 = annee2;
    }

    @Override
    public String toString() {
        return this.getAnnee1() + "-" + this.getAnnee2();
    }

    public int getAnnee1() {
        return annee1;
    }

    public int getAnnee2() {
        return annee2;
    }

    public int getSync() {
        return sync;
    }

    public int getModifSync() {
        return modifSync;
    }
}
