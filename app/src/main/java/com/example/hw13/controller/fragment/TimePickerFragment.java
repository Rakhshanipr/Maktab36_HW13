package com.example.hw13.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.hw13.R;

import java.time.LocalTime;

public class TimePickerFragment extends DialogFragment {

    public static final String ARG_LOCAL_DATE = "com.example.hw13.controller.fragment.LocalDate";
    public static final String ARG_IS_FROM_EDIT_TASK = "isFromEditTask";
    //region defind variable
    Button mButtonSave;
    Button mButtonCancel;
    TimePicker mTimePicker;
    //endregion

    public static final String ARG_TIME = "com.example.hw13.controller.fragment.time";
    public static final String BUNDLE_RESUALT_OF_LOCAL_TIME_PICKER = "resualtOfTimePicker";


    public static TimePickerFragment newInstance() {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_FROM_EDIT_TASK,false);
        fragment.setArguments(args);

        return fragment;
    }
    public static TimePickerFragment newInstance(LocalTime localTime) {
        TimePickerFragment fragment = new TimePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCAL_DATE,localTime);
        args.putBoolean(ARG_IS_FROM_EDIT_TASK,true);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        findViews(view);
        setOnClickListners();
        setInitialization();
        return view;
    }

    private void setInitialization() {
        if (getArguments().getBoolean(ARG_IS_FROM_EDIT_TASK)){
            LocalTime localTime=(LocalTime) getArguments().getSerializable(ARG_LOCAL_DATE);
            mTimePicker.setHour(localTime.getHour());
            mTimePicker.setMinute(localTime.getMinute());
        }

        mTimePicker.setIs24HourView(true);
    }

    private void setOnClickListners() {
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                LocalTime localTime = LocalTime.of(mTimePicker.getHour(), mTimePicker.getMinute());
                intent.putExtra(BUNDLE_RESUALT_OF_LOCAL_TIME_PICKER, localTime);
                Fragment fragment = getTargetFragment();
                fragment.onActivityResult(AddTaskFragment.REQUEST_CODE_TIME_PICKER_FRAGMENT, Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });
    }

    private void findViews(View view) {
        mButtonCancel = view.findViewById(R.id.button_fragmentTimePicker_cancel);
        mButtonSave = view.findViewById(R.id.button_fragmentTimePicker_save);
        mTimePicker = view.findViewById(R.id.timePicker_fragmentTimePicker_time);
    }


}