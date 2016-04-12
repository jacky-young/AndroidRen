package com.jack.androidren.processers;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.jack.androidren.data.BaseDataProvider;

/**
 * Created by jack on 4/5/16.
 */
public interface BaseProcesser<E, DataProvider extends BaseDataProvider<E>> {
    void onResume();
    void onPause();
    void onDestroy();
    void assumeView(View view);
    void loadData(boolean startup);
    AppCompatActivity getActivity();
    void setProvider(DataProvider provider);
    void setActivity(AppCompatActivity activity);
    boolean onOptionsItemSelected(MenuItem item);
    void onConfigurationChanged(Configuration newConfig);
    void onCreateOptionsMenu(Menu menu, MenuInflater inflater);
}
