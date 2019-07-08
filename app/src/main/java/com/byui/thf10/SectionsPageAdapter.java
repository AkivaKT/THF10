package com.byui.thf10;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

class SectionsPageAdapter extends PagerAdapter {
    public SectionsPageAdapter(FragmentManager supportFragmentManager) {

    }

    public void addFragment(Tab2Fragment tab1Fragment, String tab1) {
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
