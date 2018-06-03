package com.dvoroncov.furniturecataloguear.ui.furniture;

import com.dvoroncov.furniturecataloguear.base.BaseRepository;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.repository.RepositoryImpl;

public class FurniturePresenter implements FurnitureContract.Presenter {

    private FurnitureContract.View view;
    private BaseRepository repository;

    public void setView(FurnitureContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = RepositoryImpl.getInstance();
    }

    @Override
    public void loadFurnitureList(Category category) {
        view.showProgress(true);
        if (repository.isDataLoaded()) {
            view.setData(repository.getFurnitureList(category));
            view.showProgress(false);
        }
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
