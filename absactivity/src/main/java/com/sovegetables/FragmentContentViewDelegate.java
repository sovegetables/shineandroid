package com.sovegetables;

import android.view.View;

import androidx.annotation.NonNull;

import com.sovegetables.topnavbar.ITopBarAction;

public class FragmentContentViewDelegate implements IContentViewDelegate {

    @NonNull
    @Override
    public View onCreateContentView(View view) {
        return null;
    }

    @NonNull
    @Override
    public ITopBarAction onCreateTopBarAction() {
        return null;
    }
}
