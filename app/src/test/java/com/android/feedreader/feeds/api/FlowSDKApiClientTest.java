package com.android.feedreader.feeds.api;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.android.feedreader.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertNotNull;

public class FlowSDKApiClientTest {

    @Mock
    ApiService apiService;

    @Mock
    FlowSDKApiClient flowSDKApiClient;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    @Before
    public void setUp() throws Exception {
        flowSDKApiClient = new FlowSDKApiClient.Builder()
                .setBaseUrl(BuildConfig.BASE_URL)
                .create();

        apiService = flowSDKApiClient.getApiService();
    }

    @Test
    public void testApiCliet(){
        FlowSDKApiClient apiClient = new FlowSDKApiClient.Builder()
                .setBaseUrl(BuildConfig.BASE_URL)
                .create();

        ApiService apiClientApiService = apiClient.getApiService();

        assertNotNull(apiClientApiService.getFeedsList());
        assertNotNull(apiClient.getApiService().getFeedsList());
    }

    @After
    public void tearDown() throws Exception {
        apiService = null;
        flowSDKApiClient = null;
    }
}