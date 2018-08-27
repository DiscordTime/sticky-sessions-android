package br.org.cesar.discordtime.stickysessions.ui.lobby;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import br.org.cesar.discordtime.stickysessions.R;
import br.org.cesar.discordtime.stickysessions.domain.model.SessionType;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SessionTypeAdapter extends RecyclerView.Adapter<SessionTypeAdapter.ViewHolder> {

    private Context mContext;
    private List<SessionTypeViewModel> mSessionTypes;

    private PublishSubject<SessionType> clickSubject;
    Observable<SessionType> clickEvent;

    SessionTypeAdapter(Context context, List<SessionTypeViewModel> sessionTypes) {
        mContext = context;
        mSessionTypes = sessionTypes;
        clickSubject = PublishSubject.create();
        clickEvent = clickSubject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.lobby_card_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int realPosition = position % mSessionTypes.size();

        SessionTypeViewModel sessionType = mSessionTypes.get(realPosition);
        holder.media.setImageResource(sessionType.mediaImageRes);
        holder.title.setText(sessionType.titleRes);
        holder.description.setText(sessionType.descriptionRes);

        if (sessionType.type == null || sessionType.type == SessionType.CUSTOM) {
            holder.changeToDisabledState();
        } else {
            holder.changeToEnabledState();
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView media;
        TextView title;
        TextView description;
        Button createButton;

        boolean previouslyDisabled = false;
        ColorFilter mediaColorFilter;
        int titleTextColor;
        int descriptionTextColor;
        int createButtonTextColor;

        ViewHolder(View itemView) {
            super(itemView);
            media = itemView.findViewById(R.id.media_image);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            createButton = itemView.findViewById(R.id.create_button);
            createButton.setOnClickListener(this);
        }

        void changeToDisabledState() {
            if (!previouslyDisabled) {
                holdCurrentColors();
                setDisabledColors();
                createButton.setEnabled(false);
                previouslyDisabled = true;
            }
        }

        void changeToEnabledState() {
            if (previouslyDisabled) {
                restorePreviousColors();
                createButton.setEnabled(true);
                previouslyDisabled = false;
            }
        }

        private void setDisabledColors() {
            media.setColorFilter(Color.BLACK, PorterDuff.Mode.LIGHTEN);
            title.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
            description.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
            createButton.setTextColor(mContext.getResources().getColor(R.color.colorSecondaryDark));
        }

        private void holdCurrentColors() {
            mediaColorFilter = media.getColorFilter();
            titleTextColor = title.getCurrentTextColor();
            descriptionTextColor = description.getCurrentTextColor();
            createButtonTextColor = createButton.getCurrentTextColor();
        }

        private void restorePreviousColors() {
            media.setColorFilter(mediaColorFilter);
            title.setTextColor(titleTextColor);
            description.setTextColor(descriptionTextColor);
            createButton.setTextColor(createButtonTextColor);
        }

        @Override
        public void onClick(View view) {
            int realPosition = getLayoutPosition() % mSessionTypes.size();
            clickSubject.onNext(mSessionTypes.get(realPosition).type);
        }
    }
}
