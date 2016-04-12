package com.jack.androidren.data;

/**
 * Created by jack on 4/5/16.
 */
public interface DataProviderCallback<T> {
    void onLoadStart();
    void onLoadSuccess(T object);
    void onLoadFinish(int size);
    void onLoadFailure();
}
