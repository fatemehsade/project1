package com.example.taskmanagerproject.Controller.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerproject.Model.User;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.UserDBRepository;


public class SignFragment extends Fragment {
    private EditText mEditText_userName, mEditText_password;
    private Button mButton_signUp;
    private UserDBRepository mRepository;
    private User mUser;


    public SignFragment() {
        // Required empty public constructor
    }


    public static SignFragment newInstance() {
        SignFragment fragment = new SignFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserDBRepository.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        findViews(view);
        setListener();
        return view;
    }

    private void findViews(View view) {
        mEditText_userName = view.findViewById(R.id.edit_text_user_sign);
        mEditText_password = view.findViewById(R.id.edit_text_password_sign);
        mButton_signUp = view.findViewById(R.id.btn_signup_sign);
    }

    private void setListener() {
        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText_userName.getText().toString().equals("") &&
                        mEditText_password.getText().toString().equals("")) {
                    toastMethod("pleas enter userName and Password ");
                } else if (mEditText_userName.getText().toString().equals("")) {
                    toastMethod("pleas enter userName");
                } else if (mEditText_password.getText().toString().equals("")) {
                    toastMethod("pleas enter password");
                } else if (mRepository.userExist(mEditText_userName.getText().toString())) {
                    toastMethod("user name is exist please again enter user name");
                } else {
                    mUser = new User();
                    mUser.setUserName(mEditText_userName.getText().toString());
                    mUser.setPassWord(mEditText_password.getText().toString());
                    mRepository.insertUser(mUser);
                    getActivity().finish();

                }
            }
        });
    }

    private void toastMethod(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}