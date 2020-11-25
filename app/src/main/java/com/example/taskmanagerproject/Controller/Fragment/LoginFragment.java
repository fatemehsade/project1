package com.example.taskmanagerproject.Controller.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taskmanagerproject.Controller.Activity.MainActivity;
import com.example.taskmanagerproject.Controller.Activity.SignActivity;
import com.example.taskmanagerproject.Model.user;
import com.example.taskmanagerproject.R;
import com.example.taskmanagerproject.Repository.UserRepository;

import java.util.List;
import java.util.UUID;


public class LoginFragment extends Fragment {
    public static final int REQUEST_CODE_SIGN_FRAGMENT = 10;
    public static final String EXTRA_USER_ID = "userId";
    private EditText mEditText_password,mEditText_userName ;
    private Button mButton_Login,mButton_signUp;
    private UserRepository mRepository;
    private List<user> mUser;
    private user muser;


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
        mRepository=UserRepository.getInstance();
        mUser=mRepository.getUsers();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        findViews(view);
        setListener();
        return view;
    }



    private void findViews(View view) {
        mEditText_userName = view.findViewById(R.id.username_login);
        mEditText_password=view.findViewById(R.id.password_login);
        mButton_signUp=view.findViewById(R.id.btn_signUp_login);
        mButton_Login=view.findViewById(R.id.btn_login_login);
    }

    private void setListener(){
        mButton_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText_userName.getText().toString().equals("")){
                    toastMethod("pleas enter username");
                }if (mEditText_password.getText().toString().equals("")){
                    toastMethod("pleas enter password");
                }
                if (!mEditText_userName.getText().toString().equals("") &&
                        !mEditText_password.getText().toString().equals("")){
                    for (int i = 0; i <mUser.size() ; i++) {
                        if (mEditText_userName.getText().toString().equals(mUser.get(i).getUserName())){
                            if (mEditText_password.getText().toString().equals(mUser.get(i).getPassWord())) {
                                toastMethod("login");
                                muser=mRepository.get(mEditText_userName.getText().toString());
                                Intent intent= MainActivity.newIntent(getActivity(),muser.getUserId());
                                startActivity(intent);
                                return;
                            }else {
                                toastMethod("password is wrong");
                            }
                        }
                    }
                    toastMethod(" first signup");

                }




            }
        });

        mButton_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= SignActivity.newIntent(getActivity());
                startActivity(intent);
                Toast.makeText(getActivity(),"signUp",Toast.LENGTH_LONG).show();


            }
        });
    }

    private void toastMethod(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }
}