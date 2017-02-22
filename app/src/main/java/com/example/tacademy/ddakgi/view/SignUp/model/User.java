package com.example.tacademy.ddakgi.view.SignUp.model;

/**
 * 카카오톡 로그인 시 디비에 저장할 User 구조
 */

public class User {
    String nickname;
    String profile;
    String token;

    public User() {
    }

    public User(String nickname, String profile, String token) {
        this.nickname = nickname;
        this.profile = profile;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
