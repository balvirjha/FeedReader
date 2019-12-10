package com.android.feedreader.feeds.modals;

@SuppressWarnings("unused")
public class FeedsResponse {

    private Feeds feeds;
    private String errorMessage;
    private String errorCode;

    public FeedsResponse(Feeds feeds) {
        this.feeds = feeds;
    }

    public FeedsResponse(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public Feeds getFeeds() {
        return feeds;
    }

    public void setFeeds(Feeds feeds) {
        this.feeds = feeds;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @SuppressWarnings("unused")
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
