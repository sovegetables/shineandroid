package com.sovegetables.textcrumbs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TextCrumbs extends FrameLayout {

    private static final String TAG = "TextCrumbs";
    private final HashMap<String, Label> mLabelMap = new HashMap<>();
    private OnRemoveLabelListener mOnRemoveLabelListener;
    protected OnLabelClickListener mOnClickOptionListener;
    private int mItemHorizontalMargin = 30;
    private int mItemVerticalMargin = 36;
    private int mItemGravity;
    private static final int ITEM_GRAVITY_NORMAL = 0;
    private static final int ITEM_GRAVITY_ALIGN_CENTER= 1;
    private int mLabelTextColor = Color.BLACK;
    private int mLabelBackgroundColor = Color.WHITE;
    private int mCardTextColor = Color.BLACK;
    private int mCardTextSelectedColor = R.color.c_text_crumb_back;
    private int mCardBgSelectedColor = Color.BLACK;
    private int mCardBgColor = Color.WHITE;
    private int mLabelCrossBgColor = Color.BLACK;
    private Rect mTempRect = new Rect();
    private List<Rect> mLocation = new ArrayList<>();
    private SparseIntArray mLineArray = new SparseIntArray();
    private SparseIntArray mGapArray = new SparseIntArray();

    public TextCrumbs(Context context) {
        this(context, null);
    }

    public TextCrumbs(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextCrumbs(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextCrumbs);
        mItemHorizontalMargin = typedArray.getDimensionPixelSize(R.styleable.TextCrumbs_text_crumb_item_horizontal_margin, mItemHorizontalMargin);
        mItemVerticalMargin = typedArray.getDimensionPixelSize(R.styleable.TextCrumbs_text_crumb_item_vertical_margin, mItemVerticalMargin);
        mItemGravity = typedArray.getInt(R.styleable.TextCrumbs_text_crumb_item_gravity, 0);
        typedArray.recycle();
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int cWidth;
        int cHeight;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            width += cWidth + mItemHorizontalMargin;
            if (i == 0) {
                width = cWidth;
                height = cHeight + mItemVerticalMargin;
            } else if (width > sizeWidth) {
                width = cWidth;
                height += cHeight + mItemVerticalMargin;
            }
            if ((cWidth + mItemHorizontalMargin) > sizeWidth) {
                if (i == cCount - 1) {
                    height += cHeight / 2;
                }
            }
            if (i == cCount - 1) {
                height += mItemVerticalMargin;
            }
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width,
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int filterCount = getChildCount();
        int cWidth;
        int cHeight;
        int lengthX = 0;
        int lengthY = 0;
        if(mItemGravity == ITEM_GRAVITY_NORMAL) {
            for (int i = 0; i < filterCount; i++) {
                View childView = getChildAt(i);
                cWidth = childView.getMeasuredWidth();
                cHeight = childView.getMeasuredHeight();

                lengthX += cWidth + mItemHorizontalMargin;
                if (i == 0) {
                    lengthX = cWidth;
                    lengthY = cHeight + mItemVerticalMargin;
                } else if (lengthX > getWidth()) {
                    lengthX = cWidth;
                    lengthY += cHeight + mItemVerticalMargin;
                }
                if ((cWidth + mItemHorizontalMargin) > getWidth()) {
                    cWidth = cWidth - 2 * mItemHorizontalMargin;
                    lengthX = getWidth() - mItemHorizontalMargin;
                    if (i == filterCount - 1) {
                        lengthY += cHeight;
                        childView.layout(lengthX - cWidth, lengthY - 2 * cHeight, lengthX, lengthY);
                        continue;
                    }
                }
                childView.layout(lengthX - cWidth, lengthY - cHeight, lengthX, lengthY);
            }
        }else if(mItemGravity == ITEM_GRAVITY_ALIGN_CENTER){
            int tempLineNumStart = 1;
            int tempX;
            int tempGap;
            View tempView;
            for (int i = 0; i < filterCount; i++) {
                tempView = getChildAt(i);
                cWidth = tempView.getMeasuredWidth();
                cHeight = tempView.getMeasuredHeight();

                lengthX += cWidth + mItemHorizontalMargin;
                tempX = lengthX;
                if (i == 0) {
                    lengthX = cWidth;
                    lengthY = cHeight + mItemVerticalMargin;
                } else if (lengthX > getWidth()) {
                    tempGap = getWidth() - tempX + cWidth + mItemHorizontalMargin;
                    mGapArray.put(tempLineNumStart, tempGap);
                    lengthX = cWidth;
                    lengthY += cHeight + mItemVerticalMargin;
                    tempLineNumStart ++;
                }
                if ((cWidth + mItemHorizontalMargin) > getWidth()) {
                    cWidth = cWidth - 2 * mItemHorizontalMargin;
                    lengthX = getWidth() - mItemHorizontalMargin;

                    // handle the last child view
                    if (i == filterCount - 1) {
                        lengthY += cHeight;
                        tempLineNumStart ++;
                        mLineArray.append(tempLineNumStart, i);
                        mTempRect.left = lengthX - cWidth;
                        mTempRect.top = lengthY - 2 * cHeight;
                        mTempRect.right = lengthX;
                        mTempRect.bottom = lengthY;
                        mLocation.add(new Rect(mTempRect));
                        continue;
                    }
                }

                mLineArray.append(i, tempLineNumStart);
                mTempRect.left = lengthX - cWidth;
                mTempRect.top = lengthY - cHeight;
                mTempRect.right = lengthX;
                mTempRect.bottom = lengthY;
                mLocation.add(new Rect(mTempRect));
            }
            //handle the last column
            tempX = 0;
            int lastNumLine = mLineArray.get(filterCount - 1);
            for (int i = 0; i < filterCount; i++) {
                if(mLineArray.get(i) == lastNumLine) {
                    tempView = getChildAt(i);
                    cWidth = tempView.getMeasuredWidth();
                    tempX += cWidth + mItemHorizontalMargin;
                }
                if (i == filterCount - 1) {
                    tempGap = getWidth() - tempX + mItemHorizontalMargin;
                    mGapArray.put(lastNumLine, tempGap);
                }
            }
            //layout child
            for (int i = 0; i < filterCount; i++) {
                mTempRect = mLocation.get(i);
                tempLineNumStart = mLineArray.get(i);
                tempGap = mGapArray.get(tempLineNumStart);
                tempGap = tempGap / 2;
                tempView = getChildAt(i);
                tempView.layout(mTempRect.left + tempGap, mTempRect.top, mTempRect.right + tempGap, mTempRect.bottom);
            }

        }
    }

    @SuppressWarnings("unused")
    public void addLabel(List<Label> list) {
        for (Label label : list) {
            if (!TextUtils.isEmpty(label.getLabel())) {
                addView(createLabelItemView(label), getChildCount());
            }
        }
    }

    @SuppressWarnings("unused")
    public void clearLabels(){
        removeAllViews();
        mLabelMap.clear();
    }

/*    private void removeLabel() {
        LinearLayout childView = null;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            childView = (LinearLayout) getChildAt(i);
            CrossView ibtnDelete = childView.findViewById(R.id.v_delete);
            if (ibtnDelete != null && ibtnDelete.isSelected()) {
                break;
            }
        }
        if (childView != null) {
            final Label label = mLabelMap.get(((String) childView.getTag()));
            mLabelMap.remove(((String) childView.getTag()));
            removeView(childView);
            if (label != null && mOnRemoveLabelListener != null) {
                mOnRemoveLabelListener.onRemove(label);
            }
        }
    }*/

    @SuppressWarnings("unused")
    public int getLabelCount() {
        return getChildCount();
    }

    protected TextView createLabelItemView(final Label label) {
        TextView labelView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.crumb_item, this, false);
        labelView.setText(label.getLabel());
        labelView.setTextColor(createColorStateList(getItemSelectedColor(), getTextColor()));
        labelView.setText(label.getLabel());

        labelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.setSelected(!v.isSelected());
                if (mOnClickOptionListener != null) {
                    mOnClickOptionListener.onLabelClick(label);
                }
            }
        });
        return labelView;
    }

    private static ColorStateList createColorStateList(int selectedColor, int defaultColor){
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_pressed},
                        new int[]{-android.R.attr.state_pressed},
                        new int[]{}

                },
                new int[]{selectedColor,
                        defaultColor,
                        defaultColor});
    }

    @SuppressWarnings("unused")
    public void setLabelTextColor(int color){
        mLabelTextColor = color;
    }

    public int getLabelTextColor(){
        return mLabelTextColor;
    }

    @SuppressWarnings("unused")
    public void setLabelBackgroundColor(int color){
        mLabelBackgroundColor = color;
    }

    public int getLabelBackgroundColor(){
        return mLabelBackgroundColor;
    }

    public int getTextColor() {
        return mCardTextColor;
    }

    @SuppressWarnings("unused")
    public void setCardTextColor(int cardTextColor) {
        this.mCardTextColor = cardTextColor;
    }

    public int getItemSelectedColor() {
        return mCardTextSelectedColor;
    }

    @SuppressWarnings("unused")
    public void setCardTextSelectedColor(int cardTextSelectedColor) {
        this.mCardTextSelectedColor = cardTextSelectedColor;
    }

    @SuppressWarnings("unused")
    public int getCardBgColor() {
        return mCardBgColor;
    }

    @SuppressWarnings("unused")
    public void setCardBgColor(int cardBgColor) {
        this.mCardBgColor = cardBgColor;
    }

    @SuppressWarnings("unused")
    public int getCardBgSelectedColor() {
        return mCardBgSelectedColor;
    }

    @SuppressWarnings("unused")
    public void setCardBgSelectedColor(int cardBgSelectedColor) {
        this.mCardBgSelectedColor = cardBgSelectedColor;
    }

    public int getLabelCrossBgColor() {
        return mLabelCrossBgColor;
    }

    @SuppressWarnings("unused")
    public void setLabelCrossBgColor(int labelCrossBgColor) {
        this.mLabelCrossBgColor = labelCrossBgColor;
    }

    @SuppressWarnings("unused")
    public void setOnRemoveFilterLabelListener(OnRemoveLabelListener onRemoveLabelListener) {
        mOnRemoveLabelListener = onRemoveLabelListener;
    }

    public interface OnRemoveLabelListener {
        void onRemove(Label label);
    }

    @SuppressWarnings("unused")
    public void setOnClickFilterOptionListener(OnLabelClickListener onLabelClickListener) {
        mOnClickOptionListener = onLabelClickListener;
    }

    public interface OnLabelClickListener {
        void onLabelClick(Label filterOption);
    }

    @SuppressWarnings("unused")
    public interface DisplayLabelFormatter {
        String format(Label label);
    }

    @SuppressWarnings("unused")
    public interface Label {
        int getId();
        String getLabel();
    }
}
