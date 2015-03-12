package com.example.kacper.messenger.JSONClasses;

/**
 * Created by kacper on 26.01.15.
 */
public class SendMessageJSON {

    private String token;
    private String recipient;
    private long deliveryDate;
    private String content;

    public SendMessageJSON(String token, String recipient, long deliveryDate, String content) {
        this.token = token;
        this.recipient = recipient;
        this.deliveryDate = deliveryDate;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
