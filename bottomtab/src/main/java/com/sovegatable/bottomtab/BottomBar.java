package com.sovegatable.bottomtab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.sovegatable.bottomtab.tab.TabLayout;
import com.sovegetables.viewpageadapter.BaseFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by albert on 2018/2/15.
 *
 */

public class BottomBar {

    private Builder mBuilder;
    private SwitchableViewPager mSwitchableViewPager;

    private BottomBar(Builder builder){
        mBuilder = builder;
    }

    public void attach(TabLayout tabLayout, SwitchableViewPager viewPager){
        mSwitchableViewPager = viewPager;
        final Builder builder = mBuilder;
        final Context context = viewPager.getContext();
        int size = builder.mTabMap.size();
        viewPager.setOffscreenPageLimit(size);
        List<BaseTabFragment> fragments = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Item item = builder.mTabMap.get(i);
            item.position = i;
            tabLayout.addTab(tabLayout.newTab().setTag(item));
            fragments.add(item.mFragment);
        }
        final TabAdapter adapter = new TabAdapter(builder.mFm, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        int tabCount = tabLayout.getTabCount();
        TabLayout.Tab tab;
        View view;
        for (int i = 0; i < tabCount; i++) {
            tab = tabLayout.getTabAt(i);
            view = LayoutInflater.from(context).inflate(builder.mItemDisplay.layout(), null);
            builder.mItemDisplay.onItemDisplay(view, builder.mTabMap.get(i).mIcon,
                    builder.mTabMap.get(i).mTitle);
            if (tab != null) {
                tab.setCustomView(view);
            }
        }
        mSwitchableViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                adapter.getItem(position).current();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        final List<OnTabSelectedListener> onTabSelectedListeners = builder.mOnTabSelectedListeners;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public boolean onTabPreSelected(TabLayout.Tab tab) {
                for (OnTabSelectedListener l: onTabSelectedListeners){
                     if(l.onTabPreSelected((Item) tab.getTag())){
                        return true;
                     }
                }
                return false;
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (OnTabSelectedListener l: onTabSelectedListeners){
                    l.onTabSelected((Item) tab.getTag());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                for (OnTabSelectedListener l: onTabSelectedListeners){
                    l.onTabUnselected((Item) tab.getTag());
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                for (OnTabSelectedListener l: onTabSelectedListeners){
                    l.onTabReselected((Item) tab.getTag());
                }
            }
        });
    }

    public void setCurrent(int position) {
        mSwitchableViewPager.setCurrentItem(position);
    }

    public int getCurrentPosition() {
        return mSwitchableViewPager.getCurrentItem();
    }

    /**
     * Tab Item 展示接口
     */
    public interface ItemDisplay {
        @LayoutRes
        int layout();
        void onItemDisplay(View view, @DrawableRes int icon, @StringRes int title);
    }

    public static class TabAdapter extends BaseFragmentPagerAdapter<BaseTabFragment> {

        public TabAdapter(FragmentManager fm, @NonNull List<BaseTabFragment> fragments) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            setData(fragments);
        }
    }

    public interface OnTabSelectedListener{
        /**
         * Called when a tab pre selected.
         * @param tab  Tab
         * @return true: not enters the selected state.
         */
        public boolean onTabPreSelected(Item tab);

        /**
         * Called when a tab enters the selected state.
         *
         * @param tab The tab that was selected
         */
        public void onTabSelected(Item tab);

        /**
         * Called when a tab exits the selected state.
         *
         * @param tab The tab that was unselected
         */
        public void onTabUnselected(Item tab);

        /**
         * Called when a tab that is already selected is chosen again by the user. Some applications
         * may use this action to return to the top level of a category.
         *
         * @param tab The tab that was reselected.
         */
        public void onTabReselected(Item tab);
    }

    public abstract static class OnTabSelectedListenerAdapter implements OnTabSelectedListener{

        @Override
        public boolean onTabPreSelected(Item tab) {
            return false;
        }

        @Override
        public void onTabSelected(Item tab) {

        }

        @Override
        public void onTabUnselected(Item tab) {

        }

        @Override
        public void onTabReselected(Item tab) {

        }
    }

    public static class Builder{

        private int mPos = 0;
        private List<OnTabSelectedListener> mOnTabSelectedListeners = new ArrayList<>();
        private ArrayMap<Integer, Item> mTabMap = new ArrayMap<>();
        private FragmentManager mFm;
        private ItemDisplay mItemDisplay = new ItemDisplay() {
            @Override
            public int layout() {
                return R.layout.item_bottom_tab;
            }

            @Override
            public void onItemDisplay(View view, int icon, int title) {
                ((ImageView) view.findViewById(R.id.tv_tab_icon)).setImageResource(icon);
                TextView tabTitle = view.findViewById(R.id.tv_tab_title);
                tabTitle.setVisibility(title == Item.NO_TITLE? View.GONE : View.VISIBLE);
                if(title != Item.NO_TITLE){
                    tabTitle.setText(title);
                }
            }
        };

        public Builder(FragmentManager fm){
            mFm = fm;
        }

        public Builder addonTabSelectedListener(OnTabSelectedListener onTabSelectedListener){
            mOnTabSelectedListeners.add(onTabSelectedListener);
            return this;
        }

        public Builder itemDisplay(ItemDisplay itemDisplay){
            mItemDisplay = itemDisplay;
            return this;
        }

        public Builder addItems(List<Item> items){
            for (Item i:items) {
                addItem(i);
            }
            return this;
        }

        public Builder addItem(Item item){
            mTabMap.put(mPos, item);
            mPos ++;
            return this;
        }

        public BottomBar build(){
            return new BottomBar(this);
        }
    }

    public final static class Item{
        public BaseTabFragment mFragment;
        @DrawableRes
        public int mIcon;
        @StringRes
        public int mTitle;
        private int position;

        public static final int NO_TITLE = -1;

        /**
         *
         * @param fragment tab fragment
         * @param icon Tab Icon
         */
        public Item(BaseTabFragment fragment, int icon) {
            this(fragment, icon, NO_TITLE);
        }

        /**
         *
         * @param fragment tab fragment
         * @param icon Tab Icon
         * @param title Title
         */
        public Item(BaseTabFragment fragment, @DrawableRes int icon, @StringRes int title) {
            mFragment = fragment;
            mIcon = icon;
            mTitle = title;
        }

        public final int getPosition() {
            return position;
        }
    }

}
