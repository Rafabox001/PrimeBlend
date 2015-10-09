package com.manguitostudios.primeblend.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.CatalogoActivity;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.network.RegisterCalls;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by manguitodeveloper01 on 10/5/15.
 */
public class RegisterFragment extends Fragment {

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;
    @Bind(R.id.newRegister)LinearLayout newRegister;
    @Bind(R.id.existingRegister)LinearLayout existingRegister;

    @Bind(R.id.name)EditText name;
    @Bind(R.id.mail)EditText mail;
    @Bind(R.id.phone)EditText phone;
    @Bind(R.id.job)EditText job;
    @Bind(R.id.existing_mail)EditText existing_mail;
    @Bind(R.id.monogram)ImageView monogram;
    @Bind(R.id.iomabe)ImageView ioMabe;
    @Bind(R.id.profile)ImageView profile;



    private String codeResponse;
    private String idUser;

    public static final String PARAM_DISPLAY = "display";
    private String USER_ID = "";
    private String PARAM_CODE = "code";
    private String PARAM_USER = "user_id";
    private String mDisplay;
    private String operation;
    private String line = "monogram";

    public RegisterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_capture_data, container, false);
        ButterKnife.bind(this, rootView);
        ((EvaluacionActivity)getActivity()).updateFragment(EvaluacionActivity.TAG_EVAL_CAPTURE);

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

        monogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getActivity())
                        .load(R.drawable.monogram_hover)
                        .into(monogram);

                Glide.with(getActivity())
                        .load(R.drawable.profile)
                        .into(profile);

                Glide.with(getActivity())
                        .load(R.drawable.iomabe)
                        .into(ioMabe);

                line = "monogram";

            }
        });

        ioMabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getActivity())
                        .load(R.drawable.monogram)
                        .into(monogram);

                Glide.with(getActivity())
                        .load(R.drawable.profile)
                        .into(profile);

                Glide.with(getActivity())
                        .load(R.drawable.iomabe_hover)
                        .into(ioMabe);

                line = "iomabe";
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(getActivity())
                        .load(R.drawable.monogram)
                        .into(monogram);

                Glide.with(getActivity())
                        .load(R.drawable.profile_hover)
                        .into(profile);

                Glide.with(getActivity())
                        .load(R.drawable.iomabe)
                        .into(ioMabe);

                line = "profile";
            }
        });

        return rootView;
    }

    @OnClick(R.id.send)
    public void sendRegister(){
        if (isValidEmail(mail.getText().toString())){
            RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.registerRequest);
            registerCalls.execute(name.getText().toString().toUpperCase(), mail.getText().toString(), phone.getText().toString(), job.getText().toString().toUpperCase(), line);
        }else {
            Toast.makeText(getActivity(), getString(R.string.invalid_email),
                    Toast.LENGTH_LONG).show();
        }
        operation = "register";


    }

    @OnClick(R.id.next)
    public void goSurvey(){
        if (isValidEmail(existing_mail.getText().toString())){
            RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.dataRequest);
            registerCalls.execute(existing_mail.getText().toString());
        }else {
            Toast.makeText(getActivity(), getString(R.string.invalid_email),
                    Toast.LENGTH_LONG).show();
        }
        operation = "existing";
    }

    public void processOutput(JSONObject response){
        try {
            codeResponse = response.getString(PARAM_CODE);
            idUser = response.getString(PARAM_USER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (codeResponse.contentEquals("0")){
            if (operation.contentEquals("register")){
                showDialog(getString(R.string.error));
            }else if (operation.contentEquals("existing")){
                showDialog(getString(R.string.not_existing_user));
            }

        }else if (codeResponse.contentEquals("1")){
            Bundle args = new Bundle();
            args.putString(USER_ID, idUser);

            SurveyFragment surveyFragment = new SurveyFragment();
            surveyFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_evaluacion_container, surveyFragment, EvaluacionActivity.TAG_EVAL_SURVEY);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(EvaluacionActivity.TAG_EVAL_SURVEY);
            transaction.commit();

        }else if (codeResponse.contentEquals("2")){
            Toast.makeText(getActivity(), getString(R.string.existing_user),
                    Toast.LENGTH_LONG).show();
        }


    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
