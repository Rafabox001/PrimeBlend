package com.manguitostudios.primeblend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.EndProcessFragment;
import com.manguitostudios.primeblend.fragments.EvaluacionFirstFragment;
import com.manguitostudios.primeblend.fragments.MainActivityFragment;
import com.manguitostudios.primeblend.fragments.RegisterFragment;
import com.manguitostudios.primeblend.fragments.SurveyFragment;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_EVAL_FRAGMENT = "EvaluacionFragment";
    public static final String TAG_EVAL_CAPTURE = "RegisterFragment";
    public static final String TAG_MAIN="MainActivity";
    public String currentFragment = "";
    private String PARAM_USER = "user_id";
    private String mUserId;
    public static final String PREF_DATA = "json_data";
    public static final String PREF_LIST = "json_list";

    public void updateFragment(String currentFragment) {
        this.currentFragment = currentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        mUserId = intent.getStringExtra(PARAM_USER);

        if (mUserId != null){
            Bundle args = new Bundle();
            args.putString(PARAM_USER, mUserId);

            MainActivityFragment mainActivityFragment = new MainActivityFragment();
            mainActivityFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_main, mainActivityFragment, MainActivity.TAG_MAIN);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(MainActivity.TAG_MAIN);
            transaction.commit();

        }else if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_main, new EvaluacionFirstFragment(), TAG_EVAL_FRAGMENT)
                    .addToBackStack(TAG_EVAL_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        MainActivityFragment mainActivityFragment = (MainActivityFragment)fm.findFragmentByTag(TAG_MAIN);
        if (mainActivityFragment != null){
            showDialog(getString(R.string.logout));
        }else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public void onReceivedData(JSONObject object) {
        FragmentManager fm = getSupportFragmentManager();
        switch (currentFragment){
            case TAG_EVAL_CAPTURE:
                RegisterFragment registerFragment = (RegisterFragment)fm.findFragmentByTag(TAG_EVAL_CAPTURE);
                registerFragment.processOutput(object);
                break;


        }
    }

    public void showDialog (String message){
        new AlertDialog.Builder(this)
                .setTitle("Salir?")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with logout
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        if (prefs.contains(PREF_DATA)) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString(PREF_LIST, null); // value to store
                            editor.putString(PREF_DATA, null); // value to store
                            editor.commit();
                        }

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.fragment_main, new EvaluacionFirstFragment(), MainActivity.TAG_EVAL_FRAGMENT)
                                .addToBackStack(MainActivity.TAG_EVAL_FRAGMENT)
                                .commit();

                        }
                    }

                ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                }

        ).setIcon(android.R.drawable.ic_lock_idle_lock).show();

    }

}
