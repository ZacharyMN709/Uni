package com.unisoftwareproductions.uni.Handlers.CustomAdapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Main.Fragments.PopUps.PopUpItemSold;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

public class CustomAdapterAdEdit extends RecyclerView.Adapter<CustomAdapterAdEdit.Holder>{

    ArrayList<DataAd> dataAdList;
    FragmentManager fragmentManager;
    Context context;
    String USRID;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    public CustomAdapterAdEdit(Context callingclass, ArrayList<DataAd> dataadlist, FragmentManager mfragmentManager) {
        dataAdList       = dataadlist;
        context          = callingclass;
        fragmentManager  = mfragmentManager;
        App = new App(context);
        USRID = App.getUSRID();
    }

    @Override
    public int getItemCount()  {
        return dataAdList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView name, price;
        ImageView img, icon;
        Button sold;

        public Holder(View rowView) {
            super(rowView);
            img = (ImageView) rowView.findViewById(R.id.ivAdImage);
            icon = (ImageView) rowView.findViewById(R.id.AdIconBottom);
            name = (TextView) rowView.findViewById(R.id.tvItemName);
            price = (TextView) rowView.findViewById(R.id.tvPrice);
            sold = (Button) rowView.findViewById(R.id.buttonSold);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adedit, parent, false);
        return new Holder(rowView);
    }

    @Override
    public void onBindViewHolder(Holder tempholder, int itemnum) {
        final String AdID, UserID, AdName, Username;
        final Holder holder = tempholder;
        final int position = itemnum;

        final DataAd dataAd = dataAdList.get(position);
        AdID = dataAd.getadAdID();
        UserID = dataAd.getuserUserID();
        AdName = dataAd.getadName();
        Username = dataAd.getuserUsername();

        /** Get reference to views
         finds and sets information based on list position  */

        holder.img.setImageBitmap(dataAd.getadImageThumb());
        holder.name.setText(AdName);
        holder.price.setText("$" + dataAd.getadPrice());

        holder.icon.setOnClickListener(new View.OnClickListener() {
            boolean delete = false;
            @Override
            public void onClick(View v) {
                if (delete) {
                    App.getClient().deleteAd(AdID);
                } else {
                    delete = true;
                    Toast.makeText(context, "Tap again to delete ad", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("ItemName", AdName);
                args.putString("ItemPrice", dataAd.getadPrice());
                args.putString("ItemImage", ImageHandler.ConvertBitmaptoString(dataAd.getadImageThumb()));
                args.putString("AdID", AdID);
                args.putString("UserID", UserID);


                Fragment newFragment = new PopUpItemSold();
                newFragment.setArguments(args);
                FragmentTransaction FT = fragmentManager.beginTransaction();
                FT.add(R.id.fragmentholder, newFragment);
                FT.addToBackStack("");
                FT.commit();
            }
        });
    }


}
