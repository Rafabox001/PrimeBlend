package com.manguitostudios.primeblend.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manguitodeveloper01 on 10/7/15.
 */
public class EndProcessFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.purchase)LinearLayout purchase;
    @Bind(R.id.purchaseInfo)LinearLayout purchaseInfo;

    public Boolean detail = false;
    private String PARAM_USER = "user_id";
    private String PARAM_CODE = "code";
    private String mUserId;
    private String codeResponse;

    public EndProcessFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_end_process, container, false);
        ButterKnife.bind(this, rootView);
        ((EvaluacionActivity)getActivity()).updateFragment(EvaluacionActivity.TAG_EVAL_END_PROCESS);

        Glide.with(getActivity())
                .load(R.drawable.background6)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        return rootView;
    }

    @OnClick(R.id.yes)
    public void getPurchaseInfo(){
        purchase.setVisibility(View.GONE);
        purchaseInfo.setVisibility(View.VISIBLE);
        detail = true;
    }

    @OnClick(R.id.no)
    public void navigateHome(){
        getActivity().finish();
    }

    public void navigateBack(){
        purchase.setVisibility(View.VISIBLE);
        purchaseInfo.setVisibility(View.GONE);
        detail = false;
    }

    public void processOutput(JSONObject response){
        try {
            codeResponse = response.getString(PARAM_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (codeResponse.contentEquals("0")){
            showDialog(getString(R.string.error));

        }else if (codeResponse.contentEquals("1")){


        }


    }

    public void showDialog (String message){
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
