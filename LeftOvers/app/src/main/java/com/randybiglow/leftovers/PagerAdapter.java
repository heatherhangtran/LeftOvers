package com.randybiglow.leftovers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mTabs;
    Fragment currentFragment;

    public PagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.mTabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new MyFridgeFragment();
            case 1:
                return new RecipesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabs;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
