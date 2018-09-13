package com.unisoftwareproductions.uni.Handlers.CustomAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.unisoftwareproductions.uni.R;

public class CustomAdapterCategoryList extends BaseAdapter {

    String[] CategoryName;
    Integer[] CategoryImage;
    Context context;
    private static LayoutInflater inflater=null;
    public CustomAdapterCategoryList(String[] categoryName, Integer[] categoryImage, Context con) {
        CategoryName = categoryName;
        CategoryImage = categoryImage;
        context = con;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    // Get number of items in list
    public int getCount() {
        return CategoryName.length;
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
        TextView name;
        ImageView iv;
    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.item_category, null);

        /** Get relevant view
         * Set image resource, based on list name, based on list position
         */

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcategorybroadcast(position);
            }
        });

        holder.name = (TextView) rowView.findViewById(R.id.tvCategoryName);
        holder.name.setText(CategoryName[position]);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcategorybroadcast(position);
            }
        });

        holder.iv = (ImageView) rowView.findViewById(R.id.ivCategoryImage);
        holder.iv.setImageResource(CategoryImage[position]);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcategorybroadcast(position);
            }
        });

        return rowView;
    }

    public void sendcategorybroadcast(int position) {
        Intent intent = new Intent("CategoryToSearch");
        intent.putExtra("CategoryName", CategoryName[position]);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
