package com.jack.androidren.processers;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jack.androidren.R;
import com.jack.androidren.adapters.BaseAdapter;
import com.jack.androidren.data.ListDataProvider;
import com.jack.androidren.lib.kits.PreKits;
import com.jack.androidren.lib.kits.ToolKits;
import com.jack.androidren.widget.PageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;

/**
 * Created by jack on 4/5/16.
 */
public class BaseListProcesser<DataType, DataProvider extends ListDataProvider<DataType, ? extends BaseAdapter<DataType>>>
        extends BaseProcesserImpl<List<DataType>, DataProvider>
        implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listView;
    private PageLoader pageLoader;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView headView;

    public BaseListProcesser(DataProvider provider) {
        super(provider);
    }

    @Override
    public void assumeView(View view) {
        this.listView = (ListView) view.findViewById(android.R.id.list);
        this.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        this.swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.setColorSchemeColors(colorPrimary, colorPrimaryDark,colorAccent);
        this.headView = (TextView) LayoutInflater.from(activity).inflate(R.layout.type_head, listView, false);
        this.headView.setText("类型：" + provider.getTypeName());
        this.listView.addHeaderView(headView, null, false);
        PageLoader.OnLoadListener loadListener = new PageLoader.OnLoadListener() {
            @Override
            public void onLoading(PageLoader pageLoader, boolean isAutoLoad) {
                BaseListProcesser.this.provider.loadNextData();
            }
        };
        this.pageLoader = PageLoader.from(listView).setFinallyText("-- The End --").setOnLoadListener(loadListener).builder();
        this.pageLoader.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
        this.pageLoader.setAdapter(this.provider.getAdapter());
        this.listView.setOnItemClickListener(getOnItemClickListener());
        this.listView.setOnItemLongClickListener(getOnItemLongClickListener());
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadSuccess(List<DataType> object) {

    }

    @Override
    public void onLoadFinish(int size) {
        provider.getAdapter().notifyDataSetChanged();
        if (provider.getAdapter().getCount()<provider.getPageSize()||size==0) {
            pageLoader.setFinally();
        } else {
            pageLoader.setLoading(false);
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoadFailure() {

    }

    @Override
    public void onRefresh() {
        if (pageLoader.getAdapter().getCount()>0) {
            pageLoader.setEnable(true);
        }
        provider.loadNewData();
    }

    @Override
    public void onResume() {
        if (PreKits.getBoolean(activity, activity.getString(R.string.pref_auto_page_key), false)) {
            this.pageLoader.setMode(PageLoader.Mode.AUTO_LOAD);
        } else {
            this.pageLoader.setMode(PageLoader.Mode.CLICK_TO_LOAD);
        }
    }

    @Override
    public void loadData(final boolean startup) {
        ToolKits.runInUIThread(new Runnable() {
            @Override
            public void run() {
                provider.loadData(startup);
                if (!provider.isCached() || PreKits.getBoolean(activity, activity.getString(R.string.pref_auto_reflush_key), false)) {
                    swipeRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
            }
        }, startup ? 260 : 0);
    }

    public DataProvider getProvider() {
        return provider;
    }

    private AdapterView.OnItemLongClickListener getOnItemLongClickListener() {
        return provider.getOnItemLongClickListener();
    }

    private AdapterView.OnItemClickListener getOnItemClickListener() {
        return provider.getOnItemClickListener();
    }

    public ListView getListView() {
        return listView;
    }

    public PageLoader getLoader() {
        return pageLoader;
    }

    public SwipeRefreshLayout getSwipeLayout() {
        return swipeRefreshLayout;
    }

    public void setHeadViewText(String type) {
        if (headView != null) {
            headView.setText("类型：" + type);
        }
    }
}
