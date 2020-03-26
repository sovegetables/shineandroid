package com.sovegetables;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.sovegetables.topnavbar.ActionBarView;
import com.sovegetables.topnavbar.ITopBarAction;

public class DefaultIContentView implements IContentView {

    private ActionBarView actionBarView;
    private FrameLayout flContent;

    @NonNull
    @Override
    public View onCreateContentView(View view) {
        View rootView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_activity_base_delegate, null);
        actionBarView = rootView.findViewById(R.id.delegate_action_bar);
        flContent = rootView.findViewById(R.id.fl_base_delegate_content);
        flContent.addView(view);
        return rootView;
    }

    @NonNull
    @Override
    public ITopBarAction onCreateTopBarAction() {
        return actionBarView;
    }
}
