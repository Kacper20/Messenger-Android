package com.example.kacper.messenger.data;

import android.util.Log;

import io.realm.Realm;

/**
 * Created by kacper on 25.01.15.
 */
public class DbHelper {

    public static void cleanDatabase(Realm realm){
        realm.beginTransaction();
        realm.allObjects(User.class).clear();
        realm.allObjects(Message.class).clear();
        realm.commitTransaction();
    }
    public static void checkSize(Realm realm){
        int size = realm.allObjects(Message.class).size();
        Log.d("Realm DEBUG", Integer.toString(size));
    }
}
