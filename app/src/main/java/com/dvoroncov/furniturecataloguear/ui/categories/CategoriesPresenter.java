package com.dvoroncov.furniturecataloguear.ui.categories;

import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.base.BaseRepository;
import com.dvoroncov.furniturecataloguear.base.DataLoadedListener;
import com.dvoroncov.furniturecataloguear.data.repository.RepositoryImpl;

public class CategoriesPresenter implements CategoriesContract.Presenter {

    private CategoriesContract.View view;
    private BaseRepository repository;

    public void setView(CategoriesContract.View view) {
        this.view = view;
    }

    @Override
    public void subscribe() {
        repository = new RepositoryImpl();
    }

    @Override
    public void loadCategories() {
        view.showProgress(true);
        repository.loadData(new DataLoadedListener() {
            @Override
            public void dataLoaded(boolean isSuccess) {
                view.showProgress(false);
                if (isSuccess) {
                    view.setData(repository.getCategories());
                    return;
                }
                view.showError(R.string.loading_error);
            }
        });
    }

    @Override
    public void unsubscribe() {
        view = null;
    }
}
