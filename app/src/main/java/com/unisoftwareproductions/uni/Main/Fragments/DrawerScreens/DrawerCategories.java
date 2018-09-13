package com.unisoftwareproductions.uni.Main.Fragments.DrawerScreens;

import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.unisoftwareproductions.uni.Handlers.CustomAdapters.CustomAdapterCategoryList;

import com.unisoftwareproductions.uni.R;

public class DrawerCategories extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drawer_categories, container, false);

        /** Gets and sets the Category List text and drawables. */
        String[] CategoryNames = getActivity().getResources().getStringArray(R.array.Category_array);
        TypedArray tArray = getResources().obtainTypedArray(R.array.Category_images);
        int count = tArray.length();
        Integer[] CategoryImages = new Integer[count];
        for (int i = 0; i < CategoryImages.length; i++) { CategoryImages[i] = tArray.getResourceId(i, 0); }
        tArray.recycle();

        GridView catlist = (GridView) view.findViewById(R.id.categorygrid);
        catlist.setAdapter(new CustomAdapterCategoryList(CategoryNames, CategoryImages, getActivity()));
        return view;
    }

}

