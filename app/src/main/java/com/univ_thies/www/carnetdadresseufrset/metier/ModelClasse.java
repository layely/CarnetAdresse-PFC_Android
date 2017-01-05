package com.univ_thies.www.carnetdadresseufrset.metier;

/**
 * Created by layely on 12/9/16.
 */

public class ModelClasse {
    private String niveau;
    private String option;
    private boolean selected;
    private ModelFiliere parent;

    public ModelClasse(String niveau, String option, ModelFiliere parent) {
        this.niveau = niveau;
        this.option = option;
        selected = false;
        this.parent = parent;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        String str = niveau;
        if (option != null)
            str += " " + option;
        return str;
    }

    public boolean include(Etudiant etudiant) {
        if (this.parent.include(etudiant) && this.getNiveau().equalsIgnoreCase(etudiant.getNiveau())) {
            if (option == null || option.isEmpty() || option.equalsIgnoreCase(etudiant.getSpecialite()))
                return true;
        }
        return false;
    }
}
