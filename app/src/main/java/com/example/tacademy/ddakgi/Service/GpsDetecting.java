package com.example.tacademy.ddakgi.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.example.tacademy.ddakgi.U.U;

public class GpsDetecting extends Service implements LocationListener {
    Context context;
    int type;

    boolean isGPSEnable = false;            // GPS 사용 여부
    boolean isNetworkEnable = false;        // 네트워크 사용여부
    boolean isGetLocation = false;          // GPS 상태값
    boolean isPassiveEnable = false;        //

    Location location;                      // 위치 정보
    LocationManager locationManager;        // 위치 관리자

    double lat, lng;                        // 위도, 경도
    final float MIN_DISTANCE_UPDATE = 10.0f;    // GPS를 갱신하는 최소 이동 거리
    final long MIN_TIME_UPDATE = 1000 * 60 * 1; // GPS를 갱신하는 최소 시간

    public GpsDetecting() {
    }

    public GpsDetecting(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    // 위치 정보 가져올 때 ===================================================
    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public double getRealLat() {
        if (location != null)
            return location.getLatitude();
        return lat;
    }

    public double getRealLng() {
        if (location != null)
            return location.getLongitude();
        return lng;
    }

    public Location getLocation() {
        return location;
    }
    // =================================================================

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //변경된 위치 정보 세팅
            this.location = location;
            lat = location.getLatitude();   //위도
            lng = location.getLongitude();  //경도
            //sendGps();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    // 위치 받아오기 위한 설정들을 적용하는 부분
    public Location initLocation() {
        // 1. 위치 관리자 획득
        if (type == 3) {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        } else {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        }
        // 2. 하드웨어 설정 확인
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isPassiveEnable = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
        // 3. 모두 안되면 GG
        if (!isGPSEnable && !isNetworkEnable && !isPassiveEnable) {
            return null;
        }
        // 4. 되긴 된다!
        isGetLocation = true;   // 위치 정보 받을 수 있다.
        try {
            if (isGPSEnable) {
                if (locationManager != null) {
                    // 위치 정보 업데이트 요청!
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_UPDATE,
                            MIN_DISTANCE_UPDATE,
                            this
                    );
                    // 위치 정보 획득 루트 1
                    // 단말기가 gps 공급자를 통해 마지막으로 측정되었던 gps값을 획득
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        sendGps();
                    }
                }
            }
            if (isNetworkEnable) {
                if (locationManager != null) {
                    // 위치 정보 업데이트 요청
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_UPDATE,
                            MIN_DISTANCE_UPDATE,
                            this
                    );
                    //위치 정보 획득 루트 1
                    // 단말기가 gps 공급자를 통해 마지막으로 측정되었던 gps값을 획득
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();   // 위도
                        lng = location.getLongitude();  // 경도
                        sendGps();
                    }
                }
            }
            if (isPassiveEnable) {
                if (locationManager != null) {
                    // 위치 정보 업데이트 요청!
                    locationManager.requestLocationUpdates(
                            LocationManager.PASSIVE_PROVIDER,
                            MIN_TIME_UPDATE,
                            MIN_DISTANCE_UPDATE,
                            this
                    );
                    // 위치 정보 획득 루트 1
                    // 단말기가 gps 공급자를 통해 마지막으로 측정되었던 gps값을 획득
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if (location != null) {
                        lat = location.getLatitude();   // 위도
                        lng = location.getLongitude();  // 경도
                        sendGps();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    public void freeLocation() {
        //관리자에 리스너를 제거하여 디텍팅을 중단한다.
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void sendGps() {
        if (location != null) {
            // 나의 위치정보를 계속해서 업데이트한다.
            U.getInstance().setMyLocation(location);
            U.getInstance().setMyLat(location.getLatitude());
            U.getInstance().setMyLng(location.getLongitude());
            // 나의 위치정보를 특정 연결된 곳으로 보낸다.
            U.getInstance().getBus().post(location);
        }
    }
}
