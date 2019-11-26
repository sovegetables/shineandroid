package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public abstract class TopBarItemUpdater {

    protected static final int INVALID = -1;
    protected static final String INVALID_TITLE = "-1";

    protected Drawable icon;
    @DrawableRes
    protected int iconRes = INVALID;
    protected CharSequence text = INVALID_TITLE;
    @ColorInt
    protected int textColor = INVALID;
    @ColorRes
    protected int textColorRes = INVALID;
    protected int visibility = INVALID;

    public final TopBarItemUpdater icon(Drawable icon){
        this.icon = icon;
        return this;
    }

    public final TopBarItemUpdater text(CharSequence text){
        this.text = text;
        return this;
    }

    public final TopBarItemUpdater textColorRes(@ColorRes int textColorRes){
        this.textColorRes = textColorRes;
        return this;
    }

    public final TopBarItemUpdater textColor(@ColorInt int textColor){
        this.textColor = textColor;
        return this;
    }

    public final TopBarItemUpdater visibility(int visibility){
        this.visibility = visibility;
        return this;
    }

    public abstract void update();

    static class TopBarItemUpdaterImpl extends TopBarItemUpdater{

        TextView textView;

        public TopBarItemUpdaterImpl(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void update(){
            Context context = textView.getContext();
            final int visibility = this.visibility;
            if(visibility != INVALID){
                if(visibility == View.VISIBLE){
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.GONE);
                }
            }
            final CharSequence text = this.text;
            if(!INVALID_TITLE.equalsIgnoreCase(text.toString())){
                textView.setText(text);
            }

            final int textColor = this.textColor;
            if(textColor != INVALID){
                textView.setTextColor(textColor);
            }

            final int textColorRes = this.textColorRes;
            if(textColorRes != INVALID){
                textView.setTextColor(ContextCompat.getColor(context, textColorRes));
            }

            Drawable icon = this.icon;
            if(icon != null){
                ActionBarView.setItemIcon(textView, icon);
            }

            int iconRes = this.iconRes;
            if(iconRes != INVALID){
                ActionBarView.setItemIcon(textView, ContextCompat.getDrawable(context, iconRes));
            }

            if(visibility != INVALID){
                textView.setVisibility(visibility == View.VISIBLE? View.VISIBLE: View.GONE);
            }
        }
    }

}
