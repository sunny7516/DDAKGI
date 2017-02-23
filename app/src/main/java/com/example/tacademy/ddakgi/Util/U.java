package com.example.tacademy.ddakgi.util;

import android.location.Location;

import com.squareup.otto.Bus;

import java.lang.reflect.Member;

/**
 * Created by Tacademy on 2017-01-26.
 */
public class U {
    private static U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }

    Bus bus = new Bus();

    public Bus getBus() {
        return bus;
    }

    // 지도 ========================================================================================
    double myLat, myLng;
    Location myLocation;

    public double getMyLat() {
        return myLat;
    }

    public void setMyLat(double myLat) {
        this.myLat = myLat;
    }

    public double getMyLng() {
        return myLng;
    }

    public void setMyLng(double myLng) {
        this.myLng = myLng;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    // 회원정보 로그인 후 전역적으로 접근하기 위한 객체 =====================================================
    Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}

