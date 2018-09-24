package br.org.cesar.discordtime.stickysessions.ui.lobby.indicator;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.recyclerview.widget.RecyclerView;

class CanvasWrapper {

    private static final int COLOR_ACTIVE = 0xFFFFFFFF;
    private static final int COLOR_INACTIVE = 0x66FFFFFF;
    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

    // Height of the space the indicator takes up at the bottom of the view
    private final int mIndicatorHeight = (int) (DP * 16);

    // Indicator stroke width
    private final float mIndicatorStrokeWidth = DP * 4;

    // Indicator width
    private final float mIndicatorItemLength = DP * 4;

    // Padding between indicators
    private final float mIndicatorItemPadding = DP * 16;

    // Width of item indicator including padding
    private final float mItemWidth = mIndicatorItemLength + mIndicatorItemPadding;

    private final Paint mPaint = new Paint();
    private Canvas c;

    private int mItemCount;
    private float mIndicatorStartX;
    private float mIndicatorPosY;

    CanvasWrapper(int itemCount) {
        mItemCount = itemCount;
        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    void init(Canvas canvas, RecyclerView parent) {
        c = canvas;
        mIndicatorStartX = getIndicatorStartX(parent);
        mIndicatorPosY = getIndicatorPosY(parent);
    }

    int getBottomOffset() {
        return mIndicatorHeight;
    }

    void drawInactiveIndicators() {
        mPaint.setColor(COLOR_INACTIVE);

        float start = mIndicatorStartX;
        for (int i = 0; i < mItemCount; i++) {
            c.drawCircle(start, mIndicatorPosY, mIndicatorItemLength / 2F, mPaint);
            start += mItemWidth;
        }
    }

    void drawEndToEndTransition(int highlightPosition, float progress) {
        mPaint.setColor(COLOR_ACTIVE);

        float radius1 = progress * (mIndicatorItemLength / 2F);
        float radius2 = (1 - progress) * (mIndicatorItemLength / 2F);

        c.drawCircle(mIndicatorStartX, mIndicatorPosY, radius1, mPaint);
        c.drawCircle(getHighlightStart(highlightPosition), mIndicatorPosY, radius2, mPaint);
    }

    void drawPausedIndicator(int highlightPosition) {
        mPaint.setColor(COLOR_ACTIVE);

        c.drawCircle(getHighlightStart(highlightPosition),
                mIndicatorPosY, mIndicatorItemLength / 2F, mPaint);
    }

    void drawMovingIndicator(int highlightPosition, float progress) {
        mPaint.setColor(COLOR_ACTIVE);

        float partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress;

        c.drawCircle(getHighlightStart(highlightPosition) + partialLength, mIndicatorPosY,
                mIndicatorItemLength / 2F, mPaint);
    }

    private float getIndicatorPosY(RecyclerView parent) {
        // center vertically in the allotted space
        return parent.getHeight() - mIndicatorHeight / 2F;
    }

    private float getIndicatorStartX(RecyclerView parent) {
        // center horizontally, calculate width and subtract half from center
        float totalLength = mIndicatorItemLength * mItemCount;
        float paddingBetweenItems = Math.max(0, mItemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        return (parent.getWidth() - indicatorTotalWidth) / 2F;
    }

    private float getHighlightStart(int highlightPosition) {
        return mIndicatorStartX + mItemWidth * highlightPosition;
    }
}