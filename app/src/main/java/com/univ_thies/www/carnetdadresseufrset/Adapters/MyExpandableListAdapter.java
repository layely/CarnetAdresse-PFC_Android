package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.activities.HomeActivity;
import com.univ_thies.www.carnetdadresseufrset.database.FiliereDAO;

import java.util.ArrayList;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    FiliereDAO filiereDAO;

    private ArrayList<ModelFiliere> departements = new ArrayList<>();

    public MyExpandableListAdapter() {
        filiereDAO = new FiliereDAO(HomeActivity.homeContext);

        ModelFiliere modelFiliereMI = new ModelFiliere(filiereDAO.getFiliere("MATHEMATIQUE & INFORMATIQUE"));
        ModelFiliere modelFiliereSEE = new ModelFiliere(filiereDAO.getFiliere("EAU & ENVIRONNEMENT"));
        ModelFiliere modelFilierePC = new ModelFiliere(filiereDAO.getFiliere("PHYSIQUE & CHIMIE"));
        ModelFiliere modelFiliereGI = new ModelFiliere(filiereDAO.getFiliere("INFORMATIQUE"));


        ArrayList<ModelClasse> modelClasses = modelFiliereGI.getClasses();
        modelClasses.get(2).setNiveau("L3");
        modelClasses.get(2).setOption("GL");
        modelClasses.get(3).setNiveau("L3");
        modelClasses.get(3).setOption("RT");

        modelClasses.get(4).setNiveau("M1");
        modelClasses.get(4).setOption("GL");
        modelClasses.add(new ModelClasse("M1", "RT"));
        modelClasses.add(new ModelClasse("M2", "GL"));
        modelClasses.add(new ModelClasse("M2", "RT"));

        departements.add(modelFiliereGI);
        departements.add(modelFiliereMI);
        departements.add(modelFilierePC);
        departements.add(modelFiliereSEE);

        for (ModelFiliere mf : departements) {
            Log.i("tagexp", "-----------------------------------------------------------------------------");
            Log.i("tagexp", "Departement ::::> " + mf.getFiliere().getLibelleFiliere());
            for (ModelClasse cl : mf.getClasses()) {
                Log.i("tagexp", "classe :::>" + cl.toString());
            }
        }
    }

    public ArrayList<ModelFiliere> getDepartements() {
        return departements;
    }

    @Override
    public int getGroupCount() {
        return departements.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = 0;
        if (departements.get(groupPosition).getClasses() != null)
            size = departements.get(groupPosition).getClasses().size();
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return departements.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return departements.get(groupPosition).getClasses().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
//        boolean newState = !departements.get(groupPosition).isSelected();
//        departements.get(groupPosition).setSelected(newState);
        return groupPosition;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
//        boolean newState = !departements.get(groupPosition).getClasses().get(childPosition).isSelected();
//        departements.get(groupPosition).getClasses().get(childPosition).setSelected(newState);
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ModelFiliere departement = departements.get(groupPosition);
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        convertView = li.inflate(R.layout.row_expandablelist_group, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.textview_parent);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkboxparent);
        ImageView imgswitcher = (ImageView) convertView.findViewById(R.id.imageviewparent);

        textView.setText(departement.getFiliere().getLibelleFiliere());
        checkBox.setChecked(departement.isSelected());
        checkBox.setClickable(true);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                departement.setSelected(!departement.isSelected());
                departement.setAllChildSelected(departement.isSelected());
                MyExpandableListAdapter.this.notifyDataSetChanged();
            }
        });

        if (isExpanded) {
            imgswitcher.setImageResource(R.mipmap.ic_switcher_up);
        } else {
            imgswitcher.setImageResource(R.mipmap.ic_switcher_down);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ModelFiliere departement = departements.get(groupPosition);
        final ModelClasse classe = departement.getClasses().get(childPosition);

        // Inflate childrow.xml file for child rows
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        convertView = li.inflate(R.layout.row_expandablelist_child, parent, false);

        TextView textView = (TextView) convertView.findViewById(R.id.textview_child);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkboxChild);

        textView.setText(classe.toString());
        checkBox.setChecked(classe.isSelected());
        checkBox.setFocusable(false);
        checkBox.setClickable(false);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classe.setSelected(!classe.isSelected());
                if (departement.isAllChildSelected()) {
                    departement.setSelected(true);
                } else if (departement.isAllChildUnSelected()) {
                    departement.setSelected(false);
                }
                MyExpandableListAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (departements == null || departements.isEmpty());
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

    }
}
