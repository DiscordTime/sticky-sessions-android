package br.org.cesar.discordtime.stickysessions.domain.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Session implements ISessionDetail {
    public String id;
    public List<String> topics = null;
    public String createdAt;

    public void copy(Session session) {
        id = session.id;
        topics = session.topics;
        createdAt = session.createdAt;
    }

    public void setCreatedAt(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        createdAt = new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(c.getTime());
    }

    public int getYear() {
        if (createdAt != null && !createdAt.isEmpty()) {
            return Integer.parseInt(createdAt.substring(6));
        }
        return 0;
    }

    public int getMonth() {
        if (createdAt != null && !createdAt.isEmpty()) {
            return Integer.parseInt(createdAt.substring(3,5))-1;
        }
        return 0;
    }

    public int getDay() {
        if (createdAt != null && !createdAt.isEmpty()) {
            return Integer.parseInt(createdAt.substring(0,2));
        }
        return 1;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        // TODO: Get the session name
        return (topics.size() == 5) ? "Starfish" : "Gain & Pleasure";
    }

    @Override
    public List<String> getTopics() {
        return topics;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }
}
