package com.example.tacademy.ddakgi.data.Member;

/**
 * 회원정보 수정 등록 요청 모델
 */

public class ReqUpdateMemberInfo {
    String nickname;
    int lifestyle_q1;
    int lifestyle_q2;
    int lifestyle_q3;
    int lifestyle_q4;
    int lifestyle_q5;
    int lifestyle_q6;
    int lifestyle_q7;
    int lifestyle_q8;
    int lifestyle_q9;
    int lifestyle_q10;
    String profile_image;
    // String thumbnail_image;

    public ReqUpdateMemberInfo() {
    }

    public ReqUpdateMemberInfo(String nickname, int lifestyle_q1, int lifestyle_q2, int lifestyle_q3, int lifestyle_q4, int lifestyle_q5, int lifestyle_q6, int lifestyle_q7, int lifestyle_q8, int lifestyle_q9, int lifestyle_q10, String profile_image) {
        this.nickname = nickname;
        this.lifestyle_q1 = lifestyle_q1;
        this.lifestyle_q2 = lifestyle_q2;
        this.lifestyle_q3 = lifestyle_q3;
        this.lifestyle_q4 = lifestyle_q4;
        this.lifestyle_q5 = lifestyle_q5;
        this.lifestyle_q6 = lifestyle_q6;
        this.lifestyle_q7 = lifestyle_q7;
        this.lifestyle_q8 = lifestyle_q8;
        this.lifestyle_q9 = lifestyle_q9;
        this.lifestyle_q10 = lifestyle_q10;
        this.profile_image = profile_image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLifestyle_q1() {
        return lifestyle_q1;
    }

    public void setLifestyle_q1(int lifestyle_q1) {
        this.lifestyle_q1 = lifestyle_q1;
    }

    public int getLifestyle_q2() {
        return lifestyle_q2;
    }

    public void setLifestyle_q2(int lifestyle_q2) {
        this.lifestyle_q2 = lifestyle_q2;
    }

    public int getLifestyle_q3() {
        return lifestyle_q3;
    }

    public void setLifestyle_q3(int lifestyle_q3) {
        this.lifestyle_q3 = lifestyle_q3;
    }

    public int getLifestyle_q4() {
        return lifestyle_q4;
    }

    public void setLifestyle_q4(int lifestyle_q4) {
        this.lifestyle_q4 = lifestyle_q4;
    }

    public int getLifestyle_q5() {
        return lifestyle_q5;
    }

    public void setLifestyle_q5(int lifestyle_q5) {
        this.lifestyle_q5 = lifestyle_q5;
    }

    public int getLifestyle_q6() {
        return lifestyle_q6;
    }

    public void setLifestyle_q6(int lifestyle_q6) {
        this.lifestyle_q6 = lifestyle_q6;
    }

    public int getLifestyle_q7() {
        return lifestyle_q7;
    }

    public void setLifestyle_q7(int lifestyle_q7) {
        this.lifestyle_q7 = lifestyle_q7;
    }

    public int getLifestyle_q8() {
        return lifestyle_q8;
    }

    public void setLifestyle_q8(int lifestyle_q8) {
        this.lifestyle_q8 = lifestyle_q8;
    }

    public int getLifestyle_q9() {
        return lifestyle_q9;
    }

    public void setLifestyle_q9(int lifestyle_q9) {
        this.lifestyle_q9 = lifestyle_q9;
    }

    public int getLifestyle_q10() {
        return lifestyle_q10;
    }

    public void setLifestyle_q10(int lifestyle_q10) {
        this.lifestyle_q10 = lifestyle_q10;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
/*
    public String getThumbnail() {
        return thumbnail_image;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail_image = thumbnail;
    }*/
}
