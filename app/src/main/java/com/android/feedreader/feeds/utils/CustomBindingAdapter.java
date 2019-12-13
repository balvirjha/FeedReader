package com.android.feedreader.feeds.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;

import com.android.feedreader.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import static com.android.feedreader.feeds.app.AppConstants.ATTACH_PROGRESSBAR;
import static com.android.feedreader.feeds.app.AppConstants.PROGRESSBAR;
import static com.android.feedreader.feeds.app.AppConstants.VISIBLE_GONE;

public class CustomBindingAdapter {

    @BindingAdapter(VISIBLE_GONE)
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter({ATTACH_PROGRESSBAR, PROGRESSBAR})
    public static void setAttachedProgressBar(ImageView imageView, String url, ProgressBar progressBar) {
        if (!TextUtils.isEmpty(url)) {
            progressBar.setVisibility(View.VISIBLE);
            Picasso picasso = Picasso.get();
            picasso.setIndicatorsEnabled(true);
            picasso.setLoggingEnabled(true);
            picasso.load(url)
                    .resize(200, 200)
                    .onlyScaleDown()
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
