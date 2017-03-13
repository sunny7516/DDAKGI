package com.example.tacademy.ddakgi.data.RegisterRoom;

/**
 * 방 등록하기 응답
 */

public class ResStringString {
    String result;
    String error;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResStringString{" +
                "result='" + result + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
