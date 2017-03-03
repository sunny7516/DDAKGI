package com.example.tacademy.ddakgi.data.IntroTimeline;

import java.util.ArrayList;

/**
 * 인트로 글 조회
 */

public class ResPosting {
    ArrayList<PostingModel> result;
    String error;

    public ArrayList<PostingModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<PostingModel> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}