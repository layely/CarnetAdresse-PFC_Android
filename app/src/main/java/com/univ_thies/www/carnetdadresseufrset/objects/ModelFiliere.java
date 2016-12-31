package com.univ_thies.www.carnetdadresseufrset.objects;

import java.util.ArrayList;

/**
 * Created by layely on 12/9/16.
 */

public class ModelFiliere {
    Filiere filiere;
    boolean selected;
    private ArrayList<ModelClasse> classes;

    public ModelFiliere(Filiere filiere) {
        this.filiere = filiere;
        selected = false;
        classes = new ArrayList<>();
        classes.add(new ModelClasse("L1", null, this));
        classes.add(new ModelClasse("L2", null, this));
        classes.add(new ModelClasse("L3", null, this));
        classes.add(new ModelClasse("M1", null, this));
        classes.add(new ModelClasse("M2", null, this));
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    public ArrayList<ModelClasse> getClasses() {
        return classes;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isAllChildSelected() {
        boolean allareSelected = true;
        for (ModelClasse mc : classes) {
            if (!mc.isSelected()) {
                allareSelected = false;
                break;
            }
        }
        return allareSelected;
    }

    public void setAllChildSelected(boolean selected) {
        for (ModelClasse mc : classes) {
            mc.setSelected(selected);
        }
    }

    public boolean isAllChildUnSelected() {
        boolean allareUnSelected = true;
        for (ModelClasse mc : classes) {
            if (mc.isSelected()) {
                allareUnSelected = false;
                break;
            }
        }
        return allareUnSelected;
    }

    public boolean include(Etudiant etudiant) {
        return etudiant.getFiliere().getLibelleFiliere().equalsIgnoreCase(this.getFiliere().getLibelleFiliere());
    }


}
