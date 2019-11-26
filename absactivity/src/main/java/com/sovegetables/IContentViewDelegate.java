package com.sovegetables;

import android.view.View;

import androidx.annotation.NonNull;

import com.sovegetables.topnavbar.ITopBarAction;

public interface IContentViewDelegate {
    @NonNull View onCreateContentView(View view);
    @NonNull ITopBarAction onCreateTopBarAction();
}
