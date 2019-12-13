package com.android.feedreader;

import android.content.Context;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;

import java.util.HashMap;
import java.util.Map;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import static com.android.feedreader.feeds.app.AppConstants.ERROR_FEED_LIST_END_POINT;

public class ErrorDispatcher extends Dispatcher {
    private Context context;
    private Map<String, String> responseFilesByPath = new HashMap<>();

    public ErrorDispatcher() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        responseFilesByPath.put(ERROR_FEED_LIST_END_POINT, ERROR_FEED_LIST_END_POINT);
    }

    @Override
    public MockResponse dispatch(RecordedRequest request) {
        MockResponse errorResponse = new MockResponse().setResponseCode(404);

        String pathWithoutQueryParams = Uri.parse(request.getPath()).getPath();

        if (pathWithoutQueryParams != null) {
            String responseBody = new AssetReaderUtil().asset("/error_facts.json");
            return errorResponse.setBody(responseBody);
        }
        return errorResponse;
    }
}