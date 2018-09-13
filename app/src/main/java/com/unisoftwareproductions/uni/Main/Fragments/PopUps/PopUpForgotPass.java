package com.unisoftwareproductions.uni.Main.Fragments.PopUps;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.unisoftwareproductions.uni.Handlers.NavigationHandling.App;

import com.unisoftwareproductions.uni.R;

public class PopUpForgotPass extends Fragment implements View.OnClickListener {

    EditText tv;
    com.unisoftwareproductions.uni.Handlers.NavigationHandling.App App;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_forgotpass, container, false);
        App = new App(getActivity());
        view.findViewById(R.id.shadow).setOnClickListener(this);
        view.findViewById(R.id.confirm).setOnClickListener(this);
        tv = (EditText) view.findViewById(R.id.etForgot);
        return view;
    }

    public void onClick(View view) {
        if (view.getId() == R.id.confirm) {
            String success = App.getClient().changePass(tv.getText().toString());
            Toast.makeText(getActivity(), success, Toast.LENGTH_SHORT).show();
        }
        getActivity().onBackPressed();
    }
}

