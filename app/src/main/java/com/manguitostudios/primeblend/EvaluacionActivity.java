package com.manguitostudios.primeblend;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.EndProcessFragment;
import com.manguitostudios.primeblend.fragments.EvaluacionFirstFragment;
import com.manguitostudios.primeblend.fragments.RegisterFragment;
import com.manguitostudios.primeblend.fragments.SurveyFragment;

import org.json.JSONObject;

public class EvaluacionActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_EVAL_FRAGMENT = "EvaluacionFragment";
    public static final String TAG_EVAL_CAPTURE = "RegisterFragment";
    public static final String TAG_EVAL_SURVEY = "SurveyFragment";
    public static final String TAG_EVAL_END_PROCESS = "EndProcess";
    public String currentFragment = "";


    public void updateFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_evaluacion_container, new EvaluacionFirstFragment(), TAG_EVAL_FRAGMENT)
                    .addToBackStack(TAG_EVAL_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        EndProcessFragment endProcessFragment = (EndProcessFragment)fm.findFragmentByTag(TAG_EVAL_END_PROCESS);
        SurveyFragment surveyFragment = (SurveyFragment)fm.findFragmentByTag(TAG_EVAL_SURVEY);
        if (endProcessFragment !=null){
            if (endProcessFragment.detail){
                endProcessFragment.navigateBack();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }else if (surveyFragment !=null){
            if (surveyFragment.detail){
                surveyFragment.navigateBack();
            }else{
                getSupportFragmentManager().popBackStack();
            }
        }else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public void onReceivedData(JSONObject object) {
        Log.d("EvaluacionActivity", object.toString());
        Log.d("EvaluacionActivity", currentFragment);
        FragmentManager fm = getSupportFragmentManager();
        switch (currentFragment){
            case TAG_EVAL_CAPTURE:
                RegisterFragment registerFragment = (RegisterFragment)fm.findFragmentByTag(TAG_EVAL_CAPTURE);
                registerFragment.processOutput(object);
                break;
            case TAG_EVAL_SURVEY:
                SurveyFragment surveyFragment = (SurveyFragment)fm.findFragmentByTag(TAG_EVAL_SURVEY);
                surveyFragment.processOutput(object);
                break;
            case TAG_EVAL_END_PROCESS:
                EndProcessFragment endProcessFragment = (EndProcessFragment)fm.findFragmentByTag(TAG_EVAL_END_PROCESS);
                endProcessFragment.processOutput(object);
                break;

        }
    }
}