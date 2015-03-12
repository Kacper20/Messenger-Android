package com.example.kacper.messenger.NetworkTasks;

import android.os.AsyncTask;

import com.example.kacper.messenger.JSONClasses.TokenJSON;
import com.example.kacper.messenger.utillity.ApiHelper;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kacper on 26.01.15.
 */
public class LogoutTask extends AsyncTask<String, Void, Boolean>{

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        if (params == null){
            return false;
        }
        try {
            String token = params[0];
            URL logoutUrl = new URL(ApiHelper.LOGOUT_URL);
            HttpURLConnection connection = (HttpURLConnection) logoutUrl.openConnection();
            Gson gson = new Gson();
            String jsonData = gson.toJson(new TokenJSON(token));
            ApiHelper.writeStringIntoHttpRequest(jsonData, connection);
            connection.connect();
            // Read the input stream into a String
            if (connection.getResponseCode() == 200){
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}