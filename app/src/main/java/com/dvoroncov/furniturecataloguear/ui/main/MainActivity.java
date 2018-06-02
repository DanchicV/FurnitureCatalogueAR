package com.dvoroncov.furniturecataloguear.ui.main;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.base.BaseActivity;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.dvoroncov.furniturecataloguear.ui.categories.CategoriesFragment;

public class MainActivity extends BaseActivity implements MainFragmentInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.categories));
        }

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,
                        CategoriesFragment.newInstance(),
                        CategoriesFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void openCategory(Category category) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(category.getName());
        }
    }

    @Override
    public void openFurniture(Furniture furniture) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(furniture.getName());
        }
    }
}
