package com.sovegetables.topnavbar;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.ColorInt;

class TopBarItemImpl implements TopBarItem{

    private final Drawable icon;
    private final CharSequence text;
    private final View.OnClickListener listener;

    @ColorInt
    private final int textColor;
    private final int id;
    private final Visibility visibility;

    TopBarItemImpl(Drawable icon, CharSequence text,
                   View.OnClickListener listener, int textColor, int id, Visibility visibility) {
        this.icon = icon;
        this.text = text;
        this.listener = listener;
        this.textColor = textColor;
        this.id = id;
        this.visibility = visibility;
    }

    @Override
    public Drawable icon() {
        return icon;
    }

    @Override
    public CharSequence text() {
        return text;
    }

    @Override
    public View.OnClickListener listener() {
        return listener;
    }

    @Override
    public int textColor() {
        return textColor;
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public Visibility visibility() {
        return visibility;
    }
}
