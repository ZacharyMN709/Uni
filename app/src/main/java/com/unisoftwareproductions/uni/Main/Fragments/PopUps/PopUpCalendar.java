package com.unisoftwareproductions.uni.Main.Fragments.PopUps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.unisoftwareproductions.uni.R;

public class PopUpCalendar extends Fragment implements View.OnClickListener {

    DatePicker ChosenDate;
    String BirthDate;
    CalendarPass dataPasser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.popup_registercalendar, container, false);
        view.findViewById(R.id.shadow).setOnClickListener(this);
        view.findViewById(R.id.confirm).setOnClickListener(this);
        view.findViewById(R.id.cancel).setOnClickListener(this);
        ChosenDate = (DatePicker) view.findViewById(R.id.choosedate);
        return view;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (CalendarPass) a;
    }

    public interface CalendarPass {
        void onDataPass(String data);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.confirm) {
            int Year  = ChosenDate.getYear();
            int Month = ChosenDate.getMonth() + 1;
            int Day   = ChosenDate.getDayOfMonth();
            BirthDate = Year + "-" + Month + "-" + Day;

            dataPasser.onDataPass(BirthDate);
        }
        getActivity().getFragmentManager().popBackStack();
    }
}

