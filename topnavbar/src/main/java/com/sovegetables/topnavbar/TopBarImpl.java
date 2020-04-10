package com.sovegetables.topnavbar;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import java.util.List;

class TopBarImpl implements TopBar{

    private final TopBarItem left;
    private final List<TopBarItem> rights;
    private final CharSequence title;

    @ColorInt
    private int titleColor;

    @ColorInt
    private int topBarColor;


    TopBarImpl(TopBarItem left, List<TopBarItem> rights, CharSequence title, int titleColor, int topBarColor) {
        this.left = left;
        this.rights = rights;
        this.title = title;
        this.titleColor = titleColor;
        this.topBarColor = topBarColor;
    }

    @NonNull
    @Override
    public TopBarItem left() {
        return left;
    }

    @NonNull
    @Override
    public List<TopBarItem> rights() {
        return rights;
    }

    @Override
    public CharSequence title() {
        return title;
    }

    @Override @ColorInt
    public int titleColor() {
        return titleColor;
    }

    @Override @ColorInt
    public int topBarColor() {
        return topBarColor;
    }

}
