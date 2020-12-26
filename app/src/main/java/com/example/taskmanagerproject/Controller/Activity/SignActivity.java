package com.example.taskmanagerproject.Controller.Activity;


import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;

import com.example.taskmanagerproject.Controller.Fragment.SignFragment;

public class SignActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,SignActivity.class);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return SignFragment.newInstance();
    }
}