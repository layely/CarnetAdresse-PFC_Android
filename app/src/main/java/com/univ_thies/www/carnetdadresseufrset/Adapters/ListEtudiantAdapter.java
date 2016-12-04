package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

import java.util.List;

/**
 * Created by layely on 11/27/16.
 */

public class ListEtudiantAdapter extends ArrayAdapter<Etudiant> {

    public ListEtudiantAdapter(Context context, List<Etudiant> etudiants) {
        super(context, R.layout.row_list_etudiant, etudiants);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater li = LayoutInflater.from(parent.getContext());
            row = li.inflate(R.layout.row_list_etudiant, parent, false);
        }

        TextView textviewNomPrenom = (TextView) row.findViewById(R.id.textviewNomPrenom);
        TextView textviewINE = (TextView) row.findViewById(R.id.textviewINE);
        TextView textviewFilProm = (TextView) row.findViewById(R.id.textviewFilPro);

        Etudiant etudiant = getItem(position);

        textviewNomPrenom.setText(etudiant.getPrenom() + " " + etudiant.getNom());
        textviewINE.setText(etudiant.getIne());
        textviewFilProm.setText(etudiant.getFiliere().getLibelleFiliere() + " " + etudiant.getPromo().toString());

        return row;
    }
}
