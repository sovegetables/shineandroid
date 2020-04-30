package com.sovegetables.topnavbar;

import android.os.Build;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;

import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = Build.VERSION_CODES.P)
public class TopBarUpdaterTest {

    @NotNull
    private TopBarUpdater createTopBarUpdater() {
        return new TopBarUpdater.TopBarUpdaterImpl(new ActionBarView(ApplicationProvider.getApplicationContext()));
    }
}
