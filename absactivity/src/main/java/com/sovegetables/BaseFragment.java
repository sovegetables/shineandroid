package com.sovegetables;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sovegetables.topnavbar.ITopBarAction;
import com.sovegetables.topnavbar.TopBar;

public abstract class BaseFragment extends Fragment {

    private static IContentViewDelegate sDefaultContentViewDelegate;

    @NonNull
    protected ITopBarAction topBarAction;

    public static void setsDefaultContentViewDelegate(IContentViewDelegate defaultContentViewDelegate) {
        sDefaultContentViewDelegate = defaultContentViewDelegate;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = onBaseCreateView(inflater, container, savedInstanceState);
        sDefaultContentViewDelegate = getContentViewDelegate();
        if(sDefaultContentViewDelegate == null){
            sDefaultContentViewDelegate = new DefaultIContentViewDelegate();
        }
        View realContentView = sDefaultContentViewDelegate.onCreateContentView(contentView);
        if(realContentView == null){
            throw new IllegalArgumentException("IContentViewDelegate's onCreateContentView method can't return null !!!");
        }
        topBarAction = sDefaultContentViewDelegate.onCreateTopBarAction();
        if(topBarAction == null){
            throw new IllegalArgumentException("IContentViewDelegate's onCreateTopBarAction method can't return null !!!");
        }
        topBarAction.setUpTopBar(getTopBar());
        return realContentView;
    }

    protected TopBar getTopBar(){
        return TopBar.NO_ACTION_BAR;
    }

    protected IContentViewDelegate getContentViewDelegate() {
        return null;
    }

    protected View onBaseCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return null;
    }
}
