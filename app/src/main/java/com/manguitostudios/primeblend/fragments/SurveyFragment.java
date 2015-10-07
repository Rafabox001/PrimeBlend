package com.manguitostudios.primeblend.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
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


    public SurveyFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_survey, container, false);
        ButterKnife.bind(this, rootView);

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
    }

    @OnClick(R.id.next2)
    public void navigateNext(){

    }
}
