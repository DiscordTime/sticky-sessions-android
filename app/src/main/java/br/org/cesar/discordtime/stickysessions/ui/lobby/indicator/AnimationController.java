package br.org.cesar.discordtime.stickysessions.ui.lobby.indicator;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;

class AnimationController {

    private static final int PROGRESS_PRECISION = 4;
    private static final float PROGRESS_LOWER_LIMIT = 0.002F;
    private static final float SUDDEN_CHANGE_THRESHOLD = 0.8F;

    private final Interpolator mInterpolator;

    private int mLeft;
    private int mWidth;
    private int mRestartCycleCount = 0;
    private float mPreviousProgress = 0F;
    private float mEvolution = 0F;
    private boolean mIsRunning = false;
    private boolean mRestarted = false;

    float progress;

    AnimationController() {
        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    void updateProgress(final LinearLayoutManager layoutManager, int position) {
        findPageOffset(layoutManager, position);
        float rawProgress = calculateProgress();
        setProgressPrecision(rawProgress);
        calculateEvolution();
    }

    boolean isGoingForward() {
        return mEvolution > 0 && isRunning();
    }

    boolean isBackward() {
        return mEvolution < 0 && isRunning();
    }

    boolean isRunning() {
        return mIsRunning;
    }

    boolean restarted() {
        return mRestarted;
    }

    private void findPageOffset(final LinearLayoutManager layoutManager, int position) {
        final View activeChild = layoutManager.findViewByPosition(position);
        mLeft = activeChild.getLeft();
        mWidth = activeChild.getWidth();
    }

    private float calculateProgress() {
        // on swipe the active item will be positioned from [-width, 0]
        // interpolate offset for smooth animation
        float time = mLeft * -1 / (float) mWidth;

        // limit interpolation time factor to closed range of [0, 1]
        // it helps detecting animation start and end
        time = Math.max(Math.min(time, 1.0F), 0.0F);

        return mInterpolator.getInterpolation(time);
    }

    private void setProgressPrecision(float rawProgress) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMinimumFractionDigits(PROGRESS_PRECISION);
        formatter.setMaximumFractionDigits(PROGRESS_PRECISION);
        progress = Float.valueOf(formatter.format(rawProgress));

        ignoreVeryLowProgress();
        updateRunningState();
    }

    private void ignoreVeryLowProgress() {
        // It helps detecting that animation ended
        if (progress <= PROGRESS_LOWER_LIMIT) {
            progress = 0;
        }
    }

    private void updateRunningState() {
        mIsRunning = progress != 0;
    }

    private void calculateEvolution() {
        mEvolution = mPreviousProgress - progress;

        compensateForSuddenStop();
        compensateForSuddenStart();

        mPreviousProgress = progress;
    }

    private void compensateForSuddenStop() {
        // when progress goes from 1 to 0
        if (mEvolution >= SUDDEN_CHANGE_THRESHOLD) {
            mEvolution = 0;
        }
    }

    private void compensateForSuddenStart() {
        // wait one more cycle before cleaning
        // mRestarted flag
        if (mRestartCycleCount == 2) {
            mRestarted = false;
            mRestartCycleCount = 0;
        } else if (mRestarted) {
            mRestartCycleCount++;
        }

        // when progress goes from 0 to 1
        if (mEvolution <= -SUDDEN_CHANGE_THRESHOLD) {
            // Any value above zero should be
            // enough to detect a movement
            mEvolution = PROGRESS_LOWER_LIMIT;
            // If a sudden start happen and previous
            // progress is not zero, animation mRestarted
            if (mPreviousProgress != 0) {
                mRestarted = true;
                mRestartCycleCount = 1;
            }
        }
    }
}
