package com.example.tacademy.ddakgi.WriteTab.WriteRoom.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tacademy.ddakgi.R;
import com.example.tacademy.ddakgi.base.BaseActivity;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.miguelbcr.ui.rx_paparazzo.entities.size.ScreenSize;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.tacademy.ddakgi.R.mipmap.plus;

/**
 * 방 사진 등록하는 화면
 */

public class WriteRoomImage extends BaseActivity {
    SweetAlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_room_image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.writeroomImageTool);
        this.setSupportActionBar(toolbar);
    }

    // 이미지 등록하는 onClick 버튼
    // 사진이 이미 있으면 삭제 혹은 재설정, 없으면 사진 선택
    public void getPhoto(View view) {
        //if (havePhoto == null) {
        ImageButton roomImageBt = (ImageButton) view;
        if (roomImageBt.getTag() == null) {
            // 사진이 등록되지 않았으면
            onPhoto(view, roomImageBt);
        } else {
            // 사진이 이미 등록되어 있음
            alert = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setContentText("재설정 / 삭제")
                    .setConfirmText("삭제")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            deleteImage(roomImageBt);
                        }
                    })
                    .setCancelText("재설정")
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            alert.dismissWithAnimation();
                            roomImageBt.setTag(null);
                            onPhoto(view, roomImageBt);
                        }
                    });
            alert.setCancelable(true);
            alert.show();
        }
    }

    // 사진 선택 방법 (포토앨범, 사진 촬영)
    public void onPhoto(View view, ImageButton roomImageBt) {
        alert = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("사진선택")
                .setContentText("사진 선택 방법을 고르세요")
                .setConfirmText("갤러리에서 선택")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        // 갤러리에서 선택
                        onGallery(roomImageBt);
                    }
                })
                .setCancelText("사진 촬영")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        // 사진 촬영
                        onCamera(roomImageBt);
                    }
                });
        alert.setCancelable(true);
        alert.show();
    }

    // 사진 촬영
    public void onCamera(ImageButton roomImageBt) {
        // 크롭작업을 하기 위해 옵션 설정(편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.white));
        // 사진이 업로드되는데 화면에서는 보이지 않는 문제가 발생해서 주석처리
        //options.setMaxBitmapSize(1024 * 1024 * 2);

        RxPaparazzo.takeImage(this)
                .size(new ScreenSize())                     // 사이즈(smallSize, ScreenSize, OriginalSize, CustomMaxSize)
                .crop(options)                              // 편집
                .useInternalStorage()                       // 내부 저장 (안쓰면 외부 공용 공간에 앱이름으로 생성됨)
                .usingCamera()                              // 카메라 사용
                .subscribeOn(Schedulers.io())               // IO
                .observeOn(AndroidSchedulers.mainThread())  // 스레드 생성
                .subscribe(response -> {                    // 결과 처리
                    //실패 처리
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    Log.i("PH", response.data());
                    loadImage(response.data(), roomImageBt);
                });
    }

    // 갤러리에서 선택
    public void onGallery(ImageButton roomImageBt) {
        // 크롭작업을 하기 위해 옵션 설정(편집)
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(ContextCompat.getColor(this, R.color.white));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.black));
        // 사진이 업로드되는데 화면에서는 보이지 않는 문제가 발생해서 주석처리
        // options.setMaxBitmapSize(1024 * 1024 * 2);

        RxPaparazzo.takeImage(this)
                .size(new ScreenSize())
                .crop(options)
                .useInternalStorage()
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.resultCode() != RESULT_OK) {
                        return;
                    }
                    Log.i("PH", response.data());
                    loadImage(response.data(), roomImageBt);
                });
    }

    // 사진 올리기
    public void loadImage(String path, ImageButton roomImageBt) {
        ImageView plusBt = (ImageView) findViewById(R.id.plusBt);
        plusBt.setBackground(null);

        alert.dismissWithAnimation();
        // 이미지뷰에 이미지 세팅
        String url = "file://" + path;

        Picasso.with(this).setLoggingEnabled(true);
        Picasso.with(this).setIndicatorsEnabled(true);
        Picasso.with(this).invalidate(url);
        Picasso.with(this).load(url).into(roomImageBt);

        roomImageBt.setTag(path);
        //havePhoto = path;
        //uploadImage(path);
    }

    // 사진 삭제
    // 업로드했던 사진 data 삭제로 수정해야 함.
    public void deleteImage(ImageButton roomImageBt) {
        roomImageBt.setImageDrawable(getResources().getDrawable(plus));
        roomImageBt.setScaleType(ImageView.ScaleType.FIT_CENTER);
        roomImageBt.setTag(null);
        alert.dismissWithAnimation();
    }
}