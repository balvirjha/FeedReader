package com.android.feedreader;

import android.content.Context;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;

import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.android.feedreader.feeds.app.AppConstants.FEED_LIST_END_POINT;

public class SuccessDispatcher extends Dispatcher {
    private Context context;
    private Map<String, String> responseFilesByPath = new HashMap<>();

    public SuccessDispatcher() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        responseFilesByPath.put(FEED_LIST_END_POINT, FEED_LIST_END_POINT);
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        MockResponse errorResponse = new MockResponse().setResponseCode(404);

        String pathWithoutQueryParams = Uri.parse(request.getPath()).getPath();

        if (pathWithoutQueryParams != null) {
            String responseBody = new AssetReaderUtil().asset(pathWithoutQueryParams);
            return new MockResponse().setResponseCode(200).setBody(responseBody);
        } else {
            String responseBody = new AssetReaderUtil().asset(pathWithoutQueryParams);
            return errorResponse.setBody(responseBody);
        }
    }

}
