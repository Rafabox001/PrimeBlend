package com.manguitostudios.primeblend.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.manguitostudios.primeblend.Utils.Constants;
import com.manguitostudios.primeblend.Utils.onResponseRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by manguitodeveloper01 on 10/7/15.
 */
public class RegisterCalls extends AsyncTask<String,Void,Boolean> {

    ProgressDialog progressDialog;

    private final String NETWORK_TAG = RegisterCalls.class.getSimpleName();
    private final Context mContext;
    private onResponseRegister listener;
    private JSONObject data;
    private String responseJsonStr = null;
    private Request typeRequest;
    private String url;
    private String name, email, phone, career, line, category_id;
    private String survey1, survey2, survey3, survey4, survey5, survey6, survey7, userId;

    @Override
    protected void onPreExecute() {
        progressDialog= ProgressDialog.show(mContext, "Enviando","Realizando operación, por favor espere", true);
        super.onPreExecute();
    }

    public RegisterCalls(Context c){
        mContext = c;
        listener = (onResponseRegister) mContext;
        typeRequest = Request.dataRequest;
    }

    public RegisterCalls(Context c, Request type){
        mContext = c;
        listener = (onResponseRegister) mContext;
        typeRequest = type;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Uri requestURL = null;


        switch (typeRequest){
            case dataRequest:
                email = params[0];
                url = Constants.validateEmailUrl + email;
                break;
            case registerRequest:
                name = params[0];
                email = params[1];
                phone = params[2];
                career = params[3];
                line = params[4];
                url = Constants.registerUrl;
                break;
            case categoriesRequest:
                url = Constants.getAllCategories;
                break;
            case productsRequest:
                url = Constants.getProductsByCategory + params [0];
                category_id = params[0];
                break;
            case surveyRequest:
                url = Constants.postSurvey;
                survey1 = params[0];
                survey2 = params[1];
                survey3 = params[2];
                survey4 = params[3];
                survey5 = params[4];
                survey6 = params[5];
                survey7 = params[6];
                userId = params[7];
                break;
            case calendarRequest:
                url = Constants.calendar_events;
                break;
            case registerMail:
                url = Constants.register_calendar_mail;
                survey1 = params[0];
                userId = params[1];
                break;
            case surveyBuyRequest:
                url = Constants.postSurvey2;
                survey1 = params[0];
                survey2 = params[1];
                survey3 = params[2];
                survey4 = params[3];
                survey5 = params[4];
                survey6 = params[5];
                userId = params[6];
                break;
            case exchangeRateRequest:
                url = Constants.exchange_rate;
                break;
            case valuationRequest:
                url = Constants.sendValuation;
                survey1 = params[0];
                userId = params[1];
                break;

        }

        return retrieveData(url);
    }

    private boolean retrieveData(String requestedURL){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try{

            //Final URL for request
            URL url = new URL(requestedURL);

            Log.d(typeRequest.toString(), requestedURL);

            switch (typeRequest) {
                case dataRequest:
                    //Creation for the request of movies data
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.addRequestProperty("email", email);
                    urlConnection.connect();
                    break;
                case registerRequest:
                    //Creation for the request of register data
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("name", name);
                    urlConnection.addRequestProperty("email", email);
                    urlConnection.addRequestProperty("phone", phone);
                    urlConnection.addRequestProperty("career", career);
                    urlConnection.addRequestProperty("brand", line);
                    urlConnection.connect();
                    break;
                case categoriesRequest:
                    //Creation for the request of movies data
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    break;
                case productsRequest:
                    //Creation for the request of movies data
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.addRequestProperty("category_id", category_id);
                    urlConnection.connect();
                    break;
                case surveyRequest:
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.addRequestProperty("q1", survey1);
                    urlConnection.addRequestProperty("q2", survey3);
                    urlConnection.addRequestProperty("q3", survey4);
                    urlConnection.addRequestProperty("q3text", survey5);
                    urlConnection.addRequestProperty("q4", survey6);
                    urlConnection.addRequestProperty("comments", survey7);
                    urlConnection.addRequestProperty("userid", userId);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    break;
                case calendarRequest:
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    break;
                case registerMail:
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("evento", survey1);
                    urlConnection.addRequestProperty("userid", userId);
                    break;
                case surveyBuyRequest:
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.addRequestProperty("q1", survey1);
                    urlConnection.addRequestProperty("q1other", survey3);
                    urlConnection.addRequestProperty("q1mount", survey4);
                    urlConnection.addRequestProperty("q2", survey5);
                    urlConnection.addRequestProperty("q3", survey6);
                    urlConnection.addRequestProperty("q4", survey7);
                    urlConnection.addRequestProperty("userid", userId);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    break;
                case exchangeRateRequest:
                    //Creation for the request of movies data
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    break;
                case valuationRequest:
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.addRequestProperty("json", survey1);
                    urlConnection.addRequestProperty("userid", userId);
                    break;

            }



            //Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return false; //Nothing to do.
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return false; //Has no lines. String empty.
            }

            responseJsonStr = buffer.toString();
            Log.w(NETWORK_TAG,responseJsonStr);
            return true;
        }catch (IOException e){
            Log.e(NETWORK_TAG, e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result){
            try {
                data = new JSONObject(responseJsonStr);
                listener.onReceivedData(data);

            } catch (JSONException e) {
                e.printStackTrace();
                data = new JSONObject();
                if (!responseJsonStr.contains("code")){
                    try {
                        data.put("code", 2);
                        data.put("exchange_rate", responseJsonStr.trim());
                        listener.onReceivedData(data);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

            }
        }
        progressDialog.dismiss();
        super.onPostExecute(result);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public static enum Request {
        registerRequest,
        dataRequest,
        categoriesRequest,
        productsRequest,
        surveyRequest,
        calendarRequest,
        registerMail,
        surveyBuyRequest,
        exchangeRateRequest,
        valuationRequest,
    }


}
