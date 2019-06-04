package br.org.cesar.discordtime.stickysessions.ui.session;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.org.cesar.discordtime.stickysessions.presentation.session.TopicDetail;

public class TopicsAdapter extends RecyclerView.Adapter<TopicItemView> {
    private List<TopicDetail> mTopics;

    @NonNull
    @Override
    public TopicItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TopicItemView.createView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicItemView holder, int position) {
        holder.onBindView(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mTopics != null ? mTopics.size() : 0;
    }

    public void replaceAllData(List<TopicDetail> topics) {
        mTopics = topics;
        notifyDataSetChanged();
    }

    private TopicDetail getItem(int position) {
        return mTopics != null ? mTopics.get(position) : null;
    }
}
