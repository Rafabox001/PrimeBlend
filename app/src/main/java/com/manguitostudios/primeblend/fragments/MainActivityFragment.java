package com.manguitostudios.primeblend.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.manguitostudios.primeblend.MainActivity;
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
    @Bind(R.id.fab)FloatingActionButton fab;

    private String PARAM_USER = "user_id";
    private String mUserId;



    public MainActivityFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mUserId = bundle.getString(PARAM_USER);
        }

        Glide.with(getActivity())
                .load(R.drawable.background7)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.logout));
            }
        });

        return rootView;
    }

    @OnClick(R.id.evaluacion_btn)
    public void navigateEvaluacion(){
        Intent eval = new Intent(getActivity(), EvaluacionActivity.class);
        eval.putExtra(PARAM_USER, mUserId);
        startActivity(eval);
    }

    @OnClick(R.id.catalogo_btn)
    public void navigateCatalogo(){
        Intent cat = new Intent(getActivity(), CatalogoActivity.class);
        cat.putExtra(PARAM_USER, mUserId);
        startActivity(cat);
    }

    @OnClick(R.id.cotizador_btn)
    public void navigateCotizador(){
        Intent cot = new Intent(getActivity(), CotizadorActivity.class);
        cot.putExtra(PARAM_USER, mUserId);
        startActivity(cot);
    }

    @OnClick(R.id.calendario_btn)
    public void navigateCalendario(){
        Intent cal = new Intent(getActivity(), CalendarActivity.class);
        cal.putExtra(PARAM_USER, mUserId);
        startActivity(cal);
    }

    public void showDialog (String message){
        new AlertDialog.Builder(getActivity())
                .setTitle("Salir?")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with logout
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main, new EvaluacionFirstFragment(), MainActivity.TAG_EVAL_FRAGMENT)
                                .addToBackStack(MainActivity.TAG_EVAL_FRAGMENT)
                                .commit();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_lock_idle_lock)
                .show();
    }


}
