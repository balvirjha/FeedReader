package com.android.feedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.android.feedreader.feeds.app.AppConstants.ERROR_FEED_LIST_END_POINT;
import static com.android.feedreader.feeds.app.AppConstants.FEED_LIST_END_POINT;

public class AssetReaderUtil {

    public String asset(String endPoint) {
        return inputStreamToString(endPoint);
    }

    private String inputStreamToString(String endPoint) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = null;
        try {
            if (endPoint.equalsIgnoreCase("/" + FEED_LIST_END_POINT)) {
                reader = new BufferedReader(
                        new InputStreamReader(FeedReaderTestApplication.getInstance().getAssets().open(FEED_LIST_END_POINT)));

            } else if (endPoint.equalsIgnoreCase("/" + ERROR_FEED_LIST_END_POINT)) {
                reader = new BufferedReader(
                        new InputStreamReader(FeedReaderTestApplication.getInstance().getAssets().open(ERROR_FEED_LIST_END_POINT)));
            }
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                builder.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
}
