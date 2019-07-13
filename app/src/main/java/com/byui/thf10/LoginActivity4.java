package com.byui.thf10;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity4 extends AppCompatActivity {

    private static final String TAG3 = "MainActivity";

    private SectionsPageAdapter3 mSectionsPageAdapter3;

    private ViewPager mViewPager3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //change here for activity_login2
        setContentView(R.layout.activity_login4);
        Log.d(TAG3, "onCreate: Starting.");

        mSectionsPageAdapter3 = new SectionsPageAdapter3(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager3 = (ViewPager) findViewById(R.id.container3);

        mViewPager3.setAdapter(mSectionsPageAdapter3);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs3);
        tabLayout.setupWithViewPager(mViewPager3);
    }



}