package br.org.cesar.discordtime.stickysessions.presentation.session;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.NoteFilter;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import br.org.cesar.discordtime.stickysessions.logger.Logger;
import io.reactivex.observers.DisposableSingleObserver;

public class SessionPresenter implements SessionContract.Presenter {

    private static final String TAG = "SessionPresenter";
    private ObservableUseCase<String, Session> mEnterSession;
    private ObservableUseCase<Note, Note> mAddNote;
    private ObservableUseCase<NoteFilter, List<Note>> mListNotes;
    private ObservableUseCase<String, Boolean> mSaveCurrentUser;
    private ObservableUseCase<Void, String> mGetSavedUser;
    private Logger mLog;
    private SessionContract.View mView;
    private Session mActiveSession;
    private String mSessionId;
    private String mCurrentUser;

    public SessionPresenter(ObservableUseCase<String, Session> enterSession,
                            ObservableUseCase<Note, Note> addNote,
                            ObservableUseCase<NoteFilter, List<Note>> listNotes,
                            ObservableUseCase<String, Boolean> saveCurrentUser,
                            ObservableUseCase<Void, String> getSavedUser,
                            Logger logger) {
        mEnterSession = enterSession;
        mAddNote = addNote;
        mListNotes = listNotes;
        mSaveCurrentUser = saveCurrentUser;
        mGetSavedUser = getSavedUser;
        mLog = logger;
    }

    @Override
    public void attachView(SessionContract.View view) {
        mView = view;
    }

    @Override
    public void onResume() {
        mGetSavedUser.execute(new DisposableSingleObserver<String>() {
            @Override
            public void onSuccess(String userName) {
                mCurrentUser = userName;
                onEnterSession();
            }
    
            @Override
            public void onError(Throwable e) {
                mView.showWidgetAddName();
            }
        }, null);
    }

    @Override
    public void currentUser(String userName) {
        mSaveCurrentUser.execute(new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if (success) {
                    mCurrentUser = userName;
                    onEnterSession();
                }
            }
    
            @Override
            public void onError(Throwable e) {
                mView.showWidgetAddName();
                mView.displayError(e.getMessage());
            }
        }, userName);
    }

    @Override
    public void currentSession(String sessionId) {
        mSessionId = sessionId;
    }

    @Override
    public void detachView() {
        mView = null;
        disposeObservers();
    }

    private void disposeObservers() {
        mEnterSession.dispose();
        mAddNote.dispose();
        mListNotes.dispose();
        mSaveCurrentUser.dispose();
        mGetSavedUser.dispose();
    }

    private void onLoadSession() {
        mLog.d(TAG, "onLoadSession");
        mView.startLoadingSession();
    }

    private void onStopLoadSession() {
        mLog.d(TAG, "onStopLoadSession");
        mView.stopLoadingSession();
    }

    private void onEnterSession() {
        mLog.d(TAG, "onEnterSession : "+mSessionId);
        onLoadSession();
        mEnterSession.execute(new DisposableSingleObserver<Session>() {
            @Override
            public void onSuccess(Session session) {
                mActiveSession = session;
                listNotesForCurrentSession();
        
                mView.displaySession();
                onStopLoadSession();
            }
    
            @Override
            public void onError(Throwable e) {
                onStopLoadSession();
                mView.displayError(e.getMessage());
            }
        }, mSessionId);
    }

    private void listNotesForCurrentSession() {
        if (mActiveSession != null && mActiveSession.id != null) {
            mListNotes.execute(new DisposableSingleObserver<List<Note>>() {
                @Override
                public void onSuccess(List<Note> notes) {
                    mView.displayNotes(notes);
                }
    
                @Override
                public void onError(Throwable e) {
                    mView.displayErrorInvalidNotes();
                }
            }, new NoteFilter(mActiveSession.id, mCurrentUser));
        }
    }

    @Override
    public void onShareSession() {
        if(mActiveSession != null) {
            mView.shareSession(mActiveSession.id);
        }
    }

    public void onAddNoteClicked() {
        if (mActiveSession != null && mActiveSession.topics != null) {
            List<String> topics = mActiveSession.topics;
            mView.displayAddNoteDialog(topics);
        }

    }

    @Override
    public void addNewNote(String topic, String description) {
        Note note = new Note(description, mCurrentUser, topic, mActiveSession.id);
        mAddNote.execute(new DisposableSingleObserver<Note>() {
    
            @Override
            public void onSuccess(Note note) {
                mView.showAddNoteSuccessfullyMessage();
                mView.addNoteToNoteList(note);
            }
    
            @Override
            public void onError(Throwable e) {
                mView.displayError(e.getMessage());
            }
        }, note);
    }

    @Override
    public void onNoteWidgetClicked(Note note) {
        mView.displayNoteContent(note);
    }
}
