package com.android.feedreader.feeds;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.android.feedreader.R;
import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {

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
