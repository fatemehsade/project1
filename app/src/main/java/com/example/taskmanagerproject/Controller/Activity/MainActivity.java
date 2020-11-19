package com.example.taskmanagerproject.Controller.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.taskmanagerproject.Controller.Fragment.StateFragment;
import com.example.taskmanagerproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager;
    private TabLayout mTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        taskPagerAdaptor adaptor=new taskPagerAdaptor(this);
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

        public taskPagerAdaptor(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return StateFragment.newInstance("TODO");
                case 1:
                    return StateFragment.newInstance("DOING");
                case 2:
                    return StateFragment.newInstance("DONE");

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


