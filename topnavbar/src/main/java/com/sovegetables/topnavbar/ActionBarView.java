package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;


public class ActionBarView extends ConstraintLayout implements ITopBarAction{

    private static final String TAG = "ActionBarView";
    static final int ACTION_VIEW_MAX_COUNT = 3;
    private TextView mTvLeft;
    TextView mTvTitle;
    private final List<TextView> mActionViews = new ArrayList<>();
    private int mHeight;
    private static final float ELEVATION = 6;
    private TopBarItemUpdater mLeftItemUpdater;
    private TopBarUpdater mTopBarUpdater;
    private TopBar mTopBar;
    private View mLeftAnchor;
    private View mRightAnchor;
    private View mRightActionLayout;

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

    private void init(Context context) {
        inflate(context, R.layout.layout_action_bar, this);
        mTvLeft = findViewById(R.id.tv_action_bart_left);
        mLeftAnchor = findViewById(R.id.view_left_empty);
        mRightAnchor = findViewById(R.id.view_right_empty);
        mRightActionLayout = findViewById(R.id.ll_right_action);

        TextView tvAction1 = findViewById(R.id.tv_action_bar_01);
        TextView tvAction2 = findViewById(R.id.tv_action_bar_02);
        TextView tvAction3 = findViewById(R.id.tv_action_bar_03);
        mActionViews.add(tvAction1);
        mActionViews.add(tvAction2);
        mActionViews.add(tvAction3);
        mTvTitle = findViewById(R.id.tv_action_bar_title);
        mHeight = context.getResources().getDimensionPixelSize(R.dimen.d_action_bar_height);
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
            fixTitleCenter();
            setMeasuredDimension(parentWidth, mHeight);
        }else {
            fixTitleCenter();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        fixTitleCenter();
    }

    /**
     * 保证Title居中
     */
    private void fixTitleCenter() {
        int maxWidth = Math.max(mTvLeft.getWidth(), mRightActionLayout.getWidth());
        ViewGroup.LayoutParams leftLayoutParams = mLeftAnchor.getLayoutParams();
        if(maxWidth != leftLayoutParams.width && maxWidth > 0){
            leftLayoutParams.width = maxWidth;
            mLeftAnchor.setLayoutParams(leftLayoutParams);
        }
        ViewGroup.LayoutParams rightLayoutParams = mRightAnchor.getLayoutParams();
        if(maxWidth != rightLayoutParams.width && maxWidth > 0){
            rightLayoutParams.width = maxWidth;
            mRightAnchor.setLayoutParams(rightLayoutParams);
        }
    }

    @Override
    public final void setUpTopBar(TopBar topBar) {
        setVisibility(topBar == TopBar.NO_ACTION_BAR? GONE: VISIBLE);
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
            mLeftItemUpdater = new TopBarItemUpdater.TopBarItemUpdaterImpl(mTvLeft);
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
                updater = new TopBarItemUpdater.TopBarItemUpdaterImpl(getTextView(i, len));
                break;
            }
        }
        return updater;
    }

    @Override
    public TopBarUpdater getTopBarUpdater() {
        if(mTopBarUpdater == null){
            mTopBarUpdater = new TopBarUpdater.TopBarUpdaterImpl(this);
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
