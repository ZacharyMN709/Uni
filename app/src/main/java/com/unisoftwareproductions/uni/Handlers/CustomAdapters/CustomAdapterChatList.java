package com.unisoftwareproductions.uni.Handlers.CustomAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataChatPreview;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.NavigationController;
import com.unisoftwareproductions.uni.R;

import java.util.ArrayList;

public class CustomAdapterChatList extends RecyclerView.Adapter<CustomAdapterChatList.Holder>{

    ArrayList<DataChatPreview> dataChatPreviewList;
    String USRID, USR;
    Context context;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    public CustomAdapterChatList(Context callingclass, ArrayList<DataChatPreview> chatPreviews) {
        dataChatPreviewList     = chatPreviews;
        context                 = callingclass;
        App = new App(context);
        USRID = App.getUSRID();
        USR = App.getUSR();
    }

    @Override
    public int getItemCount()  {
        return dataChatPreviewList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder  extends RecyclerView.ViewHolder
    {
        TextView user, msg, time;
        ImageView adimg, usrimg;
        RelativeLayout usr;

        public Holder(View rowView) {
            super(rowView);
            adimg = (ImageView) rowView.findViewById(R.id.ivAdImage);
            usrimg = (ImageView) rowView.findViewById(R.id.profilepicturethumb);
            user = (TextView) rowView.findViewById(R.id.tvUsername);
            msg = (TextView) rowView.findViewById(R.id.tvRecentMessage);
            time = (TextView) rowView.findViewById(R.id.tvTime);
            usr = (RelativeLayout) rowView.findViewById(R.id.CHATHOLDER);
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatpreview, parent, false);
        return new Holder(rowView);
    }

    @Override
    public void onBindViewHolder(Holder tempholder, int itemnum) {

        final String AdID, UserID, AdName, Username;
        final Holder holder = tempholder;
        final int position = itemnum;

        DataChatPreview dataChatPreview = dataChatPreviewList.get(position);
        AdID = dataChatPreview.getadAdID();
        UserID = dataChatPreview.getuserUserID();
        AdName = dataChatPreview.getadName();
        Username = dataChatPreview.getuserUsername();

        holder.adimg.setImageBitmap(dataChatPreview.getadImageThumb());
        holder.usrimg.setImageBitmap(dataChatPreview.getuserUserThumb());
        holder.user.setText(Username);
        holder.msg.setText(dataChatPreview.getchatRecentMessage());
        holder.time.setText(dataChatPreview.getchatTimeStamp());

        //  Sets OnClickListeners for each clickable area on ad preview
        holder.adimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.toFullAd(context, UserID, AdID, AdName);
            }
        });

        holder.usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationController.toFullChat(context, UserID, AdID, Username);
            }
        });
    }

}
