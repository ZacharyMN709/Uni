package com.unisoftwareproductions.uni.Main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterAdList;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherProfile extends AppCompatActivity {

    ArrayList <DataAd> AdDataArrayList = new ArrayList<>();
    String UserID;
    Context context;
    DataUser dataUser;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        UserID = getIntent().getStringExtra("UserID");
        if (UserID == App.getUSRID()) {redirect(); return;}
        setContentView(R.layout.drawer_profile);
        context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        /** Future Update */
        // Todo - Add header bar with favourite/star for users & handle favourites

        /*
        setUserProfile setProfile = new setUserProfile();
        setProfile.execute();
        */

        getuserfromserver();
        setUserData();
        getadsfromserver();
        refreshAdapter();
    }

    @Override
    public void onResume () {
        super.onResume();
        refreshAdapter();
    }

    public void setUserData() {
        CircleImageView profileimage = (CircleImageView) findViewById(R.id.profilepicture);
        TextView tvUserRating = (TextView) findViewById(R.id.tvUserRating);
        TextView tvItemsSold = (TextView) findViewById(R.id.tvItemsSold);
        TextView tvStudying = (TextView) findViewById(R.id.tvStudying);
        TextView tvPSE = (TextView) findViewById(R.id.tvPSE);
        getSupportActionBar().setTitle(dataUser.getuserUsername());
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

    public void getuserfromserver() {
        String[] U = App.getClient().getUser(UserID);
        dataUser = new DataUser();
        if (U != null) { dataUser = new DataUser(U); }
    }

    public void refreshAdapter() {
        RecyclerView adpreviewlist = (RecyclerView) findViewById(R.id.profileAdPreviews);
        adpreviewlist.setPadding(0,0,0,0);
        CustomAdapterAdList mAdapter = new CustomAdapterAdList(context, AdDataArrayList, true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void getadsfromserver() {
        String[][] ads;
        String[] ids = App.getClient().getUserAds(UserID);

        if ("No results found".equalsIgnoreCase(ids[0])) {
            ads = null;
        } else {
            ads = App.getClient().getAds(ids);
        }

        if (ads == null) {
            Log.e("DrawerProfile:", "ads[][] length: NULL");
        } else {
            Log.e("DrawerProfile:", "ads[][] length: " + ads.length);
            AdDataArrayList = App.AdstoObject(ads);
            Collections.reverse(AdDataArrayList);
        }
    }

    public void redirect() {
        Intent intent = new Intent(this, Drawer_Main.class);
        intent.putExtra("Redirected", true);
        startActivity(intent);
        finish();
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


    class setAdPreviews extends AsyncTask<Void, Void, String> {

        //Constructor
        public setAdPreviews() { }

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

