package com.example.tacademy.ddakgi.data.RegisterMate;

/**
 * 방 찾는 룸메이트 등록하기 요청 모델
 */

public class ReqRegisterMate {
    String title;
    String local1;
    String local2;
    String local3;
    int room_type;
    int deposit;
    int rent;
    String available_date;
    String description;
    String extra_q1;
    String extra_q2;
    String extra_q3;

    public ReqRegisterMate(String title, String local1, String local2, String local3, int room_type, int deposit, int rent, String available_date, String description, String extra_q1, String extra_q2, String extra_q3) {
        this.title = title;
        this.local1 = local1;
        this.local2 = local2;
        this.local3 = local3;
        this.room_type = room_type;
        this.deposit = deposit;
        this.rent = rent;
        this.available_date = available_date;
        this.description = description;
        this.extra_q1 = extra_q1;
        this.extra_q2 = extra_q2;
        this.extra_q3 = extra_q3;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}

