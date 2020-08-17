package com.example.hw13.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hw13.R;
import com.example.hw13.controller.activity.ViewPagerTaskActivity;
import com.example.hw13.model.User;
import com.example.hw13.repository.UserRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    //region static variable and method
    public static final String TAG_ADD_USER_DIALOG = "com.example.hw13.controller.fragment.LoginFragmentaddUserDialog";

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    //endregion
    //region defind variable
    Button mButtonLogin;
    Button mButtonAddUser;
    EditText mEditTextUserName;
    EditText mEditTextPassword;

    UserRepository mUserRepository;
    //endregion


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setOnClickListners();
        mUserRepository.add(new User("1","1"));
        return view;
    }

    private void findViews(View view) {
        mButtonAddUser = view.findViewById(R.id.button_login_addUser);
        mButtonLogin = view.findViewById(R.id.button_login_Login);
        mEditTextUserName = view.findViewById(R.id.editText_login_userName);
        mEditTextPassword = view.findViewById(R.id.editText_login_password);
    }

    private void setOnClickListners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEditTextUserName.getText().toString();
                String password = mEditTextPassword.getText().toString();
                if (mUserRepository.isValid(userName, password)) {
//                    Toast.makeText(getActivity(), "valid", Toast.LENGTH_SHORT).show();
                    Intent intent= ViewPagerTaskActivity.newIntent(getContext());
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "userName or password not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButtonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddUserFragment addUserFragment =AddUserFragment.newInstance();
                addUserFragment.show(getFragmentManager(), TAG_ADD_USER_DIALOG);
            }
        });
    }
}