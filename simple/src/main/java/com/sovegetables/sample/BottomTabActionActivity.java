package com.sovegetables.sample;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.sovegatable.bottomtab.BaseTabFragment;
import com.sovegatable.bottomtab.BottomBar;

import java.util.ArrayList;
import java.util.List;

public class BottomTabActionActivity extends com.sovegatable.bottomtab.BottomTabActionActivity {

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
        public void onCreate(@Nullable Bundle savedInstanceState) {
            Log.d(TAG, "onCreate: " + mContent);
            super.onCreate(savedInstanceState);
        }

        @Override
        protected void onCurrent() {
        }

        @Override
        protected void onInitData() {

        }

        @Override
        protected void onInitView(View view) {
        }

        @Override
        protected int getLayoutId() {
            return 0;
        }

        @Override
        public void onStart() {
            Log.d(TAG, "onStart: " + mContent);
            super.onStart();
        }

        @Override
        public void onResume() {
            Log.d(TAG, "onResume: " + mContent);
            super.onResume();
        }

        @Override
        public void onPause() {
            Log.d(TAG, "onPause: " + mContent);
            super.onPause();
        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop: " + mContent);
            super.onStop();
        }

        @Override
        public void onDestroyView() {
            Log.d(TAG, "onDestroyView: " + mContent);
            super.onDestroyView();
        }

        @Override
        public void onAttach(Context context) {
            Log.d(TAG, "onAttach: " + mContent);
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            Log.d(TAG, "onDetach: " + mContent);
            super.onDetach();
        }

        @Override
        public void onDestroy() {
            Log.d(TAG, "onDestroy: " + mContent);
            super.onDestroy();
        }
    }
}
