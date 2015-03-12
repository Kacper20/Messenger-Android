package com.example.kacper.messenger.data;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by kacper on 24.01.15.
 */
public class User extends RealmObject {


    private String login;
    private RealmList<Message> receivedMessage;
    private RealmList<Message> sendMessage;
    private Date lastActivityDate;
    public static User createUser(String login, Realm realm){
        User user = null;
        RealmResults<User> results = realm.where(User.class).equalTo("login", login).findAll();
        if (results.size() == 0){
            realm.beginTransaction();
            user = realm.createObject(User.class);
            user.setLogin(login);
            realm.commitTransaction();
        }
        else{
            user = results.get(0);
        }
        return user;

    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public RealmList<Message> getReceivedMessage() {
        return receivedMessage;
    }
    public RealmList<Message> getSendMessage() {
        return sendMessage;
    }
    public void setReceivedMessage(RealmList<Message> receivedMessage) {
        this.receivedMessage = receivedMessage;
    }
    public void setSendMessage(RealmList<Message> sendMessage) {
        this.sendMessage = sendMessage;
    }
    public Date getLastActivityDate() {
        return lastActivityDate;
    }
    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}
