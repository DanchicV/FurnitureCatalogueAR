package com.dvoroncov.furniturecataloguear.base;

import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;

import java.util.List;

public interface BaseRepository {

    void loadData(DataLoadedListener dataLoadedListener);

    boolean dataLoaded();

    List<Category> getCategories();

    List<Furniture> getFurnitureList();
}
