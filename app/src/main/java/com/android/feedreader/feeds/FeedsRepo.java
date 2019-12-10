package com.android.feedreader.feeds;

import androidx.lifecycle.MutableLiveData;

import com.android.feedreader.feeds.modals.Feeds;
import com.android.feedreader.feeds.modals.FeedsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsRepo {
    private ApiService apiService;
    private MutableLiveData<Event<FeedsResponse>> feedsMutableLiveData;

    public FeedsRepo(ApiService apiService) {
        this.apiService = apiService;
        feedsMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Event<FeedsResponse>> getFeedsList() {
        Call<Feeds> call = apiService.getFeedsList();
        call.enqueue(new Callback<Feeds>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(Call<Feeds> call, Response<Feeds> response) {
                if (response != null && response.isSuccessful() && response.code() == FlowSDKApiClient.API_RESPONSE_STATUS_CODE) {
                    feedsMutableLiveData.postValue(new Event<>(new FeedsResponse(response.body())));
                } else {
                    feedsMutableLiveData.postValue(new Event<>(new FeedsResponse(response.message(), String.valueOf(response.code()))));
                }
            }

            @Override
            public void onFailure(Call<Feeds> call, Throwable t) {
                feedsMutableLiveData.postValue(new Event<>(new FeedsResponse(t.getMessage(), String.valueOf(t.getCause()))));
            }
        });
        return feedsMutableLiveData;
    }

}
