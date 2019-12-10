package com.android.feedreader.feeds;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.android.feedreader.R;

public class FeedsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        loadFragment(FeedsListFragment.newInstance(), FeedsListFragment.class.getSimpleName());
    }

    private void loadFragment(BaseFragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
