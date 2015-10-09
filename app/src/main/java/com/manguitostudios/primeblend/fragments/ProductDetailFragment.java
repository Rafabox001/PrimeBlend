package com.manguitostudios.primeblend.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.objects.Product;

import butterknife.Bind;
import butterknife.ButterKnife;

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


    private String mCategory;
    private String mOrigin;
    private Product mProduct;
    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    public static final String PARAM_PRODUCT = "product";

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



        return rootView;
    }
}
