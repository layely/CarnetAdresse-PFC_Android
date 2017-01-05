package com.univ_thies.www.carnetdadresseufrset.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.activities.DisplayEtudiantActivity;
import com.univ_thies.www.carnetdadresseufrset.activities.HomeActivity;
import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;

import java.util.List;

/**
 * Created by layely on 12/7/16.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private static List<Etudiant> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleViewAdapter(List<Etudiant> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_list_etudiant, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Etudiant etudiant = mDataset.get(position);
        holder.textviewPrenomNom.setText(etudiant.getPrenom() + " " + etudiant.getNom());
        holder.textviewFiliere.setText(etudiant.getFiliere().getLibelleFiliere());
        holder.textViewNumero.setText(Long.toString(etudiant.getNumDossier()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textviewPrenomNom;
        public TextView textviewFiliere;
        public TextView textViewNumero;

        public ViewHolder(View v) {
            super(v);
            textviewPrenomNom = (TextView) v.findViewById(R.id.textviewNomPrenom);
            textviewFiliere = (TextView) v.findViewById(R.id.textviewNiveauSpec);
            textViewNumero = (TextView) v.findViewById(R.id.textviewNumero);
//     rootView = v;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            listItemClicked(mDataset.get(position));
        }

        private void listItemClicked(Etudiant etudiant) {
            Intent i = new Intent(HomeActivity.homeContext, DisplayEtudiantActivity.class);
//            Bundle mBundle = new Bundle();
//            mBundle.putSerializable(HomeActivity.SER_KEY_ETU, etudiant);
            i.putExtra(HomeActivity.SER_KEY_ETU, etudiant);
            HomeActivity.homeContext.startActivity(i);
        }
    }

}
