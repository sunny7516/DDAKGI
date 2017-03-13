package com.example.tacademy.ddakgi.data.ModifyPosting;

/**
 * 방 찾는 룸메이트 글 수정 요청 모델
 */

public class ReqModifyMate {
    String title;
    String local1;
    String local2;
    String local3;
    int room_type;
    int deposit;
    int rent;
    String available_date;
    String extra_q1;
    String extra_q2;
    String extra_q3;
    String roommates_photos;

    public ReqModifyMate(String title, String local1, String local2, String local3, int room_type, int deposit, int rent, String available_date, String extra_q1, String extra_q2, String extra_q3, String roommates_photos) {
        this.title = title;
        this.local1 = local1;
        this.local2 = local2;
        this.local3 = local3;
        this.room_type = room_type;
        this.deposit = deposit;
        this.rent = rent;
        this.available_date = available_date;
        this.extra_q1 = extra_q1;
        this.extra_q2 = extra_q2;
        this.extra_q3 = extra_q3;
        this.roommates_photos = roommates_photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocal1() {
        return local1;
    }

    public void setLocal1(String local1) {
        this.local1 = local1;
    }

    public String getLocal2() {
        return local2;
    }

    public void setLocal2(String local2) {
        this.local2 = local2;
    }

    public String getLocal3() {
        return local3;
    }

    public void setLocal3(String local3) {
        this.local3 = local3;
    }

    public int getRoom_type() {
        return room_type;
    }

    public void setRoom_type(int room_type) {
        this.room_type = room_type;
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

    public String getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(String available_date) {
        this.available_date = available_date;
    }

    public String getExtra_q1() {
        return extra_q1;
    }

    public void setExtra_q1(String extra_q1) {
        this.extra_q1 = extra_q1;
    }

    public String getExtra_q2() {
        return extra_q2;
    }

    public void setExtra_q2(String extra_q2) {
        this.extra_q2 = extra_q2;
    }

    public String getExtra_q3() {
        return extra_q3;
    }

    public void setExtra_q3(String extra_q3) {
        this.extra_q3 = extra_q3;
    }

    public String getRoommates_photos() {
        return roommates_photos;
    }

    public void setRoommates_photos(String roommates_photos) {
        this.roommates_photos = roommates_photos;
    }
}
