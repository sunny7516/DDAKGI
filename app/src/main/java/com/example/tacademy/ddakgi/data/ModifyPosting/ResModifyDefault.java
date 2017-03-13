package com.example.tacademy.ddakgi.data.ModifyPosting;

/**
 * 글 수정할 때 디폴트 값 지정
 */

public class ResModifyDefault {
    DefaultModel result;
    String error;

    public DefaultModel getResult() {
        return result;
    }

    public void setResult(DefaultModel result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
