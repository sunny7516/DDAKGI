package com.example.tacademy.ddakgi.HomeTab;

import android.widget.Button;
import android.widget.ImageButton;

/**
 * HomeTab 타임라인의 글에 들어갈 구성
 */

public class TimelineItem {

    int profileImg;
    String nickName;
    Button modifyBt;
    Button deleteBt;
    int roomImg;
    String age;
    String price;
    String location;
    String date;
    ImageButton like;
    String likeNum;

    public TimelineItem() {
    }

    public TimelineItem(int roomImg) {
        //this.profileImg = profileImg;
        //this.nickName = nickName;
        this.roomImg = roomImg;
        //this.age = age;
        //this.price = price;
        //this.location = location;
        //this.date = date;
        //this.like = like;
        //this.likeNum = likeNum;
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

    public Button getModifyBt() {
        return modifyBt;
    }

    public void setModifyBt(Button modifyBt) {
        this.modifyBt = modifyBt;
    }

    public Button getDeleteBt() {
        return deleteBt;
    }

    public void setDeleteBt(Button deleteBt) {
        this.deleteBt = deleteBt;
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
