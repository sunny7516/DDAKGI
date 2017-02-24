package com.example.tacademy.ddakgi.view.Write.act;

import android.content.res.ColorStateList;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.util.U;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import static com.example.tacademy.ddakgi.util.GpsCheckActivity.gpscheckActivity;
import static com.example.tacademy.ddakgi.R.id.map;
import static com.example.tacademy.ddakgi.view.Write.act.WriteRoomActivity.room_latitude;
import static com.example.tacademy.ddakgi.view.Write.act.WriteRoomActivity.room_longitude;

/**
 * GoogleMap 띄우는 화면
 * 현 위치 띄우기,
 * 위치 검색, 해당 위치 마커 찍고 이동 & 확대하기
 */

public class WriteLocateActivity extends FragmentActivity implements OnMapReadyCallback {

    io.chooco13.NotoTextView writelocateFinish;

    // xml 화면에서 보이는 component
    private GoogleMap mMap;
    SearchView searchView;      // 검색창 전체
    EditText searchEditText;    // 검색창 위치 검색 부분
    ImageView search_close_btn; // 검색창 텍스트 전체 지우기 아이콘
    ImageView search_icon;      // 검색 아이콘

    // 값을 저장하는 공간
    String strAddress;  // 검색창에서 받아온 값 저장
    String myAddr;      // 최종 검색 결과 주소 저장(위도/경도 -> 주소값)
    List<android.location.Address> listAddress = null;  // 주소 -> 위도/경도
    List<android.location.Address> listAddressText = null;  // 위도/경도 -> 주소

    android.location.Address AddrAddress;

    // 지도 위에 위치 찍어주는 marker
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room_locate);
        Toast.makeText(this, "내 위치와 50m 정도 차이 날 수 있습니다.", Toast.LENGTH_SHORT).show();

        // 구글맵 이용하기 위해서 mapFragment 받기
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        searchView = (SearchView) findViewById(R.id.LocateView);

        // SearchView Style
        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.subTextColor));
        searchEditText.setHintTextColor(getResources().getColor(R.color.grayTextColor));
        searchEditText.setTextSize(13);

        search_close_btn = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        search_icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            search_close_btn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.subTextColor)));
            search_icon.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.subTextColor)));
        }

        // 검색필드를 항상 표시하고 싶으면 false, 아이콘으로 보이고 싶으면 true
        searchView.setIconifiedByDefault(false);

        // 주소 <-> 위도,경도
        final Geocoder geocoder = new Geocoder(this);
        writelocateFinish = (io.chooco13.NotoTextView) findViewById(R.id.writelocateFinish);

        // 검색창에 주소 검색, 주소의 위도 경도값 변환해서 해당 위치로 지도 이동
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 완료 버튼 텍스트 진하게 변경
                writelocateFinish.setTextColor(getResources().getColor(R.color.defaultTextColor));

                // 검색창에서 입력받은 주소를 가져와서 변수 strAddress에 저장
                strAddress = searchEditText.getText().toString();
                // geocoder는 주소를 통해 위도와 경도의 값을 연산
                try {
                    // 받아온 주소값을 구글맵이 이해할 수 있는 주소값으로 변환 후 listAddress에 저장
                    listAddress = geocoder.getFromLocationName(strAddress, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (listAddress != null) {
                    if (listAddress.size() == 0) {
                        // 해당되는 주소 정보 없음
                    } else {
                        // 동까지만 위도 경도 나타나고, 상세주소는 서울 위도 경도로 찍힘
                        // 해당되는 주소로 이동
                        double lat = 0;
                        double lng = 0;
                        try {
                            AddrAddress = listAddress.get(0);
                            lat = AddrAddress.getLatitude();
                            lng = AddrAddress.getLongitude();

                            listAddressText = geocoder.getFromLocation(lat, lng, 10);
                            if (listAddressText != null) {
                                if (listAddressText.size() == 0) {
                                } else {
                                    myAddr = listAddressText.get(0).getAddressLine(0).toString();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LatLng newPosition = new LatLng(lat, lng);

                        // 검색한 위치 이전 화면으로 넘기기 위해 정확한 주소 받아오기
                        // 새로운 위치 찍기
                        marker.setPosition(newPosition);
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                        // 카메라 이동
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 12));
                        CameraPosition MARKER_POS = new CameraPosition.Builder()
                                .target(newPosition)
                                .zoom(16)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(MARKER_POS));
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Location location;
        // Add a marker in Sydney and move the camera
        LatLng myPosition = new LatLng(U.getInstance().getMyLat(), U.getInstance().getMyLng());
        // 마킹
        marker =
                mMap.addMarker(new MarkerOptions().position(myPosition)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))); // 지도 위에 점 찍기
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 12)); // 위치를 중심으로 이동 / 코드 제거 시 위치 상에 표시만
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.i("GPS", "구글지도상내위치정보:" + location.getLatitude() + "," + location.getLongitude());
            }
            //구글지도상내위치정보:37.4663367,126.9605719
            //새로운위치 정보 : 37.4663367,126.9605719
        });
    }

    //back button
    public void back(View view) {
        gpscheckActivity.finish();
        finish();
    }

    // 디바이스 자체 back button
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gpscheckActivity.finish();
        finish();
    }

    // 완료 버튼 눌렀을 때
    // 위치 정보 임시 저장, 이전 화면으로 주소값 전달하기
    public void writelocateFinish(View view) {
        if (WriteRoomActivity.writelocateBt != null) {
            room_latitude = AddrAddress.getLatitude();
            room_longitude = AddrAddress.getLongitude();

            WriteRoomActivity.writelocateBt.setText(myAddr);

            WriteRoomActivity.writelocateBt.setTextColor(getResources().getColor(R.color.subTextColor));
            gpscheckActivity.finish();
            finish();
        }
    }
}
