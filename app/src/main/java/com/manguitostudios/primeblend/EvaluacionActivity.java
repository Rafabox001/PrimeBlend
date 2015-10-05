package com.manguitostudios.primeblend;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.manguitostudios.primeblend.fragments.EvaluacionFirstFragment;

public class EvaluacionActivity extends AppCompatActivity {

    public static final String TAG_EVAL_FRAGMENT = "EvaluacionFragment";
    public static final String TAG_EVAL_CAPTURE = "RegisterFragment";

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
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

}
