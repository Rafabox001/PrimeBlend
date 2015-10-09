package com.manguitostudios.primeblend;

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
import com.manguitostudios.primeblend.fragments.CotizadorFirstFragment;
import com.manguitostudios.primeblend.fragments.ProductsFragment;

import org.json.JSONObject;

public class CotizadorActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_COTIZADOR_SUBCATEGORY = "SubcategoriesFragment";
    public static final String TAG_COTIZADOR_PRODUCTS = "ProductsFragment";
    public static final String TAG_COTIZADOR_DETAIL = "ProductsDetailFragment";

    public static final String TAG_COTIZADOR_FRAGMENT = "CotizadorFirstFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizador);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_cotizador_container, new CotizadorFirstFragment(), TAG_COTIZADOR_FRAGMENT)
                    .addToBackStack(TAG_COTIZADOR_FRAGMENT)
                    .commit();
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
