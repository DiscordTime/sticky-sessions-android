package br.org.cesar.discordtime.stickysessions.data.remote.repository;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.cesar.discordtime.stickysessions.data.remote.model.SessionRemote;
import br.org.cesar.discordtime.stickysessions.data.repository.mapper.SessionMapper;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import br.org.cesar.discordtime.stickysessions.domain.repository.SessionRepository;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class SessionRemoteRepository implements SessionRepository {

    private DatabaseReference mDatabase;

    @Override
    public Single<Session> create(List<String> topics) {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        String key = mDatabase.child("sessions").push().getKey();
        SessionRemote session = new SessionRemote(key, topics);
        Map<String, Object> sessionValues = session.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/sessions/" + key, sessionValues);
        mDatabase.updateChildren(childUpdates);

        Query query = mDatabase.child("sessions").orderByKey().equalTo(key);
        return createSessionSingle(query, key);

    }

    private Single<Session> createSessionSingle(final Query queryDatabase, final String key) {

        return Single.create(new SingleOnSubscribe<Session>() {
            @Override
            public void subscribe(final SingleEmitter<Session> emitter) throws Exception {
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        SessionRemote sessionRemote = dataSnapshot.getValue(SessionRemote.class);
                        sessionRemote.id = key;
                        SessionMapper mapper = new SessionMapper();
                        Session sessionDomain = mapper.mapToDomain(sessionRemote);
                        emitter.onSuccess(sessionDomain);
                    }

                    @Override
                    public void onCancelled(@NotNull DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                };
                queryDatabase.addValueEventListener(valueEventListener);
            }
        });
    }
}
