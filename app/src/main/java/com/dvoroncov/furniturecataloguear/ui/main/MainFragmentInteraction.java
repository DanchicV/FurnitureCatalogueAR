package com.dvoroncov.furniturecataloguear.ui.main;

import com.dvoroncov.furniturecataloguear.base.BaseFragmentInteraction;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;

public interface MainFragmentInteraction extends BaseFragmentInteraction {

    void openCategory(Category category);

    void openFurniture(Furniture furniture);

    boolean userIsAuthorized();

    void login();

    void logout();
}
