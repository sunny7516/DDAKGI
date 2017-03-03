package com.example.tacademy.ddakgi.data.IntroTimeline;

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
    int deposit;    // (방 있는 룸메만)
    int rent;       // (방 있는 룸메만)
    int rooming;    // 방인지 룸메인지 구별 (0:룸메, 1:방)
    String thumbnail_image;    // 프로필 사진
    String[] roommate_image;   // 방 혹은 룸메 사진

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

    public int getRooming() {
        return rooming;
    }

    public void setRooming(int rooming) {
        this.rooming = rooming;
    }

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
