package br.org.cesar.discordtime.stickysessions.domain.model;

public class Note {

    public String description;
    public String user;
    public String topic;
    public String sessionId;

    public Note(String description, String user, String topic, String sessionId) {
        this.description = description;
        this.user = user;
        this.topic = topic;
        this.sessionId = sessionId;
    }
}
