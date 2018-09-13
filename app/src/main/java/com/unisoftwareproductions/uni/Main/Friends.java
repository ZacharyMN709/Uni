package com.unisoftwareproductions.uni.Main;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterUserList;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

/**
 * Created by Zachary on 21-Jul-16.
 */
public class Friends extends AppCompatActivity {

    ArrayList<DataUser> UserDataArrayList = new ArrayList<>();
    Context context;
    String AdID, USRID;
    boolean rateuser;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.friends);
        context = this;

        rateuser = getIntent().getBooleanExtra("RateUser", false);
        if (rateuser) {
            AdID = getIntent().getStringExtra("AdID");
            USRID = App.getUSRID();
            // Todo
//            UserDataArrayList = App.getClient().getUserList(AdID);
            getSupportActionBar().setTitle("Choose user to rate");
        }
        /** For future update */
        /*
        else {
            getSupportActionBar().setTitle("Followed Users");
            ArrayList<String> FavouriteUsers = App.getFavouriteUsers();
            UserDataArrayList = ServerInterface.getUserFavouriteList(FavouriteUsers);
        }
        */
        FragmentManager fragmentManager = getFragmentManager();
        GridView catlist = (GridView) findViewById(R.id.categorygrid);
        catlist.setAdapter(new CustomAdapterUserList(UserDataArrayList, context, rateuser, fragmentManager));
    }
}
