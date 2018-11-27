package br.org.cesar.discordtime.stickysessions.data.remote.wrapper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import br.org.cesar.discordtime.stickysessions.logger.AppLog;
import br.org.cesar.discordtime.stickysessions.logger.Logger;

public class NetworkWrapper implements INetworkWrapper {

    private Context mContext;

    public NetworkWrapper(Context context) {
        mContext = context;
    }

    @Override
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetwork();
        }
        return activeNetwork != null;
    }
}
