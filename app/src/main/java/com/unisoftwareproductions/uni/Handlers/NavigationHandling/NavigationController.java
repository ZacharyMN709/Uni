package com.unisoftwareproductions.uni.Handlers.NavigationHandling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Main.OtherProfile;
import com.unisoftwareproductions.uni.Main.FullAd;
import com.unisoftwareproductions.uni.Main.FullChat;
import com.unisoftwareproductions.uni.Main.Start.StartScreen;

/**
 * Created by Zachary on 19-Jul-16.
 */
public class NavigationController{

    public static void toFullAd(Context context, String userid, String adid, String itemname) {
        App App = new App(context);
        App.connectTest();
        Intent fullad = new Intent(context, FullAd.class);
        fullad.putExtra("UserID", userid);
        fullad.putExtra("AdID", adid);
        fullad.putExtra("ItemName", itemname);
        context.startActivity(fullad);
    }

    public static void toOtherProfile(Context context, String userid) {
        App App = new App(context);
        App.connectTest();
        Intent otherprofile = new Intent(context, OtherProfile.class);
        otherprofile.putExtra("UserID", userid);
        context.startActivity(otherprofile);
    }

    public static void toFullChat(Context context, String userid, String adid, String username) {
        App App = new App(context);
        App.connectTest();
        SharedPreferences security = PreferenceManager.getDefaultSharedPreferences(context);
        String USRID = security.getString("USRID", "");
        if (!USRID.equals(userid)) {
            Intent fullchat = new Intent(context, FullChat.class);
            fullchat.putExtra("UserID", userid);
            fullchat.putExtra("AdID", adid);
            fullchat.putExtra("Username", username);
            context.startActivity(fullchat);
        } else {
            Toast.makeText(context, "Cannot start chat with oneself!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void toStartScreen(Context context) {
        Intent logout = new Intent(context, StartScreen.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity)context).finish();
        context.startActivity(logout);
    }

}
