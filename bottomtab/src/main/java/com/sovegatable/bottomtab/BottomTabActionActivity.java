package com.sovegatable.bottomtab;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;

import com.sovegatable.bottomtab.tab.TabLayout;
import com.sovegetables.BaseActivity;


/**
 * Created by albert on 2018/2/15.
 * // TODO: 2018/11/30
 * 底部Tab的Activity
 * 1. Tab: 灵活数量配置; Icon配置, 文字配置（文字可以不显示）
 * 2. 每个tab懒加载
 * 3. 跳转到某一个Tab
 * 4. 拦截换tab
 * 5. 监听tab切换的状态，哪个tab当前被选中，离开了哪个tab
 */

public abstract class BottomTabActionActivity extends BaseActivity implements IBottomTabAction {

    protected SwitchableViewPager mVp;
    protected BottomBar mBottomBar;

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        TabLayout tab = findViewById(R.id.tab);
        mVp = findViewById(R.id.vp);
        mBottomBar = createBottomBar();
        mBottomBar.attach(tab, mVp);
    }

    protected abstract BottomBar createBottomBar();

    public final void setCurrent(int position){
        mBottomBar.setCurrent(position);
    }

    @Override
    public final int getCurrentPosition() {
        return mBottomBar.getCurrentPosition();
    }
}
