package com.android.feedreader.feeds.modules.feeds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.android.feedreader.feeds.modals.FeedsResponse;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.repo.FeedsRepo;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class FeedListViewModal extends ViewModel {

    private boolean isApiCallInProgress;
    private FeedsRepo feedsRepo;
    private MutableLiveData<Boolean> shouldCallFeedListApi;
    private LiveData<Event<FeedsResponse>> feedsResponse;

    public FeedListViewModal(FeedsRepo feedsRepo) {
        this.feedsRepo = feedsRepo;
        shouldCallFeedListApi = new MutableLiveData<>();

        feedsResponse = Transformations.switchMap(shouldCallFeedListApi, input -> {
            LiveData<Event<FeedsResponse>> feedsResponseLiveData = feedsRepo.getFeedsList();
            MediatorLiveData<Event<FeedsResponse>> mediatorLiveData = new MediatorLiveData<>();
            mediatorLiveData.addSource(feedsResponseLiveData, feedsResponse -> {
                isApiCallInProgress = false;
                mediatorLiveData.postValue(feedsResponse);
            });
            return mediatorLiveData;
        });
    }

    public void callFeedListApi() {
        if (!isApiCallInProgress) {
            isApiCallInProgress = true;
            shouldCallFeedListApi.postValue(true);
        }
    }

    public boolean isApiCallInProgress() {
        return isApiCallInProgress;
    }

    public LiveData<Event<FeedsResponse>> getFeedsResponse() {
        return feedsResponse;
    }
}
