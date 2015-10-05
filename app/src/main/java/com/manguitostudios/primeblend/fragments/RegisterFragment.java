package com.manguitostudios.primeblend.fragments;

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

/**
 * Created by manguitodeveloper01 on 10/5/15.
 */
public class RegisterFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.newRegister)LinearLayout newRegister;
    @Bind(R.id.existingRegister)LinearLayout existingRegister;

    public static final String PARAM_DISPLAY = "display";
    private String mDisplay;

    public RegisterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_capture_data, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mDisplay = bundle.getString(PARAM_DISPLAY);
        }

        if (mDisplay != null && mDisplay.contains("new")){
            newRegister.setVisibility(View.VISIBLE);
            existingRegister.setVisibility(View.GONE);
        }else {
            newRegister.setVisibility(View.GONE);
            existingRegister.setVisibility(View.VISIBLE);
        }



        Glide.with(getActivity())
                .load(R.drawable.background2)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }
}
