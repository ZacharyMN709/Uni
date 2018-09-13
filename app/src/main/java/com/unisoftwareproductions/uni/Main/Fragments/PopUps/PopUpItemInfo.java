package com.unisoftwareproductions.uni.Main.Fragments.PopUps;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unisoftwareproductions.uni.Handlers.ConnectionHandling.DataAd;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;
import com.unisoftwareproductions.uni.Handlers.NavigationHandling.NavigationController;

import com.unisoftwareproductions.uni.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopUpItemInfo extends Fragment implements View.OnClickListener {

    Context context;
    String AdID, UserID, ItemName;
    int AdIDint, UserIDint;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_iteminfo, container, false);
        App = new App(getActivity());
        view.findViewById(R.id.shadow).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        view.findViewById(R.id.buttonFullAd).setOnClickListener(this);
        view.findViewById(R.id.buttonOtherProfile).setOnClickListener(this);
        context = getActivity();

        Bundle args = getArguments();
        AdID = args.getString("AdID");
        UserID = args.getString("UserID");
        AdIDint = Integer.parseInt(AdID) - 1;
        UserIDint = Integer.parseInt(UserID);

        TextView Name = (TextView) view.findViewById(R.id.tvItemName);
        TextView Price = (TextView) view.findViewById(R.id.tvPrice);
        ImageView Image = (ImageView) view.findViewById(R.id.ivAdImage);
        TextView Username = (TextView) view.findViewById(R.id.tvUsername);
        TextView UserRating = (TextView) view.findViewById(R.id.tvUserRating);
        CircleImageView profileimage = (CircleImageView) view.findViewById(R.id.profilepicturethumb);

        DataAd dataAd = new DataAd(App.getClient().getAd(AdID), true);
        ItemName = dataAd.getadName();
        Name.setText(ItemName);
        Price.setText("$" + dataAd.getadPrice());
        Username.setText(dataAd.getuserUsername());
        UserRating.setText(dataAd.getuserRating()+ "% Rating");
        Image.setImageBitmap(dataAd.getadImageThumb());
        profileimage.setImageBitmap(dataAd.getuserUserThumb());
        return view;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.buttonFullAd) {
            NavigationController.toFullAd(context, UserID, AdID, ItemName);
        }
        if (view.getId() == R.id.buttonOtherProfile) {
            NavigationController.toOtherProfile(context, UserID);
        }
        getActivity().getFragmentManager().popBackStack();
    }
}

