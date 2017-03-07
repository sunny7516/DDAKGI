package com.example.tacademy.ddakgi.data.DetailPost;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 상세페이지 응답 성공 모델
 */

public class DetailPosting implements Serializable {
    int rid;
    int mid;
    String uid;
    int roomming;
    String nickname;
    int age;
    int heart_count;
    int heart_state;
    int matching_rate;
    int deposit;
    int rent;
    String address;
    String room_type;
    String available_date;
    String description;
    int manage_cost;
    String manage_cost_inc;
    String options;
    int floor;
    String size;
    String lifestyle_q1;
    String lifestyle_q2;
    String lifestyle_q3;
    String lifestyle_q4;
    String lifestyle_q5;
    String lifestyle_q6;
    String lifestyle_q7;
    String lifestyle_q8;
    String lifestyle_q9;
    String lifestyle_q10;
    String profile_image;
    String thumbnail_image;
    ArrayList<String> roommate_image;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getHeart_state() {
        return heart_state;
    }

    public void setHeart_state(int heart_state) {
        this.heart_state = heart_state;
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

    public int getRoomming() {
        return roomming;
    }

    public void setRoomming(int roomming) {
        this.roomming = roomming;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getManage_cost() {
        return manage_cost;
    }

    public void setManage_cost(int manage_cost) {
        this.manage_cost = manage_cost;
    }

    public String getManage_cost_inc() {
        return manage_cost_inc;
    }

    public void setManage_cost_inc(String manage_cost_inc) {
        this.manage_cost_inc = manage_cost_inc;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getLifestyle_q1() {
        return lifestyle_q1;
    }

    public void setLifestyle_q1(String lifestyle_q1) {
        this.lifestyle_q1 = lifestyle_q1;
    }

    public String getLifestyle_q2() {
        return lifestyle_q2;
    }

    public void setLifestyle_q2(String lifestyle_q2) {
        this.lifestyle_q2 = lifestyle_q2;
    }

    public String getLifestyle_q3() {
        return lifestyle_q3;
    }

    public void setLifestyle_q3(String lifestyle_q3) {
        this.lifestyle_q3 = lifestyle_q3;
    }

    public String getLifestyle_q4() {
        return lifestyle_q4;
    }

    public void setLifestyle_q4(String lifestyle_q4) {
        this.lifestyle_q4 = lifestyle_q4;
    }

    public String getLifestyle_q5() {
        return lifestyle_q5;
    }

    public void setLifestyle_q5(String lifestyle_q5) {
        this.lifestyle_q5 = lifestyle_q5;
    }

    public String getLifestyle_q6() {
        return lifestyle_q6;
    }

    public void setLifestyle_q6(String lifestyle_q6) {
        this.lifestyle_q6 = lifestyle_q6;
    }

    public String getLifestyle_q7() {
        return lifestyle_q7;
    }

    public void setLifestyle_q7(String lifestyle_q7) {
        this.lifestyle_q7 = lifestyle_q7;
    }

    public String getLifestyle_q8() {
        return lifestyle_q8;
    }

    public void setLifestyle_q8(String lifestyle_q8) {
        this.lifestyle_q8 = lifestyle_q8;
    }

    public String getLifestyle_q9() {
        return lifestyle_q9;
    }

    public void setLifestyle_q9(String lifestyle_q9) {
        this.lifestyle_q9 = lifestyle_q9;
    }

    public String getLifestyle_q10() {
        return lifestyle_q10;
    }

    public void setLifestyle_q10(String lifestyle_q10) {
        this.lifestyle_q10 = lifestyle_q10;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public ArrayList<String> getRoommate_image() {
        return roommate_image;
    }

    public void setRoommate_image(ArrayList<String> roommate_image) {
        this.roommate_image = roommate_image;
    }
}
