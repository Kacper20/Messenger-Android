package com.example.kacper.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;

import com.example.kacper.messenger.NetworkTasks.FetchMessagesTask;
import com.example.kacper.messenger.fragments.ChatFragment;
import com.example.kacper.messenger.utillity.ApiHelper;

import java.sql.Timestamp;


public class RecentChatActivity extends MainLogoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchMessagesTask(this).execute();
        setTitle("Inbox");
        setContentView(R.layout.activity_recent_chat);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ChatFragment())
                    .commit();
        }
        SharedPreferences prefs = ApiHelper.getSharedPreferences(getApplicationContext());
        if (prefs.getBoolean(ApiHelper.IS_USER_LOGGED_IN, false) == false){

            logoutButtonTapped(prefs);
        }
        //else check token
        else{
            long tokenInMillis = prefs.getLong(ApiHelper.EXPIRING_DATE_PERSIST_KEY, 1);

            Timestamp timestamp = new Timestamp(tokenInMillis);
            boolean shouldLogin = timestamp.before(new Timestamp(System.currentTimeMillis()));
            if (shouldLogin){
                logoutButtonTapped(prefs);
            }
        }
      }
    public void logoutButtonTapped(SharedPreferences prefs){
        prefs.edit().remove(ApiHelper.LOGGED_USER_KEY);
        prefs.edit().putBoolean(ApiHelper.IS_USER_LOGGED_IN, false);
        prefs.edit().remove(ApiHelper.TOKEN_PERSIST_KEY);
        startActivity(new Intent(this, LoginActivity.class));


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_recent_chat, menu);
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
