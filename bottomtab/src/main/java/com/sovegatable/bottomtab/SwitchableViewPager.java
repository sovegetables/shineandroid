package com.sovegatable.bottomtab;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by albert on 2018/1/19.
 * 可禁止滑动, 禁止切换动画ViewPager：默认不可滑动
 *      enableSwitchable(switchable)
 */

public class SwitchableViewPager extends ViewPager {

    private boolean mSwitchable;
    private boolean mHasSwitchableAnim;

    public SwitchableViewPager(Context context) {
        super(context);
    }

    public SwitchableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void enableSwitchable(boolean switchable) {
        this.mSwitchable = switchable;
    }

    public void enableSwitchableAnimation(boolean enable){
        mHasSwitchableAnim = enable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return mSwitchable && super.onTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, mHasSwitchableAnim);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, mHasSwitchableAnim);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return mSwitchable && super.onInterceptTouchEvent(arg0);
    }
}
