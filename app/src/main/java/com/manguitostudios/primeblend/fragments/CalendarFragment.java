package com.manguitostudios.primeblend.fragments;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manguitostudios.primeblend.R;
import com.manguitostudios.primeblend.network.RegisterCalls;
import com.manguitostudios.primeblend.objects.Product;
import com.tyczj.extendedcalendarview.CalendarProvider;
import com.tyczj.extendedcalendarview.Event;
import com.tyczj.extendedcalendarview.ExtendedCalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Rafael on 04/10/2015.
 */
public class CalendarFragment extends Fragment{

    @Bind(R.id.container)ImageView main_container;
    @Bind(R.id.transparency)ImageView transparency;

    @Bind(R.id.calendarView)ExtendedCalendarView calendar;
    @Bind(R.id.evento)EditText evento;
    @Bind(R.id.calendarSuscribe)RadioGroup suscribir;

    private String codeResponse;
    private String PARAM_CODE = "code";
    private String PARAM_USER = "user_id";
    private String mUserId;

    private Boolean suscribe = false;

    public CalendarFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            mUserId = bundle.getString(PARAM_USER);
        }

        Glide.with(getActivity())
                .load(R.drawable.background8)
                .into(main_container);

        Glide.with(getActivity())
                .load(R.drawable.screen_transparency)
                .into(transparency);

        RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.calendarRequest);
        registerCalls.execute();

        suscribir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case 1:
                        suscribe = true;
                        break;
                    case 2:
                        suscribe = false;
                        break;
                }
            }
        });

        return rootView;
    }

    public void processOutput(JSONObject response) throws JSONException {
        final String RESULT_CODE = "code";
        final String RESULT_ARRAY = "data";
        final String EVENT_ID = "event_id";
        final String EVENT_NAME = "name";
        final String EVENT_DESCRIPTION = "description";
        final String EVENT_DATE = "date";
        String timeInMillis;
        final String REGISTER_MAIL = "events_email_id";
        if (response.toString().contains("events_email_id")){
            if (response.getString(RESULT_CODE).contentEquals("1")){
                Toast.makeText(getActivity(), getString(R.string.register_mail),
                        Toast.LENGTH_LONG).show();
            }

        }else if (response.getString(RESULT_CODE).contentEquals("1")){
            try {
                JSONArray eventsArray = response.getJSONArray(RESULT_ARRAY);

                //Because of memory issues I´m limiting the results to 30, if necesary I will implement a method to retrieve next images
                for (int i = 0; i < eventsArray.length(); i++) {
                    JSONObject obj = eventsArray
                            .getJSONObject(i);
                    timeInMillis = obj.getString(EVENT_DATE);

                    ContentValues values = new ContentValues();
                    values.put(CalendarProvider.COLOR, Event.COLOR_PURPLE);
                    values.put(CalendarProvider.DESCRIPTION, obj.getString(EVENT_DESCRIPTION));
                    values.put(CalendarProvider.EVENT, obj.getString(EVENT_NAME));

                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(Long.valueOf(timeInMillis));
                    cal.add(Calendar.MONTH, 1);

                    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);

                    Log.d("DATE", String.valueOf(dayOfMonth) + "/" + String.valueOf(month) + "/" + String.valueOf(year));


                    TimeZone tz = TimeZone.getDefault();

                    int StartDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));
                    values.put(CalendarProvider.START, cal.getTimeInMillis());
                    values.put(CalendarProvider.START_DAY, StartDayJulian);

                    cal.set(year, month, dayOfMonth, cal.get(Calendar.HOUR_OF_DAY) + 4, cal.get(Calendar.MINUTE));
                    int endDayJulian = Time.getJulianDay(cal.getTimeInMillis(), TimeUnit.MILLISECONDS.toSeconds(tz.getOffset(cal.getTimeInMillis())));

                    values.put(CalendarProvider.END, cal.getTimeInMillis());
                    values.put(CalendarProvider.END_DAY, endDayJulian);


                    Uri uri = getActivity().getContentResolver().insert(CalendarProvider.CONTENT_URI, values);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {

        }

    }

    @OnClick(R.id.send)
    public void sendSuscription(){
        if (suscribe){
            RegisterCalls registerCalls = new RegisterCalls(getActivity(), RegisterCalls.Request.registerMail);
            registerCalls.execute(evento.getText().toString(), mUserId);
        }
    }

    public void showDialog (String message){
        new AlertDialog.Builder(getActivity())
                .setTitle("eMAIL ENVIADO")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue
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
