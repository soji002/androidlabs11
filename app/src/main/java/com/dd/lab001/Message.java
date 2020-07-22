package com.dd.lab001;
public class Message {
    public String message;
    public boolean isSend;
    public int id;

    public Message(int id , String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
        this.id=id;
    }

    public Message(String msg, boolean isSend) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}