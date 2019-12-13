package com.android.feedreader.feeds.app;

import android.content.Intent;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.android.feedreader.feeds.api.ApiService;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.repo.FeedsRepo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class FeedApplicationTest {

    @Mock
    FeedsRepo feedsRepo;

    @Mock
    FeedApplication feedApplication;

    @Mock
    ApiService apiService;

    @Spy
    NetworkStateReceiver networkStateReceiver;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        feedApplication = Mockito.spy(FeedApplication.class);
    }

    @Test
    public void testApplicationClass() {
        when(feedApplication.getFeedsRepo()).thenReturn(feedsRepo);
        when(feedApplication.getApiService()).thenReturn(apiService);
        FeedsRepo repo = feedApplication.getFeedsRepo();
        ApiService service = feedApplication.getApiService();
        assertEquals(repo, feedsRepo);
        assertEquals(service, apiService);
    }

    @Test
    public void testNetworkState() {
        networkStateReceiver = Mockito.spy(NetworkStateReceiver.class);
        networkStateReceiver.addListener(feedApplication);
        Intent intent = Mockito.mock(Intent.class);
        networkStateReceiver.onReceive(feedApplication, intent);
        MutableLiveData<Event<Boolean>> isConnected = new MutableLiveData<>();
        isConnected.postValue(new Event<>(true));
        when(feedApplication.getIsConnected()).thenReturn(isConnected);
        assertTrue(feedApplication.getIsConnected().getValue().getContentIfNotHandled());
    }
}