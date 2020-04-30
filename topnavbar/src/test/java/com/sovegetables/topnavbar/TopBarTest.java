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
    private static final int right_text_color_res = android.R.color.black;
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

    @Test
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
                .visibility(TopBarItem.Visibility.GONE)
                .build(application, right_icon_id);
        TopBarItem right2 = new TopBarItem.Builder()
                .text(right_text)
                .textColorRes(right_text_color_res)
                .visibility(TopBarItem.Visibility.VISIBLE)
                .listener(rightClickListener2)
                .build(application, right_icon_id_2);

        ArrayList<TopBarItem> items = new ArrayList<>(2);
        items.add(right);
        items.add(right2);
        TopBar topBar = new TopBar.Builder()
                .left(left)
                .rights(items)
                .title(R.string.app_name)
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
        Assert.assertEquals(rightItem.visibility(), TopBarItem.Visibility.GONE);
        Assert.assertEquals(rightItem.listener(), rightClickListener);
        Assert.assertEquals(rightItem.id(), right_icon_id);
        Assert.assertEquals(shadowOf(rightItem.icon()).getCreatedFromResId(), shadowOf(ContextCompat.getDrawable(application, right_icon)).getCreatedFromResId());

        TopBarItem rightItem2 = rights.get(1);
        Assert.assertEquals(rightItem2, right2);
        Assert.assertEquals(rightItem2.visibility(), TopBarItem.Visibility.VISIBLE);
        Assert.assertEquals(rightItem2.listener(), rightClickListener2);
        Assert.assertEquals(rightItem2.id(), right_icon_id_2);
        Assert.assertEquals(rightItem2.text(), right_text);
        Assert.assertEquals(rightItem2.textColor(), application.getColor(right_text_color_res));

        Assert.assertEquals(leftItem, left);
        Assert.assertEquals(topBar.title(), application.getString(R.string.app_name));
        Assert.assertEquals(topBar.titleColor(), title_color);
        Assert.assertEquals(topBar.topBarColor(), top_bar_color);
    }

    @Test
    public void test_no_top_bar(){
        TopBar noActionBar = TopBar.NO_ACTION_BAR;
        Assert.assertNull(noActionBar.title());
        Assert.assertEquals(noActionBar.left(), TopBarItem.EMPTY);
        List<TopBarItem> rights = noActionBar.rights();
        Assert.assertTrue(rights.isEmpty());
        Assert.assertEquals(noActionBar.titleColor(), Color.BLACK);
        Assert.assertEquals(noActionBar.topBarColor(), Color.WHITE);
    }


    @Test
    public void test_default_top_bar_params(){
        Application application = ApplicationProvider.getApplicationContext();
        TopBar topBar = new TopBar.Builder()
                .build(application);
        Assert.assertEquals(topBar.titleColor(), Color.BLACK);
        Assert.assertEquals(topBar.topBarColor(), Color.WHITE);
        Assert.assertNull(topBar.title());
    }

    @Test
    public void test_default_top_item_params(){
        Application application = ApplicationProvider.getApplicationContext();
        TopBarItem item = new TopBarItem.Builder()
                .build(application, left_icon_id);
        Assert.assertEquals(item.textColor(), Color.WHITE);
        Assert.assertEquals(item.visibility(), TopBarItem.Visibility.VISIBLE);
        Assert.assertNull(item.icon());
        Assert.assertNull(item.text());
    }

    @Test
    public void test_right_exceed_max_count(){
        Application application = ApplicationProvider.getApplicationContext();
        TopBarItem left1 = new TopBarItem.Builder()
                .build(application, 0);
        TopBarItem left2 = new TopBarItem.Builder()
                .build(application, 1);
        TopBarItem left3 = new TopBarItem.Builder()
                .build(application, 3);
        TopBarItem left4 = new TopBarItem.Builder()
                .build(application, 4);

        boolean flag = false;
        try {
            TopBar topBar = new TopBar.Builder()
                    .right(left1)
                    .right(left2)
                    .right(left3)
                    .right(left4)
                    .build(application);
        } catch (Exception e) {
            flag = true;
            Assert.assertEquals(e.getMessage(),TopBar.Builder.RIGHT_ITEM_COUNT_CAN_T_EXCEED_3);
        }

        Assert.assertTrue(flag);

        try {
            flag = false;
            ArrayList<TopBarItem> items = new ArrayList<>();
            items.add(left1);
            items.add(left2);
            items.add(left3);
            items.add(left4);
            TopBar topBar = new TopBar.Builder()
                    .rights(items)
                    .build(application);
        } catch (Exception e) {
            flag = true;
            Assert.assertEquals(e.getMessage(),TopBar.Builder.RIGHT_ITEM_COUNT_CAN_T_EXCEED_3);
        }
        Assert.assertTrue(flag);

    }

    @Test
    public void test_top_bar_item_duplicate_id(){
        Application application = ApplicationProvider.getApplicationContext();
        boolean flag = false;
        try {
            new TopBar.Builder()
                    .right(new TopBarItem.Builder().build(application, left_icon_id))
                    .right(new TopBarItem.Builder().build(application, left_icon_id))
                    .build(application);
        } catch (Exception e) {
            flag = true;
            Assert.assertEquals(e.getMessage(), TopBar.DUPLICATE_ID);
        }
        Assert.assertTrue(flag);
    }
}
