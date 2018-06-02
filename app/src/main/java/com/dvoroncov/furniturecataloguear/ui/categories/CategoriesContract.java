package com.dvoroncov.furniturecataloguear.ui.categories;

import com.dvoroncov.furniturecataloguear.base.BasePresenter;
import com.dvoroncov.furniturecataloguear.base.BaseView;
import com.dvoroncov.furniturecataloguear.data.model.Category;

import java.util.List;

public class CategoriesContract {

    public interface View extends BaseView<CategoriesPresenter> {

        void setData(List<Category> categories);
    }

    public interface Presenter extends BasePresenter {

        void loadCategories();
    }
}
