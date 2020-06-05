package com.sovegetables.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

public class MarqueeTextView extends AppCompatTextView {

    private static final String TAG = "MarqueeTextView";
    public static final int DEFAULT_SPEED = 13;

    private int currentScrollPos = 0;
    private int speed = DEFAULT_SPEED;
    private int textWidth = -1;
    private volatile boolean isMeasured = false;
    private volatile boolean isStop = true;

    final Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if (textWidth == -1) {
                postInvalidate();
                return;
            }
            if(textWidth <= getWidth()){
                return;
            }
            if (isStop) {
                return;
            }
            if (currentScrollPos >= textWidth - getWidth()) {
                reset();
            }else {
                currentScrollPos +=  getResources().getDisplayMetrics().density;
                scrollTo(currentScrollPos, 0);
            }
            postDelayed(mRunnable, speed);
        }
    };

    public MarqueeTextView(Context context) {
        super(context);
        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setSingleLine();
        super.setEllipsize(null);
    }

    @Override
    public void setEllipsize(TextUtils.TruncateAt where) {
    }

    @Override
    public void setGravity(int gravity) {
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startScroll();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }

    private void startScroll() {
        reset();
        if(isStop){
            isStop = false;
            postDelayed(mRunnable, speed);
        }
    }

    private void stopScroll() {
        isStop = true;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(visibility == VISIBLE){
            startScroll();
        }else {
            stopScroll();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isMeasured) {
            getTextWidth();
            isMeasured = true;
        }
    }

    private void getTextWidth() {
        Paint paint = this.getPaint();
        String str = this.getText().toString();
        if (TextUtils.isEmpty(str)) {
            textWidth = 0;
        }
        textWidth = (int) paint.measureText(str);
    }

    private void reset() {
        currentScrollPos = 0;
        scrollTo(currentScrollPos, 0);
    }

    public void setText(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20;i++){
            sb.append(str).append("   ");
        }
        super.setText(sb.toString());
        isMeasured = false;
        invalidate();
    }
}
