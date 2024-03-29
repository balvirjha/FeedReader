package com.android.feedreader.feeds.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.android.feedreader.R;
import com.android.feedreader.feeds.model.Event;

@SuppressWarnings("ALL")
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private FeedApplication app;
    private NetworkChangeListener networkChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (FeedApplication) getApplication();
    }

    @Override
    protected void onStart() {
        super.onStart();
        app.getIsConnected().observe(this, new Observer<Event<Boolean>>() {
            @Override
            public void onChanged(@Nullable Event<Boolean> booleanEvent) {
                Boolean isConnected = booleanEvent.getContentIfNotHandled();
                if (isConnected != null) {
                    if (findViewById(R.id.include) != null) {
                        if (isNetworkAvailable()) {
                            findViewById(R.id.include).setVisibility(View.GONE);
                            if (networkChangeListener != null)
                                networkChangeListener.onNetworkChange(true);
                        } else {
                            findViewById(R.id.include).setVisibility(View.VISIBLE);
                            if (networkChangeListener != null)
                                networkChangeListener.onNetworkChange(false);
                        }
                    }
                }
            }
        });
        if (findViewById(R.id.include) != null) {
            if (isNetworkAvailable()) {
                findViewById(R.id.include).setVisibility(View.GONE);
            } else {
                findViewById(R.id.include).setVisibility(View.VISIBLE);
            }
        }
    }

    public void addNetworkListener(final NetworkChangeListener listener) {
        this.networkChangeListener = listener;
        if (findViewById(R.id.include) != null) {
            this.networkChangeListener = listener;
        }
    }

    public void removeNetworkListener() {
        this.networkChangeListener = null;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) FeedApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
