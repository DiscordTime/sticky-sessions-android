package br.org.cesar.discordtime.stickysessions.domain.model;

import java.util.List;

public interface ISessionDetail {
    String getId();
    String getName();
    List<String> getTopics();
    String getCreatedAt();
}
