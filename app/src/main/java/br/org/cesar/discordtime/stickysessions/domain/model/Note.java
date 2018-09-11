package br.org.cesar.discordtime.stickysessions.domain.model;

import java.util.Objects;

public class Note {
    public String id;
    public String description;
    public String user;
    public String topic;
    public String sessionId;

    public Note(String description, String user, String topic, String sessionId) {
        this.id = null;
        this.description = description;
        this.user = user;
        this.topic = topic;
        this.sessionId = sessionId;
    }

    public Note(String id, String description, String user, String topic, String sessionId) {
        this(description, user, topic, sessionId);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
