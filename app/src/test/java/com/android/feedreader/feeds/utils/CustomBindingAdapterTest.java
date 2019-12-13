package com.android.feedreader.feeds.utils;

import android.view.View;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomBindingAdapterTest {

    @Test
    public void testViewVisibility() {
        View view = mock(View.class);
        CustomBindingAdapter.showHide(view, false);
        when(view.getVisibility()).thenReturn(View.GONE);
        assertTrue(view.getVisibility() == View.GONE);
    }

}