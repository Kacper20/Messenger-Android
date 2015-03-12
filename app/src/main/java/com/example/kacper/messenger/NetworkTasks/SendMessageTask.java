package com.example.kacper.messenger.NetworkTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.kacper.messenger.JSONClasses.MessageIdJSON;
import com.example.kacper.messenger.JSONClasses.MessageJSON;
import com.example.kacper.messenger.JSONClasses.SendMessageJSON;
import com.example.kacper.messenger.data.Message;
import com.example.kacper.messenger.utillity.ApiHelper;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import io.realm.Realm;

/**
 * Created by kacper on 26.01.15.
 */
public class SendMessageTask extends AsyncTask<String, Void, Boolean> {

    private Context mContext;
    private String recipient;
    private Date deliveryDate;
    private String content;

    public SendMessageTask(Context mContext, String recipient, Date deliveryDate, String content) {
        this.mContext = mContext;
        this.recipient = recipient;
        this.deliveryDate = deliveryDate;
        this.content = content;
    }


    @Override
    protected Boolean doInBackground(String... params) {

        try {
            SharedPreferences prefs = ApiHelper.getSharedPreferences(mContext);
            String token = prefs.getString(ApiHelper.TOKEN_PERSIST_KEY, null);
            String sender = prefs.getString(ApiHelper.LOGGED_USER_KEY, null);
            URL logoutUrl = new URL(ApiHelper.SEND_MESSAGE_URL);
            HttpURLConnection connection = (HttpURLConnection) logoutUrl.openConnection();
            ApiHelper.setUpConnection(connection, "POST");
            Gson gson = new Gson();
            String jsonData = gson.toJson(new SendMessageJSON(token, recipient, deliveryDate.getTime(), content));
            Log.d("Send messageTask", jsonData);
            ApiHelper.writeStringIntoHttpRequest(jsonData, connection);
            connection.connect();
            // Read the input stream into a String
            if (connection.getResponseCode() == 200){
                //Create message in database.
                String jsonAnswer = ApiHelper.getJsonString(connection);
                MessageIdJSON messageIdJSON = gson.fromJson(jsonAnswer, MessageIdJSON.class);
                MessageJSON message = new MessageJSON(messageIdJSON.getMsgId(), sender, content, deliveryDate.getTime(), new Date().getTime());
                message.setReceiver(recipient);
                Log.d("SendMessage Task",""  + message.getId() + " " + message.getSender() + " " + message.getReceiver());
                Message.createMessage(message, Realm.getInstance(mContext));

                Realm realm = Realm.getInstance(mContext);
                Log.d("Send Message Task", "" + realm.where(Message.class).equalTo("id", message.getId()).findAll().size());
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        String toShow = aBoolean == true ? "Message send successfully" : "Error while sending message";
        Toast.makeText(mContext, toShow, Toast.LENGTH_LONG).show();

    }
}
