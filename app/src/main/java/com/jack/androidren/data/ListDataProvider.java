package com.jack.androidren.data;

import android.app.Activity;
import android.widget.AdapterView;

import com.jack.androidren.adapters.BaseAdapter;

import java.util.List;

/**
 * Created by jack on 4/5/16.
 */
public abstract class ListDataProvider<DataType, DataAdapter extends BaseAdapter<DataType>> extends BaseDataProvider<List<DataType>> {
    private DataAdapter adapter;
    private Activity mActivity;
    private int minPageSize;

    private int pageSize;
    protected boolean hasCached;

    public ListDataProvider(Activity activity) {
        super(activity);
    }

    public DataAdapter getAdapter() {
        if (adapter == null) {
            adapter = newAdapter();
        }
        return adapter;
    }

    @Override
    public void setActivity(Activity activity) {
        super.setActivity(activity);
        getAdapter().setContext(activity);
    }


    protected abstract DataAdapter newAdapter();

    public abstract String getTypeKey();

    public abstract String getTypeName();

    public abstract void loadNewData();

    public abstract void loadNextData();

    public AdapterView.OnItemClickListener getOnItemClickListener() {return null;}

    public AdapterView.OnItemLongClickListener getOnItemLongClickListener() {return null;}

    public boolean isCached() {
        return hasCached;
    }

    public int getMinPageSize() {return minPageSize;}

    public void setMinPageSize(int size) {
        this.minPageSize = size;
    }

    public int getPageSize() {return  pageSize;}

    public void setPageSize(int size) {
        this.pageSize = this.minPageSize = size;
    }
}
