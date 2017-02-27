package com.example.tacademy.ddakgi.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.PostingModel;
import com.example.tacademy.ddakgi.data.ResPosting;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;

import java.util.ArrayList;

import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Intro화면 Viewpager에 관련된 Adapter
 */

public class CustomAdapter extends PagerAdapter {

    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        // 전달받은 LayoutInflater를 멤버변수로 전달
        this.inflater = inflater;
    }

    //PagerAdapter가 가지고 잇는 View의 개수 리턴
    //보여줘야하는 이미지 배열 데이터의 길이를 리턴
    @Override
    public int getCount() {
        return 20;   //이미지 개수 return
    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    //스크롤을 통해 현재 보여져야 하는 View를 만들어냄
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
    public static int pos;
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager_layout, null);

        pos = position;

        // ImageView roomImg = (ImageView) view.findViewById(R.id.roomImage);
        NotoTextView introTitle = (NotoTextView) view.findViewById(R.id.introTitle);
        NotoTextView introLocation = (NotoTextView) view.findViewById(R.id.introLocation);
        NotoTextView introNickname = (NotoTextView) view.findViewById(R.id.introNickname);
        NotoTextView introDate = (NotoTextView) view.findViewById(R.id.introDate);
        NotoTextView introHeartNum = (NotoTextView) view.findViewById(R.id.introHeartNum);

        // 나머지 객체들도 동일하게
        // 현재 position에 해당하는 이미지를 setting
        // roomImg.setScaleType(ImageView.ScaleType.FIT_XY);
        // roomImg.setImageResource(R.drawable.testroom0 + position);

        // DB에서 data get ==========================================================================
            Call<ResPosting> resPostingCall = NetSSL.getInstance().getMemberImpFactory().resPosting();
            resPostingCall.enqueue(new Callback<ResPosting>() {
                @Override
                public void onResponse(Call<ResPosting> call, Response<ResPosting> response) {
                    if (response.body().getResult() != null) {
                        Log.i("RF:INTRO", "SUCCESS" + response.body().getResult());
                        ArrayList<PostingModel> arrayListRoom = response.body().getResult();
                        if (arrayListRoom != null) {
                            Log.i("posing", arrayListRoom.get(pos).getTitle());
                            introTitle.setText(arrayListRoom.get(pos).getTitle());
                            introLocation.setText(arrayListRoom.get(pos).getAddress());
                            introNickname.setText(arrayListRoom.get(pos).getNickname());
                            introDate.setText(arrayListRoom.get(pos).getCtime());
                            introHeartNum.setText(String.valueOf(arrayListRoom.get(pos).getHeart_count()));
                        }
                    } else {
                        Log.i("RF:INTRO", "FAIL" + response.body().getError());
                    }
                }

                @Override
                public void onFailure(Call<ResPosting> call, Throwable t) {
                    Log.i("RF:INTRO", "ERR" + t.getMessage());
                }
            });

        // ViewPager 빈 공간에 만들어 낸 item View 추가
        container.addView(view);
        // itemView 누르면 상세 페이지로 이동
        view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                                        v.getContext().startActivity(detailPage);
                                    }
                                }
        );
        return view;
    }


    // Viewpager 항목들 화면 처리 =====================================================================
    //화면에 보이지 않는 View 파괴
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        //super.destroyItem(container, position, object);
    }

    // 다음 이미지 살짝 보이기
    @Override
    public float getPageWidth(int position) {
        return (0.9f);
    }
}
