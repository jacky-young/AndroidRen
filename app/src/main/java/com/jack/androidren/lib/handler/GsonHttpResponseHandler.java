package com.jack.androidren.lib.handler;

import com.google.gson.reflect.TypeToken;
import com.jack.androidren.lib.kits.ToolKits;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.lang.reflect.Type;


/**
 * Created by jack on 4/12/16.
 */
public abstract class GsonHttpResponseHandler<T> extends TextHttpResponseHandler {

    protected Type type;

    public GsonHttpResponseHandler(TypeToken<T> typeToken) {
        this.type = typeToken.getType();
    }

    @Override
    public void onSuccess(int statusCode, org.apache.http.Header[] headers, String responseString) {
        if (statusCode == 200) {
            try {
                T e = ToolKits.getGson().fromJson(responseString, type);
                if (e != null) {
                    onSuccess(statusCode, headers, responseString, e);
                } else {
                    onFailure(statusCode, headers, responseString, new RuntimeException("response empty"));
                }
            } catch (Exception e) {
                onError(statusCode, headers, responseString, e);
            }
        }
    }

    protected abstract void onError(int statusCode, Header[] headers, String responseString, Throwable cause);

    public abstract void onSuccess(int statusCode, Header[] headers, String responseString, T object);

    @Override
    public void onProgress(int bytesWritten, int totalSize) {

    }
}
