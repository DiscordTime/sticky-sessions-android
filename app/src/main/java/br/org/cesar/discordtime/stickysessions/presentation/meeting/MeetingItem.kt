package br.org.cesar.discordtime.stickysessions.presentation.meeting

data class MeetingItem (
        val id: String,
        val title: String,
        val description: String,
        val location: String,
        val date: String,
        val time: String,
        val numOfSessions: Int,
        val numOfParticipants: Int,
        var recent: Boolean) {
    constructor(id: String, title: String, description: String, location: String,
                date: String, time: String, numOfSessions: Int, numOfParticipants: Int) :
            this(id,title,description,location,date,time,numOfSessions,numOfParticipants,false)
}
