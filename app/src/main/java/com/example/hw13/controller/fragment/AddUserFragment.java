package com.example.hw13.controller.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hw13.R;
import com.example.hw13.model.User;
import com.example.hw13.repository.UserRepository;

public class AddUserFragment extends DialogFragment {
    //region defind variable
    Button mButtonAdd;
    EditText mEditTextUserName;
    EditText mEditTextPassword;

    UserRepository mUserRepository;

    //endregion
    //region defind static method and variable
    public static AddUserFragment newInstance() {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        findViews(view);
        setOnclickListners();
        return view;
    }

    private void setOnclickListners() {
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEditTextUserName.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(getContext(), "please fill the acount attribute", Toast.LENGTH_SHORT).show();
                } else {
                    mUserRepository.add(new User(userName, password));
                    getDialog().dismiss();
                }
            }
        });
    }

    private void findViews(View view) {
        mButtonAdd = view.findViewById(R.id.button_addUser_addToList);
        mEditTextUserName = view.findViewById(R.id.editText_addUser_userName);
        mEditTextPassword = view.findViewById(R.id.editText_addUser_password);
    }
}