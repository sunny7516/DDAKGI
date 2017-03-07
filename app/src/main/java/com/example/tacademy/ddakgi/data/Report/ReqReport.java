package com.example.tacademy.ddakgi.data.Report;

/**
 * Created by Tacademy on 2017-03-07.
 */

public class ReqReport {
    int other_member_id;
    int report_content;
    String etc_content;

    public ReqReport(int other_member_id, int report_content, String etc_content) {
        this.other_member_id = other_member_id;
        this.report_content = report_content;
        this.etc_content = etc_content;
    }

    public int getOther_member_id() {
        return other_member_id;
    }

    public void setOther_member_id(int other_member_id) {
        this.other_member_id = other_member_id;
    }

    public int getReport_count() {
        return report_content;
    }

    public void setReport_count(int report_count) {
        this.report_content = report_count;
    }

    public String getEtc_content() {
        return etc_content;
    }

    public void setEtc_content(String etc_content) {
        this.etc_content = etc_content;
    }
}
