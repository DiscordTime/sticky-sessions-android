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
                .inflate(R.layout.card_session_element, parent, false);

        return new SessionHolder(view);
    }

    @Override
    public void onBindViewHolder(SessionHolder holder, int position) {
        Session session = mSessions.get(position);

        // TODO: Please, change this horrible algorithm
        String sessionText;
        String description;
        int leftBarColor;
        if (session.topics.size() == 5) {
            sessionText = "Starfish";
            description = mContext.getResources().getString(R.string.starfish_description);
            leftBarColor = mContext.getResources().getColor(R.color.purple);
        } else {
            sessionText = "Gain & Pleasure";
            description = mContext.getResources().getString(R.string.gain_description);
            leftBarColor = mContext.getResources().getColor(R.color.yellow);
        }

        holder.mSessionText.setText(sessionText);
        holder.mDescriptionText.setText(
                String.format("%s (%s)", description, session.createdAt));
        holder.mLeftBar.setBackgroundColor(leftBarColor);
    }

    @Override
    public int getItemCount() {
        return mSessions.size();
    }

    class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mSessionText;
        TextView mDescriptionText;
        TextView mResponsesText;
        View mLeftBar;

        public SessionHolder(View itemView) {
            super(itemView);
            mSessionText = itemView.findViewById(R.id.text_session);
            mDescriptionText = itemView.findViewById(R.id.text_description);
            mResponsesText = itemView.findViewById(R.id.text_responses);
            mLeftBar = itemView.findViewById(R.id.left_bar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int realPosition = getLayoutPosition() % mSessions.size();
            clickSubject.onNext(mSessions.get(realPosition));
        }
    }
}
