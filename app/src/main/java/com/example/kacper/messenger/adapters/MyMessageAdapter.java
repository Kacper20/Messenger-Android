package com.example.kacper.messenger.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kacper.messenger.data.Message;
import com.example.kacper.messenger.data.User;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by kacper on 25.01.15.
 */
public class MyMessageAdapter extends RealmBaseAdapter<Message> implements ListAdapter {

    private static class ViewHolder {
        TextView userLogin;
    }
    public MyMessageAdapter(Context context, int resId, RealmResults<Message> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userLogin = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message item = realmResults.get(position);
        viewHolder.userLogin.setText(item.getContent());
        return convertView;
    }



    public RealmResults<Message> getRealmResults() {
        return realmResults;
    }


}