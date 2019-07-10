package com.byui.thf10;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPageAdapter extends FragmentPagerAdapter {


    public SectionsPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;


        switch(position) {
            case 0:
                fragment = new Tab1Fragment();
                break;
            case 1:
                fragment = new Tab2Fragment();
                break;
            case 2:
                fragment = new Tab3Fragment();
                break;

            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        //because there's 3 fragment
        return 3;
    }
}