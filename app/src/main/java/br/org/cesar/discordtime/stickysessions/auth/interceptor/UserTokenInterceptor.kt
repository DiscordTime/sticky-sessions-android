package br.org.cesar.discordtime.stickysessions.auth.interceptor

import br.org.cesar.discordtime.stickysessions.auth.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class UserTokenInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .header("Token", tokenProvider.getToken())
                .build()

        return chain.proceed(request)
    }
}