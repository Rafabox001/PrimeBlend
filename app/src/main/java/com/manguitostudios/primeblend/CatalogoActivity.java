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

import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.Utils.Constants;
import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.CatalogoFirstFragment;
import com.manguitostudios.primeblend.fragments.CheckoutFragment;
import com.manguitostudios.primeblend.fragments.EndProcessFragment;
import com.manguitostudios.primeblend.fragments.ProductsFragment;
import com.manguitostudios.primeblend.fragments.RegisterFragment;
import com.manguitostudios.primeblend.fragments.SurveyFragment;
import com.manguitostudios.primeblend.network.RegisterCalls;
import com.manguitostudios.primeblend.objects.Product;

import org.json.JSONObject;

public class CatalogoActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_CATALOGO_FRAGMENT = "CatalogoFirstFragment";
    public static final String TAG_CATALOGO_SUBCATEGORY = "SubcategoriesFragment";
    public static final String TAG_CATALOGO_PRODUCTS = "ProductsFragment";
    public static final String TAG_CATALOGO_DETAIL = "ProductsDetailFragment";
    public static final String TAG_CATALOGO_CHECKOUT = "CheckoutFragment";
    public String currentFragment = "";

    private String PARAM_USER = "user_id";
    private String mUserId;


    public void updateFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(PARAM_USER);

        if (savedInstanceState == null){
            Bundle args = new Bundle();
            args.putString(PARAM_USER, mUserId);

            CatalogoFirstFragment catalogoFirstFragment = new CatalogoFirstFragment();
            catalogoFirstFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_catalogo_container, catalogoFirstFragment, CatalogoActivity.TAG_CATALOGO_FRAGMENT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_FRAGMENT);
            transaction.commit();


        }



    }

    @Override
    public void onReceivedData(JSONObject object) {
        FragmentManager fm = getSupportFragmentManager();
        switch (currentFragment){
            case TAG_CATALOGO_PRODUCTS:
                ProductsFragment productsFragment = (ProductsFragment)fm.findFragmentByTag(TAG_CATALOGO_PRODUCTS);
                productsFragment.processOutput(object);
                break;
            case TAG_CATALOGO_CHECKOUT:
                CheckoutFragment checkoutFragment = (CheckoutFragment)fm.findFragmentByTag(TAG_CATALOGO_CHECKOUT);
                checkoutFragment.processOutput(object);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        CheckoutFragment checkoutFragment = (CheckoutFragment)fm.findFragmentByTag(TAG_CATALOGO_CHECKOUT);
        if (checkoutFragment !=null){
            if (checkoutFragment.detail){
                checkoutFragment.navigateBack();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
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
