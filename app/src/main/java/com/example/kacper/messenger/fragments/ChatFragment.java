package com.example.kacper.messenger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kacper.messenger.JSONClasses.MessageJSON;
import com.example.kacper.messenger.MessagesActivity;
import com.example.kacper.messenger.NetworkTasks.FetchMessagesTask;
import com.example.kacper.messenger.R;
import com.example.kacper.messenger.adapters.MyChatAdapter;
import com.example.kacper.messenger.data.DbHelper;
import com.example.kacper.messenger.data.Message;
import com.example.kacper.messenger.data.User;
import com.example.kacper.messenger.utillity.ApiHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by kacper on 25.01.15.
 */

    public  class ChatFragment extends Fragment {

        private MyChatAdapter mChatsAdapter;


    public ChatFragment(){
        setHasOptionsMenu(true);
    }
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Realm realm = Realm.getInstance(getActivity());

        realm.addChangeListener(new RealmChangeListener() {
            @Override
            public void onChange() {
                Log.d("REALM", "zmiana!");
            }
        });
        super.onCreate(savedInstanceState);
        String userLogin = ApiHelper.getSharedPreferences(getActivity()).getString(ApiHelper.LOGGED_USER_KEY, null);
        RealmResults<Message> us = realm.where(Message.class).findAll();
        RealmResults<User> users = realm.where(User.class)
                .notEqualTo("login", userLogin)
                .equalTo("receivedMessage.sender.login", userLogin)
                .or()
                .equalTo("sendMessage.receiver.login", userLogin)
                .findAll();
        users.sort("lastActivityDate", false);
        mChatsAdapter = new MyChatAdapter(getActivity(), R.id.listview_chats, users, true );
    }


    public void loadData1(String from){
        String jsonMessagesData = null;
        String userLogin = ApiHelper.getSharedPreferences(getActivity()).getString(ApiHelper.LOGGED_USER_KEY, null);

        try {
            InputStream stream = getActivity().getAssets().open(from);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            Gson gson = new Gson();
            jsonMessagesData = new String(buffer);
            Log.d("Chat fragment", "dane: " + jsonMessagesData);
            JSONArray array = new JSONArray(jsonMessagesData);
            Log.d("Chat fragment", "rozmiar array to: " + Integer.toString(array.length()));
            for (int i = 0; i < array.length(); i ++) {
                JSONObject obj = array.getJSONObject(i);
                MessageJSON messageJSON = gson.fromJson(obj.toString(), MessageJSON.class);
                messageJSON.setReceiver(userLogin);
                messageJSON.setRead(false);
                Message message = Message.createMessage(messageJSON, Realm.getInstance(getActivity()));
                Log.d("Chat fragment", "stworzono: " + messageJSON.toString());
            }
        } catch (Exception e) {
            // Handle exceptions here
        }
    }





    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_recent_chat, container, false);
            ListView listView = (ListView) rootView.findViewById(R.id.listview_chats);
            listView.setAdapter(mChatsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getActivity(), MessagesActivity.class);
                    User user = mChatsAdapter.getRealmResults().get(position);
                    i.putExtra(MessagesActivity.USER_TO_SHOW_MESSAGES_LOGIN, user.getLogin());
                    startActivity(i);

                }
            });

            return rootView;
        }
    }

