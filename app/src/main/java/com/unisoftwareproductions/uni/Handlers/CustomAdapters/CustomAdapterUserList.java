package com.unisoftwareproductions.uni.Handlers.CustomAdapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataUser;
import com.unisoftwareproductions.uni.Handlers.ImageHandler;
import com.unisoftwareproductions.uni.Main.Fragments.PopUps.PopUpRateUser;

import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

public class CustomAdapterUserList extends BaseAdapter {

    ArrayList<DataUser> dataUserList = new ArrayList<>();
    Context context;
    boolean rateuser;
    FragmentManager fragmentManager;

    private static LayoutInflater inflater=null;
    public CustomAdapterUserList(ArrayList<DataUser> datauser, Context con, boolean rate, FragmentManager frag) {
        dataUserList = datauser;
        context = con;
        rateuser = rate;
        fragmentManager = frag;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    // Get number of items in list
    public int getCount() {
        return dataUserList.size();
    }

    @Override
    // Get position of item in list
    public Object getItem(int position) {
        return position;
    }

    @Override
    // ???
    public long getItemId(int position) {
        return position;
    }

    public class Holder
    {
        TextView name, rating;
        ImageView iv;
        RelativeLayout rl;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        final DataUser dataUser = dataUserList.get(position);

        rowView = inflater.inflate(R.layout.item_friend, null);

        holder.rl = (RelativeLayout) rowView.findViewById(R.id.USERHOLDER);
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rateuser) {
                    Bundle args = new Bundle();
                    args.putString("Username", dataUser.getuserUsername());
                    args.putString("UserImage", ImageHandler.ConvertBitmaptoString(dataUser.getuserUserMain()));
                    args.putString("Rating", dataUser.getuserRating());
                    args.putString("UserID", dataUser.getuserUserID());


                    Fragment newFragment = new PopUpRateUser();
                    newFragment.setArguments(args);
                    FragmentTransaction FT = fragmentManager.beginTransaction();
                    FT.add(R.id.fragmentholder, newFragment);
                    FT.addToBackStack("");
                    FT.commit();
                } else {

                }
            }
        });

        holder.name = (TextView) rowView.findViewById(R.id.tvUsername);
        holder.name.setText(dataUser.getuserUsername());

        holder.rating = (TextView) rowView.findViewById(R.id.tvUserRating);
        holder.rating.setText(dataUser.getuserRating() + "% Rating");

        holder.iv = (ImageView) rowView.findViewById(R.id.profilepicture);
        holder.iv.setImageBitmap(dataUser.getuserUserMain());

        return rowView;
    }

}
