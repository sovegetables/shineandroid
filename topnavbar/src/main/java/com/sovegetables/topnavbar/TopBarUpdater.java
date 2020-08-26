package com.sovegetables.topnavbar;

import android.content.Context;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public abstract class TopBarUpdater {

    static final int INVALID = -1;
    static final String INVALID_TITLE = "-1TopBarUpdater";

    private Params params;

    public void reset() {
        Params params = getParams();
        params.title = INVALID_TITLE;
        params.titleColor = INVALID;
        params.isTitleColorSet = false;
        params.topBarColor = INVALID;
        params.isTopBarColorSet = false;
        params.titleColorRes = INVALID;
        params.topBarColorRes = INVALID;
        params.titleRes = INVALID;
    }

    static class Params{
        public CharSequence title = INVALID_TITLE;
        @ColorInt
        public int titleColor = INVALID;
        public boolean isTitleColorSet = false;
        @ColorInt
        public int topBarColor = INVALID;
        public boolean isTopBarColorSet = false;
        @ColorRes
        public int titleColorRes = INVALID;
        @ColorRes
        public int topBarColorRes = INVALID;
        public int titleRes = INVALID;
    }

    TopBarUpdater() {
        //no instance
    }

    private Params getParams() {
        if(params == null){
            params = new Params();
        }
        return params;
    }

    public final TopBarUpdater title(CharSequence title){
        this.getParams().title = title;
        return this;
    }

    public final TopBarUpdater titleRes(@StringRes int titleRes){
        this.getParams().titleRes = titleRes;
        return this;
    }

    public final TopBarUpdater titleColor(@ColorInt int titleColor){
        Params params = getParams();
        params.isTitleColorSet = true;
        params.titleColor = titleColor;
        return this;
    }

    public final TopBarUpdater titleColorRes(@ColorRes int titleColorRes){
        Params params = getParams();
        params.titleColorRes = titleColorRes;
        return this;
    }

    public final TopBarUpdater topBarColor(@ColorInt int topBarColor){
        Params params = getParams();
        params.isTopBarColorSet = true;
        params.topBarColor = topBarColor;
        return this;
    }

    public final TopBarUpdater topBarColorRes(@ColorRes int topBarColorRes){
        Params params = getParams();
        params.topBarColorRes = topBarColorRes;
        return this;
    }

    public final void update(){
        update(getParams());
    }

    abstract void update(Params params);

    static class TopBarUpdaterImpl extends TopBarUpdater{
        ActionBarView actionBarView;

        TopBarUpdaterImpl(ActionBarView actionBarView) {
            this.actionBarView = actionBarView;
        }

        @Override
        public void update(Params params){
            final CharSequence title = params.title;
            final Context context = actionBarView.getContext();
            if(title != null && !INVALID_TITLE.equalsIgnoreCase(title.toString())){
                actionBarView.setTitle(title);
            }else {
                if(params.titleRes != INVALID){
                    actionBarView.setTitle(context.getString(params.titleRes));
                }
            }
            if(params.isTitleColorSet){
                final int titleColor = params.titleColor;
                actionBarView.setTitleTextColor(titleColor);
            }else {
                final int titleColorRes = params.titleColorRes;
                if(titleColorRes != INVALID){
                    actionBarView.setTitleTextColor(ContextCompat.getColor(context, titleColorRes));
                }
            }

            if(params.isTopBarColorSet){
                final int topBarColor = params.topBarColor;
                actionBarView.setBackgroundColor(topBarColor);
            }else {
                final int topBarColorRes = params.topBarColorRes;
                if(topBarColorRes != INVALID){
                    actionBarView.setBackgroundColor(ContextCompat.getColor(context, topBarColorRes));
                }
            }
        }
    }
}
