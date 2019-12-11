package com.android.feedreader;

import com.android.feedreader.feeds.api.ApiService;
import com.android.feedreader.feeds.api.FlowSDKApiClient;
import com.android.feedreader.feeds.app.FeedApplication;

public class FeedReaderTestApplication extends FeedApplication {
    private ApiService apiService;
    private static final String BASE_URL = "http://127.0.0.1:8080";

    @Override
    public ApiService getApiService() {
        if (apiService == null) {
             FlowSDKApiClient apiClient = new FlowSDKApiClient.Builder()
                    .setBaseUrl(BASE_URL)
                    .create();

            apiService = apiClient.getApiService();
        }
        return apiService;
    }
}
