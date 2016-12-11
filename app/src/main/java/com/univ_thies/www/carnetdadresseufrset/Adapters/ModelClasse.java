package com.univ_thies.www.carnetdadresseufrset.Adapters;

/**
 * Created by layely on 12/9/16.
 */

public class ModelClasse {
    private String niveau;
    private String option;
    private boolean selected;

    public ModelClasse(String niveau, String option) {
        this.niveau = niveau;
        this.option = option;
        selected = false;
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
}
