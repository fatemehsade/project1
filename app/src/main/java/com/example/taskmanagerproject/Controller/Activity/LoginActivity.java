package com.example.taskmanagerproject.Controller.Activity;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerproject.Controller.Fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {
    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return new LoginFragment();
    }


}