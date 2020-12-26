package com.example.taskmanagerproject.Controller.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerproject.Controller.Activity.AdminActivity;
import com.example.taskmanagerproject.Controller.Activity.ViewPagerActivity;
import com.example.taskmanagerproject.Controller.Activity.SignActivity;
import com.example.taskmanagerproject.Model.User;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.UserDBRepository;

import java.util.List;
import java.util.UUID;


public class LoginFragment extends Fragment {
    private EditText mEditText_password, mEditText_userName;
    private Button mButton_Login, mButton_signUp, mButton_exit, mButton_admin;
    private UserDBRepository mRepository;
    private List<User> mUser;
    private User mFindUserId;


    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = UserDBRepository.getInstance(getActivity());
        mUser = mRepository.getUsers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListener();
        return view;
    }


    private void findViews(View view) {
        mEditText_userName = view.findViewById(R.id.username_login);
        mEditText_password = view.findViewById(R.id.password_login);
        mButton_signUp = view.findViewById(R.id.btn_signUp_login);
        mButton_Login = view.findViewById(R.id.btn_login_login);
        mButton_exit = view.findViewById(R.id.exit_login);
        mButton_admin = view.findViewById(R.id.admin_login);
    }

    private void setListener() {
        mButton_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText_userName.getText().toString().equals("")) {
                    toastMethod("pleas enter username");
                }
                if (mEditText_password.getText().toString().equals("")) {
                    toastMethod("pleas enter password");
                }
                if (!mEditText_userName.getText().toString().equals("") &&
                        !mEditText_password.getText().toString().equals("")) {
                    mUser=mRepository.getUsers();
                    for (int i = 0; i < mUser.size(); i++) {
                        if (mEditText_userName.getText().toString().equals(mUser.get(i).getUserName())) {
                            if (mEditText_password.getText().toString().equals(mUser.get(i).getPassWord())) {
                                toastMethod("login");
                                mFindUserId = mRepository.returnUserWithUserName(
                                        mEditText_userName.getText().toString());
                                Intent intent = ViewPagerActivity.newIntent(
                                        getActivity(), mFindUserId.getUserId());
                                startActivity(intent);
                                return;
                            } else {
                                toastMethod("password is wrong");
                            }
                        }
                    }
                    toastMethod(" first signUp");
                }
            }
        });


        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignActivity.newIntent(getActivity());
                startActivity(intent);
                Toast.makeText(getActivity(), "signUp", Toast.LENGTH_LONG).show();


            }
        });
        mButton_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = mRepository.returnUserWithUserName(mEditText_userName.getText().toString());
                if (mEditText_userName.getText().toString().equals("") &&
                        mEditText_password.getText().toString().equals("")) {
                    toastMethod(" pleas Enter userName and password");
                } else if (mEditText_userName.getText().toString().equals("") ||
                        mEditText_password.getText().toString().equals("")) {
                    toastMethod("pleas Enter password or userName");
                } else if (!mRepository.userExist(mEditText_userName.getText().toString())) {
                    toastMethod("user in not exist ");

                } else if (mRepository.userExist(mEditText_userName.getText().toString())) {
                    if (!user.getPassWord().toString().equals(mEditText_password.getText().toString())) {
                        toastMethod("password is wrong");
                    }
                } else if (user.getPassWord().equals(mEditText_password.getText().toString())) {
                    mRepository.deleteUser(user);
                    mEditText_userName.setText("");
                    mEditText_password.setText("");
                }
            }
        });
        mButton_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditText_userName.getText().toString().equals("admin")){
                    toastMethod("userName is wrong");
                }else if (!mEditText_password.getText().toString().equals("admin")){
                    toastMethod("password is wrong");
                }else {
                    Intent intent = AdminActivity.newIntent(getActivity());
                    startActivity(intent);
                }

            }
        });
    }

    private void toastMethod(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
}