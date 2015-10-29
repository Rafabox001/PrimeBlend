package com.manguitostudios.primeblend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.CatalogoFirstFragment;
import com.manguitostudios.primeblend.fragments.CotizadorFirstFragment;
import com.manguitostudios.primeblend.fragments.ProductsFragment;

import org.json.JSONObject;

public class CotizadorActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_COTIZADOR_SUBCATEGORY = "SubcategoriesFragment";
    public static final String TAG_COTIZADOR_PRODUCTS = "ProductsFragment";
    public static final String TAG_COTIZADOR_DETAIL = "ProductsDetailFragment";

    public static final String TAG_COTIZADOR_FRAGMENT = "CotizadorFirstFragment";
    public static final String TAG_COTIZADOR_CHECKOUT = "CheckoutFragment";
    public String currentFragment = "";

    private String PARAM_USER = "user_id";
    private String mUserId;

    public void updateFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizador);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(PARAM_USER);

        if (savedInstanceState == null){
            Bundle args = new Bundle();
            args.putString(PARAM_USER, mUserId);

            CotizadorFirstFragment cotizadorFirstFragment = new CotizadorFirstFragment();
            cotizadorFirstFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_cotizador_container, cotizadorFirstFragment, CotizadorActivity.TAG_COTIZADOR_FRAGMENT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_FRAGMENT);
            transaction.commit();


        }

    }

    @Override
    public void onReceivedData(JSONObject object) {
        FragmentManager fm = getSupportFragmentManager();
        ProductsFragment productsFragment = (ProductsFragment)fm.findFragmentByTag(TAG_COTIZADOR_PRODUCTS);
        productsFragment.processOutput(object);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(TAG_COTIZADOR_DETAIL);
        ft.commit();
    }
}
