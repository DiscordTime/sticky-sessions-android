package br.org.cesar.discordtime.stickysessions.presentation.session;

import android.util.Log;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.domain.model.Note;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.executor.ObservableUseCase;
import io.reactivex.observers.DisposableSingleObserver;

public class SessionPresenter implements SessionContract.Presenter {

    private static final String TAG = "SessionPresenter";
    private ObservableUseCase<String, Session> mEnterSession;
    private ObservableUseCase<Note, Note> mAddNote;
    private ObservableUseCase<String, List<Note>> mListNotes;
    private SessionContract.View mView;
    private Session mActiveSession;
    private DisposableSingleObserver<Session> mSessionObserver;
    private DisposableSingleObserver<Note> mNoteObserver;
    private DisposableSingleObserver<List<Note>> mListNotesObserver;

    public SessionPresenter(ObservableUseCase<String, Session> enterSession,
                            ObservableUseCase<Note, Note> addNote,
                            ObservableUseCase<String, List<Note>> listNotes) {
        mEnterSession = enterSession;
        mAddNote = addNote;
        mListNotes = listNotes;
    }

    @Override
    public void attachView(SessionContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mSessionObserver != null && !mSessionObserver.isDisposed()) {
            mSessionObserver.dispose();
        }

        if (mNoteObserver != null && !mNoteObserver.isDisposed()) {
            mNoteObserver.dispose();
        }

        if (mListNotesObserver != null && !mListNotesObserver.isDisposed()) {
            mListNotesObserver.dispose();
        }
    }

    private void onLoadSession() {
        Log.d(TAG, "onLoadSession");
        mView.startLoadingSession();
    }

    private void onStopLoadSession() {
        Log.d(TAG, "onStopLoadSession");
        mView.stopLoadingSession();
    }

    @Override
    public void onEnterSession(String sessionId) {
        Log.d(TAG, "onEnterSession : "+sessionId);
        onLoadSession();
        mSessionObserver = new DisposableSingleObserver<Session>() {
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
        };
        mEnterSession.execute(mSessionObserver, sessionId);
    }

    private void listNotesForCurrentSession() {
        if (mActiveSession != null && mActiveSession.id != null) {
            mListNotesObserver = new DisposableSingleObserver<List<Note>>() {
                @Override
                public void onSuccess(List<Note> notes) {
                    mView.displayNotes(notes);
                }

                @Override
                public void onError(Throwable e) {
                    mView.displayErrorInvalidNotes();
                }
            };
            mListNotes.execute(mListNotesObserver, mActiveSession.id);
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
        Note note = new Note(description, "Author", topic, mActiveSession.id);
        mNoteObserver = new DisposableSingleObserver<Note>() {
            @Override
            public void onSuccess(Note note) {
                mView.showAddNoteSuccessfullyMessage();
                mView.addNoteToNoteList(note);
            }

            @Override
            public void onError(Throwable e) {
                mView.displayError(e.getMessage());
            }
        };
        mAddNote.execute(mNoteObserver, note);
    }

    @Override
    public void onNoteWidgetClicked(Note note) {
        mView.displayNoteContent(note);
    }
}
