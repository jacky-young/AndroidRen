package com.jack.androidren.data.impl;


import android.app.Activity;

import com.jack.androidren.adapters.BaseAdapter;
import com.jack.androidren.data.ListDataProvider;
import com.jack.androidren.entitys.NewsItem;

/**
 * Created by jack on 4/11/16.
 */
public abstract class BaseNewsListDataProvider<Adapter extends BaseAdapter<NewsItem>> extends ListDataProvider<NewsItem, Adapter>{
    public BaseNewsListDataProvider(Activity activity) {
        super(activity);
    }
}
