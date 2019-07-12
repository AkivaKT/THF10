package com.byui.thf10;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionsPageAdapter2 extends FragmentPagerAdapter {


    public SectionsPageAdapter2(@NonNull FragmentManager fm2) {
        super(fm2);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment2;


        switch(position) {
            case 0:
                fragment2 = new Tab3Fragment();
                break;
            case 1:
                fragment2 = new Tab4Fragment();
                break;

            default:
                fragment2 = null;
        }
        return fragment2;
    }

    @Override
    public int getCount() {
        //because there's 2 fragment
        return 2;
    }
}