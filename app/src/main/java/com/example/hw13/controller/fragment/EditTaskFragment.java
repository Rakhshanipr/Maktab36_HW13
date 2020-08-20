package com.example.hw13.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hw13.R;
import com.example.hw13.model.State;
import com.example.hw13.model.Task;
import com.example.hw13.model.User;
import com.example.hw13.repository.TaskRepository;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTaskFragment extends DialogFragment {
    //region defind variable
    TaskRepository mTaskRepository;


    Button mButtonSetTime;
    Button mButtonSetDate;
    Button mButtonSave;
    ImageButton mImageButtonDelete;

    EditText mEditTextDescribe;
    EditText mEditTextTitle;

    RadioButton mRadioButtonDone;
    RadioButton mRadioButtonDoing;
    RadioButton mRadioButtonToDo;

    Date mDate;
    LocalTime mLocalTime;

    Task mTask;
    //endregion

    //region defind static method and variable

    public static final int REQUEST_CODE_DATE_PICKER_FRAGMENT = 1;
    public static final int REQUEST_CODE_TIME_PICKER_FRAGMENT = 0;

    public static final String ARG_UUID_OF_TASK = "UUIDOfTask";


    public static final String TAG_DATE_PICKER_DIALOG = "com.example.hw13.controller.fragment.datePickerDialog";
    public static final String TAG_TIME_PICKER_DIALOG = "com.example.hw13.controller.fragment.TimePickerDialog";

    //endregion

    public EditTaskFragment() {
        // Required empty public constructor
    }

    public static EditTaskFragment newInstance(UUID uuid) {
        EditTaskFragment fragment = new EditTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_UUID_OF_TASK, uuid);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);
        findViews(view);
        setInitialization();
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

    private void findViews(View view) {
        mButtonSetTime = view.findViewById(R.id.button_fragmentEditTask_setTime);
        mButtonSetDate = view.findViewById(R.id.button_fragmentEditTask_setDate);
        mButtonSave = view.findViewById(R.id.button_fragmentEditTask_save);
        mImageButtonDelete = view.findViewById(R.id.imageButtton_fragmentEditTask_delete);

        mEditTextTitle = view.findViewById(R.id.editText_fragmentEditTask_title);
        mEditTextDescribe = view.findViewById(R.id.editText_fragmentEditTask_describe);


        mRadioButtonToDo = view.findViewById(R.id.radioButton_fragmentEditTask_toDo);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_fragmentEditTask_Doing);
        mRadioButtonDone = view.findViewById(R.id.radioButton_fragmentEditTask_Done);


    }

    private void setInitialization() {
        mTask = mTaskRepository.get((UUID) getArguments().getSerializable(ARG_UUID_OF_TASK));

        mEditTextTitle.setText(mTask.getTitle());
        mEditTextDescribe.setText(mTask.getDescribe());

        if (mTask.getState() == State.Done)
            mRadioButtonDone.setChecked(true);
        if (mTask.getState() == State.Doing)
            mRadioButtonDoing.setChecked(true);
        if (mTask.getState() == State.ToDo)
            mRadioButtonToDo.setChecked(true);

        mDate = mTask.getDate();
        mLocalTime = mTask.getLocalTime();
    }

    private void setOnclickListners() {
        mButtonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(mDate);
                datePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_TIME_PICKER_FRAGMENT);
                datePickerFragment.show(getFragmentManager(), TAG_DATE_PICKER_DIALOG);
            }
        });

        mButtonSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(mLocalTime);
                timePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_DATE_PICKER_FRAGMENT);
                timePickerFragment.show(getFragmentManager(), TAG_TIME_PICKER_DIALOG);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTask.setDate(mDate);
                mTask.setLocalTime(mLocalTime);
                mTask.setDescribe(mEditTextDescribe.getText().toString());
                mTask.setTitle(mEditTextTitle.getText().toString());
                mTask.setState(getState());
                mTask.setUser(User.sOnlineUser);
                mTaskRepository.update(mTask);
                getDialog().dismiss();
            }
        });

        mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTaskRepository.delete(mTask);
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
}