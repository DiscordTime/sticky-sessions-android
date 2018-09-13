package br.org.cesar.discordtime.stickysessions.ui.session.custom;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import br.org.cesar.discordtime.stickysessions.R;

public class ItemAnimator extends SimpleItemAnimator {

    private final Context mContext;
    private List<RecyclerView.ViewHolder> mPendingAdd;
    private List<RecyclerView.ViewHolder> mPendingRemove;
    private List<MoveInfo> mPendingMove;

    public ItemAnimator(Context context) {
        mContext = context;

        mPendingAdd = new ArrayList<>();
        mPendingRemove = new ArrayList<>();
        mPendingMove = new ArrayList<>();
    }

    private class MoveInfo {
        RecyclerView.ViewHolder mHolder;
        int mFromX;
        int mFromY;
        int mToX;
        int mToY;

        MoveInfo(RecyclerView.ViewHolder holder, int fromX,
                 int fromY, int toX, int toY) {

            mHolder = holder;
            mFromX = fromX;
            mFromY = fromY;
            mToX = toX;
            mToY = toY;
        }
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        mPendingAdd.add(holder);
        return true;
    }

    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        mPendingRemove.add(holder);
        return true;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX,
                               int fromY, int toX, int toY) {
        mPendingMove.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder,
                                 RecyclerView.ViewHolder newHolder,
                                 int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }

    @Override
    public void runPendingAnimations() {
        runAddAnimations();
        runRemoveAnimations();
        runMoveAnimations();
    }

    private void runAddAnimations() {
        for (RecyclerView.ViewHolder holder : mPendingAdd) {
            AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(
                mContext, R.animator.add_note_animator);

            animator.setTarget(holder.itemView);
            animator.setInterpolator(new BounceInterpolator());
            animator.setDuration(500);

            animator.start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    dispatchAddStarting(holder);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animator.removeAllListeners();
                    dispatchAddFinished(holder);
                }

                @Override
                public void onAnimationCancel(Animator animation) { }

                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
        }

        mPendingAdd.clear();
    }

    private void runRemoveAnimations() {
        for (RecyclerView.ViewHolder holder : mPendingRemove) {
            AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(
                mContext, R.animator.remove_note_animator);

            animator.setTarget(holder.itemView);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(500);

            animator.start();
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    dispatchRemoveStarting(holder);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animator.removeAllListeners();
                    dispatchRemoveFinished(holder);

                    holder.itemView.setScaleX(1.0f);
                    holder.itemView.setScaleY(1.0f);
                    holder.itemView.setAlpha(1.0f);

                    holder.itemView.invalidate();
                }

                @Override
                public void onAnimationCancel(Animator animation) { }

                @Override
                public void onAnimationRepeat(Animator animation) { }
            });
        }

        mPendingRemove.clear();
    }

    private void runMoveAnimations() {

        for (MoveInfo moveInfo : mPendingMove) {
            AnimatorSet animator = new AnimatorSet();

            animator.playTogether(
                ObjectAnimator.ofFloat(moveInfo.mHolder.itemView, "x", moveInfo.mFromX,
                    moveInfo.mToX),
                ObjectAnimator.ofFloat(moveInfo.mHolder.itemView, "y", moveInfo.mFromY,
                    moveInfo.mToY),
                ObjectAnimator.ofFloat(moveInfo.mHolder.itemView, "alpha",
                    0.5f, 1.0f)
            );

            animator.setTarget(moveInfo.mHolder.itemView);
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());

            animator.start();
            animator.addListener(
                new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        dispatchMoveStarting(moveInfo.mHolder);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animator.removeAllListeners();
                        dispatchMoveFinished(moveInfo.mHolder);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                });
        }

        mPendingMove.clear();
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
    }

    @Override
    public void endAnimations() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
