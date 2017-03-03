package com.example.tacademy.ddakgi.view.SplashIntro;

/**
 * intro화면
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.HomeActivity;
import com.example.tacademy.ddakgi.data.IntroTimeline.PostingModel;
import com.example.tacademy.ddakgi.data.IntroTimeline.ResPosting;
import com.example.tacademy.ddakgi.data.NetSSL;
import com.example.tacademy.ddakgi.data.Ottobus;
import com.example.tacademy.ddakgi.view.Home.act.HomeRoomDetailPageActivity;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.chooco13.NotoTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;

    boolean ottoflag = false;
    ResPosting items;
    ArrayList<PostingModel> arrayListRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 레트로핏 통신 ==============================================================================
        if (!ottoflag) {
            Ottobus.getInstance().getMaingfrag_bus().register(this);
            ottoflag = true;
        }

        getIntroData();

        // intent 애니메이션
        overridePendingTransition(R.anim.act_slide_in_from_bottom, R.anim.act_slide_out_to_top);

        pager = (ViewPager) findViewById(R.id.viewPager);

        // viewpager 양쪽 항목도 조금씩 보이게 하기
        pager.setClipToPadding(false);
        pager.setPadding(40, 0, 40, 0);
        pager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -30);
    }

    // 인트로 화면에 나타낼 데이터 가져오는 부분 (Retrofit 통신)
    public void getIntroData() {
        // DB에서 data get ==========================================================================
        Call<ResPosting> resPostingCall = NetSSL.getInstance().getMemberImpFactory().resPosting();
        resPostingCall.enqueue(new Callback<ResPosting>() {
            @Override
            public void onResponse(Call<ResPosting> call, Response<ResPosting> response) {
                if (response.body().getResult() != null) {
                    Log.i("RF:INTRO", "SUCCESS" + response.body().getResult());
                    arrayListRoom = response.body().getResult();
                    if (arrayListRoom != null) {
                        Ottobus.getInstance().getMaingfrag_bus().post(response.body());
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
    }

    // Adapter ====================================================================================
    class IntroAdapter extends PagerAdapter {

        LayoutInflater inflater;

        // 전달받은 LayoutInflater를 멤버변수로 전달
        public IntroAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        //PagerAdapter가 가지고 잇는 View의 개수 리턴
        //보여줘야하는 이미지 배열 데이터의 길이를 리턴
        @Override
        public int getCount() {
            return 20;
        }

        //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
        //스크롤을 통해 현재 보여져야 하는 View를 만들어냄
        //첫번째 파라미터 : ViewPager
        //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            view = inflater.inflate(R.layout.main_viewpager_layout, null);

            // 화면에서 보여줄 데이터들 가져와서 넣어주기
            NotoTextView introTitle = (NotoTextView) view.findViewById(R.id.introTitle);
            NotoTextView introLocation = (NotoTextView) view.findViewById(R.id.introLocation);
            NotoTextView introNickname = (NotoTextView) view.findViewById(R.id.introNickname);
            NotoTextView introDate = (NotoTextView) view.findViewById(R.id.introDate);
            NotoTextView introHeartNum = (NotoTextView) view.findViewById(R.id.introHeartNum);
            NotoTextView introPrice = (NotoTextView) view.findViewById(R.id.introPrice);
            ImageView introRoomImage = (ImageView) view.findViewById(R.id.introRoomImage);
            CircleImageView introProfile = (CircleImageView)view.findViewById(R.id.introProfile);

            introTitle.setText(items.getResult().get(position).getTitle());
            introLocation.setText(items.getResult().get(position).getAddress());
            introNickname.setText(items.getResult().get(position).getNickname());
            introDate.setText(items.getResult().get(position).getCtime());
            introHeartNum.setText(String.valueOf(items.getResult().get(position).getHeart_count()));

            // deposit 정보나 rent정보가 0이면 가격정보를 나타내지 않는다.
            if (items.getResult().get(position).getDeposit() == 0
                    && items.getResult().get(position).getRent() == 0) {
                introPrice.setText(null);
            } else {
                introPrice.setText(String.valueOf(items.getResult().get(position).getDeposit()) + "/"
                        + String.valueOf(items.getResult().get(position).getRent()));
            }
            // 사진이 null이 아니면 적용시킨다.
            if (items.getResult().get(position).getRoommate_image()==null) {
            }else{
                Picasso
                        .with(getApplicationContext())
                        .load(items.getResult().get(position).getRoommate_image()[0])
                        .fit()
                        .into(introRoomImage);
            }
            // 프로필 사진 적용하기
            if(items.getResult().get(position).getThumbnail_image()== null){
                introProfile.setImageResource(R.mipmap.profile);
            }else{
                Picasso
                        .with(getApplicationContext())
                        .load(items.getResult().get(position).getThumbnail_image())
                        .fit()
                        .into(introProfile);
            }

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

    @Subscribe
    public void FinishLoad(ResPosting data) {
        items = data;
        IntroAdapter introAdapter = new IntroAdapter(getLayoutInflater());
        pager.setAdapter(introAdapter);
        pager.getAdapter().notifyDataSetChanged();
        // Ottobus 연결 끊어주기
        Ottobus.getInstance().getMaingfrag_bus().unregister(this);
    }

    // 액티비티 종료 시 애니메이션
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.act_slide_in_from_top, R.anim.act_slide_out_to_bottom);
    }

    // Main화면으로 이동하는 onClick함수(기본 HomeTab으로 이동)
    public void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

        this.finish();
    }
}
