package com.android.feedreader.feeds;

@SuppressWarnings("WeakerAccess")
public interface NetworkChangeListener {
    void onNetworkChange(boolean isConnected);
}
