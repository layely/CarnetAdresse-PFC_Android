package com.univ_thies.www.carnetdadresseufrset.objects;

/**
 * Created by layely on 11/26/16.
 */

public class Promo {

    int annee1;
    int annee2;

    public Promo(int annee1, int annee2) {
        this.annee1 = annee1;
        this.annee2 = annee2;

    }

    public static Promo getInstance(String promoStr) {
        //TODO
        return null;
    }

    public String getPromo() {
        return annee1 + "-" + annee2;
    }

    public void setPromo(int annee1, int annee2) {
        this.annee1 = annee1;
        this.annee2 = annee2;
    }

    public int getAnnee1() {
        return annee1;
    }

    public int getAnnee2() {
        return annee2;
    }
}
