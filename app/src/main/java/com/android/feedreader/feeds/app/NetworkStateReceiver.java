package com.android.feedreader.feeds.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class NetworkStateReceiver extends BroadcastReceiver {

    protected Set<NetworkStateReceiverListener> listeners;
    protected Boolean connected;

    public NetworkStateReceiver() {
        listeners = new HashSet<>();
        connected = null;
    }

    @SuppressWarnings("ConstantConditions")
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            connected = false;
        }

        notifyStateToAll();
    }

    private void notifyStateToAll() {
        for (NetworkStateReceiverListener listener : listeners)
            notifyState(listener);
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if (connected == null || listener == null)
            return;

        if (connected)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void addListener(NetworkStateReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

// --Commented out by Inspection START (2019-12-10 12:15):
//    public void removeListener(NetworkStateReceiverListener l) {
//        listeners.remove(l);
//    }
// --Commented out by Inspection STOP (2019-12-10 12:15)

    public interface NetworkStateReceiverListener {
        void networkAvailable();

        void networkUnavailable();
    }
}
