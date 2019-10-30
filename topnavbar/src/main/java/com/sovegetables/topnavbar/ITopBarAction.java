package com.sovegetables.topnavbar;

public interface ITopBarAction {
    void setUpTopBar(TopBar topBar);
    TopBarItemUpdater leftItemUpdater();
    TopBarItemUpdater findRightItemUpdaterById(int id);
    TopBarUpdater getTopBarUpdater();
}
