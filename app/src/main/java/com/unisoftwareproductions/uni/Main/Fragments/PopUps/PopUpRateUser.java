package com.unisoftwareproductions.uni.Main.Fragments.PopUps;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.unisoftwareproductions.uni.Handlers.ImageHandler;

import com.unisoftwareproductions.uni.R;

/**
 * Created by Zachary on 21-Jul-16.
 */
public class PopUpRateUser extends Fragment implements View.OnClickListener {

    String UserID;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_rateuser, container, false);
        context = getActivity();
        view.findViewById(R.id.shadow).setOnClickListener(this);
        view.findViewById(R.id.ibUp).setOnClickListener(this);
        view.findViewById(R.id.ibNeutral).setOnClickListener(this);
        view.findViewById(R.id.ibDown).setOnClickListener(this);

        Bundle args = getArguments();
        TextView Name = (TextView) view.findViewById(R.id.tvUsername);
        TextView Rating = (TextView) view.findViewById(R.id.tvUserRating);
        ImageView Image = (ImageView) view.findViewById(R.id.profilepicture);
        Name.setText(args.getString("Username"));
        Rating.setText("$" + args.getString("Rating"));
        Image.setImageBitmap(ImageHandler.ReassembleBitmapfromString(args.getString("UserImage"), ImageHandler.image_ad_main_dp()));
        UserID = args.getString("UserID");
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibUp:
                //Todo
//                App.getClient().rateUser(UserID, true);
                break;
            case R.id.ibDown:
                //Todo
//                App.getClient().rateUser(UserID, false);
                break;
        }
        getActivity().getFragmentManager().popBackStack();
        getActivity().finish();
    }
}