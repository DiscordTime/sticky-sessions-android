package br.org.cesar.discordtime.stickysessions.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.Session;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionHolder> {

    private Context mContext;
    private List<Session> mSessions;
    private PublishSubject<Session> clickSubject;
    public Observable<Session> clickEvent;

    public SessionAdapter(Context context) {
        mContext = context;
        mSessions = new ArrayList<>();
        clickSubject = PublishSubject.create();
        clickEvent = clickSubject;
    }

    public Context getContext() {
        return mContext;
    }

    public void setSessions(List<Session> sessions) {
        mSessions = sessions;
        notifyDataSetChanged();
    }

    public List<Session> getSessions() {
        return mSessions;
    }

    public void refreshSession(Session session) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getSessions().get(i).id.equals(session.id)) {
                mSessions.set(i,session);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_session_element, parent, false);

        return new SessionHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionHolder holder, int position) {
        Session session = mSessions.get(position);

        // TODO: Please, change this horrible algorithm
        String sessionText = (session.topics.size() == 5) ? "Starfish" : "Gain & Pleasure";
        holder.mSessionView.setText(sessionText);

        holder.mCreateDateView.setText(session.createdAt);
    }

    @Override
    public int getItemCount() {
        return mSessions.size();
    }

    class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mSessionView;
        TextView mCreateDateView;
        TextView mAuthorView;

        public SessionHolder(View itemView) {
            super(itemView);
            mSessionView = itemView.findViewById(R.id.session_name);
            mCreateDateView = itemView.findViewById(R.id.session_create_time);
            mAuthorView = itemView.findViewById(R.id.session_author_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int realPosition = getLayoutPosition() % mSessions.size();
            clickSubject.onNext(mSessions.get(realPosition));
        }
    }
}
