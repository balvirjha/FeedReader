package com.android.feedreader;

import com.android.feedreader.feeds.api.ApiService;
import com.android.feedreader.feeds.api.FlowSDKApiClient;
import com.android.feedreader.feeds.app.FeedApplication;

import static com.android.feedreader.feeds.app.AppConstants.TEST_BASE_URL;

public class FeedReaderTestApplication extends FeedApplication {
    private ApiService apiService;


    @Override
    public ApiService getApiService() {
        if (apiService == null) {
             FlowSDKApiClient apiClient = new FlowSDKApiClient.Builder()
                    .setBaseUrl(TEST_BASE_URL)
                    .create();

            apiService = apiClient.getApiService();
        }
        return apiService;
    }
}
