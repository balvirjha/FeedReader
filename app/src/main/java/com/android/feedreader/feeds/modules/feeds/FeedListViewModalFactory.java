package com.android.feedreader.feeds.modules.feeds;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.feedreader.feeds.repo.FeedsRepo;

@SuppressWarnings({"unchecked", "NullableProblems"})
public class FeedListViewModalFactory extends ViewModelProvider.NewInstanceFactory {
    private FeedsRepo feedsRepo;

    public FeedListViewModalFactory(FeedsRepo feedsRepo) {
        this.feedsRepo = feedsRepo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FeedListViewModal(feedsRepo);
    }
}