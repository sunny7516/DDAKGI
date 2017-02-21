package com.example.tacademy.ddakgi.Search.fragment;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.Util.U;
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

public class SearchMap extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;

    SearchView searchView;

    String strAddress;
    List<android.location.Address> listAddress;
    EditText searchEditText;
    android.location.Address AddrAddress;

    public SearchMap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // 주소 <-> 위도,경도
        final Geocoder geocoder = new Geocoder(getActivity());

        // 검색창에 주소 검색, 주소의 위도 경도값 변환해서 해당 위치로 지도 이동
        searchView = (SearchView) getActivity().findViewById(R.id.homeSearchView);
        searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
                        AddrAddress = listAddress.get(0);
                        lat = AddrAddress.getLatitude();
                        lng = AddrAddress.getLongitude();
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

        return view;
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
            }
        });
    }
}
