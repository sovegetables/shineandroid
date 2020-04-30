package com.sovegetables.topnavbar;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class ActionBarViewTest {

    private static final String TITLE = "Title";
    private static final int TITLE_COLOR = Color.BLACK;
    private static final int TOP_BAR_COLOR = Color.WHITE;
    private static final int TITLE_COLOR_RES = android.R.color.black;
    private static final int TOP_BAR_COLOR_RES = android.R.color.white;

    private ActivityController<AppCompatActivity> activityController;
    private Activity activity;
    private ActionBarView actionBarView;

    private static final int left_icon_id = android.R.drawable.btn_plus;
    private static final int left_icon = android.R.drawable.btn_dropdown;
    private static final int right_icon = android.R.drawable.btn_dialog;
    private static final int right_icon_id = android.R.drawable.title_bar_tall;

    @Before
    public void setUp(){
        activityController = Robolectric.buildActivity(AppCompatActivity.class);
        activity = activityController.get();
        activity.setTheme(R.style.Theme_AppCompat);
        activityController.create();
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        activity.setContentView(frameLayout);
        actionBarView = new ActionBarView(activity);
        frameLayout.addView(actionBarView);
    }

    /**
     * 测试TopBar, 左item
     */
    @Test
    public void test_actionbar_view_left_item(){
        //左icon Item + Title
        View.OnClickListener leftOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .icon(left_icon)
                .listener(leftOnClickListener)
                .build(activity, left_icon_id);

        TopBar topBar = new TopBar.Builder()
                .left(left)
                .title(TITLE)
                .titleColor(TITLE_COLOR)
                .build(activity);
        actionBarView.setUpTopBar(topBar);
        activityController.start().postCreate(null).resume().visible();

        TextView titleView = actionBarView.getTitleView();
        CharSequence title = titleView.getText();
        Assert.assertEquals(title, TITLE);
        Assert.assertEquals(ColorStateList.valueOf(TITLE_COLOR), titleView.getTextColors());

        TextView leftActionView = actionBarView.getLeftActionView();
        Assert.assertEquals(shadowOf(leftActionView.getCompoundDrawables()[0]).getCreatedFromResId(), shadowOf(ContextCompat.getDrawable(activity, left_icon)).getCreatedFromResId());

        leftActionView.performClick();
        Mockito.verify(leftOnClickListener).onClick(leftActionView);
    }

    @Test
    public void test_actionbar_view_only_one_right_item(){
        //左Icon Item + Title +  右Icon Item
        View.OnClickListener leftOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .listener(leftOnClickListener)
                .build(activity, left_icon_id);

        View.OnClickListener rightOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem right = new TopBarItem.Builder()
                .icon(right_icon)
                .listener(rightOnClickListener)
                .build(activity, right_icon_id);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .right(right)
                .build(activity);

        actionBarView.setUpTopBar(topBar);
        activityController.start().postCreate(null).resume().visible();

        TextView titleView = actionBarView.getTitleView();
        CharSequence title = titleView.getText();
        Assert.assertEquals(title, "");

        TextView leftActionView = actionBarView.getLeftActionView();
        leftActionView.performClick();
        Mockito.verify(leftOnClickListener).onClick(leftActionView);

        TextView rightTextView = actionBarView.findRightActionViewById(right_icon_id);
        rightTextView.performClick();
        Mockito.verify(rightOnClickListener).onClick(rightTextView);
    }

    @Test
    public void test_actionbar_view_other_constructor(){

    }

    @Test
    public void test_action_view_top_bar_updater(){
        View.OnClickListener leftOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .listener(leftOnClickListener)
                .build(activity, left_icon_id);

        View.OnClickListener rightOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem right = new TopBarItem.Builder()
                .icon(right_icon)
                .listener(rightOnClickListener)
                .build(activity, right_icon_id);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .right(right)
                .build(activity);

        actionBarView.setUpTopBar(topBar);
        activityController.start().postCreate(null).resume().visible();

        actionBarView.getTopBarUpdater()
                .title("New Title")
                .titleColor(Color.GREEN)
                .topBarColor(Color.CYAN)
                .update();

        Assert.assertEquals(actionBarView.getTitleView().getText(), "New Title");
        Assert.assertEquals(actionBarView.getTitleView().getTextColors(), ColorStateList.valueOf(Color.GREEN));
//        Assert.assertEquals(actionBarView.getTitleView().getBackground(),
//                new ColorDrawable(Color.CYAN));

        actionBarView.getTopBarUpdater()
                .titleRes(android.R.string.dialog_alert_title)
                .titleColorRes(android.R.color.holo_green_dark)
                .update();
        Assert.assertEquals(actionBarView.getTitleView().getText(),
                actionBarView.getContext().getString(android.R.string.dialog_alert_title));
        Assert.assertEquals(actionBarView.getTitleView().getTextColors(),
                ColorStateList.valueOf(ContextCompat.getColor(actionBarView.getContext(), android.R.color.holo_green_dark)));

        actionBarView.leftItemUpdater()
                .iconRes(android.R.drawable.btn_dropdown)
                .textColor(Color.GRAY)
                .enable(false)
                .visibility(View.GONE)
                .update();

        Assert.assertEquals(actionBarView.getLeftActionView().getTextColors(),
                ColorStateList.valueOf(Color.GRAY));
        Assert.assertEquals(shadowOf(actionBarView.getLeftActionView().getCompoundDrawables()[0]).getCreatedFromResId(),
                shadowOf(ContextCompat.getDrawable(actionBarView.getContext(), android.R.drawable.btn_dropdown)).getCreatedFromResId());
        Assert.assertFalse(actionBarView.getLeftActionView().isEnabled());
        Assert.assertEquals(actionBarView.getLeftActionView().getVisibility(), View.GONE);

        actionBarView.findRightItemUpdaterById(right_icon_id)
                .text("Action Right")
                .textColorRes(android.R.color.holo_orange_dark)
                .enable(true)
                .visibility(View.VISIBLE)
                .enable(true)
                .update();
        TextView rightActionViewById = actionBarView.findRightActionViewById(right_icon_id);
        Assert.assertEquals(rightActionViewById.getText(), "Action Right");
        Assert.assertEquals(rightActionViewById.getTextColors(),
                ColorStateList.valueOf(ContextCompat.getColor(actionBarView.getContext(), android.R.color.holo_orange_dark)));
        Assert.assertTrue(rightActionViewById.isEnabled());
        Assert.assertEquals(rightActionViewById.getVisibility(), View.VISIBLE);

    }

    @Test
    public void test_no_top_bar(){
        actionBarView.setUpTopBar(TopBar.NO_ACTION_BAR);
        activityController.start().postCreate(null).resume().visible();
        Assert.assertEquals(actionBarView.getVisibility(), View.GONE);

        View.OnClickListener leftOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .listener(leftOnClickListener)
                .build(activity, left_icon_id);
        View.OnClickListener rightOnClickListener = Mockito.mock(View.OnClickListener.class);
        TopBarItem right = new TopBarItem.Builder()
                .icon(right_icon)
                .listener(rightOnClickListener)
                .build(activity, right_icon_id);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .right(right)
                .build(activity);
        ITopBarAction topBarAction = ITopBarAction.NO_TOP_BAR_ACTION;
        topBarAction.setUpTopBar(topBar);
        TopBarItemUpdater leftItemUpdater = topBarAction.leftItemUpdater();
        TopBarUpdater topBarUpdater = topBarAction.getTopBarUpdater();
        TopBarItemUpdater topBarItemUpdater = topBarAction.findRightItemUpdaterById(right_icon_id);

    }
}
