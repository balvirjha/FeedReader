package com.android.feedreader.feeds.repo;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.android.feedreader.feeds.modals.FeedsResponse;
import com.android.feedreader.feeds.model.Event;
import com.android.feedreader.feeds.model.Resource;
import com.android.feedreader.feeds.model.Status;
import com.android.feedreader.feeds.modules.feeds.FeedListViewModal;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class FeedsRepoTest {

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
    public void testRepo(){
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