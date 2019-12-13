package com.android.feedreader.feeds.modals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RowTest {
    FeedsResponse feedsResponse;

    Feeds feedsObject;

    @Before
    public void setUp() {
        feedsResponse = new FeedsResponse("Error", "200");
        feedsObject = new Feeds();
    }

    @Test
    public void testFeedsClass() {
        String errorCode = "200";
        String errorMessage = "Error";
        assertNotNull(feedsResponse);
        feedsResponse.setErrorCode("200");
        assertNotNull(feedsResponse.getErrorCode());
        assertEquals(errorCode, feedsResponse.getErrorCode());
        feedsResponse.setErrorMessage("Error");
        assertNotNull(feedsResponse.getErrorMessage());
        assertEquals(errorMessage, feedsResponse.getErrorMessage());
        feedsResponse.setFeeds(feedsObject);
        assertNotNull(feedsResponse.getFeeds());
        assertEquals(feedsObject,
                feedsResponse.getFeeds());

    }

    @After
    public void tearDown() throws Exception {
        feedsResponse = null;
        feedsObject = null;
    }

}