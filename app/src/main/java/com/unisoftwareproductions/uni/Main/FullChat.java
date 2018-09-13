package com.unisoftwareproductions.uni.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataChatMessage;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.CalendarPlus;
import com.unisoftwareproductions.uni.Main.Fragments.PopUps.PopUpItemInfo;
import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterChatMessages;
import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

/**
 * Created by Zachary on 10-Jul-16.
 */
public class FullChat extends AppCompatActivity  {

    ArrayList <DataChatMessage> dataChatMessageList = new ArrayList<>();

    Context context;
    String Username, AdID, UserID, USRID, ChatID;
    boolean deleteconfirm;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App = new App(this);
        setContentView(R.layout.fullchat);
        context = this;
        USRID = App.getUSRID();
        AdID = getIntent().getStringExtra("AdID");
        UserID = getIntent().getStringExtra("UserID");
        Username = getIntent().getStringExtra("Username");
        deleteconfirm = false;

        // Todo
//        dataChatMessageList = App.getClient().getMessages(AdID);
        getSupportActionBar().setTitle(Username);
        refreshAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_info) {
            hideSoftKeyboard(this);
            infopopup();
        }
        if (id == R.id.action_delete) {
            if (!deleteconfirm) {
                deleteconfirm = true;
                Toast.makeText(FullChat.this, "Click again to delete chat.", Toast.LENGTH_SHORT).show();
            }
            else {
                //Todo
                App.getClient().deleteChat("");
                onBackPressed();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendmessage(View view) {
        EditText etMessage = (EditText) findViewById(R.id.etMessage);
        String message = etMessage.getText().toString().trim();
        if (message != "") {
            etMessage.setText("");
            String timestamp = CalendarPlus.getTimeStamp();
            DataChatMessage dataChatMessage = new DataChatMessage(message, USRID, timestamp, ChatID);
            // Todo
//            App.getClient().sendMessage("", dataChatMessage);
            dataChatMessageList.add(dataChatMessage);
            refreshAdapter();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.headerbar_fullchat, menu);
        return true;
    }

    public void infopopup () {
        Fragment newFragment = new PopUpItemInfo();
        FragmentManager fragmentManager = getFragmentManager();
        Bundle args = new Bundle();
        args.putString("AdID", AdID);
        args.putString("UserID", UserID);
        newFragment.setArguments(args);
        FragmentTransaction FT = fragmentManager.beginTransaction();
        FT.replace(R.id.fragmentholder, newFragment, "InfoPopUp");
        FT.addToBackStack("InfoPopUp");
        FT.commit();
    }

    @Override
    public void onBackPressed() {
        PopUpItemInfo myFragment = (PopUpItemInfo)getFragmentManager().findFragmentByTag("InfoPopUp");
        if (myFragment != null && myFragment.isVisible())
        { getFragmentManager().popBackStack(); }
        else
        { super.onBackPressed(); }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void refreshAdapter() {
        RecyclerView fullchat = (RecyclerView) findViewById(R.id.lvFullChat);
        CustomAdapterChatMessages mAdapter = new CustomAdapterChatMessages(context, dataChatMessageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setStackFromEnd(true);
        fullchat.setLayoutManager(layoutManager);
        fullchat.setItemAnimator(new DefaultItemAnimator());
        fullchat.setAdapter(mAdapter);
    }

}
