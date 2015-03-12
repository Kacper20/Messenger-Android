package com.example.kacper.messenger.data;

import com.example.kacper.messenger.JSONClasses.MessageJSON;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by kacper on 24.01.15.
 */
public class Message extends RealmObject {
    private String content;
    private long id;
    private Date deliveryDate;
    private Date sendDate;
    private boolean read;
    private User receiver;
    private User sender;



    public static Message createMessage(MessageJSON jsonData, Realm realm){
        Map<String, Object> jsonDict = new HashMap<>();
        Gson gson = new Gson();
        Message message = null;
        try {
            String receiverLogin = jsonData.getReceiver();
            String senderLogin = jsonData.getSender();
            long messageId = jsonData.getId();
        /* zapytanie do bazy danych - czy mamy już taki obiekt. */
        RealmResults<Message> results = realm.where(Message.class).equalTo("id", messageId).findAll();
        if (results.size() == 0){
            realm.beginTransaction();
            message = realm.createObject(Message.class);
            message.setId(messageId);
            message.setContent(jsonData.getContent());
            message.setRead(jsonData.isRead());
            message.setDeliveryDate(new Date(jsonData.getDeliveryDate()));
            message.setSendDate(new Date(jsonData.getSendDate()));
            realm.commitTransaction();
            User receiver = User.createUser(receiverLogin, realm);
            User sender = User.createUser(senderLogin, realm);
            realm.beginTransaction();
            Date activityDate = receiver.getLastActivityDate();
            //TODO: LOGIC TO receiving/sending
            //Update message sender and received fields
            message.setReceiver(User.createUser(receiverLogin, realm));
            message.setSender(User.createUser(senderLogin, realm));
            receiver.getReceivedMessage().add(message);
            sender.getSendMessage().add(message);
            realm.commitTransaction();

        }
        else{
            message = results.get(0);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return message;
    }


    /* Setters & getters */

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
