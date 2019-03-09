package br.org.cesar.discordtime.stickysessions.domain.model;

import java.util.List;

public class Session {
    public String id;
    public List<String> topics = null;
    public String createdAt;

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
}
