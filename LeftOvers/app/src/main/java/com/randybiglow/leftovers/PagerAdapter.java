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
                currentFragment = new MyFridgeFragment();
                return currentFragment;
            case 1:
                currentFragment = new RecipesFragment();
                return currentFragment;
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
