package com.byui.thf10;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class LoginActivity3 extends AppCompatActivity {

    private static final String TAG2 = "MainActivity";

    private SectionsPageAdapter2 mSectionsPageAdapter2;

    private ViewPager mViewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //change here for activity_login2
        setContentView(R.layout.activity_login3);
        Log.d(TAG2, "onCreate: Starting.");

        mSectionsPageAdapter2 = new SectionsPageAdapter2(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager2 = (ViewPager) findViewById(R.id.container2);

        mViewPager2.setAdapter(mSectionsPageAdapter2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager2);
    }



}