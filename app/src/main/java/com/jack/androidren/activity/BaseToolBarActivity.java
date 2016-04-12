package com.jack.androidren.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jack.androidren.R;
import com.jack.androidren.lib.CroutonStyle;
import com.jack.androidren.lib.ThemeManager;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * Created by jack on 3/29/16.
 */
public class BaseToolBarActivity extends AppCompatActivity{

    protected FrameLayout content;
    protected Toolbar toolbar;
    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int colorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (shouldChangeTheme()) {
            ThemeManager.onActivityCreateSetTheme(this);
        }

        super.onCreate(savedInstanceState);
        super.setContentView(getBasicContentLayout());
        TypedArray array = obtainStyledAttributes(new int[]{R.attr.colorPrimary,R.attr.colorPrimaryDark,R.attr.colorAccent});
        colorPrimary = array.getColor(0, 0xFF1473AF);
        colorPrimaryDark = array.getColor(1, 0xFF11659A);
        colorAccent = array.getColor(2, 0xFF3C69CE);
        CroutonStyle.buildStyleInfo(colorPrimaryDark);
        CroutonStyle.buildStyleConfirm(colorAccent);
        array.recycle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        content = (FrameLayout) findViewById(R.id.content);
    }

    @Override
    public void setContentView(int layoutResID) {
        content.removeAllViews();
        getLayoutInflater().inflate(layoutResID, content);
    }

    @Override
    public void setContentView(View view) {
        content.removeAllViews();
        content.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        content.removeAllViews();
        content.addView(view, params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.clearCroutonsForActivity(this);
    }

    protected boolean shouldChangeTheme() {
        return true;
    }

    protected int getBasicContentLayout() {
        return R.layout.activity_base_toolbar;
    }

    protected FrameLayout getRootView() {
        return content;
    }

    public void setContentViewSuper(View view) {
        super.setContentView(view);
    }

    public void setContentViewSuper(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void setContentViewSuper(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }
}
