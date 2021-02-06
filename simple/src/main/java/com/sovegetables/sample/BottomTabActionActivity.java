package com.sovegetables.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sovegatable.bottomtab.BaseTabFragment;
import com.sovegatable.bottomtab.BottomBar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BottomTabActionActivity extends com.sovegatable.bottomtab.BottomTabActionActivity {

    private static final String TAG = "BottomTabActionActivity";

    private List<BottomBar.Item> bottomBarItems() {
        List<BottomBar.Item> items = new ArrayList<>();
        items.add(new BottomBar.Item(TabFragment.newInstance("首页"), R.drawable.selector_tab_home));
        items.add(new BottomBar.Item(TabFragment.newInstance("联系人"), R.drawable.selector_tab_contacts));
        items.add(new BottomBar.Item(TabFragment.newInstance("我"), R.drawable.selector_tab_contacts));
        return items;
    }

    @Override
    protected BottomBar createBottomBar() {
        return new BottomBar.Builder(getSupportFragmentManager())
                .addItems(bottomBarItems())
                .addonTabSelectedListener(new BottomBar.OnTabSelectedListener() {
                    @Override
                    public boolean onTabPreSelected(BottomBar.Item tab) {
                        Log.d(TAG, "onTabPreSelected: " + tab);
                        return false;
                    }

                    @Override
                    public void onTabSelected(BottomBar.Item tab) {
                        Log.d(TAG, "onTabSelected: "+ tab);
                    }

                    @Override
                    public void onTabUnselected(BottomBar.Item tab) {
                        Log.d(TAG, "onTabUnselected: "+ tab);
                    }

                    @Override
                    public void onTabReselected(BottomBar.Item tab) {
                        Log.d(TAG, "onTabReselected: "+ tab);
                    }
                })
                .build();
    }

    public static class TabFragment extends BaseTabFragment {

        private static final String TAG = "TabFragment";
        private String mContent;

        public static TabFragment newInstance(String content) {
            Bundle args = new Bundle();
            TabFragment fragment = new TabFragment();
            fragment.mContent = content;
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        protected void onCreateViewAfterViewStubInflated(@NotNull View inflatedView, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        }

        @Override
        protected int layoutIdRes() {
            return R.layout.fragment_item_tab;
        }

        @Override
        public void onCurrent() {

        }
    }
}
