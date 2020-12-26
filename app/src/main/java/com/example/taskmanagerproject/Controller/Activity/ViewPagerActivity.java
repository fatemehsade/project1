package com.example.taskmanagerproject.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.taskmanagerproject.Controller.Fragment.StateFragment;
import com.example.taskmanagerproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "com.example.taskmanagerproject.userId";
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;
    private UUID mUserId;


    public static Intent newIntent(Context context, UUID userId){
        Intent intent=new Intent(context, ViewPagerActivity.class);
        intent.putExtra(EXTRA_USER_ID,userId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mUserId= (UUID) getIntent().getSerializableExtra(EXTRA_USER_ID);
        taskPagerAdaptor adaptor=new taskPagerAdaptor(this,mUserId);
        mViewPager.setAdapter(adaptor);
        new TabLayoutMediator(mTabLayout, mViewPager,
                (tab, position) ->{
            switch (position){
                case 0:
                    tab.setText("TODO");
                    break;
                case 1:
                    tab.setText("DOING");
                    break;
                case 2:
                    tab.setText("DONE");
                    break;
            }
        }
        ).attach();

    }

    private void findViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
    }


    public class taskPagerAdaptor extends FragmentStateAdapter {
        private final UUID mIdUser;

        public taskPagerAdaptor(@NonNull FragmentActivity fragmentActivity,UUID userId) {
            super(fragmentActivity);
            mIdUser=userId;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return StateFragment.newInstance("TODO",mIdUser);
                case 1:
                    return StateFragment.newInstance("DOING",mIdUser);
                case 2:
                    return StateFragment.newInstance("DONE",mIdUser);

                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}


