package com.android.feedreader.feeds.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.android.feedreader.R;
import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("setImage")
    public static void setImageViewResource(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);
        }
    }
}
