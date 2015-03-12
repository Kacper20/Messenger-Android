package com.example.kacper.messenger.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kacper.messenger.data.User;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by kacper on 25.01.15.
 */
public class MyChatAdapter extends RealmBaseAdapter<User> implements ListAdapter {

    private static class ViewHolder {
        TextView userLogin;
    }
    public MyChatAdapter(Context context, int resId, RealmResults<User> realmResults, boolean automaticUpdate) {
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
        User item = realmResults.get(position);
        viewHolder.userLogin.setText(item.getLogin());
        return convertView;
    }



    public RealmResults<User> getRealmResults() {
        return realmResults;
    }


}
