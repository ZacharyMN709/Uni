package com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterAdList;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterAdEdit;

import com.unisoftwareproductions.uni.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerProfile extends Fragment {

    ArrayList <DataAd> AdDataArrayList = new ArrayList<>();
    boolean editable;
    Context context;
    View view;
    DataUser dataUser;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.drawer_profile, container, false);
        App = new App(getActivity());
        context = getActivity();
        editable = false;
        LocalBroadcastManager.getInstance(context).registerReceiver(
                EditSwitchReciever, new IntentFilter("EditSwitch"));

        /*
        setUserProfile setProfile = new setUserProfile();
        setProfile.execute();
        */

        getuserfromserver();
        setUserData();
        getadsfromserver();
        refreshAdapterMain();
        return view;
    }

    private BroadcastReceiver EditSwitchReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switchAdapter();
        }
    };

    @Override
    public void onResume () {
        super.onResume();
        refreshAdapterMain();
        editable = false;
    }

    public void switchAdapter() {
        editable = !editable;
        if (!editable) {refreshAdapterMain();}
        if (editable) {refreshAdapterEdit();}
    }

    public void setUserData() {
        if (dataUser != null) {
            CircleImageView profileimage = (CircleImageView) view.findViewById(R.id.profilepicture);
            TextView tvUserRating = (TextView) view.findViewById(R.id.tvUserRating);
            TextView tvItemsSold = (TextView) view.findViewById(R.id.tvItemsSold);
            TextView tvStudying = (TextView) view.findViewById(R.id.tvStudying);
            TextView tvPSE = (TextView) view.findViewById(R.id.tvPSE);
            tvUserRating.setText(dataUser.getuserRating() + "%");
            tvItemsSold.setText(dataUser.getuserItemsSold());
            tvPSE.setText(dataUser.getuserPSE());
            tvStudying.setText(dataUser.getuserStudying());
            profileimage.setImageBitmap(dataUser.getuserUserMain());

            /*
            setAdPreviews setAds = new setAdPreviews();
            setAds.execute();
            */
        }
    }

    public void getuserfromserver() {
        Log.e("DrawerProfile", "USRID = " + App.getUSRID());
        Log.e("DrawerProfile:", "Pre-getUser");
        String[] U = App.getClient().getUser(App.getUSRID());
        Log.e("DrawerProfile:", "Pre-UserObject");
        if (U != null) { dataUser = new DataUser(U); }
    }

    public void refreshAdapterMain() {
        RecyclerView adpreviewlist = (RecyclerView) view.findViewById(R.id.profileAdPreviews);
        CustomAdapterAdList mAdapter = new CustomAdapterAdList(context, AdDataArrayList, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void refreshAdapterEdit() {
        FragmentManager fm = getFragmentManager();
        RecyclerView adpreviewlist = (RecyclerView) view.findViewById(R.id.profileAdPreviews);
        CustomAdapterAdEdit mAdapter = new CustomAdapterAdEdit(context, AdDataArrayList, fm);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void getadsfromserver() {
        String[][] ads = null;
        String[] ids;
        ids = App.getClient().getUserAds(App.getUSRID());
        Log.e("getadsfromserver: ", ids[0]);
        String toFetch = "";
        for(String s: ids) {
            toFetch += s + ",";
        }
        Log.e("server return: ", toFetch);

        if (ids != null) {
            if ("Error: null".equalsIgnoreCase(ids[0])) {
                Log.e("DrawerProfile: ", "ids = NULL");
                ads = null;
            }
            else {
                Log.e("DrawerProfile: ", ids.length + " AdIds found");
                if (ids.length == 1) {
                    ads = new String[][]{App.getClient().getAd(ids[0])};
                } else {
                    ads = App.getClient().getAds(ids);
                }
            }
        } else {
            Log.e("DrawerProfile: ", "Error! ids[] is NULL");
        }

        if (ads == null) { Log.e("DrawerProfile:", "ads[][] length: NULL");
        } else {
            Log.e("DrawerProfile:", "ads[][] length: " + ads.length);
            AdDataArrayList = App.AdstoObject(ads);
            Collections.reverse(AdDataArrayList);
        }
    }

    class setUserProfile extends AsyncTask<Void, Void, String>
    {

        //Constructor
        public setUserProfile() {   }

        @Override
        protected String doInBackground(Void... args) {
            getuserfromserver();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setUserData();
        }
    }

    class setAdPreviews extends AsyncTask<Void, Void, String>
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
            refreshAdapterMain();
        }
    }

}

