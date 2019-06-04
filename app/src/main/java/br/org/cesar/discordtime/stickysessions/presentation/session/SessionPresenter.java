package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.IObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import io.reactivex.observers.DisposableSingleObserver;

public class SessionPresenter implements SessionContract.Presenter {

    private static final String TAG = "SessionPresenter";
    private IObservableUseCase<String, Session> mEnterSession;
    private IObservableUseCase<Note, Note> mAddNote;
    private IObservableUseCase<Note, Boolean> mRemoveNote;
    private IObservableUseCase<NoteFilter, List<Note>> mListNotes;
    private IObservableUseCase<Void, String> mGetSavedUser;
    private Logger mLog;
    private SessionContract.View mView;
    private Session mActiveSession;
    private String mSessionId;
    private String mCurrentUser;

    public SessionPresenter(IObservableUseCase<String, Session> enterSession,
                            IObservableUseCase<Note, Note> addNote,
                            IObservableUseCase<Note, Boolean> removeNote,
                            IObservableUseCase<NoteFilter, List<Note>> listNotes,
                            IObservableUseCase<Void, String> getSavedUser,
                            Logger logger) {
        mEnterSession = enterSession;
        mAddNote = addNote;
        mRemoveNote = removeNote;
        mListNotes = listNotes;
        mGetSavedUser = getSavedUser;
        mLog = logger;
    }

    @Override
    public void attachView(SessionContract.View view) {
        mView = view;
    }

    @Override
    public void onResume() {
        mView.startLoadingAllNotes();
        mGetSavedUser.execute(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String userName) {
                mCurrentUser = userName;
                onEnterSession();
            }
    
            @Override
            public void onError(Throwable e) {
                mView.stopLoadingAllNotes();
                // TODO: Go back to login?
            }
        }, null);
    }

    @Override
    public void currentSession(String sessionId) {
        mSessionId = sessionId;
    }

    @Override
    public void removeNote(Note note) {
        mRemoveNote.execute(new DisposableSingleObserver() {
            @Override
            public void onSuccess(Object o) {
                mView.removeNote(note);
            }

            @Override
            public void onError(Throwable e) {
                mView.displayError(e.getMessage());
            }
        }, note);
    }

    @Override
    public void detachView() {
        mView = null;
        disposeObservers();
    }

    @Override
    public void onShareSession() {
        if(mActiveSession != null) {
            mView.shareSession(mActiveSession.id);
        }
    }

    @Override
    public void onAddNoteClicked() {

        if (mActiveSession != null && mActiveSession.topics != null) {
            List<String> topics = mActiveSession.topics;
            mView.displayAddNoteDialog(topics);
        }
    }

    @Override
    public void addNewNote(String topic, String description) {
        if (mActiveSession != null && mActiveSession.id != null && mCurrentUser != null) {
            mView.startLoadingNote();
            Note note = new Note(description, mCurrentUser, topic, mActiveSession.id);
            mAddNote.execute(new DisposableSingleObserver<Note>() {

                @Override
                public void onSuccess(Note note) {
                    mView.stopLoadingNote();
                    mView.addNoteToNoteList(note);
                }

                @Override
                public void onError(Throwable e) {
                    mView.stopLoadingNote();
                    mView.displayError(e.getMessage());
                }
            }, note);
        }
    }

    @Override
    public void onNoteWidgetClicked(Note note) {
        if (note != null) {
            mView.displayNoteContent(note);
        }
    }

    private void disposeObservers() {
        mEnterSession.dispose();
        mAddNote.dispose();
        mRemoveNote.dispose();
        mListNotes.dispose();
        mGetSavedUser.dispose();
    }

    private void onEnterSession() {
        mLog.d(TAG, "onEnterSession : "+mSessionId);
        mEnterSession.execute(new DisposableSingleObserver<Session>() {
            @Override
            public void onSuccess(Session session) {
                mActiveSession = session;
                listNotesForCurrentSession();
                mView.displaySession();
            }

            @Override
            public void onError(Throwable e) {
                // todo : handle errors gracefully
                mView.stopLoadingAllNotes();
                mView.displayError(e.getMessage());
            }
        }, mSessionId);
    }

    private void listNotesForCurrentSession() {
        if (mActiveSession != null && mActiveSession.id != null) {
            mListNotes.execute(new DisposableSingleObserver<List<Note>>() {
                @Override
                public void onSuccess(List<Note> notes) {
                    onSessionLoaded(notes);
//                    mView.stopLoadingAllNotes();
//                    mView.displayNotes(notes);
                }

                @Override
                public void onError(Throwable e) {
                    mView.stopLoadingAllNotes();
                    mView.displayErrorInvalidNotes();
                }
            }, new NoteFilter(mActiveSession.id, mCurrentUser));
        } else {
            mView.stopLoadingAllNotes();
            mView.displayErrorInvalidNotes();
        }
    }

    private void onSessionLoaded(List<Note> notes) {
        // TODO: run this task in background and so post it into ui thread.
        displayTopicsOnView(convertTopicList(
                mActiveSession.topics, createHashMapOfTopics(mActiveSession.topics, notes)));
    }

    private HashMap<String, List<Note>> createHashMapOfTopics(
            List<String> topics, List<Note> notes) {
        HashMap<String, List<Note>> topicMap = new HashMap<>();

        for (String topic : topics) {
            topicMap.put(topic, createNewEmptyNoteList());
        }

        for (Note note : notes) {
            try {
                topicMap.get(note.topic).add(note);
            } catch (NullPointerException ignored) { }
        }

        return topicMap;
    }

    private List<TopicDetail> convertTopicList(
            List<String> topics, HashMap<String, List<Note>> topicMap) {
        List<TopicDetail> topicDetailList = new ArrayList<>();
        for (String topic : topics) {
            topicDetailList.add(new TopicDetail(topic, topicMap.get(topic)));
        }
        return topicDetailList;
    }

    private void displayTopicsOnView(List<TopicDetail> topicDetailList) {
        mView.stopLoadingAllNotes();
        mView.displayTopics(topicDetailList);
    }

    private List<Note> createNewEmptyNoteList() {
        return new ArrayList<>();
    }
}
