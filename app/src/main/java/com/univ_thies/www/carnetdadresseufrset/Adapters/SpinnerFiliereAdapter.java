package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.metier.Filiere;

import java.util.List;

/**
 * Created by layely on 12/4/16.
 */

public class SpinnerFiliereAdapter extends ArrayAdapter<Filiere> {

    public SpinnerFiliereAdapter(Context context, int resource, List<Filiere> objects) {
        super(context, resource, objects);
    }


    public SpinnerFiliereAdapter(Context context, int resource, int textViewResourceId, List<Filiere> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Filiere filiere = getItem(position);

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_filiere, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.textFil_item);
        textView.setText(filiere.getLibelleFiliere());
        return itemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Filiere filiere = getItem(position);

        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_filiere, parent, false);
        TextView textView = (TextView) itemView.findViewById(R.id.textFil_item);
        textView.setText(filiere.getLibelleFiliere());
        return itemView;
    }
}
