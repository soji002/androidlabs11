package com.dd.lab001;
public class Message {
    public String message;
    public boolean isSend;
    private long id;

    public Message(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}