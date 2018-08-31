package br.org.cesar.discordtime.stickysessions.domain.model;

public class NoteFilter {

    public String user;
    public String idSession;

    public NoteFilter(String idSession, String user) {
        this.user = user;
        this.idSession = idSession;
    }
}
