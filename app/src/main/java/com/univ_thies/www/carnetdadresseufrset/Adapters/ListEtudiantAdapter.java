package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by layely on 11/27/16.
 */

public class ListEtudiantAdapter extends ArrayAdapter<Etudiant> implements SectionIndexer {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SECTION = 1;
    List<Etudiant> etudiants;
    private String toHighlight = "";
    private ArrayList<Character> sections;

    public ListEtudiantAdapter(Context context, List<Etudiant> etudiants) {
        super(context, R.layout.row_list_etudiant, etudiants);
        this.etudiants = etudiants;
        sections = new ArrayList<>(26);
        for (Etudiant etudiant : etudiants) {
            Character letter = etudiant.getPrenom().charAt(0);
            if (!sections.contains(letter)) {
                sections.add(letter);
            }
        }
    }

//    public void addSectionHeaderItem(final String section) {
//        sections.add(section);
//        notifyDataSetChanged();
//    }

//    @Override
//    public int getItemViewType(int position) {
//        return sections.contains(position) ? TYPE_SECTION : TYPE_ITEM;
//    }

    @Override
    public Object[] getSections() {
        return this.sections.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {

        int positionForSection = 0;
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getPrenom().toUpperCase().charAt(0) == sections.get(sectionIndex)) {
                positionForSection = i;
                break;
            }
        }
        Log.i("sectionIndexer", "section index: " + sectionIndex);
        Log.i("sectionIndexer", "position for section: " + positionForSection);
        return positionForSection;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

//    @Override
//    public int getItemViewType(int position) {
//        Character c = etudiants.get(position).getPrenom().charAt(0);
//        if ()
//    }

    @Override
    public int getCount() {
        return etudiants.size();
    }

    @Override
    public Etudiant getItem(int position) {
        return etudiants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        int rowType = getItemViewType(position);

        if (rowType == TYPE_ITEM) {
            if (row == null) {
                LayoutInflater li = LayoutInflater.from(parent.getContext());
                row = li.inflate(R.layout.row_list_etudiant, parent, false);
            }

            ImageView imageView = (ImageView) row.findViewById(R.id.imageviewlist);
            TextView textviewNomPrenom = (TextView) row.findViewById(R.id.textviewNomPrenom);
            TextView textviewNumero = (TextView) row.findViewById(R.id.textviewNumero);
            TextView textviewFilProm = (TextView) row.findViewById(R.id.textviewNiveauSpec);

            Etudiant etudiant = getItem(position);

            String nomPrenom = etudiant.getPrenom() + " " + etudiant.getNom();
            String[] splited = nomPrenom.split(Pattern.quote(toHighlight));
            String toHightlightStyled = "<font size=\"\" color=\"#ff0000\" face=\"\">" + toHighlight + "</font>";
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
            textviewNumero.setText(Long.toString(etudiant.getNumDossier()));
            textviewFilProm.setText(etudiant.getNiveau() + " " + etudiant.getSpecialite());

            int resId = 0;
            if (etudiant.getFiliere().getLibelleFiliere().equalsIgnoreCase("INFORMATIQUE")) {
                resId = R.mipmap.ic_i;
            } else if (etudiant.getFiliere().getLibelleFiliere().equalsIgnoreCase("MATHEMATIQUE & INFORMATIQUE")) {
                resId = R.mipmap.ic_mig;
            } else if (etudiant.getFiliere().getLibelleFiliere().equalsIgnoreCase("PHYSIQUE & CHIMIE")) {
                resId = R.mipmap.ic_mi;
            } else if (etudiant.getFiliere().getLibelleFiliere().equalsIgnoreCase("EAU & ENVIRONNEMENT")) {
                resId = R.mipmap.ic_seeg;
            }

            imageView.setImageResource(resId);

        } else if (rowType == TYPE_SECTION) {
            if (row == null) {
                LayoutInflater li = LayoutInflater.from(parent.getContext());
                row = li.inflate(R.layout.list_header, parent, false);
            }

            row = row.findViewById(R.id.textview_list_header);

        }
        return row;
    }

    public String getToHighlight() {
        return toHighlight;
    }

    public void setToHighlight(String toHighlight) {
        this.toHighlight = toHighlight.toUpperCase();
    }

}
