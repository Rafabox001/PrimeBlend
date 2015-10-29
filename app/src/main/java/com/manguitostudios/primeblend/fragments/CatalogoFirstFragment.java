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
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Rafael on 04/10/2015.
 */
public class CatalogoFirstFragment extends Fragment{

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.monogram)ImageView monogram;
    @Bind(R.id.iomabe)ImageView ioMabe;
    @Bind(R.id.profile)ImageView profile;

    public static final String PARAM_BRAND = "brand";
    public static final String PARAM_ORIGIN = "origin";
    private String PARAM_USER = "user_id";
    private String mUserId;


    public CatalogoFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_catalogo, container, false);
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
                args.putString(PARAM_ORIGIN, "catálogo");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_catalogo_container, subcategoriesFragment, CatalogoActivity.TAG_CATALOGO_FRAGMENT);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_FRAGMENT);
                transaction.commit();
            }
        });

        ioMabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(PARAM_BRAND, "iomabe");
                args.putString(PARAM_ORIGIN, "catálogo");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_catalogo_container, subcategoriesFragment, CatalogoActivity.TAG_CATALOGO_FRAGMENT);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_FRAGMENT);
                transaction.commit();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString(PARAM_BRAND, "profile");
                args.putString(PARAM_ORIGIN, "catálogo");
                args.putString(PARAM_USER, mUserId);

                SubcategoriesFragment subcategoriesFragment = new SubcategoriesFragment();
                subcategoriesFragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragment_catalogo_container, subcategoriesFragment, CatalogoActivity.TAG_CATALOGO_SUBCATEGORY);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(CatalogoActivity.TAG_CATALOGO_SUBCATEGORY);
                transaction.commit();
            }
        });

        return rootView;
    }


}
