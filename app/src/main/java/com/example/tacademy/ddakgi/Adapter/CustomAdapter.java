package com.example.tacademy.ddakgi.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.example.tacademy.ddakgi.R;

/**
 * Intro화면 Viewpager에 관련된 Adapter
 */

public class CustomAdapter extends PagerAdapter{

    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater){
        // 전달받은 LayoutInflater를 멤버변수로 전달
        this.inflater = inflater;
    }

    //PagerAdapter가 가지고 잇는 View의 개수 리턴
    //보여줘야하는 이미지 배열 데이터의 길이를 리턴
    @Override
    public int getCount() {
        return 3;   //이미지 개수 return
    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    //스크롤을 통해 현재 보여져야 하는 View를 만들어냄
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        view = inflater.inflate(R.layout.main_viewpager_layout, null);

        ImageView roomImg = (ImageView)view.findViewById(R.id.roomImage);
        // 나머지 객체들도 동일하게
        // 현재 position에 해당하는 이미지를 setting
        roomImg.setScaleType(ImageView.ScaleType.FIT_XY);
        roomImg.setImageResource(R.drawable.testroom0 +position);

        //ViewPager에 만들어 낸 view 추가
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailPage = new Intent(v.getContext(), HomeRoomDetailPageActivity.class);
                v.getContext().startActivity(detailPage);
            }
        });
        return view;
        //return super.instantiateItem(container, position);
    }


    //화면에 보이지 않은 View 파괴
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }

    // 다음 이미지 살짝 보이기
    @Override public float getPageWidth(int position) {
        return(0.9f);
    }
}
