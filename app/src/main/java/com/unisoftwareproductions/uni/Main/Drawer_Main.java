package com.unisoftwareproductions.uni.Main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Main.Settings.Legal_Drawer;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.NavigationController;
import com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens.DrawerAdList;
import com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens.DrawerCategories;
import com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens.DrawerChatList;
import com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens.DrawerFavourites;
import com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens.DrawerProfile;
import com.unisoftwareproductions.uni.Main.Settings.ChangeProfilePicture;
import com.unisoftwareproductions.uni.Main.Settings.Feedback;
import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Drawer_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar = null;
    NavigationView navigationView = null;
    Menu menureference;
    int menuxmllayout, deleteallcounter;
    String Username, AdTitle, Search, USRID;
    boolean categorysearch;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                CategorySelectReceiver, new IntentFilter("CategoryToSearch"));

        USRID = App.getUSRID();
        AdTitle = "Listings";
        Search = "ALL";
        categorysearch = false;
        deleteallcounter = 0;

        handleIntent(getIntent());
        setDrawerLayout();
    }

    @Override
    public void onResume () {
        super.onResume();
        View headerview = navigationView.getHeaderView(0);
        CircleImageView drawerprofileimage = (CircleImageView) headerview.findViewById(R.id.drawerheader_profilepicturethumb);
        drawerprofileimage.setImageBitmap(ImageHandler.ReassembleBitmapfromString(App.getPPS(), ImageHandler.image_profile_thumb_dp()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        boolean redirected = intent.getBooleanExtra("Redirected", false);
        if (redirected) {
            loadfragment(new DrawerProfile(), R.menu.headerbar_profile, Username);
        } else {
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                Search = intent.getStringExtra(SearchManager.QUERY);
                AdTitle = "Search Results";
                loadfragment(new DrawerAdList(), R.menu.headerbar_adlist, AdTitle);
            } else {
                loadfragment(new DrawerAdList(), R.menu.headerbar_adlist, AdTitle);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menureference = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(menuxmllayout, menu);

        if (menuxmllayout == R.menu.headerbar_adlist) {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_message) {
            navigationView.setCheckedItem(R.id.nav_chat);
            loadfragment(new DrawerChatList(), R.menu.headerbar_textonly, "Chat List");
            return true;
        }
        if (id == R.id.action_edit) {
            Intent intent = new Intent("EditSwitch");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_ads:
                AdTitle = "Ads";
                loadfragment(new DrawerAdList(), R.menu.headerbar_adlist, AdTitle);
                break;

            case R.id.nav_categories:
                loadfragment(new DrawerCategories(), R.menu.headerbar_searchless, "Categories");
                break;

            case R.id.nav_favourites:
                loadfragment(new DrawerFavourites(), R.menu.headerbar_searchless, "Favourites");
                break;

            case R.id.nav_profile:
                loadfragment(new DrawerProfile(), R.menu.headerbar_profile, Username);
                break;

            case R.id.nav_chat:
                loadfragment(new DrawerChatList(), R.menu.headerbar_textonly, "Chat List");
                break;

            case R.id.nav_logout:
                ArrayList<String> Favourites = App.getFavouriteAds();
                String[] favourites = Favourites.toArray(new String[0]);
                App.getClient().updateFavorites(favourites);
                App.clearLoginALL();
                App.setClient("Null","Null");
                NavigationController.toStartScreen(this);
                break;

            case R.id.nav_deleteall:
                deleteallcounter = deleteallcounter + 1;
                if (deleteallcounter == 4) {
                    App.getClient().deleteAllAds();
                    deleteallcounter = 0;
                } else {
                    Toast.makeText(Drawer_Main.this, "Tap " + (4 - deleteallcounter)
                            + " more times to delete all ads", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.nav_changepicture:
                Intent changepic = new Intent(this, ChangeProfilePicture.class);
                startActivity(changepic);
                break;

            case R.id.nav_feedback:
                Intent feedback = new Intent(this, Feedback.class);
                startActivity(feedback);
                break;

            case R.id.nav_legal:
                Intent legal = new Intent(this, Legal_Drawer.class);
                startActivity(legal);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void placeAd(View view){
        Intent intent = new Intent(this, PlaceAd.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        DrawerAdList myFragment = (DrawerAdList)getFragmentManager().findFragmentByTag("AdList");
        if (myFragment == null) {
            navigationView.setCheckedItem(R.id.nav_ads);
            AdTitle = "Ads";
            Search = "ALL";
            loadfragment(new DrawerAdList(), R.menu.headerbar_adlist, AdTitle);
        } else {
            super.onBackPressed();
        }
    }

    public void loadfragment(Fragment fragment, int xml, String titletext) {
        menuxmllayout = xml;
        this.invalidateOptionsMenu();
        getSupportActionBar().setTitle(titletext);

        Bundle args = new Bundle();
        FragmentTransaction FT = getFragmentManager().beginTransaction();
        String fragmenttag = null;
        if (xml == R.menu.headerbar_adlist) {
            args.putString("Search", Search);
            args.putBoolean("Category", categorysearch);
            fragment.setArguments(args);
           if (Search.equals("ALL")) {fragmenttag = "AdList";}
        }
        FT.replace(R.id.drawer_fragment_holder, fragment, fragmenttag);
        FT.commit();
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(CategorySelectReceiver);
        super.onDestroy();
    }

    private void setDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.inflateHeaderView(R.layout.drawerheader);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_ads);

        Username = App.getUSR();
        String PPS = App.getPPS();
        String Email = App.getEmail();

        TextView drawerusername = (TextView) headerview.findViewById(R.id.drawerheader_username);
        TextView draweremail = (TextView) headerview.findViewById(R.id.drawerheader_email);
        CircleImageView drawerprofileimage = (CircleImageView) headerview.findViewById(R.id.drawerheader_profilepicturethumb);
        drawerusername.setText(Username);
        draweremail.setText(Email);
        drawerprofileimage.setImageBitmap(ImageHandler.ReassembleBitmapfromString(PPS, ImageHandler.image_profile_thumb_dp()));
    }

    private BroadcastReceiver CategorySelectReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AdTitle = intent.getStringExtra("CategoryName");
            Search = AdTitle;
            categorysearch = true;
            loadfragment(new DrawerAdList(), R.menu.headerbar_adlist, AdTitle);
            navigationView.setCheckedItem(R.id.nav_ads);
        }
    };

}

