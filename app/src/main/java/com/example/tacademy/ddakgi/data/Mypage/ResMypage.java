package com.example.tacademy.ddakgi.data.Mypage;

import java.util.ArrayList;

/**
 * 내 글 목록 조회
 */

public class ResMypage {
    ResultMember result_member;
    ArrayList<ResultRoommate> result_roommate;
    String error;

    public ResultMember getResult_member() {
        return result_member;
    }

    public void setResult_member(ResultMember result_member) {
        this.result_member = result_member;
    }

    public ArrayList<ResultRoommate> getResult_roommate() {
        return result_roommate;
    }

    public void setResult_roommate(ArrayList<ResultRoommate> result_roommate) {
        this.result_roommate = result_roommate;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
