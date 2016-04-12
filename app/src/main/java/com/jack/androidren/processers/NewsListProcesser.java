package com.jack.androidren.processers;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jack.androidren.R;
import com.jack.androidren.adapters.BaseAdapter;
import com.jack.androidren.data.ListDataProvider;
import com.jack.androidren.entitys.NewsItem;

/**
 * Created by jack on 4/11/16.
 */
public class NewsListProcesser<DataProvider extends ListDataProvider<NewsItem, ? extends BaseAdapter<NewsItem>>> extends BaseListProcesser<NewsItem, DataProvider> {


    public NewsListProcesser(DataProvider provider) {
        super(provider);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list, menu);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
