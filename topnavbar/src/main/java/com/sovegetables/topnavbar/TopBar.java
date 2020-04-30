package com.sovegetables.topnavbar;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public interface TopBar {

    @NonNull
    TopBarItem left();

    @NonNull
    List<TopBarItem> rights();

    CharSequence title();

    @ColorInt
    int titleColor();

    @ColorInt
    int topBarColor();

    TopBar NO_ACTION_BAR = new TopBarImpl(TopBarItem.EMPTY, new ArrayList<TopBarItem>(), null, Color.BLACK, Color.WHITE);

    String DUPLICATE_ID = "duplicate item id !";


    class Builder {

        static final String RIGHT_ITEM_COUNT_CAN_T_EXCEED_3 = "right item count can't exceed 3!";
        private TopBarItem mLeft = TopBarItem.EMPTY;
        private final List<TopBarItem> mRightItems = new ArrayList<>(3);
        private final ArrayList<Integer> itemIds = new ArrayList<>(10);

        private CharSequence mTitle;
        private Context mContext;
        TopBarUpdater updater;

        static final int INVALID = -1;
        @ColorInt
        private int mTitleColor = INVALID;
        private boolean mIsTitleColorSet;
        @ColorRes
        private int mTopBarColorRes = INVALID;
        @ColorInt
        private int mTopBarColor = INVALID;
        private boolean mIsTopBarColorSet;
        @ColorRes
        private int mTitleColorRes = INVALID;
        private int mTitleRes = INVALID;

        public Builder(){
        }

        public Builder left(TopBarItem left){
            mLeft = left;
            return this;
        }

        public Builder right(TopBarItem right){
            mRightItems.add(right);
            checkMaxCount(mRightItems);
            return this;
        }

        private void checkMaxCount(List<TopBarItem> rightItems) {
            if(rightItems.size() > ActionBarView.ACTION_VIEW_MAX_COUNT){
                throw new IllegalArgumentException(RIGHT_ITEM_COUNT_CAN_T_EXCEED_3);
            }
        }

        public Builder rights(@NonNull List<TopBarItem> rights){
            mRightItems.addAll(rights);
            checkMaxCount(mRightItems);
            return this;
        }

        public Builder title(CharSequence title){
            mTitle = title;
            return this;
        }

        public Builder title(@StringRes int titleRes){
            mTitleRes = titleRes;
            return this;
        }

        public Builder titleColor(@ColorInt int titleColor){
            mIsTitleColorSet = true;
            mTitleColor = titleColor;
            return this;
        }

        public Builder titleColorRes(@ColorRes int titleColorRes){
            mTitleColorRes = titleColorRes;
            return this;
        }

        public Builder topBarColor(@ColorInt int topBarColor){
            mIsTopBarColorSet = true;
            mTopBarColor = topBarColor;
            return this;
        }

        public Builder topBarColorRes(@ColorRes int topBarColorRes){
            mTopBarColorRes = topBarColorRes;
            return this;
        }

        public TopBar build(Context context){
            mContext = context.getApplicationContext();
            List<TopBarItem> rightItems = this.mRightItems;
            for (TopBarItem i: rightItems){
                if(!itemIds.contains(i.id())){
                    itemIds.add(i.id());
                }else {
                    throw new IllegalArgumentException(DUPLICATE_ID);
                }
            }
            return new TopBarImpl(mLeft, rightItems, getTitle(), getTitleColor() , getTopBarColor()) ;
        }

        private CharSequence getTitle(){
            if(mTitle != null){
                return mTitle;
            }
            if(mTitleRes != INVALID){
                return mContext.getString(mTitleRes);
            }
            return null;
        }

        private int getTitleColor() {
            if(mIsTitleColorSet){
                return mTitleColor;
            }
            if(mTitleColorRes != INVALID){
                return ContextCompat.getColor(mContext, mTitleColorRes);
            }
            return Color.BLACK;
        }

        private int getTopBarColor() {
            if(mIsTopBarColorSet){
                return mTopBarColor;
            }
            if(mTopBarColorRes != INVALID){
                return ContextCompat.getColor(mContext, mTopBarColorRes);
            }
            return Color.WHITE;
        }

    }
}
