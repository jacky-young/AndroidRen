package com.jack.androidren.data;

import android.app.Activity;

/**
 * Created by jack on 4/5/16.
 */
public abstract class BaseDataProvider<T> {
    private Activity mActivity;
    protected DataProviderCallback<T> callback;

    public BaseDataProvider(Activity activity) {
        mActivity = activity;
    }

    public void setCallback(DataProviderCallback<T> callback) {
        this.callback = callback;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    public abstract void loadData(boolean startup);
}
