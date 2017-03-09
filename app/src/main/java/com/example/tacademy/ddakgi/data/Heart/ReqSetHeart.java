package com.example.tacademy.ddakgi.data.Heart;

/**
 * Created by Tacademy on 2017-03-08.
 */

public class ReqSetHeart {
    int roommate_id;

    public ReqSetHeart(int roommate_id) {
        this.roommate_id = roommate_id;
    }

    public int getPosting_id() {
        return roommate_id;
    }

    public void setPosting_id(int roommate_id) {
        this.roommate_id = roommate_id;
    }
}
