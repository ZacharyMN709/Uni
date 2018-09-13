package com.unisoftwareproductions.uni.Handlers.NavigationHandling;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.UniClient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zachary on 18-Jul-16.
 */
public class App {

    private Context mContext;
    private SharedPreferences storage;
    private SharedPreferences.Editor editor;
    private UniClient ServerLink;
    private DataAd AdHolder;
    private String IP = "http://" + "192.168.2.69:8080";

    public App(Context context, boolean bool) {
        mContext = context;
        storage = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = storage.edit();
        ServerLink = new UniClient(IP, getUSR(), getPASS(), getContext());  }

    public App(Context context) {
        mContext = context;
        connectTest();
        storage = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = storage.edit();
        ServerLink = new UniClient(IP, getUSR(), getPASS(), getContext());
    }

    // --------------------------------------------------------------------------------------------

    public Context getContext(){
        return mContext;
    }

    // --------------------------------------------------------------------------------------------

    public UniClient getClient() {
        return ServerLink;
    }

    public void setClient(String username, String password) {
        ServerLink = new UniClient(IP, username, password, getContext());
    }

    // --------------------------------------------------------------------------------------------

    public void internetconnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean connected = netInfo != null && netInfo.isConnected();
        if (!connected) {
            NavigationController.toStartScreen(mContext);
            Toast.makeText(mContext, "No Internet Connection - Returning to Login", Toast.LENGTH_LONG).show();
        }
    }

    public boolean pingserver() {
        UniClient holder = new UniClient(IP, "Null", "Null", getContext());
        if (!holder.pingServer()) {
            NavigationController.toStartScreen(mContext);
            Toast.makeText(mContext, "Connection Error - Returning to Login", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void connectTest() {
        internetconnected();
        pingserver();
    }

    // --------------------------------------------------------------------------------------------

    public ArrayList <DataAd> AdstoObject(String[][] adlist) {
        ArrayList <DataAd> AdDataArrayList = new ArrayList<>();
        if (adlist != null) {
            for (int x = 0; x < adlist.length; x++) {
                String[] ad = adlist[x];
                DataAd AD = new DataAd(ad, true);
                AdDataArrayList.add(AD);
            }
        }
        return AdDataArrayList;
    }


    public ArrayList <DataUser> UserstoObject(String[][] userlist) {
        ArrayList <DataUser> UserDataArrayList = new ArrayList<>();
        if (userlist != null) {
            for (int x = 0; x < userlist.length; x++) {
                String[] user = userlist[x];
                DataUser USER = new DataUser(user);
                UserDataArrayList.add(USER);
            }
        }
        return UserDataArrayList;
    }

    // --------------------------------------------------------------------------------------------


    public App(DataAd dataAd) { this.AdHolder = dataAd; }
    public void setAdHolder(DataAd dataAd) { this.AdHolder = dataAd; }
    public DataAd getAdHolder(){ return AdHolder; }

    // --------------------------------------------------------------------------------------------


    public String fromStorage(String caller) {
        return storage.getString(caller, "");
    }
    public void toStorage(String caller, String data) {
        editor.putString(caller, data);
        editor.apply();
    }

    // --------------------------------------------------------------------------------------------

    public String getSavedUsername() {return fromStorage("SavedUsername");}
    public String getSavedPassword() {return fromStorage("SavedPassword");}
    public String getUSR() {return fromStorage("USR");}
    public String getPASS() {return fromStorage("PASS");}
    public String getUSRID() {return fromStorage("USRID");}
    public String getPPS() {return fromStorage("PPS");}
    public String getPPL() {return fromStorage("PPL");}
    public String getEmail() {return fromStorage("Email");}
    public boolean getFirstLoad() { return storage.getBoolean("FirstLoad", true); }

    public void setSavedUsername(String data) {toStorage("SavedUsername", data);}
    public void setSavedPassword(String data) {toStorage("SavedPassword", data);}
    public void setUSR(String data) {toStorage("USR", data);}
    public void setPASS(String data) {toStorage("PASS", data);}
    public void setUSRID(String data) {toStorage("USRID", data);}
    public void setPPL(String data) {toStorage("PPL", data);}
    public void setPPS(String data) {toStorage("PPS", data);}
    public void setEmail(String data) {toStorage("Email", data);}
    public void setLoginALL(DataUser datauser) {
        setUSR(datauser.getuserUsername());
        setPASS(datauser.getuserPassword());
        setUSRID(datauser.getuserUserID());
        setPPL(datauser.getUserMainString());
        setPPS(datauser.getUserThumbString());
        setEmail(datauser.getuserEmail());
    }

    public void clearSavedUsername() {setSavedUsername(null);}
    public void clearSavedPassword() {setSavedPassword(null);}
    public void clearUSR() {setUSR(null);}
    public void clearPASS() {setPASS(null);}
    public void clearUSRID() {setUSRID(null);}
    public void clearPPL() {setPPL(null);}
    public void clearPPS() {setPPS(null);}
    public void clearEmail() {setEmail(null);}
    public void clearLoginALL() {
        clearSavedUsername();
        clearSavedPassword();
        clearUSR();
        clearPASS();
        clearUSRID();
        clearPPL();
        clearPPS();
        clearEmail();
    }

    // --------------------------------------------------------------------------------------------

    public int getMaxAds() {
        return storage.getInt("MaxAds", 50);
    }
    public ArrayList getFavouriteAds() {
        ArrayList<String> holder = new ArrayList<>();
        Set<String> StringSet = storage.getStringSet("FavouriteAds", null);
        if (StringSet != null) {holder.addAll(StringSet);}
        return holder;
    }
    public ArrayList getFavouriteUsers() {
        ArrayList<String> holder = new ArrayList<>();
        Set<String> StringSet = storage.getStringSet("FavouritesUsers", null);
        if (StringSet != null) {holder.addAll(StringSet);}
        return holder;
    }

    public void setFavouriteAds(ArrayList favourite) {
        Set<String> StringSet = new HashSet<>(favourite);
        StringSet.addAll(favourite);
        editor.remove("FavouriteAds");
        editor.apply();
        editor.putStringSet("FavouriteAds", StringSet);
        editor.apply();
    }
    public void setFavouriteUsers(ArrayList favourite) {
        Set<String> StringSet = new HashSet<>(favourite);
        StringSet.addAll(favourite);
        editor.remove("FavouritesUsers");
        editor.apply();
        editor.putStringSet("FavouritesUsers", StringSet);
        editor.apply();
    }
    public void setFirstLoad() {
        editor.putBoolean("FirstLoad", false);
        editor.apply();
    }
}
