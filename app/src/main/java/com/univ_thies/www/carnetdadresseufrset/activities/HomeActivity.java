package com.univ_thies.www.carnetdadresseufrset.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.univ_thies.www.carnetdadresseufrset.Adapters.ListEtudiantAdapter;
import com.univ_thies.www.carnetdadresseufrset.Adapters.MyExpandableListAdapter;
import com.univ_thies.www.carnetdadresseufrset.R;
import com.univ_thies.www.carnetdadresseufrset.database.EtudiantDAO;
import com.univ_thies.www.carnetdadresseufrset.database.UtilDAO;
import com.univ_thies.www.carnetdadresseufrset.metier.Etudiant;
import com.univ_thies.www.carnetdadresseufrset.server_sync.ServerConnection;
import com.univ_thies.www.carnetdadresseufrset.util.Communication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class HomeActivity extends AppCompatActivity {

    public static final String SER_KEY_ETU = "om.univ_thies.www.carnetdadresseufrset.activities.key_etu";
    public static Context homeContext;
    public static FloatingActionButton fab;
    public static CoordinatorLayout coordinatorLayout;
    private static EtudiantDAO etudiantDAO;
    private static UtilDAO utilDAO;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeContext = this;
        etudiantDAO = new EtudiantDAO(this);
        utilDAO = new UtilDAO(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setCurrentItem(1);

        OverScrollDecoratorHelper.setUpOverScroll(mViewPager);

        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTitleStrip);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.colorBackgroundTitle));

        ((ViewPager.LayoutParams) pagerTabStrip.getLayoutParams()).isDecor = true;


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        utilDAO.incrementNumberOfLaunch();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        finish();
//        startActivity(getIntent());
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_sync) {
            try {
                ServerConnection.executeUpdate(this, null, fab, null);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        SearchView searchView;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

//        @Override
//        public void onResume() {
//            int numPage = getArguments().getInt(ARG_SECTION_NUMBER);
//            super.onResume();
//            if (numPage == 1) {
//                searchView.onActionViewExpanded();
//            }
//        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int numPage = getArguments().getInt(ARG_SECTION_NUMBER);
//            Toast.makeText(getContext(), String.valueOf(numPage), Toast.LENGTH_SHORT).show();

            if (numPage == 1) {
                //On search page
                return onCreateViewPage1(inflater, container, savedInstanceState);
            }

            if (numPage == 2) {
                //On Student list page
                return onCreateViewPage2(inflater, container, savedInstanceState);
            }

            if (numPage == 3) {
                //on broadcast page
                return onCreateViewPage3(inflater, container, savedInstanceState);
            }
            return null;
        }

        private View onCreateViewPage1(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            Toolbar toolbarSearch = (Toolbar) rootView.findViewById(R.id.toolbarSearch);
            toolbarSearch.setVisibility(View.VISIBLE);

            searchView = (SearchView) rootView.findViewById(R.id.searchview);

            final ListView listView = (ListView) rootView.findViewById(R.id.listview_etudiant);
            final List<Etudiant> listEtudiants = new ArrayList<>();

//            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycleview_etudiant);
//            rv.setHasFixedSize(true);
//
//            // use a linear layout manager
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
//            rv.setLayoutManager(mLayoutManager);
//
//            RecycleViewAdapter mAdapter = new RecycleViewAdapter(listEtudiants);
//            rv.setAdapter(mAdapter);

//            searchView.requestFocus();
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    listEtudiants.clear();
                    ((ListEtudiantAdapter) listView.getAdapter()).notifyDataSetChanged();
                    return false;
                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    listEtudiants.clear();
                    listEtudiants.addAll(Etudiant.search(etudiantDAO.getAllEtudiants(), searchView.getQuery().toString()));
                    ((ListEtudiantAdapter) listView.getAdapter()).notifyDataSetChanged();
                    Log.i("tag", "mylistview data has changed --- on Text submit");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ListEtudiantAdapter arrayAdapter = ((ListEtudiantAdapter) listView.getAdapter());
                    arrayAdapter.setToHighlight(searchView.getQuery().toString());
                    listEtudiants.clear();
                    listEtudiants.addAll(Etudiant.search(etudiantDAO.getAllEtudiants(), searchView.getQuery().toString().trim()));
                    arrayAdapter.notifyDataSetChanged();
                    Log.i("tag", "mylistview data has changed --- on Text Change");
                    return false;
                }
            });

            searchView.setQueryHint("Numéro, INE, Nom, Prenom ...");

            listView.setAdapter(new ListEtudiantAdapter(homeContext, listEtudiants));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listItemClicked(view, (Etudiant) listView.getItemAtPosition(position));
                }
            });

            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.requestFocus();
                }
            });

            searchView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    searchView.requestFocus();
                    return false;
                }
            });

