package com.android.feedreader.feeds.model;

import androidx.annotation.Nullable;

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
@SuppressWarnings("ALL")
public class Event<T> {

    private T mContent;

    private boolean hasBeenHandled = false;


    public Event(T content) {
        if (content == null) {
            throw new IllegalArgumentException();
        }
        mContent = content;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return mContent;
        }
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }

    public void setHasBeenHandled(boolean hasBeenHandled) {
        this.hasBeenHandled = hasBeenHandled;
    }
}