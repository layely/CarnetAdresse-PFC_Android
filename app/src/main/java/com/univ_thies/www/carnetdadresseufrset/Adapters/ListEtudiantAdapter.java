package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by layely on 11/27/16.
 */

public class ListEtudiantAdapter extends ArrayAdapter<Etudiant> {
    private String toHighlight = "";

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

        String nomPrenom = etudiant.getPrenom() + " " + etudiant.getNom();
        String[] splited = nomPrenom.split(Pattern.quote(toHighlight));
        String toHightlightStyled = "<font size=\"\" color=\"#FF0000\" face=\"\">" + toHighlight + "</font>";
        StringBuilder toHTML = new StringBuilder();
        for (int i = 0; i < splited.length; i++) {
            String str = splited[i];
            Log.i("tag", "i ======= " + i + "/" + (splited.length - 1));
            Log.i("tag", "Str ====&" + str + "&");

            toHTML.append(str);
            if (i == splited.length - 1 && !str.equalsIgnoreCase(toHighlight)) ;
            else
                toHTML.append(toHightlightStyled);
        }
        String subStr = nomPrenom.substring(nomPrenom.length() - toHighlight.length());
        Log.i("tag", "SubStr :::::::&" + subStr);
        if (subStr.equalsIgnoreCase(toHighlight))
            toHTML.append(toHightlightStyled);

        Log.i("tag", "::::::: toHighlightStr: " + toHighlight.toString());
        Log.i("tag", "::::::: toHLML: " + toHTML.toString());
//        textviewNomPrenom.setText(Html.fromHtml("<font size=\"\" color=\"#FF0000\" face=\"\">Laye</font> Ly"));
        textviewNomPrenom.setText(Html.fromHtml(toHTML.toString()));
        textviewINE.setText(etudiant.getIne());
        textviewFilProm.setText(etudiant.getFiliere().getLibelleFiliere() + " " + etudiant.getPromo().toString());

        return row;
    }

    public String getToHighlight() {
        return toHighlight;
    }

    public void setToHighlight(String toHighlight) {
        this.toHighlight = toHighlight.toUpperCase();
    }
}
