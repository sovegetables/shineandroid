package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public class TopBarItemUpdater {

    TextView textView;

    private static final int INVALID = -1;
    private static final String INVALID_TITLE = "-1";

    private Drawable icon;
    @DrawableRes
    private int iconRes = INVALID;
    private CharSequence text = INVALID_TITLE;
//    private View.OnClickListener listener;
    @ColorInt
    private int textColor = INVALID;
    @ColorRes
    private int textColorRes = INVALID;
    private int visibility = INVALID;

    TopBarItemUpdater() {
        //no instance
    }

    public TopBarItemUpdater icon(Drawable icon){
        this.icon = icon;
        return this;
    }

    public TopBarItemUpdater text(CharSequence text){
        this.text = text;
        return this;
    }

    /*public TopBarItemUpdater listener(View.OnClickListener listener){
        this.listener = listener;
        return this;
    }*/

    public TopBarItemUpdater textColorRes(@ColorRes int textColorRes){
        this.textColorRes = textColorRes;
        return this;
    }

    public TopBarItemUpdater textColor(@ColorInt int textColor){
        this.textColor = textColor;
        return this;
    }

    public TopBarItemUpdater visibility(int visibility){
        this.visibility = visibility;
        return this;
    }

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
