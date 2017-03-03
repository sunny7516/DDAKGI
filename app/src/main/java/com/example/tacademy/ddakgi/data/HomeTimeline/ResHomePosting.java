package com.example.tacademy.ddakgi.data.HomeTimeline;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2017-03-03.
 */

public class ResHomePosting {

    ArrayList<HomePostingModel> result;
    String error;

    public ArrayList<HomePostingModel> getResult() {
        return result;
    }

    public void setResult(ArrayList<HomePostingModel> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
