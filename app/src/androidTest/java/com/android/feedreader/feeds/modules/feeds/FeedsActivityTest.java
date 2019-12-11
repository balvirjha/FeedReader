package com.android.feedreader.feeds.modules.feeds;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.View;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.android.feedreader.ErrorDispatcher;
import com.android.feedreader.R;
import com.android.feedreader.SuccessDispatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FeedsActivityTest {
    @Rule
    public ActivityTestRule<FeedsActivity> mActivityTestRule = new ActivityTestRule<>(FeedsActivity.class, true, false);

    private MockWebServer mockWebServer = new MockWebServer();

    @Before
    public void setUp() throws Exception {
        mockWebServer.start(8080);
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void feedsActivityTest1() {
        mockWebServer.setDispatcher(new SuccessDispatcher());
        mActivityTestRule.launchActivity(null);
        sleep(1000);
        onView(withIndex(withId(R.id.headingTextView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.descriptionTextView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.feedImageView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.imageViewNext), 1)).check(matches(isDisplayed()));
    }

    @Test
    public void feedsActivityTestCheckToolbar() {
        mockWebServer.setDispatcher(new SuccessDispatcher());
        mActivityTestRule.launchActivity(null);
        sleep(1000);
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void feedsActivityTestNoNetworkBanner() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        mockWebServer.setDispatcher(new SuccessDispatcher());
        mActivityTestRule.launchActivity(null);
        disableWIFI();
        sleep(5000);
        if (getMobileDataState() == false)
            onView(withId(R.id.include)).check(matches(isDisplayed()));
    }

    @Test
    public void feedsActivityTestPullTopRefresh(){
        mockWebServer.setDispatcher(new SuccessDispatcher());
        mActivityTestRule.launchActivity(null);
        sleep(1000);
        onView(withId(R.id.swipeToRefreshLayout)).perform(swipeDown());
        sleep(2000);
        onView(withIndex(withId(R.id.headingTextView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.descriptionTextView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.feedImageView), 1)).check(matches(isDisplayed()));
        onView(withIndex(withId(R.id.imageViewNext), 1)).check(matches(isDisplayed()));
    }

    @Test
    public void feedsActivityTestAPIErrorCase() {
        sleep(1000);
        mockWebServer.setDispatcher(new ErrorDispatcher());
        mActivityTestRule.launchActivity(null);
        sleep(1000);
        onView(withId(R.id.headingTextView)).check(doesNotExist());
        onView(withId(R.id.descriptionTextView)).check(doesNotExist());
        onView(withId(R.id.feedImageView)).check(doesNotExist());
        onView(withId(R.id.imageViewNext)).check(doesNotExist());
        onView(withId(R.id.errorView)).check(matches(isDisplayed()));
    }

    private void disableWIFI() {
        WifiManager wifi = (WifiManager) mActivityTestRule.getActivity().getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(false);
    }

    public boolean getMobileDataState() {
        try {
            TelephonyManager telephonyService = (TelephonyManager) mActivityTestRule.getActivity().getSystemService(Context.TELEPHONY_SERVICE);

            Method getMobileDataEnabledMethod = telephonyService.getClass().getDeclaredMethod("getDataEnabled");

            if (null != getMobileDataEnabledMethod) {
                boolean mobileDataEnabled = (Boolean) getMobileDataEnabledMethod.invoke(telephonyService);

                return mobileDataEnabled;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}
