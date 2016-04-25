package com.jack.androidren.fragment;


import com.jack.androidren.adapters.BaseAdapter;
import com.jack.androidren.data.impl.BaseNewsListDataProvider;
import com.jack.androidren.entitys.NewsItem;
import com.jack.androidren.processers.NewsListProcesser;


/**
 * Created by jack on 4/12/16.
 */
public abstract class BaseNewsListFragment<Adapter extends BaseAdapter<NewsItem>, Provider extends BaseNewsListDataProvider<Adapter>> extends BaseListFragment<NewsItem, Adapter, Provider, NewsListProcesser<Provider>> {
    @Override
    protected NewsListProcesser<Provider> createProcesser(Provider provider) {
        return new NewsListProcesser<>(provider);
    }
}
