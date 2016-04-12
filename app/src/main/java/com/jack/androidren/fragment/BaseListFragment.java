package com.jack.androidren.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jack.androidren.R;
import com.jack.androidren.adapters.BaseAdapter;
import com.jack.androidren.data.ListDataProvider;
import com.jack.androidren.processers.BaseListProcesser;
import com.jack.androidren.processers.BaseProcesserImpl;

/**
 * Created by jack on 4/11/16.
 */
public abstract class BaseListFragment<DataType, Adapter extends BaseAdapter<DataType>, Provider extends ListDataProvider<DataType, Adapter>,
        Processer extends BaseListProcesser<DataType, Provider>> extends Fragment{

    protected Processer processer;
    private BaseProcesserImpl.onOptionMenuSelect menuCallBack;

    protected abstract Provider getProvider();


    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (processer==null) {
            processer = createProcesser(getProvider());
        }
        processer.setMenuCallBack(menuCallBack);
        processer.setActivity((AppCompatActivity) activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(hasMenu());
    }

    @Override
    public void onResume() {
        super.onResume();
        processer.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        processer.loadData(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_layout, container, false);
        processer.assumeView(view);
        return view;

    }

    @Override
    public void onDestroy() {
        processer.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return processer.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        processer.onCreateOptionsMenu(menu, inflater);
    }

    public boolean hasMenu() {
        return false;
    }

    protected abstract Processer createProcesser(Provider provider);

    public BaseListFragment setMenuCallBack(BaseProcesserImpl.onOptionMenuSelect menuCallBack) {
        this.menuCallBack = menuCallBack;
        return this;
    }
}
