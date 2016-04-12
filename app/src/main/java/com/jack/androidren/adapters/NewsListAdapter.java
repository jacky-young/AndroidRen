package com.jack.androidren.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.androidren.entitys.NewsItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by jack on 4/11/16.
 */
public class NewsListAdapter extends BaseAdapter<NewsItem> {
    private boolean showLarge;
    private boolean showImage;
    private DisplayImageOptions optionsLarge;
    private DisplayImageOptions optionsSmall;

    @Override
    protected View bindViewAndData(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        return null;
    }

    public NewsListAdapter(Context context, List<NewsItem> items) {
        super(context, items);

    }
}
