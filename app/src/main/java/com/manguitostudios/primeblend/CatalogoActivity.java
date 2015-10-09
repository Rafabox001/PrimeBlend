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

import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.Utils.Constants;
import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.CatalogoFirstFragment;
import com.manguitostudios.primeblend.fragments.ProductsFragment;
import com.manguitostudios.primeblend.network.RegisterCalls;
import com.manguitostudios.primeblend.objects.Product;

import org.json.JSONObject;

public class CatalogoActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_CATALOGO_FRAGMENT = "CatalogoFirstFragment";
    public static final String TAG_CATALOGO_SUBCATEGORY = "SubcategoriesFragment";
    public static final String TAG_CATALOGO_PRODUCTS = "ProductsFragment";
    public static final String TAG_CATALOGO_DETAIL = "ProductsDetailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_catalogo_container, new CatalogoFirstFragment(), TAG_CATALOGO_FRAGMENT)
                    .addToBackStack(TAG_CATALOGO_FRAGMENT)
                    .commit();
        }



    }

    @Override
    public void onReceivedData(JSONObject object) {
        FragmentManager fm = getSupportFragmentManager();
        ProductsFragment productsFragment = (ProductsFragment)fm.findFragmentByTag(TAG_CATALOGO_PRODUCTS);
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
        ft.addToBackStack(TAG_CATALOGO_DETAIL);
        ft.commit();
    }
}
