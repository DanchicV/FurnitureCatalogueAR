package com.dvoroncov.furniturecataloguear.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.dvoroncov.furniturecataloguear.R;
import com.dvoroncov.furniturecataloguear.base.BaseActivity;
import com.dvoroncov.furniturecataloguear.data.model.Category;
import com.dvoroncov.furniturecataloguear.data.model.Furniture;
import com.dvoroncov.furniturecataloguear.ui.categories.CategoriesFragment;
import com.dvoroncov.furniturecataloguear.ui.furniture.FurnitureFragment;
import com.dvoroncov.furniturecataloguear.ui.furniture_info.FurnitureInfoFragment;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements MainFragmentInteraction {

    private static final int REQUEST_CODE_SIGN_IN = 123;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,
                        CategoriesFragment.newInstance(),
                        CategoriesFragment.class.getSimpleName())
                .commit();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (currentUser != null && !currentUser.isAnonymous()) {
            menu.findItem(R.id.login).setVisible(false);
            menu.findItem(R.id.logout).setVisible(true);
        } else {
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.logout).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                login();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean userIsAuthorized() {
        return currentUser != null && !currentUser.isAnonymous();
    }

    @Override
    public void login() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        checkUser();
                    }
                });
    }

    private void checkUser() {
        currentUser = mAuth.getCurrentUser();
        invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
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
