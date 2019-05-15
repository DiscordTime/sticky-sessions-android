package br.org.cesar.discordtime.stickysessions.auth

interface TokenProvider {
    fun getToken(): String
}
