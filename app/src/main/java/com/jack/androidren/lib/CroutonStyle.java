package com.jack.androidren.lib;

import java.util.HashMap;

import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by jack on 3/30/16.
 */
public class CroutonStyle {
    public static Style INFO;
    public static Style CONFIRM;
    private static HashMap<String, Style> styleHashMap = new HashMap<>(3);

    public static void buildStyleInfo(int color) {
        INFO = new Style.Builder(Style.INFO).setBackgroundColorValue(color&0xA0FFFFFF).build();
        styleHashMap.put("info", INFO);
    }

    public static void buildStyleConfirm(int color) {
        CONFIRM = new Style.Builder(Style.CONFIRM).setBackgroundColorValue(color&0xA0FFFFFF).build();
        styleHashMap.put("info", CONFIRM);
    }

    public static Style getStyle(String key) {
        Style style = styleHashMap.get(key);
        if (style != null) {
            return style;
        } else {
            return Style.ALERT;
        }
    }
}
