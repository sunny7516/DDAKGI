package com.example.tacademy.ddakgi.view.Search.frag;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.view.Search.adapter.PlaceAutocompleteAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.view.View.GONE;

public class SearchMap extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView,AutoCompleteviewAll;
    MapView map;
    private GoogleMap mMap;
    boolean alwaysCameraflag = true;    // 처음 위치를 잡기 위한 flag
    Location mLocation;                 // 계속 갱신되는 나의 주소
    Marker marker;                      // 자신의 장소에대한 마커
    LatLng queriedLocation;             // 검색한 장소에대한 GPS정보
    String locationName;                // 검색한 장소의 이름

    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_map, container, false);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Places.GEO_DATA_API)
                .build();

        mAutocompleteView = (AutoCompleteTextView) getActivity().findViewById(R.id.autocomplete_places);
        mAutocompleteView.setVisibility(View.VISIBLE);
        AutoCompleteviewAll = (AutoCompleteTextView)getActivity().findViewById(R.id.autocomplete_all);
        AutoCompleteviewAll.setVisibility(GONE);
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);

        mAdapter = new PlaceAutocompleteAdapter(getContext(), mGoogleApiClient, BOUNDS_GREATER_SYDNEY, null);
        mAutocompleteView.setAdapter(mAdapter);

        // 초기화
        map = (MapView) view.findViewById(R.id.search_map);
        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        mAutocompleteView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    newPosition();
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    // 검색하는 글에 따라 나오는 아이템 중 하나 클릭시
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
        }
    };

    // 장소불러오기 성공 실패 -> 성공일 경우 검색한 장소에대한 정보를 가지고 있음
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // 실패
                places.release();
                return;
            }

            // 장소 이름     : place.getName()
            // 장소 ID       : place.getId()
            // 장소 주소     : place.getAddress()
            // 장소 전화번호  : place.getPhoneNumber()
            // 장소 url      : place.getWebsiteUri()

            // 성공
            final Place place = places.get(0);

            // 장소에 대한 GPS 정보
            queriedLocation = place.getLatLng();
            locationName = "" + place.getName();

            // 장소 클릭했을 때 뜨는 위치 명
            Log.i("검색한 위치명: ", place.getName()+"");

            places.release();
        }
    };

    // 장소 연결 실패
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(getContext(), "장소를 다시 검색하세요", Toast.LENGTH_SHORT).show();
    }

    // 장소 검색을 받고 정보에대한 위치를 마커로 나타내는 부분
    public void newPosition() {
        marker.setPosition(queriedLocation);
        marker.setTitle(locationName);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(queriedLocation, 12));
        CameraPosition MARKER_POS = new CameraPosition.Builder()
                .target(queriedLocation)
                .zoom(16)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(MARKER_POS));
    }

    // 지도 띄우기
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // 지도상에서 내위치 정보 표시 및 획득 -> 빨간줄 : 퍼미션문제
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {/*
                Log.i("구글지도상내위치정보:", location.getLatitude()
                        + "," + location.getLongitude());*/

                mLocation = location;

                // 내 위치 세팅
                if (alwaysCameraflag) {
                    LatLng myPosision
                            = new LatLng(location.getLatitude(),
                            location.getLongitude());

                    marker = mMap.addMarker(new MarkerOptions().position(myPosision).title("현재 나의 위치"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosision, 12));
                    alwaysCameraflag = false;
                }
            }
        });
    }

    // 완료 버튼을 눌렀을 경우
    // 위치 정보 임시 저장, 이전 화면으로 주소값 전달하기
    public void onNextBtn(View view) {
        getActivity().finish();
    }

}