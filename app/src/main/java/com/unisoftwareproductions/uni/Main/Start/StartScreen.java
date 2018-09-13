package com.unisoftwareproductions.uni.Main.Start;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Main.Drawer_Main;
import com.unisoftwareproductions.uni.Main.Fragments.PopUps.PopUpForgotPass;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StartScreen extends Activity {

    String Username, Password;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this, true);
        boolean firstload = App.getFirstLoad();
        Username = App.getSavedUsername();
        Password =  App.getSavedPassword();

        if (App.getUSR() != null && App.getPASS() != null) {
            App.setClient(App.getUSR(), App.getPASS());
            loginmain(true);
        }

        if (firstload) {
            setContentView(R.layout.startscreen_register);
            App.setFirstLoad();
        }
        else {
            setContentView(R.layout.startscreen_login);
            if ((Username != "" & Password != "") & (Username != null & Password != null)) {
                CheckBox remember = (CheckBox) findViewById(R.id.remember);
                remember.setChecked(true);
                EditText etUsername = (EditText) findViewById(R.id.etUsername);
                EditText etPassword = (EditText) findViewById(R.id.etPassword);
                etUsername.setText(Username);
                etPassword.setText(Password);
            }
        }
    }

    public void login(View view) {
        if (servertest() == false) {return;}
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        CheckBox remember = (CheckBox) findViewById(R.id.remember);
        if (remember.isChecked()) {
            Username = etUsername.getText().toString();
            Password = etPassword.getText().toString();
            App.setSavedPassword(Password);
            App.setSavedUsername(Username);
        } else {
            App.clearSavedPassword();
            App.clearSavedUsername();
        }
        Log.e("StartScreen", "Pre-setClient");
        App.setClient(Username, Password);
        loginmain(false);
    }

    public void loginmain(boolean loggedin) {
        Log.e("StartScreen", "Pre-tryLogin");
        String login = App.getClient().tryLogin();
        if (login.contains(",")) {
            Log.e("StartScreen", "if = true");
            StringTokenizer tokens = new StringTokenizer(login, ",");
            String first = tokens.nextToken();
            Toast.makeText(StartScreen.this, first, Toast.LENGTH_SHORT).show();
            String second = tokens.nextToken();
            String UserID = second;
            Log.e("StartScreen", "Pre-getUser");
            String[] H = App.getClient().getUser(UserID);
            Log.e("StartScreen", "Pre-UserObject");
            DataUser dataUser = new DataUser(H);
            Log.e("StartScreen", "pre-AsyncTask");
            App.setLoginALL(dataUser);

            if (!loggedin) {
                /*
                String[] favourites = App.getClient().getFavorites();
                ArrayList<String> Favourites = new ArrayList();
                for (int x = 0; x < favourites.length; x++) {Favourites.add(favourites[x]);}
                App.setFavouriteAds(Favourites);
                */
            }

            Intent intent = new Intent(this, Drawer_Main.class);
            startActivity(intent);
        }  else {
            Log.e("StartScreen", "if = false");
            Toast.makeText(StartScreen.this, login, Toast.LENGTH_SHORT).show();
        }
    }

    public void signup(View view) {
        if (servertest() == false) {return;}
        EditText etEmail = (EditText) findViewById(R.id.etEmail);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String Email = etEmail.getText().toString();
        String Password = etPassword.getText().toString();
        if (master(Email, Password)) {
            Intent register = new Intent(this, Legal_Register.class);
            register.putExtra("Email", Email);
            register.putExtra("Password", Password);
            startActivity(register);
        }
    }

    public void forgot(View view) {
        if (servertest() == false) {return;}
        Fragment newFragment = new PopUpForgotPass();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction FT = fragmentManager.beginTransaction();
        FT.add(R.id.fragmentholder, newFragment);
        FT.addToBackStack("");
        FT.commit();
    }

    public void loadlogin(View view) {
        setContentView(R.layout.startscreen_login);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername.setText(Username);
        etPassword.setText(Password);
    }

    public void loadregister(View view) {
        setContentView(R.layout.startscreen_register);
    }

    private boolean master(String email, String password) {
        if (!filled(email, password)) {
            return false; }
        if (!relevant(email)) {
            return false; }
        else
            return true;
    }

    // Ensures text fields is not empty
    private boolean filled(String email, String password) {
        if (email.equals("")) {
            Toast.makeText(this, getString(R.string.empty_email), Toast.LENGTH_SHORT).show();
            return false; }
        if (password.equals("")) {
            Toast.makeText(this, getString(R.string.empty_password), Toast.LENGTH_SHORT).show();
            return false; }
        else
            return true;
    }

    private boolean servertest() {
        if (!App.getClient().pingServer()) {
            Toast.makeText(StartScreen.this, "Error Connecting! Please try again later!", Toast.LENGTH_SHORT).show();
            return false;
        } else { return true; }
    }

    // Checks to make sure relevant email is entered
    private boolean relevant(String email){
        if(email.contains("@mun.ca")){
            return true; }
        if(email.contains("@mi.mun.ca")){
            return true; }
        if(email.contains("@ed.cna.nl.ca")){
            return true; }
        if(email.contains("@unisoftwareproductions.ca")){
            return true; }
        else {
            Toast.makeText(this, getString(R.string.invalid_email), Toast.LENGTH_LONG).show();
            return false; }
    }
}




