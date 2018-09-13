package br.org.cesar.discordtime.stickysessions.ui.session.custom;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;

public class NoteGridLayoutManager extends androidx.recyclerview.widget.GridLayoutManager {
    public NoteGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }
}
