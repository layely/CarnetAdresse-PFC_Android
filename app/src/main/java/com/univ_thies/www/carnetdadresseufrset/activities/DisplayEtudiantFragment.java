package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.objects.Etudiant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayEtudiantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DisplayEtudiantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayEtudiantFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "om.univ_thies.www.carnetdadresseufrset.activities.etu_key";
    //Widgets
    TextView textViewNomPrenom;
    TextView textViewFiliere;
    TextView textViewPromo;
    TextView textViewINE;
    TextView textViewMobile1;
    TextView textViewMobile2;
    TextView textViewEmail;
    TextView textViewSexe;
    TextView textViewDateNais;
    TextView textViewAdresse;
    private Etudiant etudiant;
    private OnFragmentInteractionListener mListener;

    public DisplayEtudiantFragment() {
        // Required empty public constructor
    }


    public static DisplayEtudiantFragment newInstance(Etudiant etudiant) {
        DisplayEtudiantFragment fragment = new DisplayEtudiantFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, etudiant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_display_etudiant, container, false);
        if (getArguments() != null) {
            etudiant = (Etudiant) getArguments().getSerializable(ARG_PARAM1);
            Toast.makeText(this.getContext(), etudiant.getNom(), Toast.LENGTH_LONG).show();
//            TextView test = (TextView) rootView.findViewById(R.id.textviewNomPrenomDis);
//            test.setText("Hello");
            initWidgets(rootView);
        }
        return rootView;
    }

    private void initWidgets(View rootView) {
        textViewNomPrenom = (TextView) (rootView.findViewById(R.id.textviewNomPrenomDis));
        textViewNomPrenom.setText(etudiant.getPrenom() + " " + etudiant.getNom());

        textViewFiliere = (TextView) (rootView.findViewById(R.id.textviewFilDis));
        if (etudiant.getFiliere() != null)
            textViewFiliere.setText(etudiant.getFiliere().getLibelleFiliere());

        textViewPromo = (TextView) rootView.findViewById(R.id.textviewPromoDis);
        if (etudiant.getPromo() != null)
            textViewPromo.setText(etudiant.getPromo().getPromo());

        textViewINE = (TextView) rootView.findViewById(R.id.textviewINEDis);
        textViewINE.setText(etudiant.getIne());

        textViewMobile1 = (TextView) rootView.findViewById(R.id.textviewMob1Dis);
        textViewMobile1.setText(String.valueOf(etudiant.getMobile1()));

        textViewMobile2 = (TextView) rootView.findViewById(R.id.textviewMob2Dis);
        textViewMobile2.setText(String.valueOf(etudiant.getMobile2()));

        textViewEmail = (TextView) rootView.findViewById(R.id.textviewEmailDis);
        textViewEmail.setText(etudiant.getEmail());

        textViewSexe = (TextView) rootView.findViewById(R.id.textviewSexeDis);
        textViewSexe.setText(String.valueOf(etudiant.getSexe()));

        textViewAdresse = (TextView) rootView.findViewById(R.id.textviewAddrDis);
        textViewAdresse.setText(etudiant.getAddresse());

        textViewDateNais = (TextView) rootView.findViewById(R.id.textviewDateNaisDis);
        textViewDateNais.setText(etudiant.getDateNais());

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
