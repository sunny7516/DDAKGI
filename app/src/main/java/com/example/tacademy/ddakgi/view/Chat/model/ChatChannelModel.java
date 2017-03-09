package com.example.tacademy.ddakgi.view.Chat.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 채팅 채널에 저장된 상대방 정보
 * "상대방 아이디":"kkk",
 * "마지막 메시지":"hi",
 * "읽기 여부":1,
 * "시간 (송신/저장)":487946515515,
 * "상대방 프로필":"http://x.x./a.jpg"
 */

public class ChatChannelModel {
    int mid;    // 신고할 때 상대방 회원번호
    String uid;
    String lastMsg;
    int readCount;
    long time;
    String profile;
    String chatting_channel;

    public ChatChannelModel() {
    }

    public ChatChannelModel(int mid, String uid, String lastMsg, int readCount, long time, String profile) {
        this.mid = mid;
        this.uid = uid;
        this.lastMsg = lastMsg;
        this.readCount = readCount;
        this.time = time;
        this.profile = profile;
    }

    public Map<String, Object> toChannelMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("mid", mid);
        map.put("uid", uid);
        map.put("lastMsg", lastMsg);
        map.put("readCount", readCount);
        map.put("time", time);
        map.put("profile", profile);
        map.put("chatting_channel", chatting_channel);
        return map;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getChatting_channel() {
        return chatting_channel;
    }

    public void setChatting_channel(String chatting_channel) {
        this.chatting_channel = chatting_channel;
    }
}
