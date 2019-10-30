package com.sovegetables.topnavbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;


public class ActionBarView extends FrameLayout implements ITopBarAction{

    private static final String TAG = "ActionBarView";
    public static final int ACTION_VIEW_MAX_COUNT = 3;
    private TextView mTvLeft;
    TextView mTvTitle;
    private final List<TextView> mActionViews = new ArrayList<>();
    private int mHeight;
    private View mViewPlaceHolder;
    private static final float ELEVATION = 6;
    private TopBarItemUpdater mLeftItemUpdater;
    private TopBarUpdater mTopBarUpdater;
    private TopBar mTopBar;

    public ActionBarView(Context context) {
        super(context, null);
        init(context);
    }

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public ActionBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ActionBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_action_bar, this);
        mTvLeft = findViewById(R.id.tv_action_bart_left);
        TextView tvAction1 = findViewById(R.id.tv_action_bar_01);
        TextView tvAction2 = findViewById(R.id.tv_action_bar_02);
        TextView tvAction3 = findViewById(R.id.tv_action_bar_03);
        mActionViews.add(tvAction1);
        mActionViews.add(tvAction2);
        mActionViews.add(tvAction3);
        mTvTitle = findViewById(R.id.tv_action_bar_title);
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.d_action_bar_height);
        mViewPlaceHolder = findViewById(R.id.view_place_holder);
        float elevation = ViewCompat.getElevation(this);
        if(elevation == 0){
            int defElevation = (int) (getResources().getDisplayMetrics().density * ELEVATION);
            ViewCompat.setElevation(this, defElevation);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if(mode == MeasureSpec.AT_MOST){
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY));
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(parentWidth, mHeight);
        }else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        int tvLeftMeasuredWidth = mTvLeft.getMeasuredWidth();
        if(mViewPlaceHolder.getLayoutParams().width == 0){
            mViewPlaceHolder.getLayoutParams().width = tvLeftMeasuredWidth;
        }
    }

    @Override
    public final void setUpTopBar(TopBar topBar) {
        mTopBar = topBar;
        TopBarItem left = topBar.left();
        setUpItem(mTvLeft, left);
        List<TopBarItem> rights = topBar.rights();
        setRightItems(rights);
        mTvTitle.setText(topBar.title());
        mTvTitle.setTextColor(topBar.titleColor());
        setBackgroundColor(topBar.topBarColor());
    }

    @Override
    public TopBarItemUpdater leftItemUpdater() {
        if(mLeftItemUpdater == null){
            mLeftItemUpdater = new TopBarItemUpdater();
            mLeftItemUpdater.textView = mTvLeft;
        }
        return mLeftItemUpdater;
    }

    @Override
    public TopBarItemUpdater findRightItemUpdaterById(int id) {
        List<TopBarItem> rights = mTopBar.rights();
        TopBarItemUpdater updater = null;
        TopBarItem topBarItem;
        for (int i = 0,len = rights.size(); i < len; i++){
            topBarItem = rights.get(i);
            if(topBarItem.id() == id){
                updater = new TopBarItemUpdater();
                updater.textView = getTextView(i, len);
                break;
            }
        }
        return updater;
    }

    @Override
    public TopBarUpdater getTopBarUpdater() {
        if(mTopBarUpdater == null){
            mTopBarUpdater = new TopBarUpdater();
            mTopBarUpdater.actionBarView = this;
        }
        return mTopBarUpdater;
    }

    static void setItemIcon(TextView tv, Drawable drawable){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            tv.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }else {
            tv.setCompoundDrawables(drawable, null, null, null);
        }
    }

    private void setRightItems(@NonNull List<TopBarItem> rights) {
        int len = rights.size();
        TextView tempTv;
        TopBarItem tempItem;
        for (int i = 0; i < len ; i++){
            tempTv = getTextView(i, len);
            tempItem = rights.get(i);
            setUpItem(tempTv, tempItem);
        }
    }

    private void setUpItem(TextView tempTv, @NonNull TopBarItem tempItem) {
        tempTv.setVisibility(tempItem.visibility() == TopBarItem.Visibility.GONE? GONE : VISIBLE);
        setItemIcon(tempTv, tempItem.icon());
        CharSequence leftText = tempItem.text();
        if(!TextUtils.isEmpty(leftText)){
            tempTv.setText(leftText);
            tempTv.setTextColor(tempItem.textColor());
        }
        OnClickListener listener = tempItem.listener();
        tempTv.setOnClickListener(listener);
    }

    private TextView getTextView(int i, int len) {
        return mActionViews.get(i + ACTION_VIEW_MAX_COUNT - len);
    }
}
