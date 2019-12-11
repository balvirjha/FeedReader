package com.android.feedreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetReaderUtil {

    public String asset() {
        return inputStreamToString();
    }

    private String inputStreamToString() {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(FeedReaderTestApplication.getInstance().getAssets().open("facts.json")));
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
