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

    private UpdaterParam updaterParam;
    static final int INVALID = -1;
    static final String INVALID_TITLE = "-1TopBarItemUpdater";

    public void reset() {
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.icon = null;
        updaterParam.iconRes = INVALID;
        updaterParam.text = INVALID_TITLE;
        updaterParam.textColor = 0;
        updaterParam.isTextColorSet = false;
        updaterParam.textColorRes = INVALID;
        updaterParam.visibility = INVALID;
        updaterParam.enable = false;
    }

    static class UpdaterParam{
        public Drawable icon;
        @DrawableRes
        public int iconRes = INVALID;
        public CharSequence text = INVALID_TITLE;
        @ColorInt
        public int textColor;
        public boolean isTextColorSet;
        @ColorRes
        public int textColorRes = INVALID;
        public int visibility = INVALID;
        public boolean enable = true;
    }

    private UpdaterParam getUpdaterParam() {
        if(updaterParam == null){
            updaterParam = new UpdaterParam();
        }
        return updaterParam;
    }

    public final TopBarItemUpdater icon(Drawable icon){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.icon = icon;
        return this;
    }

    public final TopBarItemUpdater iconRes(int iconRes){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.iconRes = iconRes;
        return this;
    }

    public final TopBarItemUpdater text(CharSequence text){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.text = text;
        return this;
    }

    public final TopBarItemUpdater textColorRes(@ColorRes int textColorRes){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.textColorRes = textColorRes;
        return this;
    }

    public final TopBarItemUpdater textColor(@ColorInt int textColor){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.isTextColorSet = true;
        updaterParam.textColor = textColor;
        return this;
    }

    public final TopBarItemUpdater enable(boolean enable){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.enable = enable;
        return this;
    }

    public final TopBarItemUpdater visibility(int visibility){
        UpdaterParam updaterParam = getUpdaterParam();
        updaterParam.visibility = visibility;
        return this;
    }

    abstract void update(UpdaterParam updaterParam);

    public final void update(){
        update(getUpdaterParam());
    }

    static class TopBarItemUpdaterImpl extends TopBarItemUpdater{

        TextView textView;

        public TopBarItemUpdaterImpl(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void update(UpdaterParam updaterParam){
            Context context = textView.getContext();
            final int visibility = updaterParam.visibility;
            if(visibility != INVALID){
                if(visibility == View.VISIBLE){
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.GONE);
                }
            }
            final CharSequence text = updaterParam.text;
            if(!INVALID_TITLE.equalsIgnoreCase(text.toString())){
                textView.setText(text);
            }
            final int textColor = updaterParam.textColor;
            if(updaterParam.isTextColorSet){
                textView.setTextColor(textColor);
            }else {
                final int textColorRes = updaterParam.textColorRes;
                if(textColorRes != INVALID){
                    textView.setTextColor(ContextCompat.getColor(context, textColorRes));
                }
            }
            Drawable icon = updaterParam.icon;
            if(icon != null){
                ActionBarView.setItemIcon(textView, icon);
            }
            int iconRes = updaterParam.iconRes;
            if(iconRes != INVALID){
                ActionBarView.setItemIcon(textView, ContextCompat.getDrawable(context, iconRes));
            }
            boolean enable = updaterParam.enable;
            textView.setEnabled(enable);
        }
    }

}
