package com.example.kacper.messenger;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kacper.messenger.NetworkTasks.FetchMessagesTask;
import com.example.kacper.messenger.NetworkTasks.LogoutTask;
import com.example.kacper.messenger.utillity.ApiHelper;

/**
 * Created by kacper on 25.01.15.
 */
public class MainLogoutActivity extends ActionBarActivity {


    protected void logout(){
        SharedPreferences sharedPreferences= ApiHelper.getSharedPreferences(this);
        String Token = sharedPreferences.getString(ApiHelper.TOKEN_PERSIST_KEY, null);
        LogoutTask logoutTask = new LogoutTask();
        logoutTask.execute();
        sharedPreferences.edit().remove(ApiHelper.TOKEN_PERSIST_KEY).apply();
        sharedPreferences.edit().putBoolean(ApiHelper.IS_USER_LOGGED_IN, false).apply();
        startActivity(new Intent(this, LoginActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        else if (id == R.id.action_create_message){
            startCreateMessageActivity();
            return true;
        }
        else if (id == R.id.action_refresh){
            new FetchMessagesTask(this).execute();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startCreateMessageActivity(){
        startActivity(new Intent(this, CreateMessageActivity.class));
    }
}
