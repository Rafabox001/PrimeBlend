package com.manguitostudios.primeblend.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CalendarActivity;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.CotizadorActivity;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivityFragment extends Fragment {

    @Bind(R.id.evaluacion_btn)Button evaluacion;
    @Bind(R.id.catalogo_btn)Button catalogo;
    @Bind(R.id.cotizador_btn)Button cotizador;
    @Bind(R.id.calendario_btn)Button calendario;
    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;



    public MainActivityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Glide.with(getActivity())
                .load(R.drawable.background7)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }

    @OnClick(R.id.evaluacion_btn)
    public void navigateEvaluacion(){
        Intent eval = new Intent(getActivity(), EvaluacionActivity.class);
        startActivity(eval);
    }

    @OnClick(R.id.catalogo_btn)
    public void navigateCatalogo(){
        Intent cat = new Intent(getActivity(), CatalogoActivity.class);
        startActivity(cat);
    }

    @OnClick(R.id.cotizador_btn)
    public void navigateCotizador(){
        Intent cot = new Intent(getActivity(), CotizadorActivity.class);
        startActivity(cot);
    }

    @OnClick(R.id.calendario_btn)
    public void navigateCalendario(){
        Intent cal = new Intent(getActivity(), CalendarActivity.class);
        startActivity(cal);
    }

}
