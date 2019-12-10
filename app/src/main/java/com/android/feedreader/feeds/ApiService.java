package com.android.feedreader.feeds;


import com.android.feedreader.feeds.modals.Feeds;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("facts.json")
    Call<Feeds> getFeedsList();
}
