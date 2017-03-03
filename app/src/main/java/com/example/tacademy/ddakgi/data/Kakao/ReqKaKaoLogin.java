package com.example.tacademy.ddakgi.data.Kakao;

/**
 * Created by Tacademy on 2017-03-02.
 */

public class ReqKaKaoLogin {
    String access_token;

    public ReqKaKaoLogin() {
    }

    public ReqKaKaoLogin(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
