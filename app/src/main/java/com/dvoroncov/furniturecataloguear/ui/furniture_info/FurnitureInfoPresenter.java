package com.dvoroncov.furniturecataloguear.ui.furniture_info;

public class FurnitureInfoPresenter implements FurnitureInfoContract.Presenter {

    private FurnitureInfoContract.View view;

    public void setView(FurnitureInfoContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
