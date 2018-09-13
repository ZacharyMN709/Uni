package com.unisoftwareproductions.uni.Handlers.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.NavigationController;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

public class CustomAdapterAdFavourites extends RecyclerView.Adapter<CustomAdapterAdFavourites.Holder>{

    ArrayList<DataAd> dataAdList;
    UnfavouritedPass dataPasser;
    ArrayList<String> Favourites = new ArrayList<>();
    boolean clickdirect;
    Context context;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    public CustomAdapterAdFavourites(Context callingclass, ArrayList<DataAd> dataadlist, boolean movetoprofile, UnfavouritedPass listener) {
        dataPasser        = listener;
        clickdirect       = movetoprofile;
        dataAdList        = dataadlist;
        context           = callingclass;
        App               = new App(context);
        Favourites        = App.getFavouriteAds();
    }

    public interface UnfavouritedPass {
        void onDataPass(String data);
    }

    @Override
    public int getItemCount()  {
        return dataAdList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public TextView name, user, price, rating;
        public ImageView adimg, usrimg, chat, favourite;
        public RelativeLayout ad, usr;

        public Holder(View rowView) {
            super(rowView);
            adimg = (ImageView) rowView.findViewById(R.id.ivAdImage);
            name = (TextView) rowView.findViewById(R.id.tvItemName);
            price = (TextView) rowView.findViewById(R.id.tvPrice);
            usrimg = (ImageView) rowView.findViewById(R.id.profilepicturethumb);
            user = (TextView) rowView.findViewById(R.id.tvUsername);
            rating = (TextView) rowView.findViewById(R.id.tvUserRating);
            chat = (ImageView) rowView.findViewById(R.id.AdIconBottom);
            favourite = (ImageView) rowView.findViewById(R.id.AdIconFavourite);
            favourite.setImageResource(R.drawable.ic_heart_red_full);
            ad = (RelativeLayout) rowView.findViewById(R.id.ADHOLDER);
            usr = (RelativeLayout) rowView.findViewById(R.id.USERHOLDER);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adpreview, parent, false);
        return new Holder(rowView);
    }

    @Override
    public void onBindViewHolder(Holder tempholder, int itemnum) {
        final String AdID, UserID, AdName, Username;
        final Holder holder = tempholder;
        final int position = itemnum;

        final DataAd dataAd = dataAdList.get(holder.getAdapterPosition());
        AdID = dataAd.getadAdID();
        UserID = dataAd.getuserUserID();
        AdName = dataAd.getadName();
        Username = dataAd.getuserUsername();

        holder.adimg.setImageBitmap(dataAd.getadImageThumb());
        holder.name.setText(dataAd.getadName());
        holder.price.setText("$" + dataAd.getadPrice());
        holder.usrimg.setImageBitmap(dataAd.getuserUserThumb());
        holder.user.setText(dataAd.getuserUsername());
        holder.rating.setText(dataAd.getuserRating() + "% Rating");

        holder.adimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.toFullAd(context, UserID, AdID, AdName);
            }
        });

        holder.ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.toFullAd(context, UserID, AdID, AdName);
            }
        });

        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.toFullChat(context, UserID, AdID, Username);
            }
        });

        if (clickdirect) {
            holder.usr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavigationController.toOtherProfile(context, UserID);
                }
            });
        }

        holder.favourite.setOnClickListener(new View.OnClickListener() {
            boolean delete = false;
            @Override
            public void onClick(View v) {
                if (delete) {
                    holder.favourite.setImageResource(R.drawable.ic_heart_red_empty);
                    Favourites.remove(Favourites.indexOf(AdID));
                    App.setFavouriteAds(Favourites);
                    dataPasser.onDataPass(AdID);
                } else {
                    delete = true;
                    Toast.makeText(context, "Tap again to remove from Favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
