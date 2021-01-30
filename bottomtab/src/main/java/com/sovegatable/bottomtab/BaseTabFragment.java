package com.sovegatable.bottomtab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseTabFragment extends Fragment implements BottomBarContentLifecycle{

    @NonNull
    protected IBottomTabAction mIBottomTabAction;
    private boolean mLoaded;

    @Override
    @CallSuper
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IBottomTabAction){
            mIBottomTabAction = ((IBottomTabAction) context);
        }else {
            throw new IllegalArgumentException(context.getClass().getName() + " must implements IBottomTabAction !");
        }
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_tab_fragment, container, false);
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mIBottomTabAction.getCurrentPosition() == 0){
            current();
        }
    }

    @Override
    @CallSuper
    public void current() {
        if (!mLoaded && loadRealLayout()) { // 懒加载
            mLoaded = true;
        }
        onCurrent();
    }

    protected abstract void onCurrent();

    private boolean loadRealLayout() {
        ViewGroup root = (ViewGroup) getView();
        if (root != null) {
            root.removeAllViewsInLayout();
            int layoutId = getLayoutId();
            if(layoutId > 0){
                View child = View.inflate(root.getContext(), layoutId, root);
                onInitView(child);
                onInitData();
            }
        }
        return root != null;
    }

    protected abstract void onInitData();

    protected abstract void onInitView(View view);

    @LayoutRes
    protected abstract int getLayoutId();
}
