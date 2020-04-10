package com.sovegetables.topnavbar;

import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class TopBarTest {

    private static final int left_icon = android.R.drawable.btn_dropdown;
    private static final int left_icon_id = android.R.drawable.btn_plus;
    private static final String left_text = "Left Text";
    private static final int left_text_color = Color.BLACK;

    private static final String right_text = "Right Text";
    private static final int right_text_color = Color.BLACK;
    private static final int right_icon = android.R.drawable.btn_dialog;
    private static final int right_icon_id = android.R.drawable.title_bar_tall;
    private static final int right_icon_id_2 = android.R.integer.config_shortAnimTime;

    private static final String title = "title";
    private static final int title_color = Color.BLACK;
    private static final int top_bar_color = Color.WHITE;
    private static final int title_color_res = android.R.color.black;
    private static final int top_bar_res = android.R.color.white;

    @Test
    public void test_top_bar_builder(){
        // left icon, one right icon and title
        Application application = ApplicationProvider.getApplicationContext();
        View.OnClickListener leftClickListener = mock(View.OnClickListener.class);
        View.OnClickListener rightClickListener = mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .icon(left_icon)
                .listener(leftClickListener)
                .build(application, left_icon_id);
        TopBarItem right = new TopBarItem.Builder()
                .icon(right_icon)
                .listener(rightClickListener)
                .build(application, right_icon_id);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .right(right)
                .title(title)
                .titleColor(title_color)
                .topBarColor(top_bar_color)
                .build(application);

        TopBarItem leftItem = topBar.left();
        Assert.assertEquals(shadowOf(leftItem.icon()).getCreatedFromResId(), shadowOf(ContextCompat.getDrawable(application, left_icon)).getCreatedFromResId());
        Assert.assertEquals(leftItem.id(), left_icon_id);
        Assert.assertEquals(leftItem.listener(), leftClickListener);

        List<TopBarItem> rights = topBar.rights();
        TopBarItem rightItem = rights.get(0);
        Assert.assertEquals(rightItem, right);
        Assert.assertEquals(rightItem.listener(), rightClickListener);
        Assert.assertEquals(rightItem.id(), right_icon_id);
        Assert.assertEquals(shadowOf(rightItem.icon()).getCreatedFromResId(), shadowOf(ContextCompat.getDrawable(application, right_icon)).getCreatedFromResId());

        Assert.assertEquals(leftItem, left);
        Assert.assertEquals(topBar.title(), title);
        Assert.assertEquals(topBar.titleColor(), title_color);
        Assert.assertEquals(topBar.topBarColor(), top_bar_color);
    }

    public void test_top_bar_builder_v2(){
        // left text, one right text, one right icon and title
        Application application = ApplicationProvider.getApplicationContext();
        View.OnClickListener leftClickListener = mock(View.OnClickListener.class);
        View.OnClickListener rightClickListener = mock(View.OnClickListener.class);
        View.OnClickListener rightClickListener2 = mock(View.OnClickListener.class);
        TopBarItem left = new TopBarItem.Builder()
                .text(left_text)
                .textColor(left_text_color)
                .listener(leftClickListener)
                .build(application, left_icon_id);
        TopBarItem right = new TopBarItem.Builder()
                .icon(right_icon)
                .listener(rightClickListener)
                .build(application, right_icon_id);
        TopBarItem right2 = new TopBarItem.Builder()
                .text(right_text)
                .textColor(right_text_color)
                .listener(rightClickListener2)
                .build(application, right_icon_id_2);

        ArrayList<TopBarItem> items = new ArrayList<>(2);
        items.add(right);
        items.add(right2);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .rights(items)
                .title(title)
                .titleColorRes(title_color_res)
                .topBarColorRes(top_bar_res)
                .build(application);

        TopBarItem leftItem = topBar.left();
        Assert.assertEquals(leftItem.text(), left_text);
        Assert.assertEquals(leftItem.textColor(), left_text_color);
        Assert.assertEquals(leftItem.id(), left_icon_id);
        Assert.assertEquals(leftItem.listener(), leftClickListener);

        List<TopBarItem> rights = topBar.rights();
        TopBarItem rightItem = rights.get(0);
        Assert.assertEquals(rightItem, right);
        Assert.assertEquals(rightItem.listener(), rightClickListener);
        Assert.assertEquals(rightItem.id(), right_icon_id);
        Assert.assertEquals(shadowOf(rightItem.icon()).getCreatedFromResId(), shadowOf(ContextCompat.getDrawable(application, right_icon)).getCreatedFromResId());

        TopBarItem rightItem2 = rights.get(1);
        Assert.assertEquals(rightItem2, right2);
        Assert.assertEquals(rightItem2.listener(), rightClickListener2);
        Assert.assertEquals(rightItem2.id(), right_icon_id_2);
        Assert.assertEquals(rightItem2.text(), right_text);
        Assert.assertEquals(rightItem2.textColor(), right_text_color);

        Assert.assertEquals(leftItem, left);
        Assert.assertEquals(topBar.title(), title);
        Assert.assertEquals(topBar.titleColor(), title_color);
        Assert.assertEquals(topBar.topBarColor(), top_bar_color);
    }

    public void test_no_top_bar(){
        TopBar noActionBar = TopBar.NO_ACTION_BAR;
        Assert.assertNull(noActionBar.title());
        Assert.assertEquals(noActionBar.left(), TopBarItem.EMPTY);
        List<TopBarItem> rights = noActionBar.rights();
        Assert.assertTrue(rights.isEmpty());
        Assert.assertEquals(noActionBar.titleColor(), Color.BLACK);
        Assert.assertEquals(noActionBar.topBarColor(), Color.WHITE);
    }


}
