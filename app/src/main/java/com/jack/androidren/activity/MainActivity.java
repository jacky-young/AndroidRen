package com.jack.androidren.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jack.androidren.R;
import com.jack.androidren.fragment.NavigationDrawerFragment;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * Created by jack on 3/29/16.
 */
public class MainActivity extends BaseToolBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private int current = -1;
    private long lastpress;
    public boolean changeTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        changeTheme = false;
    }

    @Override
    public void onNavigationDrawerItemSelected(Fragment fragment, int pos) {
        if (fragment != null && current != pos) {
            Crouton.clearCroutonsForActivity(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit();
            current = pos;
        }
    }

    @Override
    protected int getBasicContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mNavigationDrawerFragment.isDrawerOpen()) {
                mNavigationDrawerFragment.closeDrawer();
                return true;
            } else if (current != 0) {
                mNavigationDrawerFragment.onBackPressed();
                return true;
            }

            if (System.currentTimeMillis() - lastpress < 1500) {
                this.finish();
            } else {
                lastpress = System.currentTimeMillis();
                Toast.makeText(this, "再按一次返回退出程序", Toast.LENGTH_SHORT).show();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!changeTheme) {
            this.finish();
            System.exit(0);
        }
    }
}
