package com.example.tacademy.ddakgi.data.DeleteMe;

/**
 * 회원 탈퇴하기 요청 모델
 */

public class ReqDeleteMe {
    int id;

    public ReqDeleteMe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
