package com.manguitostudios.primeblend.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.MainActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.objects.Product;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manguitodeveloper01 on 10/8/15.
 */
public class ProductDetailFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.origin)TextView origen;
    @Bind(R.id.choice)TextView seleccion;

    @Bind(R.id.imageDetail)ImageView image;
    @Bind(R.id.name)TextView name;
    @Bind(R.id.descripcion)TextView descripcion;
    @Bind(R.id.precio)TextView precio;
    @Bind(R.id.currency)TextView currency;
    @Bind(R.id.numberpicker)NumberPicker picker;


    private String mCategory;
    private String mOrigin;
    private Product mProduct;
    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_PRODUCT = "product";
    public static final String PARAM_CANTIDAD = "cantidad";

    private String quantity;

    private String mUserId;
    private String PARAM_USER = "user_id";

    public ProductDetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, rootView);

        Glide.with(getActivity())
                .load(R.drawable.background9)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mCategory = bundle.getString(PARAM_BRAND);
            mOrigin = bundle.getString(PARAM_ORIGIN);
            mProduct = bundle.getParcelable(PARAM_PRODUCT);
            mUserId = bundle.getString(PARAM_USER);
            origen.setText(mOrigin);
            seleccion.setText(mCategory);

            Glide.with(getActivity())
                    .load(mProduct.getImage())
                    .into(image);
            name.setText(mProduct.getName());
            descripcion.setText(mProduct.getDescription());
            precio.setText(mProduct.getPrice());
            currency.setText(mProduct.getCurrency());
        }

        picker.setMaxValue(100);
        picker.setMinValue(0);
        picker.setWrapSelectorWheel(true);
        picker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                quantity = String.valueOf(newVal);
            }
        });


        return rootView;
    }

    @OnClick(R.id.addToBalance)
    public void agregaACotizacion(){
        mProduct.setCantidad(picker.getValue());
        Bundle args = new Bundle();
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putParcelable(PARAM_PRODUCT, mProduct);
        args.putString(PARAM_USER, mUserId);

        CheckoutFragment checkoutFragment = new CheckoutFragment();
        checkoutFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, checkoutFragment, CatalogoActivity.TAG_CATALOGO_CHECKOUT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_CHECKOUT);
            transaction.commit();
        }else{
            transaction.replace(R.id.fragment_cotizador_container, checkoutFragment, CotizadorActivity.TAG_COTIZADOR_CHECKOUT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_CHECKOUT);
            transaction.commit();
        }



    }

    @OnClick(R.id.goBalance)
    public void irACotizacion(){
        Bundle args = new Bundle();
        args.putString(PARAM_BRAND, mCategory);
        args.putString(PARAM_ORIGIN, mOrigin);
        args.putString(PARAM_USER, mUserId);

        CheckoutFragment checkoutFragment = new CheckoutFragment();
        checkoutFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (mOrigin.contentEquals("catálogo")){
            transaction.replace(R.id.fragment_catalogo_container, checkoutFragment, CatalogoActivity.TAG_CATALOGO_CHECKOUT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_CHECKOUT);
            transaction.commit();
        }else{
            transaction.replace(R.id.fragment_cotizador_container, checkoutFragment, CotizadorActivity.TAG_COTIZADOR_CHECKOUT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_CHECKOUT);
            transaction.commit();
        }


    }

    @OnClick(R.id.viewProducts)
    public void verProductos(){
        Intent i  = new Intent(getActivity(), MainActivity.class);
        i.putExtra(PARAM_USER, mUserId);
        startActivity(i);
    }

    @OnClick(R.id.fichaTecnica)
    public void verFichaTecnica(){
        String url = mProduct.getPdf();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
