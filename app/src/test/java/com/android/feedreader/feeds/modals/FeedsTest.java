package com.android.feedreader.feeds.modals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;

public class FeedsTest {

    @Mock
    Feeds feeds;

    @Before
    public void setUp() throws Exception {
        feeds = spy(Feeds.class);
    }

    @Test
    public void testFeedsClass() {
        String title = "my Name";
        List<Row> rows = null;
        feeds.setRows(null);
        assertNull(feeds.getRows());
        assertNull(feeds.getTitle());
        feeds.setTitle("my Name");
        assertEquals(title, feeds.getTitle());
        assertNotNull(feeds.getTitle());
    }

    @After
    public void tearDown() throws Exception {
        feeds = null;
    }
}