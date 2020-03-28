package com.sovegetables.textcrumbs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class CrossView extends View {
    private int mBackgroundColor;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.RED;
    private static final int CROSS_LINE_WIDTH = 3;
    private final PorterDuffXfermode mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private final Paint mPaint = new Paint();

    public CrossView(Context context) {
        super(context);
    }

    public CrossView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CrossView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrossView);
        try {
            mBackgroundColor = typedArray.getColor(R.styleable.CrossView_backgroundColor, DEFAULT_BACKGROUND_COLOR);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        Paint paint = mPaint;
        paint.setAntiAlias(true);
        paint.setColor(mBackgroundColor);
        int width = getWidth();
        int circleRadius = width / 2;
        float radius = width >> 2;
        canvas.saveLayer(0,0,1000,1000,paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(circleRadius, circleRadius, (float) circleRadius, paint);
        paint.setXfermode(mPorterDuffXfermode);
        paint.setDither(true);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(CROSS_LINE_WIDTH);
        canvas.drawLine(circleRadius + radius, circleRadius + radius, radius, radius, paint);
        canvas.drawLine(circleRadius + radius, radius, radius, circleRadius + radius, paint);

    }

    public void setBgColor(int bgColor){
        mBackgroundColor = bgColor;
    }
}
