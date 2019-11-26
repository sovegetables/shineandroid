package com.sovegetables.topnavbar;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public abstract class TopBarUpdater {

    protected static final int INVALID = -1;
    protected static final String INVALID_TITLE = "-1";

    protected CharSequence title = INVALID_TITLE;
    @ColorInt
    protected int titleColor = INVALID;
    @ColorInt
    protected int topBarColor = INVALID;
    @ColorRes
    protected int titleColorRes = INVALID;
    @ColorRes
    protected int topBarColorRes = INVALID;

    TopBarUpdater() {
        //no instance
    }

    public final TopBarUpdater title(CharSequence title){
        this.title = title;
        return this;
    }

    public final TopBarUpdater titleColor(@ColorInt int titleColor){
        this.titleColor = titleColor;
        return this;
    }

    public final TopBarUpdater titleColorRes(@ColorRes int titleColorRes){
        this.topBarColorRes = titleColorRes;
        return this;
    }

    public final TopBarUpdater topBarColor(@ColorInt int topBarColor){
        this.topBarColor = topBarColor;
        return this;
    }

    public final TopBarUpdater topBarColorRes(@ColorRes int topBarColorRes){
        this.topBarColorRes = topBarColorRes;
        return this;
    }

    public abstract void update();

    static class TopBarUpdaterImpl extends TopBarUpdater{
        ActionBarView actionBarView;

        TopBarUpdaterImpl(ActionBarView actionBarView) {
            this.actionBarView = actionBarView;
        }

        @Override
        public void update(){
            final CharSequence title = this.title;
            final Context context = actionBarView.getContext();
            if(!INVALID_TITLE.equalsIgnoreCase(title.toString())){
                actionBarView.mTvTitle.setText(title);
            }
            final int titleColor = this.titleColor;
            if(titleColor != INVALID){
                actionBarView.mTvTitle.setTextColor(titleColor);
            }
            final int titleColorRes = this.titleColorRes;
            if(titleColorRes != INVALID){
                actionBarView.mTvTitle.setTextColor(ContextCompat.getColor(context, titleColorRes));
            }

            final int topBarColor = this.topBarColor;
            if(topBarColor != INVALID){
                actionBarView.setBackgroundColor(topBarColor);
            }

            final int topBarColorRes = this.topBarColorRes;
            if(topBarColorRes != INVALID){
                actionBarView.setBackgroundColor(ContextCompat.getColor(context, topBarColorRes));
            }
        }
    }
}
