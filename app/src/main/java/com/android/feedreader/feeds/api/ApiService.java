package com.android.feedreader.feeds.api;


import com.android.feedreader.feeds.modals.Feeds;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.android.feedreader.feeds.app.AppConstants.FEED_LIST_END_POINT;

public interface ApiService {
    @GET(FEED_LIST_END_POINT)
    Call<Feeds> getFeedsList();
}
