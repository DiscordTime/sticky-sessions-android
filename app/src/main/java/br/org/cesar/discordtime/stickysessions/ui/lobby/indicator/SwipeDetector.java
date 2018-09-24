package br.org.cesar.discordtime.stickysessions.ui.lobby.indicator;

import android.util.Log;

class SwipeDetector {

    private int mItemCount;

    private SwipeState mPreviousSwipeState;
    private SwipeState mSwipeState;

    private enum SwipeState {
        LEFT, RIGHT, PAUSED
    }

    SwipeDetector(int itemCount) {
        mItemCount = itemCount;
        mSwipeState = SwipeState.PAUSED;
        mPreviousSwipeState = SwipeState.PAUSED;
    }

    private boolean isGoingLeft() {
        return mSwipeState == SwipeState.LEFT;
    }

    private boolean isGoingRight() {
        return mSwipeState == SwipeState.RIGHT;
    }

    boolean isPaused() {
        return mSwipeState == SwipeState.PAUSED;
    }

    private boolean hasChangedDirection() {
        return mSwipeState != mPreviousSwipeState
                && mSwipeState != SwipeState.PAUSED
                && mPreviousSwipeState != SwipeState.PAUSED;
    }

    void detectDirection(AnimationController animationController) {

        // when direction has changed, keep previous state the same
        // until animation ends and state goes back to PAUSED
        if (!hasChangedDirection()) mPreviousSwipeState = mSwipeState;

        if (animationController.isGoingForward()) {
            mSwipeState = SwipeState.LEFT;
        } else if (animationController.isBackward()) {
            mSwipeState = SwipeState.RIGHT;
        }  else if (!animationController.isRunning()){
            mSwipeState = SwipeState.PAUSED;
        }
    }

    boolean isAnEndToEndSwipe(int previousPosition, int targetPosition) {
        if (isFirst(previousPosition) && isGoingLeft()) return true;
        if (isLast(targetPosition) && isGoingRight()) return true;
        if (isLast(targetPosition) && isGoingLeft() && hasChangedDirection()) return true;
        return false;
    }

    private boolean isFirst(int position) {
        return position == 0;
    }

    private boolean isLast(int position) {
        return position == (mItemCount - 1);
    }
}
