package com.jack.androidren.processers;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jack.androidren.R;
import com.jack.androidren.data.BaseDataProvider;
import com.jack.androidren.data.DataProviderCallback;

/**
 * Created by jack on 4/5/16.
 */
public abstract class BaseProcesserImpl<E, DataProvider extends BaseDataProvider<E>> implements BaseProcesser<E, DataProvider>, DataProviderCallback<E> {
    protected DataProvider provider;
    protected AppCompatActivity activity;
    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int titleColor;
    protected int windowBackground;
    protected int colorAccent;
    protected onOptionMenuSelect menuCallBack;

    public interface onOptionMenuSelect {
        boolean onMenuSelect(MenuItem item);
    }

    public BaseProcesserImpl(DataProvider provider) {
        this.provider = provider;
        provider.setCallback(this);
    }

    @Override
    public AppCompatActivity getActivity() {
        return activity;
    }

    @Override
    public void loadData(boolean startup) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuCallBack != null && menuCallBack.onMenuSelect(item);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
        this.provider.setActivity(activity);
        TypedArray array = activity.obtainStyledAttributes(new int[]{R.attr.colorPrimary,
                R.attr.colorPrimaryDark,R.attr.titleColor,android.R.attr.windowBackground,
                R.attr.colorAccent
        });
        colorPrimary = array.getColor(0,activity.getResources().getColor(R.color.toolbarColor));
        colorPrimaryDark = array.getColor(1,activity.getResources().getColor(R.color.statusColor));
        titleColor = array.getColor(2,activity.getResources().getColor(R.color.toolbarColor));
        windowBackground = array.getColor(3, Color.WHITE);
        colorAccent = array.getColor(4,activity.getResources().getColor(R.color.toolbarColor));
        array.recycle();
    }

    @Override
    public void setProvider(DataProvider provider) {
        this.provider = provider;
        provider.setCallback(this);
    }

    public void setMenuCallBack(onOptionMenuSelect menuCallBack) {
        this.menuCallBack = menuCallBack;
    }
}
