package com.dvoroncov.furniturecataloguear.ui.furniture;

import com.dvoroncov.furniturecataloguear.base.BasePresenter;
import com.dvoroncov.furniturecataloguear.base.BaseView;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;

import java.util.List;

public class FurnitureContract {

    public interface View extends BaseView<FurniturePresenter> {

        void setData(List<Furniture> furnitureList);
    }

    public interface Presenter extends BasePresenter {

        void loadFurnitureList(Category category);
    }
}
