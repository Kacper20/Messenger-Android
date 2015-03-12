package com.example.kacper.messenger.NetworkTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.kacper.messenger.JSONClasses.MessageJSON;
import com.example.kacper.messenger.JSONClasses.TokenJSON;
import com.example.kacper.messenger.data.Message;
import com.example.kacper.messenger.utillity.ApiHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;

/**
 * Created by kacper on 25.01.15.
 */
public class FetchMessagesTask extends AsyncTask<String, Void, Boolean> {


    private Context mContext;

    public FetchMessagesTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            String token = ApiHelper.getSharedPreferences(mContext).getString(ApiHelper.TOKEN_PERSIST_KEY, null);
            Log.d("Fetch messages task token", token);
            URL logoutUrl = new URL(ApiHelper.CHECK_MESSAGES_URL);
            HttpURLConnection connection = (HttpURLConnection) logoutUrl.openConnection();
            ApiHelper.setUpConnection(connection, "POST");
            Gson gson = new Gson();
            String jsonData = gson.toJson(new TokenJSON(token));
            Log.d("Fetch messages task", jsonData);
            ApiHelper.writeStringIntoHttpRequest(jsonData, connection);
            connection.connect();
            if (connection.getResponseCode() == 200){
                Log.d("fetch messages task", "response 200");
                String jsonMessagesData = ApiHelper.getJsonString(connection);
                Log.d("Fetch messages task", jsonMessagesData);
                Realm realm = Realm.getInstance(mContext);
                String userLoggedIn = ApiHelper.getSharedPreferences(mContext).getString(ApiHelper.LOGGED_USER_KEY, null);
                // Now parse JSON :)
                JSONObject messagesJSON = new JSONObject(jsonMessagesData);

                JSONArray array = messagesJSON.getJSONArray(ApiHelper.MESSAGE_LIST_KEY);
                for (int i = 0; i < array.length(); i ++){
                    JSONObject obj = array.getJSONObject(i);
                    Log.d("Fetch Messages Task", obj.toString());
                    MessageJSON messageInJson = gson.fromJson(obj.toString(), MessageJSON.class);
                    messageInJson.setRead(false);
                    messageInJson.setReceiver(userLoggedIn);
                    Message.createMessage(messageInJson, realm);
                }
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Fetch messages task", "nie 200!!");


        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
