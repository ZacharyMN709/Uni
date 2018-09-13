package com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterChatList;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataChatPreview;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;
import java.util.Collections;

public class DrawerChatList extends Fragment {

    ArrayList <DataChatPreview> dataChatPreviewList = new ArrayList<>();
    Context context;
    View view;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_chat, container, false);
        App = new App(getActivity());
        context = getActivity();
        String USRID = App.getUSRID();

        /*
        setChatPreviews setUI = new setChatPreviews();
        setUI.execute();
        */

//        getchatfromserver();
        refreshAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Todo - Handle removal of chat preview if deleted
        refreshAdapter();
    }

    public void refreshAdapter() {
        RecyclerView adpreviewlist = (RecyclerView) view.findViewById(R.id.lvChatList);
        CustomAdapterChatList mAdapter = new CustomAdapterChatList(context, dataChatPreviewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        adpreviewlist.setLayoutManager(mLayoutManager);
        adpreviewlist.setItemAnimator(new DefaultItemAnimator());
        adpreviewlist.setAdapter(mAdapter);
    }

    public void getchatfromserver() {
        //Todo
        /*
        dataChatPreviewList = App.getClient().getChatPreview();
        Collections.reverse(dataChatPreviewList);
        */
    }

    class setChatPreviews extends AsyncTask<Void, Void, String>
    {

        //Constructor
        public setChatPreviews() {   }

        @Override
        protected String doInBackground(Void... args) {
            getchatfromserver();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshAdapter();
        }
    }
}

