package com.dvoroncov.furniturecataloguear.ui.furniture_info;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.base.BaseFragment;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.dvoroncov.furniturecataloguear.ui.ar.ArActivity;
import com.dvoroncov.furniturecataloguear.ui.main.MainFragmentInteraction;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FurnitureInfoFragment extends BaseFragment implements FurnitureInfoContract.View {

    private static final String KEY_FURNITURE = "CATEGORY";

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.furniture_image_view)
    ImageView furnitureImageView;

    @BindView(R.id.furniture_name)
    TextView furnitureName;

    @BindView(R.id.furniture_price)
    TextView furniturePrice;

    @BindView(R.id.furniture_description)
    TextView furnitureDescription;

    private MainFragmentInteraction fragmentInteraction;
    private Unbinder unbinder;
    private FurnitureInfoPresenter presenter;
    private Furniture furniture;

    public static FurnitureInfoFragment newInstance(Furniture furniture) {
        FurnitureInfoFragment fragment = new FurnitureInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_FURNITURE, furniture);
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
        View view = inflater.inflate(R.layout.fragment_furniture_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter = new FurnitureInfoPresenter();
        presenter.setView(this);
        presenter.subscribe();
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        if (bundle != null) {
            furniture = bundle.getParcelable(KEY_FURNITURE);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Picasso.get().load(furniture.getImage()).into(furnitureImageView);
        furnitureName.setText(furniture.getName());
        furniturePrice.setText(getString(R.string.rub, new BigDecimal(furniture.getPrice()).setScale(2).toPlainString()));
        furnitureDescription.setText(furniture.getDescription());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isAdded()) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null && furniture != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(furniture.getName());
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
    public void setPresenter(FurnitureInfoPresenter presenter) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.unsubscribe();
    }

    @OnClick(R.id.show_3d_button)
    public void onShow3DClicked() {
        if (TextUtils.isEmpty(furniture.getModelObjUrl())
                || TextUtils.isEmpty(furniture.getModelTextureUrl())) {
            return;
        }
        startActivity(new Intent(getContext(), ArActivity.class));
    }

    @OnClick(R.id.buy_button)
    public void onBuyClicked() {
        if (!fragmentInteraction.userIsAuthorized()) {
            fragmentInteraction.login();
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... fileUrl) {
            int count;
            try {
                URL url = new URL(fileUrl[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                int lengthOfFile = connection.getContentLength();

                OutputStream output = new FileOutputStream(Environment
                        .getExternalStorageDirectory().toString()
                        + "/2011.kml");

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));

                    output.write(data, 0, count);
                }

                output.flush();

                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onProgressUpdate(String... progress) {
        }

        @Override
        protected void onPostExecute(String file_url) {
            showProgress(false);
        }

    }
}
