package com.example.tacademy.ddakgi.data.Mypage;

import java.util.ArrayList;

/**
 * Created by Tacademy on 2017-03-09.
 */

public class ResMypage {
    ArrayList<Mypage> result;
    String error;

    public ArrayList<Mypage> getResult() {
        return result;
    }

    public void setResult(ArrayList<Mypage> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
