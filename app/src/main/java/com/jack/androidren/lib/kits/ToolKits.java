package com.jack.androidren.lib.kits;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;


/**
 * Created by jack on 4/11/16.
 */
public class ToolKits {
    private static Gson gson;
    private static Handler mUIHandler;

    public static void runInUIThread(Runnable runnable, long delay) {
        if (mUIHandler == null) {
            mUIHandler = new Handler(Looper.getMainLooper());
        }
        mUIHandler.postDelayed(runnable, delay);
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

}
