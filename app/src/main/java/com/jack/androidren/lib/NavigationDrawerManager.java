package com.jack.androidren.lib;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 3/29/16.
 */
public class NavigationDrawerManager {

    private List<String> titles = new ArrayList<String>(6);
    private List<Fragment> fragments = new ArrayList<Fragment>(6);

    public Fragment getFragment(int pos) {
        return fragments.get(pos);
    }

    public List<String> getTitles() {
        return titles;
    }

    public String getTitle(int pos) {
        return  titles.get(pos);
    }

    public void registerFragment(String title, Fragment fragment) {
        titles.add(title);
        fragments.add(fragment);
    }

    public void unRegisterFragment(Fragment fragment) {
        int index = fragments.indexOf(fragment);
        if (index != -1) {
            fragments.remove(index);
            titles.remove(index);
        }
    }
}
