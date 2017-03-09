package com.example.tacademy.ddakgi.data.Heart;

import java.util.ArrayList;

/**
 * 찜목록 조회 응답 모델
 */

public class ResHeartPosting {
    ArrayList<HeartPosing> result;
    String error;

    public ArrayList<HeartPosing> getResult() {
        return result;
    }

    public void setResult(ArrayList<HeartPosing> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
