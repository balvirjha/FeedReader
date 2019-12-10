package com.android.feedreader.feeds;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements NetworkChangeListener {

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            BaseActivity view = (BaseActivity) getActivity();
            view.addNetworkListener(this);
        }
    }

    @Override
    public void onStop() {
        if (getActivity() != null) {
            BaseActivity view = (BaseActivity) getActivity();
            view.removeNetworkListener();
        }
        super.onStop();
    }

    @SuppressWarnings("unused")
    @Override
    public void onNetworkChange(boolean isConnected) {
        // do nothing
    }
}
