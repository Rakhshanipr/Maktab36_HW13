package com.example.hw13.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hw13.R;


public class AddTaskFragment extends DialogFragment {
    //region defind variable
    Button mButtonSetTime;
    Button mButtonSetDate;
    //endregion

    //region defind static method and variable
    public static final String TAG_DATE_PICKER_DIALOG = "com.example.hw13.controller.fragment.datePickerDialog";
    public static final String TAG_TIME_PICKER_DIALOG = "com.example.hw13.controller.fragment.TimePickerDialog";

    //endregion


    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
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
        View view= inflater.inflate(R.layout.fragment_add_task, container, false);
        findViews(view);
        setOnclickListners();
        return view;
    }

    private void setOnclickListners() {
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_DIALOG);
            }
        });

        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment=new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_DIALOG);
            }
        });

    }

    private void findViews(View view) {
        mButtonSetDate=view.findViewById(R.id.button_fragmentAddTask_setDate);
        mButtonSetTime=view.findViewById(R.id.button_fragmentAddTask_setTime);
    }
}