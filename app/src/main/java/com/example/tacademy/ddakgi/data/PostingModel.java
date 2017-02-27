package com.example.tacademy.ddakgi.data;

import java.util.ArrayList;

/**
 * Intro화면 글 조회
 */

public class PostingModel {
    int rid;    // 게시글 번호
    int mid;    // 회원 번호
    String nickname;
    String title;
    String ctime;   // 등록 날짜
    int age;
    String address; // 주소
    int heart_count;
    String location;    // 사진 저장 위치
    ArrayList<String> roommage_image;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHeart_count() {
        return heart_count;
    }

    public void setHeart_count(int heart_count) {
        this.heart_count = heart_count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<String> getRoommage_image() {
        return roommage_image;
    }

    public void setRoommage_image(ArrayList<String> roommage_image) {
        this.roommage_image = roommage_image;
    }
}
