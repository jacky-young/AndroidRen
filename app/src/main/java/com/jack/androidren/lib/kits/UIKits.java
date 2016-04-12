package com.jack.androidren.lib.kits;

import android.content.res.ColorStateList;

/**
 * Created by jack on 3/29/16.
 */
public class UIKits {

    public static ColorStateList createColorStateList(int normal, int actived) {
        int[] colors = new int[]{normal, actived};
        int[][] states = new int[2][];
        states[0] = new int[] {android.R.attr.state_activated};
        states[1] = new int[] {};
        return new ColorStateList(states, colors);
    }
}
