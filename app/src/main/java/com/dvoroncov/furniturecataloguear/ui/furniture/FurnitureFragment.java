package com.dvoroncov.furniturecataloguear.ui.furniture;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.base.BaseFragment;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.dvoroncov.furniturecataloguear.ui.main.MainFragmentInteraction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FurnitureFragment extends BaseFragment implements FurnitureContract.View, FurnitureAdapter.FurnitureSelectedListener {

    private static final String KEY_CATEGORY = "CATEGORY";

    @BindView(R.id.categories_recycler_view)
    RecyclerView categoriesRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MainFragmentInteraction fragmentInteraction;
    private Unbinder unbinder;
    private FurniturePresenter presenter;
    private FurnitureAdapter adapter = new FurnitureAdapter(this);
    private Category category;

    public static FurnitureFragment newInstance(Category category) {
        FurnitureFragment fragment = new FurnitureFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragmentInteraction) {
            fragmentInteraction = ((MainFragmentInteraction) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        presenter = new FurniturePresenter();
        presenter.setView(this);
        presenter.subscribe();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        categoriesRecyclerView.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            category = bundle.getParcelable(KEY_CATEGORY);
            presenter.loadFurnitureList(category);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isAdded()) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null && category != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(category.getName());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home
                && isAdded()) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(FurniturePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress(Boolean b) {
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(String message) {
        if (isAdded()) {
            MaterialDialog materialDialog = new MaterialDialog.Builder(getContext())
                    .title(R.string.warning)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show();
            materialDialog.getActionButton(DialogAction.POSITIVE).requestFocus();
        }
    }

    @Override
    public void showError(int message) {
        showError(getString(message));
    }

    @Override
    public void setData(List<Furniture> furnitureList) {
        adapter.setCategories(furnitureList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }

    @Override
    public void itemSelected(Furniture furniture) {
        fragmentInteraction.openFurniture(furniture);
    }
}
