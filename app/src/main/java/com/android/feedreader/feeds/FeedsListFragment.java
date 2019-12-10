package com.android.feedreader.feeds;

public class FeedsListFragment extends BaseFragment {

    public FeedsListFragment() {
        // Required empty public constructor
    }

    public static FeedsListFragment newInstance() {
        return new FeedsListFragment();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        super.onNetworkChange(isConnected);
    }
}
