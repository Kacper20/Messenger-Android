package com.example.kacper.messenger.JSONClasses;

/**
 * Created by kacper on 26.01.15.
 */
public class MessageJSON {
    private long id;
    private String sender;
    private String receiver;
    private String content;
    private long deliveryDate;
    private long sendDate;
    private boolean read;

    public MessageJSON(long id, String sender, String content, long deliveryDate, long sendDate) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.deliveryDate = deliveryDate;
        this.sendDate = sendDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public long getSendDate() {
        return sendDate;
    }

    public void setSendDate(long sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
