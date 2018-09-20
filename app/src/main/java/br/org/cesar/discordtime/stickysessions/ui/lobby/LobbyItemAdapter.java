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
import br.org.cesar.discordtime.stickysessions.presentation.lobby.LobbyContract;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class LobbyItemAdapter extends RecyclerView.Adapter<LobbyItemAdapter.ViewHolder> {

    private Context mContext;
    private List<LobbyItemViewModel> mLobbyItems;

    private PublishSubject<LobbyContract.ActionType> clickSubject;
    Observable<LobbyContract.ActionType> clickEvent;

    LobbyItemAdapter(Context context, List<LobbyItemViewModel> lobbyItems) {
        mContext = context;
        mLobbyItems = lobbyItems;
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
        int realPosition = position % mLobbyItems.size();

        LobbyItemViewModel lobbyItemViewModel = mLobbyItems.get(realPosition);
        holder.media.setImageResource(lobbyItemViewModel.mediaImageRes);
        holder.title.setText(lobbyItemViewModel.titleRes);
        holder.description.setText(lobbyItemViewModel.descriptionRes);
        holder.button.setText(lobbyItemViewModel.buttonText);

        if (lobbyItemViewModel.type == null
                || lobbyItemViewModel.type == LobbyContract.ActionType.CREATE_CUSTOM_SESSION) {
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
        Button button;

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
            button = itemView.findViewById(R.id.create_button);
            button.setOnClickListener(this);
        }

        void changeToDisabledState() {
            if (!previouslyDisabled) {
                holdCurrentColors();
                setDisabledColors();
                button.setEnabled(false);
                previouslyDisabled = true;
            }
        }

        void changeToEnabledState() {
            if (previouslyDisabled) {
                restorePreviousColors();
                button.setEnabled(true);
                previouslyDisabled = false;
            }
        }

        private void setDisabledColors() {
            media.setColorFilter(Color.BLACK, PorterDuff.Mode.LIGHTEN);
            title.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
            description.setTextColor(mContext.getResources().getColor(android.R.color.darker_gray));
            button.setTextColor(mContext.getResources().getColor(R.color.colorSecondaryDark));
        }

        private void holdCurrentColors() {
            mediaColorFilter = media.getColorFilter();
            titleTextColor = title.getCurrentTextColor();
            descriptionTextColor = description.getCurrentTextColor();
            createButtonTextColor = button.getCurrentTextColor();
        }

        private void restorePreviousColors() {
            media.setColorFilter(mediaColorFilter);
            title.setTextColor(titleTextColor);
            description.setTextColor(descriptionTextColor);
            button.setTextColor(createButtonTextColor);
        }

        @Override
        public void onClick(View view) {
            int realPosition = getLayoutPosition() % mLobbyItems.size();
            clickSubject.onNext(mLobbyItems.get(realPosition).type);
        }
    }
}
