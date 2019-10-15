package br.org.cesar.discordtime.stickysessions.data.remote.wrapper

class NetworkWrapperTest : INetworkWrapper {

    override fun isConnected(): Boolean {
        return true
    }
}