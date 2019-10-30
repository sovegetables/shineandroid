package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

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

        @DrawableRes
        private int icon;
        private CharSequence text;
        private View.OnClickListener listener;
        @ColorInt
        private int textColor;

        private final ArrayList<Integer> ids = new ArrayList<>(10);
        private Visibility visibility = Visibility.VISIBLE;

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
            this.textColor = textColor;
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
            return new TopBarItemImpl(ContextCompat.getDrawable(context, icon), text, listener, textColor, id, visibility);
        }
    }
}
