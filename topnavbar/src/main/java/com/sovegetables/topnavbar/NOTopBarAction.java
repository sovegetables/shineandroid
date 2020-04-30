package com.sovegetables.topnavbar;

class NOTopBarAction implements ITopBarAction{
    @Override
    public void setUpTopBar(TopBar topBar) {
    }

    @Override
    public TopBarItemUpdater leftItemUpdater() {
        return new TopBarItemUpdater() {
            @Override
            void update(UpdaterParam updaterParam) {
            }
        };
    }

    @Override
    public TopBarItemUpdater findRightItemUpdaterById(int id) {
        return new TopBarItemUpdater() {

            @Override
            void update(UpdaterParam updaterParam) {
            }
        };
    }

    @Override
    public TopBarUpdater getTopBarUpdater() {
        return new TopBarUpdater() {
            @Override
            public void update(TopBarUpdater.Params params) {
            }
        };
    }
}
