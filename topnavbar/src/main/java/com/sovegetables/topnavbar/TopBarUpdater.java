package com.sovegetables.topnavbar;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

public class TopBarUpdater {

    ActionBarView actionBarView;
    private static final int INVALID = -1;
    private static final String INVALID_TITLE = "-1";

    private CharSequence title = INVALID_TITLE;
    @ColorInt
    private int titleColor = INVALID;
    @ColorInt
    private int topBarColor = INVALID;
    @ColorRes
    private int titleColorRes = INVALID;
    @ColorRes
    private int topBarColorRes = INVALID;

    TopBarUpdater() {
        //no instance
    }

    /*public TopBarItem left(){

    }

    public List<TopBarItem> rights(){

    }*/

    public TopBarUpdater title(CharSequence title){
        this.title = title;
        return this;
    }

    public TopBarUpdater titleColor(@ColorInt int titleColor){
        this.titleColor = titleColor;
        return this;
    }

    public TopBarUpdater titleColorRes(@ColorRes int titleColorRes){
        this.topBarColorRes = titleColorRes;
        return this;
    }

    public TopBarUpdater topBarColor(@ColorInt int topBarColor){
        this.topBarColor = topBarColor;
        return this;
    }

    public TopBarUpdater topBarColorRes(@ColorRes int topBarColorRes){
        this.topBarColorRes = topBarColorRes;
        return this;
    }

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
