package com.byui.thf10;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPageAdapter3 extends FragmentPagerAdapter {


    public SectionsPageAdapter3(@NonNull FragmentManager fm3) {
        super(fm3);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment3;


        switch(position) {
            case 0:
                fragment3 = new Tab5Fragment();
                break;
            case 1:
                fragment3 = new Tab6Fragment();
                break;

            default:
                fragment3 = null;
        }
        return fragment3;
    }

    @Override
    public int getCount() {
        //because there's 2 fragment
        return 2;
    }
}