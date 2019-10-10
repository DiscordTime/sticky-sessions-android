package br.org.cesar.discordtime.stickysessions.presentation.notes

class NoteTopicDetail(
        private val topicName: String?,
        private val sessionId: String?,
        private val userID: String?) : INoteTopicDetail {
    override fun getTopicName() = topicName
    override fun getSessionId() = sessionId
    override fun getUserId() = userID
}