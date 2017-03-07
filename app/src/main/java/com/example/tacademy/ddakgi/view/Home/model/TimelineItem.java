package com.example.tacademy.ddakgi.view.Home.model;

import android.widget.ImageButton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * HomeTab 타임라인의 글에 들어갈 구성
 */

public class TimelineItem implements Serializable {

    int profileImg;
    String nickName;
    String date;
    int roomImg;
    String title;
    String price;
    String age;
    String location;
    ImageButton like;
    String likeNum;

    public TimelineItem() {
    }

    public TimelineItem(int profileImg, String nickName, String date, int roomImg, String title, String price, String age, String location, ImageButton like, String likeNum) {
        this.profileImg = profileImg;
        this.nickName = nickName;
        this.date = date;
        this.roomImg = roomImg;
        this.title = title;
        this.price = price;
        this.age = age;
        this.location = location;
        this.like = like;
        this.likeNum = likeNum;
    }

    public Map<String, Object> toPostMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("profileImg", profileImg);
        map.put("nickName", nickName);
        map.put("date", date);
        map.put("roomImg", roomImg);
        map.put("title", title);
        map.put("price", price);
        map.put("age", age);
        map.put("location", location);
        map.put("like", like);
        map.put("likeNum", likeNum);
        return map;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(int profileImg) {
        this.profileImg = profileImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(int roomImg) {
        this.roomImg = roomImg;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ImageButton getLike() {
        return like;
    }

    public void setLike(ImageButton like) {
        this.like = like;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }
}
