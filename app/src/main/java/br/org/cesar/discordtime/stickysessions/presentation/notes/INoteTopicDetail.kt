package br.org.cesar.discordtime.stickysessions.presentation.notes

interface INoteTopicDetail {
    fun getTopicName(): String?
    fun getUserId(): String?
    fun getSessionId(): String?
}