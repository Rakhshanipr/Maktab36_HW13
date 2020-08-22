package com.example.hw13.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hw13.R;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;
import com.example.hw13.repository.TaskRepository;

import java.time.LocalTime;
import java.util.Date;


public class AddTaskFragment extends DialogFragment {
    //region defind variable
    Button mButtonSetTime;
    Button mButtonSetDate;
    Button mButtonSave;

    EditText mEditTextDescribe;
    EditText mEditTextTitle;
    RadioButton mRadioButtonDone;
    RadioButton mRadioButtonDoing;
    RadioButton mRadioButtonToDo;

    Date mDate;
    LocalTime mLocalTime;

    TaskRepository mTaskRepository;
    //endregion

    //region defind static method and variable
    public static final String TAG_DATE_PICKER_DIALOG = "com.example.hw13.controller.fragment.datePickerDialog";
    public static final String TAG_TIME_PICKER_DIALOG = "com.example.hw13.controller.fragment.TimePickerDialog";


    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 0;
    //endregion

    public static AddTaskFragment newInstance() {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskRepository = TaskRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        findViews(view);
        setOnclickListners();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_DATE_PICKER_FRAGMENT) {
            onActivityResultDatePickerFragment(data);
        } else if (requestCode == REQUEST_CODE_TIME_PICKER_FRAGMENT) {
            onActivityResultTimePickerFragment(data);

        }


    }

    private void onActivityResultTimePickerFragment(Intent intent) {
        mLocalTime = (LocalTime) intent.getSerializableExtra(TimePickerFragment.BUNDLE_RESUALT_OF_LOCAL_TIME_PICKER);
        Toast.makeText(getContext(), mLocalTime.toString(), Toast.LENGTH_SHORT).show();

    }

    private void onActivityResultDatePickerFragment(Intent intent) {
        mDate = (Date) intent.getSerializableExtra(DatePickerFragment.BUNDLE_RESUALT_OF_DATE_PICKER);
        Toast.makeText(getContext(), mDate.toString(), Toast.LENGTH_SHORT).show();
    }

    private void setOnclickListners() {
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance();
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_DIALOG);
            }
        });

        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance();
                timePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_DATE_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_DIALOG);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task task = new Task();
                task.setDate(mDate);
                task.setLocalTime(mLocalTime);
                task.setDescribe(mEditTextDescribe.getText().toString());
                task.setTitle(mEditTextTitle.getText().toString());
                task.setState(getState());
                task.setUser(User.sOnlineUser);
                mTaskRepository.add(task);
                ListTaskFragment listTaskFragment=(ListTaskFragment) getTargetFragment();
                listTaskFragment.onActivityResult(ListTaskFragment.REQUEST_CODE_ADD_TASK_FRAGMENT_DIALOG,Activity.RESULT_OK,new Intent());
//                FragmentManager fragmentManager=getFragmentManager();
//                Fragment fragmentName = getFragmentManager().findFragmentByTag("TagName");
                getDialog().dismiss();

            }
        });

    }

    private State getState() {
        State state = State.Done;
        if (mRadioButtonDoing.isChecked())
            state = State.Doing;
        if (mRadioButtonToDo.isChecked())
            state = State.ToDo;
        return state;
    }

    private void findViews(View view) {
        mButtonSetDate = view.findViewById(R.id.button_fragmentAddTask_setDate);
        mButtonSetTime = view.findViewById(R.id.button_fragmentAddTask_setTime);
        mButtonSave = view.findViewById(R.id.button_fragmentAddTask_save);

        mEditTextTitle = view.findViewById(R.id.editText_fragmentAddTask_title);
        mEditTextDescribe = view.findViewById(R.id.editText_fragmentAddTask_describe);

        mRadioButtonDone = view.findViewById(R.id.radioButton_fragmentAddTask_Done);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_fragmentAddTask_Doing);
        mRadioButtonToDo = view.findViewById(R.id.radioButton_fragmentAddTask_toDo);
    }
}