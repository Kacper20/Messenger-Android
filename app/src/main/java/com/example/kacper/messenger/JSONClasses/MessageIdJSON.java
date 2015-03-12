package com.example.kacper.messenger.JSONClasses;

/**
 * Created by kacper on 27.01.15.
 */
public class MessageIdJSON {
    long msgId;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public MessageIdJSON(long msgId) {

        this.msgId = msgId;
    }
}
