package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;

public class TopicDetail {
    private String mTopic;
    private List<Note> mNotes;

    TopicDetail(String topic, List<Note> notes) {
        mTopic = topic;
        mNotes = notes;
    }

    public String getTopic() {
        return mTopic;
    }

    public List<Note> getNotes() {
        return mNotes;
    }
}
