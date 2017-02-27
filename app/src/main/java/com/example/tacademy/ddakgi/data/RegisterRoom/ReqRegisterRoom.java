package com.example.tacademy.ddakgi.data.RegisterRoom;

/**
 * 방 등록하기 요청
 */

public class ReqRegisterRoom {
    String title;
    String local1;
    String local2;
    String local3;
    String detailed_local;
    double room_latitude;
    double room_longitude;
    int room_type;
    int size;
    int deposit;
    int rent;
    int floor;
    String available_date;
    int manage_cost;
    int manage_cost_inc;
    int options;
    String description;
    String extra_q1;
    String extra_q2;
    String extra_q3;

    public ReqRegisterRoom() {
    }

    public ReqRegisterRoom(String title, String local1, String local2, String local3, String detailed_local, double room_latitude, double room_longitude, int room_type, int size, int deposit, int rent, int floor, String available_date, int manage_cost, int manage_cost_inc, int options, String description, String extra_q1, String extra_q2, String extra_q3) {
        this.title = title;
        this.local1 = local1;
        this.local2 = local2;
        this.local3 = local3;
        this.detailed_local = detailed_local;
        this.room_latitude = room_latitude;
        this.room_longitude = room_longitude;
        this.room_type = room_type;
        this.size = size;
        this.deposit = deposit;
        this.rent = rent;
        this.floor = floor;
        this.available_date = available_date;
        this.manage_cost = manage_cost;
        this.manage_cost_inc = manage_cost_inc;
        this.options = options;
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

    public String getDetailed_local() {
        return detailed_local;
    }

    public void setDetailed_local(String detailed_local) {
        this.detailed_local = detailed_local;
    }

    public double getRoom_latitude() {
        return room_latitude;
    }

    public void setRoom_latitude(float room_latitude) {
        this.room_latitude = room_latitude;
    }

    public double getRoom_longitude() {
        return room_longitude;
    }

    public void setRoom_longitude(float room_longitude) {
        this.room_longitude = room_longitude;
    }

    public int getRoom_type() {
        return room_type;
    }

    public void setRoom_type(int room_type) {
        this.room_type = room_type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getAvailable_date() {
        return available_date;
    }

    public void setAvailable_date(String available_date) {
        this.available_date = available_date;
    }

    public int getManage_cost() {
        return manage_cost;
    }

    public void setManage_cost(int manage_cost) {
        this.manage_cost = manage_cost;
    }

    public int getManage_cost_inc() {
        return manage_cost_inc;
    }

    public void setManage_cost_inc(int manage_cost_inc) {
        this.manage_cost_inc = manage_cost_inc;
    }

    public int getOptions() {
        return options;
    }

    public void setOptions(int options) {
        this.options = options;
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
