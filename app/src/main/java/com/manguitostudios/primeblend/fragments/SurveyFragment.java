package com.manguitostudios.primeblend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rafael on 06/10/2015.
 */
public class SurveyFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.surveyPart1)LinearLayout surveyFirst;
    @Bind(R.id.surveyPart2)LinearLayout surveySecond;

    public Boolean detail = false;


    public SurveyFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_survey, container, false);
        ButterKnife.bind(this, rootView);
        ((EvaluacionActivity)getActivity()).updateFragment(EvaluacionActivity.TAG_EVAL_SURVEY);

        Glide.with(getActivity())
                .load(R.drawable.background2)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }

    @OnClick(R.id.next)
    public void continueSurvey(){
        surveyFirst.setVisibility(View.GONE);
        surveySecond.setVisibility(View.VISIBLE);
        detail = true;
    }

    @OnClick(R.id.next2)
    public void navigateNext(){
        EndProcessFragment endProcessFragment = new EndProcessFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_evaluacion_container, endProcessFragment, EvaluacionActivity.TAG_EVAL_END_PROCESS);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(EvaluacionActivity.TAG_EVAL_END_PROCESS);
        transaction.commit();

    }

    public void navigateBack(){
        surveyFirst.setVisibility(View.VISIBLE);
        surveySecond.setVisibility(View.GONE);
        detail = false;
    }
}
