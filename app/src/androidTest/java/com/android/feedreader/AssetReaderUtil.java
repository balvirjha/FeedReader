package com.android.feedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetReaderUtil {

    public String asset(String endPoint) {
        return inputStreamToString(endPoint);
    }

    private String inputStreamToString(String endPoint) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = null;
        try {
            if (endPoint.equalsIgnoreCase("/facts.json")) {
                reader = new BufferedReader(
                        new InputStreamReader(FeedReaderTestApplication.getInstance().getAssets().open("facts.json")));

            } else if (endPoint.equalsIgnoreCase("/error_facts.json")) {
                reader = new BufferedReader(
                        new InputStreamReader(FeedReaderTestApplication.getInstance().getAssets().open("error_facts.json")));
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
