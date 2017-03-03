package com.example.tacademy.ddakgi.data.Member;

/**
 * 회원 조회 결과 모델
 */

public class ResMember {
    MemberModel result;
    String error;

    public MemberModel getResult() {
        return result;
    }

    public void setResult(MemberModel result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
