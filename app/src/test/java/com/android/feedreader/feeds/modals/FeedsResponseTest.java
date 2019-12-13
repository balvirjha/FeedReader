package com.android.feedreader.feeds.modals;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeedsResponseTest {

    @Test
    public void testFeedsClass() {
        String tite = "title";
        String description = "Error";
        String imageHref = "www.google.com";
        Row row = new Row();
        assertNotNull(row);
        row.setTitle("title");
        assertNotNull(row.getTitle());
        assertEquals(tite, row.getTitle());
        row.setDescription("Error");
        assertNotNull(row.getDescription());
        assertEquals(description, row.getDescription());
        row.setImageHref("www.google.com");
        assertNotNull(row.getImageHref());
        assertEquals(imageHref,
                row.getImageHref());

    }

}