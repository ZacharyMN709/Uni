package com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterAdFavourites;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;
import java.util.Collections;

public class DrawerFavourites extends Fragment implements CustomAdapterAdFavourites.UnfavouritedPass{

    ArrayList <DataAd> AdDataArrayList = new ArrayList<>();
    View view;
    Context context;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_adlist, container, false);
        App = new App(getActivity());
        context = getActivity();
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.hide();

        /*
        setAdPreviews setUI = new setAdPreviews();
        setUI.execute();
        */

        getfavouritesfromserver();
        refreshAdapter();
        return view;
    }

    @Override
    public void onResume () {
        super.onResume();
        setAdPreviews setUI = new setAdPreviews();
        setUI.execute();
    }

    @Override
    public void onDataPass(String adid) {
        updateAdapter(adid);
    }

    public void updateAdapter(String adid) {
        ArrayList<String> AdIDList = new ArrayList();
        for (int x = 0; x < AdDataArrayList.size(); x++) {
            AdIDList.add(AdDataArrayList.get(x).getadAdID());
        }
        int i = AdIDList.indexOf(adid);
        AdDataArrayList.remove(i);
        refreshAdapter();
    }

    public void refreshAdapter() {
        RecyclerView adpreviewlist = (RecyclerView) view.findViewById(R.id.lvAdPreviews);
        adpreviewlist.setPadding(0,0,0,0);
        CustomAdapterAdFavourites mAdapter = new CustomAdapterAdFavourites(context, AdDataArrayList, true, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void getfavouritesfromserver() {
        ArrayList<String> Favourites = App.getFavouriteAds();
        String[] favourites = Favourites.toArray(new String[0]);
        AdDataArrayList.clear();
        AdDataArrayList = App.AdstoObject(App.getClient().getAds(favourites));
        Collections.reverse(AdDataArrayList);
    }

    class setAdPreviews extends AsyncTask<Void, Void, String>
    {

        //Constructor
        public setAdPreviews() {   }

        @Override
        protected String doInBackground(Void... args) {
            getfavouritesfromserver();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshAdapter();
        }
    }

}

