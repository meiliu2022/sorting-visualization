package com.meiliu.mysortingvisualizer.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.meiliu.mysortingvisualizer.R;

public class RectView extends AppCompatImageView {
    public static final int TEXT_BASELINE_Y = 40;
    public static final float TEXT_SIZE = 45f;
    Paint paint;
    Rect bounds;
    private Integer valueToDraw;
    private boolean isSelected;
    private boolean isOnFinalPlace;
    private int mWidth;
    private int mRectHeight;
    private int mRectWidth;
    private boolean mHidden;

    public RectView(Context context) {
        this(context, null);
        init();
    }

    public RectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.LINEAR_TEXT_FLAG);
        paint.setAntiAlias(true);
        paint.setTextSize(TEXT_SIZE);
        bounds = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mRectWidth = mWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (valueToDraw != null) {
            String text = valueToDraw.toString();
            if (isOnFinalPlace) {
                paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                if (isSelected) {
                    paint.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    paint.setColor(getResources().getColor(R.color.colorAccent));
                }
            }
            if (!mHidden) {
                int fullHeight = getMeasuredHeight();
                canvas.drawRect(0, fullHeight - mRectHeight, mRectWidth, fullHeight + 5, paint);

                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);

                canvas.drawText(text, mRectWidth / 2, fullHeight - TEXT_BASELINE_Y, paint);
            }
        }
    }

    public Integer getNumber() {
        return valueToDraw;
    }

    public void setNumber(Integer valueToDraw) {
        this.valueToDraw = valueToDraw;
        invalidate();
    }

    public void setIsOnFinalPlace(boolean isOnFinalPlace) {
        this.isOnFinalPlace = isOnFinalPlace;
        invalidate();
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        invalidate();
    }

    public void setRectHeight(int mRectHeight) { // modified
        this.mRectHeight = mRectHeight;
    }

    public void setHidden(boolean mHidden) {
        this.mHidden = mHidden;
    }
}
