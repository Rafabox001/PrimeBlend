package com.manguitostudios.primeblend.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.Utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
public class SubcategoriesFragment extends Fragment{

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;

    @Bind(R.id.origin)TextView origen;
    @Bind(R.id.choice)ImageView seleccion;

    private String mCategory;
    private String mOrigin;
    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_SUBCATEGORY = "subcategory";
    public static final String PARAM_SUBCATEGORY_ID = "subcategory_id";
    private String categoryId;

    public SubcategoriesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subcategories, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mCategory = bundle.getString(PARAM_BRAND);
            mOrigin = bundle.getString(PARAM_ORIGIN);
            origen.setText(mOrigin);
        }
        updateUi(mCategory);

        Glide.with(getActivity())
                .load(R.drawable.background9)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }

    private void updateUi(String category){
        switch (category){
            case "monogram":
                Glide.with(getActivity())
                        .load(R.drawable.monogram)
                        .into(seleccion);
                break;
            case "iomabe":
                Glide.with(getActivity())
                        .load(R.drawable.iomabe)
                        .into(seleccion);
                break;
            case "profile":
                Glide.with(getActivity())
                        .load(R.drawable.profile)
                        .into(seleccion);
                break;
        }
    }

    @OnClick(R.id.coccion)
    public void productosCoccion(){
        switch (mCategory){
            case "monogram":
                categoryId = Constants.monogram_coccion;
                break;
            case "iomabe":
                categoryId = Constants.iomabe_coccion;
                break;
            case "profile":
                categoryId = Constants.profile_coccion;
                break;
        }
        Bundle args = new Bundle();
        args.putString(PARAM_SUBCATEGORY_ID, categoryId);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_SUBCATEGORY, "cocinado");

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, productsFragment, CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.commit();
        }else {
            transaction.replace(R.id.fragment_cotizador_container, productsFragment, CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.commit();
        }


    }

    @OnClick(R.id.refrigeracion)
    public void productosRefrigeracion(){
        switch (mCategory){
            case "monogram":
                categoryId = Constants.monogram_refrigeracion;
                break;
            case "iomabe":
                categoryId = Constants.iomabe_refrigeracion;
                break;
            case "profile":
                categoryId = Constants.profile_refrigeracion;
                break;
        }
        Bundle args = new Bundle();
        args.putString(PARAM_SUBCATEGORY_ID, categoryId);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_SUBCATEGORY, "refrigeracion");

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, productsFragment, CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.commit();
        }else {
            transaction.replace(R.id.fragment_cotizador_container, productsFragment, CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.commit();
        }
    }

    @OnClick(R.id.ventilacion)
    public void productosVentilacion(){
        switch (mCategory){
            case "monogram":
                categoryId = Constants.monogram_colecciones;
                break;
            case "iomabe":
                categoryId = Constants.iomabe_colecciones;
                break;
            case "profile":
                categoryId = Constants.profile_colecciones;
                break;
        }
        Bundle args = new Bundle();
        args.putString(PARAM_SUBCATEGORY_ID, categoryId);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_SUBCATEGORY, "ventilacion");

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, productsFragment, CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.commit();
        }else {
            transaction.replace(R.id.fragment_cotizador_container, productsFragment, CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.commit();
        }
    }

    @OnClick(R.id.lavavajillas)
    public void productosLavavajillas(){
        switch (mCategory){
            case "monogram":
                categoryId = Constants.monogram_lavavajillas;
                break;
            case "iomabe":
                categoryId = Constants.iomabe_lavavajillas;
                break;
            case "profile":
                categoryId = Constants.profile_lavavajillas;
                break;
        }
        Bundle args = new Bundle();
        args.putString(PARAM_SUBCATEGORY_ID, categoryId);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_SUBCATEGORY, "lavavajillas");

        ProductsFragment productsFragment = new ProductsFragment();
        productsFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, productsFragment, CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_PRODUCTS);
            transaction.commit();
        }else {
            transaction.replace(R.id.fragment_cotizador_container, productsFragment, CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_PRODUCTS);
            transaction.commit();
        }
    }
}
