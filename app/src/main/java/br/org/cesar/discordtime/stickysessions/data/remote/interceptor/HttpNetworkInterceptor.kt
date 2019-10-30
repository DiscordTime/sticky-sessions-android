package br.org.cesar.discordtime.stickysessions.data.remote.interceptor

import java.io.IOException

import br.org.cesar.discordtime.stickysessions.data.remote.wrapper.INetworkWrapper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HttpNetworkInterceptor(private val mNetworkWrapper: INetworkWrapper) : Interceptor {

    private var local: Boolean = false

    init {
        local = false
    }

    fun setLocal(local: Boolean) {
        this.local = local
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!local && !mNetworkWrapper.isConnected) {
            NoNetworkException()
        }
        return chain.proceed(request)
    }

    private inner class NoNetworkException {
        init {
            throw RuntimeException("Please check Network Connection")
        }
    }
}
