package com.manguitostudios.primeblend.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

    private final String NETWORK_TAG = RegisterCalls.class.getSimpleName();
    private final Context mContext;
    private onResponseRegister listener;
    private JSONObject data;
    private String responseJsonStr = null;
    private Request typeRequest;
    private String url;
    private String name, email, phone, career;

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
                url = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=get_users&email=" + email;
                break;
            case registerRequest:
                name = params[0];
                email = params[1];
                phone = params[2];
                career = params[3];
                url = "http://primeblend.life/_app/wp-admin/admin-ajax.php?action=api&method=set_user";

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
                    urlConnection.addRequestProperty("brand","monogram");
                    urlConnection.connect();
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
                if(typeRequest == Request.dataRequest) {
                    listener.onReceivedData(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public static enum Request {
        registerRequest,dataRequest
    }


}
