package com.android.feedreader.feeds.modules.feeds;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.feedreader.feeds.modals.FeedsResponse;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.model.Resource;
import com.android.feedreader.feeds.model.Status;
import com.android.feedreader.feeds.repo.FeedsRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FeedListViewModalTest {

    @Mock
    FeedListViewModal feedListViewModal;

    @Mock
    FeedsRepo feedsRepo;

    @Mock
    Observer<Resource<Event<FeedsResponse>>> observer;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        feedListViewModal = new FeedListViewModal(feedsRepo);
        feedListViewModal.getFeedsResponse().observeForever(observer);
    }

    @Test
    public void testNull() {
        when(feedsRepo.getFeedsList()).thenReturn(null);
        assertNotNull(feedListViewModal.getFeedsResponse());
        assertTrue(feedListViewModal.getFeedsResponse().hasObservers());
    }

    @Test
    public void testApiFetchDataSuccess() {
        MutableLiveData<Resource<Event<FeedsResponse>>> eventMutableLiveData = new MutableLiveData<>();
        eventMutableLiveData.postValue(Resource.success(new Event<>(new FeedsResponse("", ""))));
        when(feedsRepo.getFeedsList()).thenReturn(eventMutableLiveData);
        LiveData<Resource<Event<FeedsResponse>>> eventMutableLiveData1 = feedsRepo.getFeedsList();
        LiveData<Resource<Event<FeedsResponse>>> eventMutableLiveData2 = feedListViewModal.getFeedsResponse();
        assertEquals(Status.SUCCESS, eventMutableLiveData1.getValue().status);
        assertTrue(eventMutableLiveData2.hasObservers());

    }


    @After
    public void tearDown() throws Exception {
        feedListViewModal = null;
        feedsRepo = null;
    }
}