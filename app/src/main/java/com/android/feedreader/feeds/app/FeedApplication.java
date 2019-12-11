package com.android.feedreader.feeds.app;

import android.content.Context;
import android.content.IntentFilter;

import androidx.lifecycle.MutableLiveData;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.feedreader.BuildConfig;
import com.android.feedreader.feeds.api.ApiService;
import com.android.feedreader.feeds.api.FlowSDKApiClient;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.repo.FeedsRepo;

public class FeedApplication extends MultiDexApplication implements NetworkStateReceiver.NetworkStateReceiverListener {
    private MutableLiveData<Event<Boolean>> isConnected;
    private static FeedApplication instance;
    private FeedsRepo feedsRepo;
    private ApiService apiService;
    public static final String APP_TAG = FeedApplication.class.getSimpleName();

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

    public FeedsRepo getFeedsRepo() {
        if (feedsRepo == null) {
            feedsRepo = new FeedsRepo(getApiService());
        }
        return feedsRepo;
    }

    public ApiService getApiService() {
        if (apiService == null) {
            FlowSDKApiClient apiClient = new FlowSDKApiClient.Builder()
                    .setBaseUrl(BuildConfig.BASE_URL)
                    .create();

            apiService = apiClient.getApiService();
        }
        return apiService;
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
