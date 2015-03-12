package com.example.kacper.messenger.utillity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * Created by kacper on 24.01.15.
 */
public class ApiHelper {
    /* Metody sprawdzające czy hasło i login są odpowiednie */

    public static final String BASE_PATH = "http://sikorski.ovh";
    public static final String PORT = ":8080/";
    public static final String NAME = "GolompServer";
    public static final String LOGIN_URL = BASE_PATH + PORT + NAME + "/login";
    public static final String LOGOUT_URL = BASE_PATH + PORT + NAME + "/logout";
    public static final String SEND_MESSAGE_URL = BASE_PATH + PORT + NAME + "/send";

    public static final int SUPPORTED_PROTOCOL_VERSION = 0;
    public static final String CHECK_MESSAGES_URL = BASE_PATH + PORT + NAME + "/incoming";
    public static final String TOKEN_PERSIST_KEY = "token_prefs";
    public static final String EXPIRING_DATE_PERSIST_KEY = "expiring_date_prefs";
    public static final String LOGGED_USER_KEY = "user_login";
    public static final String IS_USER_LOGGED_IN = "is_user_logged_in";
    public static final String MESSAGE_LIST_KEY = "msgList";



    public static SharedPreferences getSharedPreferences(Context context ){
        SharedPreferences sharedPf = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences prefs = new ObscuredSharedPreferences(context, sharedPf);
        return prefs;
    }
    public static String getUserLogin(Activity activity){
        return ApiHelper.getSharedPreferences(activity).getString(ApiHelper.LOGGED_USER_KEY, null);
    }

    public static String getJsonString(HttpURLConnection connection){
        BufferedReader reader;
        StringBuffer buffer = new StringBuffer();
        try {
            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Line for debug purposes
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
        }catch(IOException exception){

        }
        return buffer.toString();

    }
    public static void writeStringIntoHttpRequest(String jsonString, HttpURLConnection connection){
        try {
            OutputStream outputStream = null;
            outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void setUpConnection(HttpURLConnection connection, String method){
        try {
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }


    }

}
