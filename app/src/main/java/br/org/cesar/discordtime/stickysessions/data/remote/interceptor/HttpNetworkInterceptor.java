package br.org.cesar.discordtime.stickysessions.data.remote.interceptor;

import java.io.IOException;

import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpNetworkInterceptor implements Interceptor {

    private boolean local;
    private INetworkWrapper mNetworkWrapper;

    public HttpNetworkInterceptor(INetworkWrapper NetworkWrapper) {
        mNetworkWrapper = NetworkWrapper;
        local = false;
    }

    public void setLocal(boolean local){
        this.local = local;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if(!local && !mNetworkWrapper.isConnected()){
            new NoNetworkException();
        }
        return chain.proceed(request);
    }

    private class NoNetworkException {
        public NoNetworkException(){
            throw new RuntimeException("Please check Network Connection");
        }
    }
}
