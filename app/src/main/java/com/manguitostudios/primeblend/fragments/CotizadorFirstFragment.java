package com.manguitostudios.primeblend.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manguitostudios.primeblend.R;

/**
 * Created by Rafael on 04/10/2015.
 */
public class CotizadorFirstFragment extends Fragment {

    public CotizadorFirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cotizador, container, false);
    }
}
