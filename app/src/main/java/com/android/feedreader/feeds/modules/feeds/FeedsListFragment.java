package com.android.feedreader.feeds.modules.feeds;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.feedreader.R;
import com.android.feedreader.databinding.FragmentFeedListBinding;
import com.android.feedreader.feeds.app.BaseFragment;
import com.android.feedreader.feeds.app.FeedApplication;
import com.android.feedreader.feeds.modals.FeedsResponse;
import com.android.feedreader.feeds.modals.Row;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.model.Resource;

import java.util.List;

@SuppressWarnings("ALL")
public class FeedsListFragment extends BaseFragment {
    FragmentFeedListBinding binding;
    private FeedListViewModal feedListViewModal;

    public FeedsListFragment() {
        // Required empty public constructor
    }

    public static FeedsListFragment newInstance() {
        return new FeedsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FeedListViewModalFactory viewModelFactory = new FeedListViewModalFactory(FeedApplication.getInstance().getFeedsRepo());
        feedListViewModal = ViewModelProviders.of(this, viewModelFactory).get(FeedListViewModal.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedListBinding.inflate(inflater, container, false);
        binding.setViewModal(feedListViewModal);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() == null) return;
        observeFeedsApi();
        callFeedsApi();
    }

    @SuppressWarnings("ConstantConditions")
    private void initRecyclerView(List<Row> feedsList) {
        RecyclerView productListRecyclerView = binding.feedListRecyclerView;
        FeedsAdapter mAdapter = new FeedsAdapter();
        mAdapter.addAllFeeds(feedsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        productListRecyclerView.setLayoutManager(mLayoutManager);
        productListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        productListRecyclerView.setAdapter(mAdapter);
        initSwipeToRefresh();
    }

    private void initSwipeToRefresh() {
        binding.swipeToRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeToRefreshLayout.setRefreshing(true);
            callFeedsApi();
        });
    }


    @SuppressWarnings("ConstantConditions")
    private void observeFeedsApi() {
        feedListViewModal.getFeedsResponse().observe(this, new Observer<Resource<Event<FeedsResponse>>>() {
            @Override
            public void onChanged(Resource<Event<FeedsResponse>> feedsResponse) {
                binding.setResource(feedsResponse);
                if (feedsResponse.data != null) {
                    FeedsResponse response = feedsResponse.data.getContentIfNotHandled();
                    if (response != null) {
                        if (binding.swipeToRefreshLayout != null) {
                            binding.swipeToRefreshLayout.setRefreshing(false);
                        }
                        if (response.getFeeds() != null) {
                            binding.errorView.setVisibility(View.GONE);
                            FeedsListFragment.this.initRecyclerView(response.getFeeds().getRows());
                            FeedsListFragment.this.updateToolbarTitle(response.getFeeds().getTitle());
                        } else if (response.getFeeds() == null && !TextUtils.isEmpty(response.getErrorMessage())) {
                            binding.errorView.setVisibility(View.VISIBLE);
                            Toast.makeText(FeedsListFragment.this.getActivity(), FeedsListFragment.this.getActivity().getString(R.string.errorString) + response.getErrorMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    @SuppressWarnings("ConstantConditions")
    private void updateToolbarTitle(String title) {
        Activity activity = getActivity();
        if (activity instanceof FeedsActivity) {
            ((FeedsActivity) activity).getSupportActionBar().setTitle(title);
        }
    }

    private void callFeedsApi() {
        binding.errorView.setVisibility(View.GONE);
        if (!feedListViewModal.isApiCallInProgress()) {
            if (binding.swipeToRefreshLayout == null || !binding.swipeToRefreshLayout.isRefreshing()) {
                binding.progressFeed.setVisibility(View.VISIBLE);
            }
            feedListViewModal.callFeedListApi();
        }
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        super.onNetworkChange(isConnected);
        callFeedsApi();
    }
}
