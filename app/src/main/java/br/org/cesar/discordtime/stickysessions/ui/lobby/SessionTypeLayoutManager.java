package br.org.cesar.discordtime.stickysessions.ui.lobby;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SessionTypeLayoutManager extends LinearLayoutManager {

    private final float mShrinkAmount = 0.15f;
    private final float mShrinkDistance = 0.9f;
    private RecyclerView.Recycler mRecycler;

    // Orientation helpers are lazily created per LayoutManager.
    @Nullable
    private OrientationHelper mVerticalHelper;
    @Nullable
    private OrientationHelper mHorizontalHelper;

    public SessionTypeLayoutManager(Context context) {
        super(context);
    }

    SessionTypeLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        setItemPrefetchEnabled(true);
        setInitialPrefetchItemCount(1);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                          int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        mRecycler = recycler;
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {
            shrinkHorizontally();
        } else {
            shrinkVertically();
        }
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        int orientation = getOrientation();
        OrientationHelper helper;
        if (orientation == HORIZONTAL) {
            helper = getHorizontalHelper();
        } else {
            helper = getVerticalHelper();
        }
        snapToCenter(state, helper, orientation);
    }

    @Override
    public int scrollVerticallyBy(
            int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == VERTICAL) {
            int scrolled = super.scrollVerticallyBy(dy, recycler, state);
            shrinkVertically();
            return scrolled;
        } else {
            return 0;
        }
    }

    @Override
    public int scrollHorizontallyBy(
            int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int orientation = getOrientation();
        if (orientation == HORIZONTAL) {
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            shrinkHorizontally();
            return scrolled;
        } else {
            return 0;
        }
    }

    private void snapToCenter(RecyclerView.State state, OrientationHelper helper, int orientation) {
        View child = getChildAt(0);
        if (child != null) {
            final int childCenter = helper.getDecoratedStart(child)
                    + (helper.getDecoratedMeasurement(child) / 2);
            final int containerCenter;
            if (getClipToPadding()) {
                containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
            } else {
                containerCenter = helper.getEnd() / 2;
            }

            if (orientation == HORIZONTAL) {
                scrollHorizontallyBy(childCenter - containerCenter, mRecycler, state);
            } else {
                scrollVerticallyBy(childCenter - containerCenter, mRecycler, state);
            }
        }
    }

    @NonNull
    private OrientationHelper getVerticalHelper() {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(this);
        }
        return mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper() {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(this);
        }
        return mHorizontalHelper;
    }

    private void shrinkVertically() {
        float midpoint = getHeight() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1 = 1.f - mShrinkAmount;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint =
                    (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }

    private void shrinkHorizontally() {
        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1 = 1.f - mShrinkAmount;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            float childMidpoint =
                    (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
            float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
            float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }
}