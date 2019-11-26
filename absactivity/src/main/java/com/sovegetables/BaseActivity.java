package com.sovegetables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.sovegetables.topnavbar.ITopBarAction;
import com.sovegetables.topnavbar.TopBar;
import com.sovegetables.topnavbar.TopBarItem;

public abstract class BaseActivity extends AppCompatActivity {

    static IContentViewDelegate defaultContentViewDelegate;
    @DrawableRes static int leftTopItemIconRes;

    public static void setDefaultContentViewDelegate(IContentViewDelegate defaultContentViewDelegate) {
        BaseActivity.defaultContentViewDelegate = defaultContentViewDelegate;
    }

    public static void setLeftTopIcon(@DrawableRes int res){
        leftTopItemIconRes = res;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @NonNull
    protected ITopBarAction topBarAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        defaultContentViewDelegate = getContentViewDelegate();
        if(defaultContentViewDelegate == null){
            defaultContentViewDelegate = new DefaultIContentViewDelegate();
        }
        View realContentView = defaultContentViewDelegate.onCreateContentView(view);
        if(realContentView == null){
            throw new IllegalArgumentException("IContentViewDelegate's onCreateContentView method can't return null !!!");
        }
        super.setContentView(realContentView);
        topBarAction = defaultContentViewDelegate.onCreateTopBarAction();
        if(topBarAction == null){
            throw new IllegalArgumentException("IContentViewDelegate's onCreateTopBarAction method can't return null !!!");
        }
        topBarAction.setUpTopBar(getTopBar());
    }

    protected TopBar getTopBar(){
        return TopBar.NO_ACTION_BAR;
    }

    protected void onLeftTopBarItemClick(View view){
        onBackPressed();
    }

    protected final TopBar title(CharSequence title) {
        TopBarItem leftItem = new TopBarItem
                .Builder()
                .listener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftTopBarItemClick(v);
                    }
                })
                .icon(leftTopItemIconRes)
                .build(this, 0);
        return new TopBar.Builder()
                .left(leftItem)
                .title(title)
                .build(this);
    }



    protected final TopBar.Builder titleBuilder(CharSequence title){
        TopBarItem leftItem = new TopBarItem
                .Builder()
                .listener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftTopBarItemClick(v);
                    }
                })
                .icon(leftTopItemIconRes)
                .build(this, 0);
        return new TopBar.Builder()
                .left(leftItem)
                .title(title);
    }

    protected <T extends IContentViewDelegate> T getContentViewDelegate(){
        return null;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        throw new UnsupportedOperationException("Not support!");
    }
}
