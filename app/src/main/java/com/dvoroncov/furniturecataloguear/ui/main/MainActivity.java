package com.dvoroncov.furniturecataloguear.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.dvoroncov.furniturecataloguear.base.BaseActivity;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.dvoroncov.furniturecataloguear.ui.categories.CategoriesFragment;
import com.dvoroncov.furniturecataloguear.ui.furniture.FurnitureFragment;
import com.dvoroncov.furniturecataloguear.ui.furniture_info.FurnitureInfoFragment;

public class MainActivity extends BaseActivity implements MainFragmentInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,
                        CategoriesFragment.newInstance(),
                        CategoriesFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openCategory(Category category) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        FurnitureFragment.newInstance(category),
                        FurnitureFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(FurnitureFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openFurniture(Furniture furniture) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content,
                        FurnitureInfoFragment.newInstance(furniture),
                        FurnitureInfoFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(FurnitureFragment.class.getSimpleName())
                .commit();
    }
}
