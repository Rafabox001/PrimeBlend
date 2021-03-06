package com.manguitostudios.primeblend.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.R;

import java.util.zip.CheckedOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rafael on 04/10/2015.
 */
public class CotizadorFirstFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.monogram)ImageView monogram;
    @Bind(R.id.iomabe)ImageView ioMabe;
    @Bind(R.id.profile)ImageView profile;

    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    private String PARAM_USER = "user_id";
    private String mUserId;


    public CotizadorFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cotizador, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mUserId = bundle.getString(PARAM_USER);
        }

        Glide.with(getActivity())
                .load(R.drawable.background9)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        monogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(PARAM_BRAND, "monogram");
                args.putString(PARAM_ORIGIN, "cotizador");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_cotizador_container, subcategoriesFragment, CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.commit();
            }
        });

        ioMabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(PARAM_BRAND, "iomabe");
                args.putString(PARAM_ORIGIN, "cotizador");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_cotizador_container, subcategoriesFragment, CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(PARAM_BRAND, "profile");
                args.putString(PARAM_ORIGIN, "cotizador");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_cotizador_container, subcategoriesFragment, CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CotizadorActivity.TAG_COTIZADOR_SUBCATEGORY);
                transaction.commit();
            }
        });

        return rootView;
    }
}
