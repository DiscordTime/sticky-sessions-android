package br.org.cesar.discordtime.stickysessions.ui.lobby.indicator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageIndicator extends RecyclerView.ItemDecoration {

    private final AnimationController mAnimationController;
    private final CanvasWrapper mCanvasWrapper;
    private final SwipeDetector mSwipeDetector;

    private int mItemCount;
    private int mPreviousPosition = -1;

    public PageIndicator(int itemCount) {
        mItemCount = itemCount;
        mAnimationController = new AnimationController();
        mCanvasWrapper = new CanvasWrapper(mItemCount);
        mSwipeDetector = new SwipeDetector(mItemCount);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        mCanvasWrapper.init(c, parent);
        mCanvasWrapper.drawInactiveIndicators();

        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = findActivePage(layoutManager);
        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        mAnimationController.updateProgress(layoutManager, activePosition);
        mSwipeDetector.detectDirection(mAnimationController);

        activePosition = convertInfinitePositionToRealOne(activePosition);
        updatePreviousPosition(activePosition);
        drawHighlights(activePosition, mAnimationController.progress);
    }

    private int convertInfinitePositionToRealOne(int position) {
        return position % mItemCount;
    }

    private void updatePreviousPosition(int currentPosition) {
        if ((mPreviousPosition != currentPosition)
                && (mPreviousPosition == -1
                || !mAnimationController.isRunning() || mAnimationController.restarted())) {
            mPreviousPosition = currentPosition;
        }
    }

    private int findActivePage(LinearLayoutManager layoutManager) {
        int completePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int activePosition;
        if (completePosition != RecyclerView.NO_POSITION) {
            activePosition = completePosition;
        } else {
            activePosition = layoutManager.findFirstVisibleItemPosition();
        }
        return activePosition;
    }

    private void drawHighlights(int highlightPosition, float progress) {
        // no swipe
        if (mSwipeDetector.isPaused()) {
            mCanvasWrapper.drawPausedIndicator(highlightPosition);

        // end to end
        } else if (mSwipeDetector.isAnEndToEndSwipe(mPreviousPosition, highlightPosition)) {
            mCanvasWrapper.drawEndToEndTransition(highlightPosition, progress);

        // swipe
        } else {
            mCanvasWrapper.drawMovingIndicator(highlightPosition, progress);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mCanvasWrapper.getBottomOffset();
    }
}
