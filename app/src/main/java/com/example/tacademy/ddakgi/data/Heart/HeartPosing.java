package com.example.tacademy.ddakgi.data.Heart;

/**
 * 찜 목록 조회 result 모델
 */

public class HeartPosing {
    int rid;
    int mid;    // 회원아이디
    String nickname;
    String title;
    String ctime;
    int age;
    String address;
    int deposit;
    int rent;
    int roomming;
    int heart_count;
    int matching_rate;
    String thumbnail_image;
    String[] roommate_image;

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

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getRoomming() {
        return roomming;
    }

    public void setRoomming(int roomming) {
        this.roomming = roomming;
    }

    public int getHeart_count() {
        return heart_count;
    }

    public void setHeart_count(int heart_count) {
        this.heart_count = heart_count;
    }

    public int getMatching_rate() {
        return matching_rate;
    }

    public void setMatching_rate(int matching_rate) {
        this.matching_rate = matching_rate;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public String[] getRoommate_image() {
        return roommate_image;
    }

    public void setRoommate_image(String[] roommate_image) {
        this.roommate_image = roommate_image;
    }
}
