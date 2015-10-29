package com.manguitostudios.primeblend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.manguitostudios.primeblend.Utils.onResponseRegister;
import com.manguitostudios.primeblend.fragments.CalendarFragment;
import com.manguitostudios.primeblend.fragments.CatalogoFirstFragment;
import com.manguitostudios.primeblend.fragments.EvaluacionFirstFragment;
import com.manguitostudios.primeblend.fragments.ProductsFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class CalendarActivity extends AppCompatActivity implements onResponseRegister {

    public static final String TAG_CALENDAR_FRAGMENT = "CalendarFragment";

    private String PARAM_USER = "user_id";
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(PARAM_USER);

        if (savedInstanceState == null){
            Bundle args = new Bundle();
            args.putString(PARAM_USER, mUserId);

            CalendarFragment calendarFragment = new CalendarFragment();
            calendarFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_calendar_container, calendarFragment, CalendarActivity.TAG_CALENDAR_FRAGMENT);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.addToBackStack(CalendarActivity.TAG_CALENDAR_FRAGMENT);
            transaction.commit();


        }

    }

    @Override
    public void onReceivedData(JSONObject object) {
        FragmentManager fm = getSupportFragmentManager();
        CalendarFragment calendarFragment = (CalendarFragment)fm.findFragmentByTag(TAG_CALENDAR_FRAGMENT);
        try {
            calendarFragment.processOutput(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
