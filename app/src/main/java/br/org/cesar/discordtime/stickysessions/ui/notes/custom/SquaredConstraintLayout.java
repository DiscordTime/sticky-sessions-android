package br.org.cesar.discordtime.stickysessions.ui.notes.custom;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SquaredConstraintLayout extends ConstraintLayout {
    public SquaredConstraintLayout(Context context) {
        super(context);
    }

    public SquaredConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquaredConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
