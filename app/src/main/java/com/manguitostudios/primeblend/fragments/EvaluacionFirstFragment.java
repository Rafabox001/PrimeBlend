package com.manguitostudios.primeblend.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.MainActivity;
import com.manguitostudios.primeblend.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rafael on 04/10/2015.
 */
public class EvaluacionFirstFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;

    public static final String PARAM_DISPLAY = "display";


    public EvaluacionFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_evaluacion, container, false);
        ButterKnife.bind(this, rootView);

        Glide.with(getActivity())
                .load(R.drawable.background4)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }

    @OnClick(R.id.register_btn)
    public void register(){
        Bundle args = new Bundle();
        args.putString(PARAM_DISPLAY, "new");

        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_main, registerFragment, MainActivity.TAG_EVAL_CAPTURE);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(EvaluacionActivity.TAG_EVAL_CAPTURE);
        transaction.commit();
    }

    @OnClick(R.id.already_register_btn)
    public void existing(){
        Bundle args = new Bundle();
        args.putString(PARAM_DISPLAY, "existing");

        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_main, registerFragment, MainActivity.TAG_EVAL_CAPTURE);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(EvaluacionActivity.TAG_EVAL_CAPTURE);
        transaction.commit();
    }
}
