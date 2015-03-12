package com.example.kacper.messenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kacper.messenger.ContentMessageActivity;
import com.example.kacper.messenger.R;
import com.example.kacper.messenger.data.Message;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kacper on 26.01.15.
 */
public class ContentMessageFragment extends Fragment {

    private long messageId;
    RealmResults<Message> results;

    public ContentMessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageId = getActivity().getIntent().getLongExtra(ContentMessageActivity.ID_MESSAGE_TO_SHOW, 0);
        Log.d("Content message fragment", Long.toString(messageId));
        Realm realm = Realm.getInstance(getActivity());
        results = realm.where(Message.class).equalTo("id", messageId).findAll();
        Log.d("Content message fragment", Integer.toString(results.size()));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content_message, container, false);
        TextView contentTextView = (TextView)rootView.findViewById(R.id.message_content);


        Message message = results.get(0);
        contentTextView.setText(message.getContent());
        TextView recipientTextView = (TextView) rootView.findViewById(R.id.message_receiver_name);
        recipientTextView.setText(message.getReceiver().getLogin());
        TextView senderTextView = (TextView) rootView.findViewById(R.id.message_sender_name);
        senderTextView.setText(message.getSender().getLogin());

        return rootView;
    }
}