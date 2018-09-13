package com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterAdList;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;
import java.util.Collections;

public class DrawerAdList extends Fragment {

    ArrayList <DataAd> AdDataArrayList = new ArrayList<>();

    String Search;
    Context context;
    View view;
    int MaxAds;
    boolean categorysearch;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.drawer_adlist, container, false);
        App = new App(getActivity());
        Bundle args = getArguments();
        Search = args.getString("Search" ,"ALL");
        categorysearch = args.getBoolean("Category", false);

        MaxAds = App.getMaxAds();
        context = getActivity();

        /*
        setAdPreviews setUI = new setAdPreviews();
        setUI.execute();
        */

        getadsfromserver();
        refreshAdapter();

        return view;
    }

    @Override
    public void onResume () {
        super.onResume();
        refreshAdapter();
    }

    public void refreshAdapter() {
        RecyclerView adpreviewlist = (RecyclerView) view.findViewById(R.id.lvAdPreviews);
        CustomAdapterAdList mAdapter = new CustomAdapterAdList(context, AdDataArrayList, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void getadsfromserver() {
        String[][] ads;
        String[] ids;
        if (Search == "ALL") {ids = App.getClient().getRecentAds();
            ads = App.getClient().getAdPreviews(ids);}
        else {
            if (categorysearch) {ids = App.getClient().searchDataBase(Search);}
            else {ids = App.getClient().searchDataBase(Search);}
            Log.e("Ad IDs ", ids[0]);
            if ("No results found".equalsIgnoreCase(ids[0])) {ads = null;} else {
                ads = App.getClient().getAdPreviews(ids);}
        }
        if (ads == null) {Log.e("DrawerAdList:", "ads[][] length: NULL");
        } else { Log.e("DrawerAdList:", "ads[][] length: " + ads.length);
            AdDataArrayList = App.AdstoObject(ads);
            Collections.reverse(AdDataArrayList);
            Log.e("DrawerAdList:", "AdDataArrayList length: " + AdDataArrayList.size()); }
    }

    private class setAdPreviews extends AsyncTask<Void, Void, String>
    {
        //Constructor
        public setAdPreviews() {   }

        @Override
        protected String doInBackground(Void... args) {
            getadsfromserver();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshAdapter();
        }
    }

}

