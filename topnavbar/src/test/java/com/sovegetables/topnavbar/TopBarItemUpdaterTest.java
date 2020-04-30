package com.sovegetables.topnavbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class TopBarItemUpdaterTest {

    private static final String TEXT = "Text";
    private static final int COLOR = Color.BLUE;
    private static final int COLOR_RES = android.R.color.holo_blue_light;
    private static final int ICON_RES = android.R.drawable.btn_dialog;

    @Before
    public void setUp(){
    }

    @Test
    public void test_TopBarItemUpdater(){
        Context applicationContext = ApplicationProvider.getApplicationContext();
        TopBarItemUpdater.TopBarItemUpdaterImpl topBarItemUpdater = createTopBarItemUpdater();
        topBarItemUpdater.text(TEXT)
                .enable(false)
                .iconRes(ICON_RES)
                .textColor(COLOR)
                .visibility(View.VISIBLE)
                .update();

        Assert.assertFalse(topBarItemUpdater.textView.isEnabled());
        Assert.assertEquals(topBarItemUpdater.textView.getText(), TEXT);
        Assert.assertEquals(topBarItemUpdater.textView.getVisibility(), View.VISIBLE);
        Assert.assertEquals(topBarItemUpdater.textView.getTextColors(), ColorStateList.valueOf(COLOR));
        Assert.assertEquals(shadowOf(topBarItemUpdater.textView.getCompoundDrawables()[0]).getCreatedFromResId(),
                shadowOf(ContextCompat.getDrawable(applicationContext, ICON_RES)).getCreatedFromResId());

        TopBarItemUpdater.TopBarItemUpdaterImpl topBarItemUpdater2 = createTopBarItemUpdater();
        topBarItemUpdater2.textColorRes(COLOR_RES)
                .enable(true)
                .icon(ContextCompat.getDrawable(applicationContext, ICON_RES))
                .visibility(View.GONE)
                .update();
        Assert.assertTrue(topBarItemUpdater2.textView.isEnabled());
        Assert.assertEquals(topBarItemUpdater2.textView.getVisibility(), View.GONE);
        Assert.assertEquals(topBarItemUpdater2.textView.getTextColors(), ColorStateList.valueOf(ContextCompat.getColor(applicationContext, COLOR_RES)));
        Assert.assertEquals(shadowOf(topBarItemUpdater2.textView.getCompoundDrawables()[0]).getCreatedFromResId(),
                shadowOf(ContextCompat.getDrawable(applicationContext, ICON_RES)).getCreatedFromResId());
    }

    @Test
    public void test_default_updater_value(){
        TopBarItemUpdater mock = Mockito.mock(TopBarItemUpdater.class);
        mock.update();
        Mockito.verify(mock).update(Mockito.any(TopBarItemUpdater.UpdaterParam.class));

        Context applicationContext = ApplicationProvider.getApplicationContext();
        TopBarItemUpdater.TopBarItemUpdaterImpl topBarItemUpdater = createTopBarItemUpdater();
        topBarItemUpdater.update();
        Assert.assertTrue(topBarItemUpdater.textView.isEnabled());
        Assert.assertEquals(topBarItemUpdater.textView.getVisibility(), View.VISIBLE);
    }

    @NotNull
    private TopBarItemUpdater.TopBarItemUpdaterImpl createTopBarItemUpdater() {
        return new TopBarItemUpdater.TopBarItemUpdaterImpl(new TextView(ApplicationProvider.getApplicationContext()));
    }


}
