package com.example.tacademy.ddakgi.view.Chat.model;

/**
 * 채팅창에서 필요한 데이터
 */

public class ChatModel {
    String sender;
    String msg;
    String type;
    long time;
    int read;

    public ChatModel() {
    }

    public ChatModel(String sender, String msg, String type, long time, int read) {
        this.sender = sender;
        this.msg = msg;
        this.type = type;
        this.time = time;
        this.read = read;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
