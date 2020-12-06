package com.example.taskmanagerproject.Controller.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.taskmanagerproject.Controller.Fragment.AdminFragment;
import com.example.taskmanagerproject.R;

public class AdminActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,AdminActivity.class);
        return intent;

    }


    @Override
    public Fragment createFragment() {
        return AdminFragment.newInstance();
    }
}