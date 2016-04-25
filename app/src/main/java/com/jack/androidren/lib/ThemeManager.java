package com.jack.androidren.lib;

import android.app.Activity;
import android.content.Context;

import com.jack.androidren.R;
import com.jack.androidren.lib.kits.PreKits;

/**
 * Created by jack on 3/29/16.
 */
public class ThemeManager {

    private static int[][] ThemeRes = {{
            R.style.Theme_Basic,
            R.style.Theme_Basic_NoAnim
    }, {
            R.style.Theme_Basic_TEAL,
            R.style.Theme_Basic_TEAL_NoAnim
    }, {
            R.style.Theme_Basic_BROWN,
            R.style.Theme_Basic_BROWN_NoAnim
    }, {
            R.style.Theme_Basic_ORANGE,
            R.style.Theme_Basic_ORANGE_NoAnim
    }, {
            R.style.Theme_Basic_PURPLE,
            R.style.Theme_Basic_PURPLE_NoAnim
    }, {
            R.style.Theme_Basic_GREEN,
            R.style.Theme_Basic_GREEN_NoAnim
    },{
            R.style.Theme_Basic_Night,
            R.style.Theme_Basic_Night_NoAnim
    }};

    public static void onActivityCreateSetTheme(Activity activity) {
        int[] themes = ThemeRes[PreKits.getInt(activity, "theme", 0)];
        activity.setTheme(themes[1]);
    }

    public static boolean isNightTheme(Context context) {
        return PreKits.getInt(context, "theme", 0) == ThemeRes.length-1;
    }
}
