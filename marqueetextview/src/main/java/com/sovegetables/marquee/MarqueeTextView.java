package com.sovegetables.marquee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MarqueeTextView extends AppCompatTextView {

    private static final String TAG = "MarqueeTextView";

    private int currentScrollPos = 0;
    private int speed = 8;
    private int textWidth = -1;
    private volatile boolean isMeasured = false;
    private volatile boolean flag = false;
    private volatile boolean isStop = false;
    private IMarqueeListener marqueeListener;
    private Future future;

    private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (textWidth == -1) {
                postInvalidate();
                return;
            }
            if (isStop) {
                return;
            }
            if (!flag && currentScrollPos >= textWidth - getWidth()) {
                task.cancel();
                flag = true;
                if (marqueeListener != null) {
                    marqueeListener.onFinish();
                }
            }
            if (!flag && getVisibility() == View.VISIBLE) {
                currentScrollPos += 1;
                scrollTo(currentScrollPos, 0);
            }
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
        setEllipsize(null);
    }

    @Override
    public void setEllipsize(TextUtils.TruncateAt where) {
//        super.setEllipsize(where);
    }

    @Override
    public void setGravity(int gravity) {
//        super.setGravity(gravity);
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
        stopFuture();
        future = pool.scheduleAtFixedRate(task, 0, speed, TimeUnit.MILLISECONDS);
    }

    private void postStartScroll(int delay) {
        reset();
        stopFuture();
        future = pool.scheduleAtFixedRate(task, delay, speed, TimeUnit.MILLISECONDS);
    }

    private void stopScroll() {
        isStop = true;
        stopFuture();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setMarqueeListener(IMarqueeListener marqueeListener) {
        this.marqueeListener = marqueeListener;
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

    public void reset() {
        flag = false;
        isStop = false;
        currentScrollPos = 0;
        scrollTo(currentScrollPos, 0);
    }

    public void setText(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100;i++){
            sb.append(str).append("   ");
        }
        super.setText(sb.toString());
        isMeasured = false;
        invalidate();
    }

    private synchronized void stopFuture() {
        if (future != null && !future.isCancelled()) {
            future.cancel(true);
        }
        task.cancel();
    }



}
