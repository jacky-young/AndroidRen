package com.jack.androidren.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jack.androidren.R;
import com.pnikosis.materialishprogress.ProgressWheel;

/**
 * Created by jack on 4/6/16.
 */
public class PageLoader extends DataSetObserver implements OnClickListener, OnScrollListener{
    private TextView normalTextView;
    private TextView finallyTextView;
    private ListAdapter adapter;
    private ListView listView;
    private ProgressWheel progressBar;
    //ListView 底部View
    private View moreView;
    //最后所见条目的索引
    private int lastVisibleIndex;
    private OnScrollListener mOnScrollListener;
    private OnLoadListener mOnLoadListener;
    private boolean enable;
    private boolean isLoading;
    private boolean isFinally = false;
    private Mode mode = Mode.AUTO_LOAD;

    private PageLoader() {

    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    @Override
    public void onInvalidated() {
        onChanged();
    }

    @Override
    public void onChanged() {
        if (this.adapter == null) {
            throw new RuntimeException("must set adapter after notifyDataSetChanged");
        }
        bindEvent();
    }

    public ListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.adapter == null && adapter == null) {
            throw new IllegalArgumentException("adapter not to be null");
        }
        if (this.adapter != null) {
            this.adapter.unregisterDataSetObserver(this);
        }
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(this);
        this.listView.setAdapter(adapter);
        bindEvent();
    }

    @Override
    public void onClick(View view) {
        if (enable && mode == Mode.CLICK_TO_LOAD) {
            setLoading(true);
            mOnLoadListener.onLoading(this, false);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        //滑动到底部，判断listview已停止滚动并且最后可视的条码等于adapter的条目
        if (enable && mode == Mode.AUTO_LOAD && !isLoading && scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == listView.getAdapter().getCount()) {
            if (mOnLoadListener != null) {
                setLoading(true);
                mOnLoadListener.onLoading(this, true);
            }
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean isloading) {
        if (enable) {
            isLoading = isloading;
            if (isloading) {
                progressBar.spin();
                progressBar.setVisibility(View.VISIBLE);
                normalTextView.setVisibility(View.GONE);
            } else {
                progressBar.stopSpinning();
                progressBar.setVisibility(View.GONE);
                normalTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastVisibleIndex = firstVisibleItem + visibleItemCount;
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(absListView, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public enum Mode {
        CLICK_TO_LOAD, AUTO_LOAD
    }

    public interface OnLoadListener {
        void onLoading(PageLoader pageLoader, boolean isAutoLoad);
    }

    public CharSequence getLoadingText() {
        return normalTextView.getText();
    }

    public CharSequence getFinallyText() {
        return finallyTextView.getText();
    }

    public void setNormalText(CharSequence title) {
        normalTextView.setText(title);
    }

    public void setNormalText(int res) {
        normalTextView.setText(res);
    }

    public void setFinallyText(CharSequence text) {
        finallyTextView.setText(text);
    }

    public void setFinallyText(int res) {
        finallyTextView.setText(res);
    }

    public void setFinally() {
        if (enable) {
            isFinally = true;
            setLoading(false);
            normalTextView.setVisibility(View.GONE);
            finallyTextView.setVisibility(View.VISIBLE);
            enable = false;
        }
    }

    private void bindEvent() {
        if (adapter.getCount() == 0) {
            setEnable(false);
        } else {
            if (mOnLoadListener == null) {
                setEnable(false);
            } else {
                if (!isFinally) {
                    setEnable(true);
                }
            }
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable && isFinally) {
            normalTextView.setVisibility(View.VISIBLE);
            finallyTextView.setVisibility(View.GONE);
            isFinally = false;
        }
        setupFootView();
    }

    private void setupFootView() {
        if (enable) {
            moreView.setVisibility(View.VISIBLE);
        } else {
            moreView.setVisibility(View.GONE);
        }
    }

    private PageLoader getPageLoader() {
        return this;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public static Builder from(ListView listView) {
        return new Builder(listView);
    }

    public static class Builder {
        private Context mContext;
        private PageLoader pageLoader;

        private Builder(ListView listView) {
            this.mContext = listView.getContext();
            pageLoader = new PageLoader();

            pageLoader.moreView = LayoutInflater.from(mContext).inflate(R.layout.paged_foot, pageLoader.listView, false);
            pageLoader.normalTextView = (TextView) pageLoader.moreView.findViewById(R.id.bt_load);
            pageLoader.finallyTextView = (TextView) pageLoader.moreView.findViewById(R.id.bt_finally);
            pageLoader.progressBar = (ProgressWheel) pageLoader.moreView.findViewById(R.id.pg);
            pageLoader.listView = listView;
        }

        public Builder setMode(Mode mode) {
            pageLoader.setMode(mode);
            return this;
        }
        public Builder setNormalText(CharSequence text) {
            pageLoader.setNormalText(text);
            return this;
        }

        public Builder setNormalText(int res) {
            pageLoader.setNormalText(res);
            return this;
        }

        public Builder setFinallyText(CharSequence text) {
            pageLoader.setFinallyText(text);
            return this;
        }

        public Builder setFinallyText(int res) {
            pageLoader.setFinallyText(res);
            return this;
        }

        public Builder setOnLoadListener(OnLoadListener mOnLoadListener) {
            pageLoader.setOnLoadListener(mOnLoadListener);
            return this;
        }

        public Builder setOnScrollListener(OnScrollListener mOnScrollListener) {
            pageLoader.setOnScrollListener(mOnScrollListener);
            return this;
        }

        public Builder setFinallyText(String text) {
            pageLoader.setFinallyText(text);
            return this;
        }

        public PageLoader builder() {
            if (pageLoader.listView.getAdapter() != null) {
                throw new RuntimeException("must set footview before set adapter");
            }

            pageLoader.listView.addFooterView(pageLoader.moreView, null, false);
            pageLoader.listView.setFooterDividersEnabled(false);
            pageLoader.normalTextView.setOnClickListener(pageLoader);
            if (pageLoader.mode == Mode.AUTO_LOAD) {
                pageLoader.listView.setOnScrollListener(pageLoader.getPageLoader());
            }
            pageLoader.setEnable(false);
            return  pageLoader;
        }
    }
}
