package com.example.kacper.messenger.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kacper.messenger.ContentMessageActivity;
import com.example.kacper.messenger.MessagesActivity;
import com.example.kacper.messenger.R;
import com.example.kacper.messenger.adapters.MyMessageAdapter;
import com.example.kacper.messenger.data.Message;
import com.example.kacper.messenger.utillity.ApiHelper;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kacper on 25.01.15.
 */
public class MessagesFragment extends Fragment {


    private MyMessageAdapter messagesAdapter;
    public MessagesFragment() {
    }

    private String userToShow;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MessagesFragment", "tworzymy");
        super.onCreate(savedInstanceState);
        userToShow = getActivity().getIntent().getStringExtra(MessagesActivity.USER_TO_SHOW_MESSAGES_LOGIN);
        Log.d("Messages Fragment","Pokazujemy wiadomosci z " +  userToShow);
        getActivity().setTitle(userToShow);
        Realm realm = Realm.getInstance(getActivity());
        String userLogin = ApiHelper.getSharedPreferences(getActivity()).getString(ApiHelper.LOGGED_USER_KEY, null);
        RealmResults<Message> messages = realm.where(Message.class)
                .beginGroup()
                .equalTo("sender.login", userToShow)
                .or()
                .equalTo("receiver.login", userToShow)
                .endGroup()
                .beginGroup()
                .equalTo("sender.login", userLogin)
                .or()
                .equalTo("receiver.login", userLogin)
                .endGroup()
                .findAll();

        messagesAdapter = new MyMessageAdapter(getActivity(), R.id.listview_messages, messages, true );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_messages);
        listView.setAdapter(messagesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message messageToshow = messagesAdapter.getRealmResults().get(position);
                Intent intent = new Intent(getActivity(), ContentMessageActivity.class);
                intent.putExtra(ContentMessageActivity.ID_MESSAGE_TO_SHOW, messageToshow.getId());
                startActivity(intent);
            }
        });
        return rootView;
    }
}

