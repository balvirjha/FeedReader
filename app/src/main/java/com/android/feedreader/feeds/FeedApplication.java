package com.android.feedreader.feeds;

import android.content.Context;
import android.content.IntentFilter;

import androidx.lifecycle.MutableLiveData;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class FeedApplication extends MultiDexApplication implements NetworkStateReceiver.NetworkStateReceiverListener {
    private MutableLiveData<Event<Boolean>> isConnected;
    private static FeedApplication instance;

    @SuppressWarnings("unused")
    public FeedApplication() {
        //nothing to handle
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isConnected = new MutableLiveData<>();
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public static FeedApplication getInstance() {
        return instance;
    }

    @Override
    public void networkAvailable() {
        isConnected.postValue(new Event<>(true));
    }

    @Override
    public void networkUnavailable() {
        isConnected.postValue(new Event<>(false));
    }

    public MutableLiveData<Event<Boolean>> getIsConnected() {
        return isConnected;
    }
}
