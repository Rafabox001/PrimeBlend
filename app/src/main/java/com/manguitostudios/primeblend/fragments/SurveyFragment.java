package com.manguitostudios.primeblend.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.EvaluacionActivity;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.network.RegisterCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    @Bind(R.id.q1)RadioGroup question1;
    @Bind(R.id.q_media)RadioGroup questionMedia;
    @Bind(R.id.q2)RadioGroup question2;
    @Bind(R.id.q3)RadioGroup question3;
    @Bind(R.id.q4)RadioGroup question4;
    @Bind(R.id.q3_comments)EditText q3_comments;
    @Bind(R.id.q4_comments)EditText q4_comments;
    @Bind(R.id.q1_other)RadioButton other;
    @Bind(R.id.other_text)EditText otherText;

    private String PARAM_USER = "user_id";
    private String PARAM_CODE = "code";
    private String PARAM_SURVEY = "survey_id";
    private String idUser;
    private String mUserId;
    private String codeResponse;
    private String mSurveyId;

    private String respuesta1, respuestaMedia, respuesta2, respuesta3, respuesta4;



    public Boolean detail = false;


    public SurveyFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eval_survey, container, false);
        ButterKnife.bind(this, rootView);
        ((EvaluacionActivity)getActivity()).updateFragment(EvaluacionActivity.TAG_EVAL_SURVEY);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mUserId = bundle.getString(PARAM_USER);
        }

        Glide.with(getActivity())
                .load(R.drawable.background2)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        question1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("POSITION", String.valueOf(checkedId));
                switch (checkedId) {
                    case 1:
                        respuesta1 = "Restaurante";
                        questionMedia.clearCheck();
                        other.setChecked(false);
                        break;
                    case 2:
                        respuesta1 = "Evento";
                        questionMedia.clearCheck();
                        other.setChecked(false);
                        break;
                    case 3:
                        respuesta1 = "Referido";
                        questionMedia.clearCheck();
                        other.setChecked(false);
                        break;
                }
            }
        });

        questionMedia.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("POSITION", String.valueOf(checkedId));
                switch (checkedId){
                    case 4:
                        respuesta1 = "Digital";
                        question1.clearCheck();
                        other.setChecked(false);
                        break;
                    case 5:
                        respuesta1 = "Impreso";
                        question1.clearCheck();
                        other.setChecked(false);
                        break;
                }
            }
        });

        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    question1.clearCheck();
                    questionMedia.clearCheck();
                    respuesta1 = "otro";
                }
            }
        });

        question2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("POSITION", String.valueOf(checkedId));
                switch (checkedId){
                    case 6:
                        respuesta2 = "Excelente";
                        break;
                    case 7:
                        respuesta2 = "Bueno";
                        break;
                    case 8:
                        respuesta2 = "Regular";
                        break;
                    case 9:
                        respuesta2 = "Malo";
                        break;
                    case 10:
                        respuesta2 = "Muy Malo";
                        break;
                }
            }
        });

        question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("POSITION", String.valueOf(checkedId));
                switch (checkedId) {
                    case 11:
                        respuesta3 = "Si";
                        break;
                    case 12:
                        respuesta3 = "No";
                        break;
                }
            }
        });

        question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                Log.d("POSITION", String.valueOf(checkedId));
                switch (checkedId) {
                    case 13:
                        respuesta4 = "Si";
                        break;
                    case 14:
                        respuesta4 = "No";
                        break;
                }
            }
        });


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
        RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.surveyRequest);
        registerCalls.execute(respuesta1, respuestaMedia, respuesta2, respuesta3, q3_comments.getText().toString(), respuesta4,
                q4_comments.getText().toString(), mUserId);






    }

    public void navigateBack(){
        surveyFirst.setVisibility(View.VISIBLE);
        surveySecond.setVisibility(View.GONE);
        detail = false;
    }

    public void processOutput(JSONObject response){
        if (response.toString().contains("data")){
            try {
                codeResponse = response.getString(PARAM_CODE);
                JSONArray userArray = response.getJSONArray("data");
                JSONObject userInfo = userArray.getJSONObject(0);
                idUser = userInfo.getString(PARAM_USER);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            try {
                codeResponse = response.getString(PARAM_CODE);
                mSurveyId = response.getString(PARAM_SURVEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (codeResponse.contentEquals("0")){
            showDialog(getString(R.string.error));

        }else if (codeResponse.contentEquals("1")){
            Bundle args = new Bundle();
            args.putString(PARAM_USER, mUserId);

            EndProcessFragment endProcessFragment = new EndProcessFragment();
            endProcessFragment.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_evaluacion_container, endProcessFragment, EvaluacionActivity.TAG_EVAL_END_PROCESS);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(EvaluacionActivity.TAG_EVAL_END_PROCESS);
            transaction.commit();

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
