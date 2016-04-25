package com.jack.androidren.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jack.androidren.R;
import com.jack.androidren.entitys.NewsItem;
import com.jack.androidren.lib.ThemeManager;
import com.jack.androidren.lib.kits.PreKits;
import com.jack.androidren.lib.kits.UIKits;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jack on 4/11/16.
 */
public class NewsListAdapter extends BaseAdapter<NewsItem> {
    private boolean showLarge;
    private boolean showImage;
    private DisplayImageOptions optionsLarge;
    private DisplayImageOptions optionsSmall;
    private NightBitmapProcessor bitmapProcessor;
    private AnimateFirstDisplayListener listener = new AnimateFirstDisplayListener();

    @Override
    protected View bindViewAndData(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        return null;
    }

    public NewsListAdapter(Context context, List<NewsItem> items) {
        super(context, items);
        bitmapProcessor = new NightBitmapProcessor();
        showLarge = PreKits.getBoolean(context, context.getString(R.string.pref_show_large_image_key), false);
        showImage = PreKits.getBoolean(context, context.getString(R.string.pref_show_list_news_image_key), true);
        optionsLarge = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.imagehoder)
                .showImageOnFail(R.drawable.imagehoder_error)
                .preProcessor(bitmapProcessor)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        optionsSmall = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.imagehoder_sm)
                .showImageOnFail(R.drawable.imagehoder_error_sm)
                .preProcessor(bitmapProcessor)
                .displayer(new RoundedBitmapDisplayer(UIKits.dip2px(context,10))).build();
        bitmapProcessor.setEnable(ThemeManager.isNightTheme(context));
    }

    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay && imageView.getVisibility() == View.VISIBLE) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    class NightBitmapProcessor implements BitmapProcessor {
        private boolean enable = false;
        private PorterDuffXfermode mode;

        public NightBitmapProcessor() {
            mode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
        }

        @Override
        public Bitmap process(Bitmap bitmap) {
            if (enable) {
                final Paint paint = new Paint();
                paint.setAntiAlias(true);
                Bitmap target = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(target);
                canvas.drawBitmap(bitmap, 0, 0, paint);
                paint.setXfermode(mode);
                canvas.drawARGB(70, 0, 0, 0);
                bitmap.recycle();
                return target;
            } else {
                return bitmap;
            }
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }
    }
}
