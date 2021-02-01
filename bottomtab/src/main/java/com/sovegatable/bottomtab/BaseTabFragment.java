package com.sovegatable.bottomtab;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sovegetables.viewstubfragment.ViewStubFragment;

public abstract class BaseTabFragment extends ViewStubFragment implements BottomBarContentLifecycle{

    protected IBottomTabAction mIBottomTabAction;

    @Override
    @CallSuper
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IBottomTabAction){
            mIBottomTabAction = ((IBottomTabAction) context);
        }else {
            throw new IllegalArgumentException(context.getClass().getName() + " must implements IBottomTabAction !");
        }
    }

    @Override
    public final void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mIBottomTabAction.getCurrentPosition() == 0){
            onCurrent();
        }
    }

    public abstract void onCurrent();
}
