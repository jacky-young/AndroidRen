package com.jack.androidren.lib.kits;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jack on 3/29/16.
 */
public class PreKits {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences appPrefs = getSharedPreferences(context);
        return appPrefs.getBoolean(key, def);
    }

    public static void writeBoolean(Context context, String key, boolean val) {
        SharedPreferences appPrefs = getSharedPreferences(context);
        SharedPreferences.Editor editor = appPrefs.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static int getInt(Context context, String key, int def) {
        SharedPreferences appPrefs = getSharedPreferences(context);
        return appPrefs.getInt(key, def);
    }
}