//            searchView.onActionViewExpanded();

            return rootView;
        }

        private View onCreateViewPage2(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.textviewUserName);
            final ListView listView = (ListView) rootView.findViewById(R.id.listview_etudiant);
            final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            final List<Etudiant> listEtudiants = etudiantDAO.getAllEtudiants();

            listView.setAdapter(new ListEtudiantAdapter(this.getContext(), listEtudiants));
            listView.setFastScrollAlwaysVisible(true);
            listView.setFastScrollEnabled(true);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listItemClicked(view, (Etudiant) listView.getItemAtPosition(position));
                }
            });

            swipeRefreshLayout.setColorSchemeResources(R.color.AppBarColor);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    try {
                        ServerConnection.executeUpdate(PlaceholderFragment.this.getContext(), listView, fab, swipeRefreshLayout);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            OverScrollDecoratorHelper.setUpOverScroll(listView);
//            RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recycleview_etudiant);
//            rv.setHasFixedSize(true);
//
//            // use a linear layout manager
//            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
//            rv.setLayoutManager(mLayoutManager);
//
//            final RecycleViewAdapter mAdapter = new RecycleViewAdapter(listEtudiants);
//            rv.setAdapter(mAdapter);

//            Log.i("tagasync", "about to instanciate");
//            new FetchEtudiantTask(this.getContext(), listView, fab).execute();

            try {
                ServerConnection.executeUpdate(PlaceholderFragment.this.getContext(), listView, fab, swipeRefreshLayout);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rootView;
        }

        private View onCreateViewPage3(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_diffusion, container, false);
            ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.expandablelistview);
            final MyExpandableListAdapter myExpandableListAdapter = new MyExpandableListAdapter(getContext());
            elv.setAdapter(myExpandableListAdapter);
            OverScrollDecoratorHelper.setUpOverScroll(elv);
//            NestedScrollingChildHelper nsch = new NestedScrollingChildHelper(elv);
//            nsch.setNestedScrollingEnabled(true);
            TextView textView = (TextView) rootView.findViewById(R.id.textview_testdiffusion);

            final RadioButton rdbtnSMS = (RadioButton) rootView.findViewById(R.id.radioSMSDiff);
            final RadioButton rdbtnEmail = (RadioButton) rootView.findViewById(R.id.radioEmailDiff);

            final ImageButton imgBtnSend = (ImageButton) rootView.findViewById(R.id.imageBntSendDiff);

            imgBtnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myExpandableListAdapter.getSelectedEtudiants() == null || myExpandableListAdapter.getSelectedEtudiants().isEmpty())
                        return;

                    else if (rdbtnSMS.isChecked()) {
                        Communication.sendMessage(PlaceholderFragment.this.getActivity(), myExpandableListAdapter.getSelectedEtudiantNumber());

                    } else if (rdbtnEmail.isChecked()) {
                        Communication.sendMultipleMail(PlaceholderFragment.this.getActivity(), myExpandableListAdapter.getSelectedEtudiantEmail());
                        return;
                    }
                }
            });
            return rootView;
        }

        private void listItemClicked(View view, Etudiant etudiant) {
            final CardView cardView = (CardView) view.findViewById(R.id.card_view);
            final ColorStateList colorState = cardView.getCardBackgroundColor();
            cardView.setCardBackgroundColor(
                    (getResources().getColor(R.color.colorBackgroundTitle)));
            Intent i = new Intent(HomeActivity.homeContext, DisplayEtudiantActivity.class);
//            Bundle mBundle = new Bundle();
//            mBundle.putSerializable(HomeActivity.SER_KEY_ETU, etudiant);
            i.putExtra(HomeActivity.SER_KEY_ETU, etudiant);
            startActivity(i);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((Activity) homeContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cardView.setCardBackgroundColor(colorState);
                        }
                    });

                }
            });

            thread.start();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Recherche";
                case 1:
                    return "Etudiants";
                case 2:
                    return "Diffusion";
            }
            return null;
        }
    }
}
