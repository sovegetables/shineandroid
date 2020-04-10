package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.VisibleForTesting;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public interface TopBarItem {

    enum Visibility{
        VISIBLE, GONE
    }

    Drawable icon();

    CharSequence text();

    View.OnClickListener listener();

    @ColorInt int textColor();

    int id();

    Visibility visibility();

    int DEFAULT_TOP_BAR_ITEM_ID = Integer.MIN_VALUE;
    TopBarItem EMPTY = new TopBarItemImpl(null, null, null, Color.BLACK, DEFAULT_TOP_BAR_ITEM_ID, Visibility.GONE);

    class Builder{

        private static final int INVALID = -1;
        private int icon = INVALID;
        private Drawable iconDrawable;
        private CharSequence text;
        private View.OnClickListener listener;
        @ColorInt private int textColor;
        private boolean isTextColorSet;
        private final ArrayList<Integer> ids = new ArrayList<>(10);
        private Visibility visibility = Visibility.VISIBLE;
        @ColorRes private int textColorRes = INVALID;

        public Builder icon(@DrawableRes int icon){
            this.icon = icon;
            return this;
        }

        public Builder text(CharSequence text){
            this.text = text;
            return this;
        }

        public Builder listener(View.OnClickListener listener){
            this.listener = listener;
            return this;
        }

        public Builder textColor(@ColorInt int textColor){
            isTextColorSet = true;
            this.textColor = textColor;
            return this;
        }

        public Builder textColorRes(@ColorRes int textColorRes){
            this.textColorRes = textColorRes;
            return this;
        }

        public Builder visibility(Visibility visibility){
            this.visibility = visibility;
            return this;
        }

        public TopBarItem build(final Context context, final int id){
            if(ids.contains(id)){
                throw new IllegalArgumentException("duplicate id !");
            }
            ids.add(id);
            return new TopBarItemImpl(getDrawable(context), text, listener, getColor(context), id, visibility);
        }

        @VisibleForTesting
        private int getColor(Context context) {
            if(isTextColorSet){
                return textColor;
            }
            if(textColorRes != INVALID){
                return ContextCompat.getColor(context, textColorRes);
            }
            return Color.WHITE;
        }

        @Nullable
        private Drawable getDrawable(Context context) {
            if(icon > 0){
                iconDrawable = ContextCompat.getDrawable(context, icon);
            }
            return iconDrawable;
        }
    }
}
