package com.manguitostudios.primeblend.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rafael on 04/10/2015.
 */
public class CalendarFragment extends Fragment{

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;

    public CalendarFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, rootView);

        Glide.with(getActivity())
                .load(R.drawable.background8)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }
}
