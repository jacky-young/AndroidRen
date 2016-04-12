package com.jack.androidren.fragment;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jack.androidren.R;
import com.jack.androidren.lib.NavigationDrawerManager;
import com.jack.androidren.lib.kits.PreKits;
import com.jack.androidren.lib.kits.UIKits;

/**
 * Created by jack on 3/29/16.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    private NavigationDrawerCallbacks callbacks;

    private ActionBarDrawerToggle toggle;

    private DrawerLayout mDrawerLayout;

    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 1;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnDrawer;
    private ListView mListView;
    private ColorStateList textColorStateList;

    private static NavigationDrawerManager manager = new NavigationDrawerManager();

    static {
        manager.registerFragment("AAAAAA", new Fragment());
        manager.registerFragment("BBBBBB", new Fragment());
        manager.registerFragment("CCCCCC", new Fragment());
        manager.registerFragment("DDDDDD", new Fragment());
        manager.registerFragment("EEEEEE", new Fragment());
    }

    public NavigationDrawerFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnDrawer = PreKits.getBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, false);
        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mHeadView = inflater.inflate(R.layout.drawer_head_layout, container, false);
        mListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mListView.addHeaderView(mHeadView, null, false);
        mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.drawer_list_item, android.R.id.text1, manager.getTitles()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView)view.findViewById(android.R.id.text1)).setTextColor(textColorStateList);
                return view;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectItem(position);
            }
        });
        mListView.setItemChecked(mCurrentSelectedPosition, true);
        return mListView;
    }
    
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        ActionBar actionbar = getActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        toggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnDrawer) {
                    mUserLearnDrawer = true;
                    PreKits.writeBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, true);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnDrawer) {
                    mUserLearnDrawer = true;
                    PreKits.writeBoolean(getActivity(), PREF_USER_LEARNED_DRAWER, true);
                }
            }
        };
        if (!mUserLearnDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                toggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(toggle);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }

        TypedArray array = activity.getTheme().obtainStyledAttributes(new int[]{R.attr.textSelectColor,android.R.attr.textColor});
        textColorStateList = UIKits.createColorStateList(
                array.getColor(0, getResources().getColor(R.color.textColor)),
                array.getColor(1, getResources().getColor(R.color.toolbarColor))
        );
        array.recycle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    public interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(Fragment fragment, int pos);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mListView != null) {
            getActionBar().setTitle(manager.getTitle(position - 1));
            mListView.setItemChecked(position, true);
        }

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }

        if (callbacks != null) {
            callbacks.onNavigationDrawerItemSelected(manager.getFragment(position-1), position-1);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer(){
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void onBackPressed() {
        selectItem(1);
    }
}
