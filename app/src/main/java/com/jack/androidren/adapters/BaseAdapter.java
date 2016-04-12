package com.jack.androidren.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jack on 4/5/16.
 */
public abstract class BaseAdapter<E> extends android.widget.BaseAdapter {
    protected LayoutInflater inflater;
    protected List<E> items;
    protected Context context;

    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, List<E> items) {
        this(context);
        this.items = items;
    }

    public void setContext(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return bindViewAndData(inflater, i, view, viewGroup);
    }

    protected abstract View bindViewAndData(LayoutInflater inflater, int position, View convertView, ViewGroup parent);

    public List<E> getDataSet() {
        return items;
    }

    public void setDataSet(List<E> items) {
        this.items = items;
    }

    public E getDataSetItem(int position) {
        return items.get(position);
    }

    public void notifyDataSetChanged(boolean changeConfig) {
        super.notifyDataSetChanged();
    }
}
