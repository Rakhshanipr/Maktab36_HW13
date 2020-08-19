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
import android.widget.DatePicker;

import com.example.hw13.R;

import java.sql.ResultSet;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.Result;

public class DatePickerFragment extends DialogFragment {
    public static final String BUNDLE_RESUALT_OF_DATE_PICKER = "resualtOfDatePicker";
    //region defind variable
    Button mButtonSave;
    Button mButtonCancel;
    DatePicker mDatePicker;
    //endregion
    public static final String ARG_DATE = "com.example.hw13.controller.fragment.date";

    public static DatePickerFragment newInstance(Time time) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, time);
        fragment.setArguments(args);
        return fragment;
    }

    public static DatePickerFragment newInstance() {
        DatePickerFragment fragment = new DatePickerFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        findViews(view);
        setOnClickListners();
        return view;
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
                int day = mDatePicker.getDayOfMonth();
                int month = mDatePicker.getMonth();
                int year = mDatePicker.getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                intent.putExtra(BUNDLE_RESUALT_OF_DATE_PICKER, calendar.getTime());
                Fragment fragment = getTargetFragment();
                fragment.onActivityResult(AddTaskFragment.REQUEST_CODE_DATE_PICKER_FRAGMENT, Activity.RESULT_OK, intent);
                getDialog().dismiss();
            }
        });
    }

    private void findViews(View view) {
        mButtonCancel = view.findViewById(R.id.button_fragmentDatePicker_cancel);
        mButtonSave = view.findViewById(R.id.button_fragmentDatePicker_save);
        mDatePicker = view.findViewById(R.id.datePicker_fragmentDatePicker_date);
    }


}