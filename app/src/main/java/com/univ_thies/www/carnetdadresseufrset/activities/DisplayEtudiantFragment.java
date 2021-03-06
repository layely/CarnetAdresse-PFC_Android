package com.univ_thies.www.carnetdadresseufrset.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.util.Communication;

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
    private static final String ARG_PARAM1 = "com.univ_thies.www.carnetdadresseufrset.activities.etu_key";
    //Widgets
    private TextView textViewNomPrenom;
    private TextView textViewFiliere;
    private TextView textviewSpecialite;
    private TextView textViewINE;
    private TextView textViewNumDossier;
    private TextView textViewMobile1;
    private TextView textViewMobile2;
    private TextView textViewEmail;
    private TextView textViewSexe;
    private TextView textViewDateNais;
    private TextView textViewAdresse;
    private TextView textViewNiveau;

    private NestedScrollView scrollView;

    private Etudiant etudiant;

    private OnFragmentInteractionListener mListener;
    private ImageButton btnCall1;

    private ImageButton btnMess1;
    private ImageButton btnCAll2;
    private ImageButton btnMess2;
    private ImageButton btnMail;

    private View layoutSpecialite;
    private View layoutINE;
    private View layoutMob1;
    private View layoutAdresse;
    private RelativeLayout layoutMob2;
    private View rootView;

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
//            Toast.makeText(this.getContext(), etudiant.getNom(), Toast.LENGTH_LONG).show();
//            TextView test = (TextView) rootView.findViewById(R.id.textviewNomPrenomDis);
//            test.setText("Hello");
            this.rootView = rootView;
            initWidgets(rootView);
        }
        return rootView;
    }

    public void reload(Etudiant etudiant) {
        this.etudiant = etudiant;
        if (rootView != null) {
            initWidgets(rootView);
        }
    }

    private void initWidgets(View rootView) {
        scrollView = (NestedScrollView) rootView.findViewById(R.id.scrollViewDis);

        layoutSpecialite = rootView.findViewById(R.id.layout_specialiteDis);
        layoutINE = rootView.findViewById(R.id.layout_ineDis);
        layoutMob1 = rootView.findViewById(R.id.layout_mob1Dis);
        layoutAdresse = rootView.findViewById(R.id.layout_adresseDis);

//        View viewInsideScroll = rootView.findViewById(R.id.viewIncideScrollviewDis);

//        OverScrollDecoratorHelper.setUpStaticOverScroll(viewInsideScroll, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);

        textViewNomPrenom = (TextView) (rootView.findViewById(R.id.textviewNomPrenomDis));
        textViewNomPrenom.setText(etudiant.getPrenom() + " " + etudiant.getNom());

        textViewFiliere = (TextView) (rootView.findViewById(R.id.textviewFilDis));
        if (etudiant.getFiliere() != null)
            textViewFiliere.setText(etudiant.getFiliere().getLibelleFiliere());

        textViewNiveau = (TextView) (rootView.findViewById(R.id.textviewNiveauDis));
        textViewNiveau.setText(etudiant.getNiveau());

        textviewSpecialite = (TextView) (rootView.findViewById(R.id.textviewSpeDis));
        if (etudiant.getSpecialite() == null || etudiant.getSpecialite().isEmpty())
            layoutSpecialite.setVisibility(View.GONE);
        else
            textviewSpecialite.setText(etudiant.getSpecialite());

        textViewINE = (TextView) rootView.findViewById(R.id.textviewINEDis);
        if (etudiant.getIne() == null || etudiant.getIne().isEmpty())
            layoutINE.setVisibility(View.GONE);
        else
            textViewINE.setText(etudiant.getIne());

        textViewNumDossier = (TextView) rootView.findViewById(R.id.textviewNumDossier);
        textViewNumDossier.setText(Long.toString(etudiant.getNumDossier()));

        textViewMobile1 = (TextView) rootView.findViewById(R.id.textviewMob1Dis);
        if (etudiant.getMobile1() == 0)
            layoutMob1.setVisibility(View.GONE);
        else
            textViewMobile1.setText(String.valueOf(etudiant.getMobile1()));

        textViewMobile2 = (TextView) rootView.findViewById(R.id.textviewMob2Dis);
        textViewMobile2.setText(String.valueOf(etudiant.getMobile2()));

        textViewEmail = (TextView) rootView.findViewById(R.id.textviewEmailDis);
        textViewEmail.setText(etudiant.getEmail());

        textViewSexe = (TextView) rootView.findViewById(R.id.textviewSexeDis);
        textViewSexe.setText(String.valueOf(etudiant.getSexe()));

        textViewAdresse = (TextView) rootView.findViewById(R.id.textviewAddrDis);
        if (etudiant.getAddresse() == null || etudiant.getIne().isEmpty())
            layoutAdresse.setVisibility(View.GONE);
        else
            textViewAdresse.setText(etudiant.getAddresse());

        textViewDateNais = (TextView) rootView.findViewById(R.id.textviewDateNaisDis);
        textViewDateNais.setText(etudiant.getDateNais());

        btnCall1 = (ImageButton) rootView.findViewById(R.id.imageBtnCallMob1);

        btnCall1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.call(DisplayEtudiantFragment.this.getActivity(), textViewMobile1.getText().toString());
            }
        });

        btnCAll2 = (ImageButton) rootView.findViewById(R.id.imageBtnCallMob2);
        btnCAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.call(DisplayEtudiantFragment.this.getActivity(), textViewMobile2.getText().toString());
            }
        });

        btnMess1 = (ImageButton) rootView.findViewById(R.id.imageBtnMessMob1);
        btnMess1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.sendMessage(DisplayEtudiantFragment.this.getActivity(), textViewMobile1.getText().toString());
            }
        });

        btnMess2 = (ImageButton) rootView.findViewById(R.id.imageBtnMessMob2);
        btnMess2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.sendMessage(DisplayEtudiantFragment.this.getActivity(), textViewMobile2.getText().toString());
            }
        });

        btnMail = (ImageButton) rootView.findViewById(R.id.imageBtnMail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Communication.sendMail(DisplayEtudiantFragment.this.getActivity(), textViewEmail.getText().toString());
            }
        });

        layoutMob2 = (RelativeLayout) rootView.findViewById(R.id.layout_mob2);
        if (etudiant.getMobile2() == 0) {
            layoutMob2.setVisibility(View.GONE);
        }
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
