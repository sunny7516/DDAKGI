package com.example.tacademy.ddakgi.util;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.example.tacademy.ddakgi.data.NetSSL;
import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

/**
 * Camera, MultiDex
 */

public class MyApplication extends MultiDexApplication {
    private static volatile MyApplication instance = null;

    public static MyApplication getGlobalApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 사진
        RxPaparazzo.register(this);
        // 카카오톡
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
        // 보안 통신 초기화
        NetSSL.getInstance().launch(getApplicationContext());
        // 저장소에 저장된 것을 앱 구동되고 바로 들고오게
        StorageHelper.getInstance().setContext(getApplicationContext());
    }

    private static class KakaoSDKAdapter extends KakaoAdapter {
        /**
         * Session Config에 대해서는 default값들이 존재한다.
         * 필요한 상황에서만 override해서 사용하면 됨.
         *
         * @return Session의 설정값.
         */
        @Override
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[]{AuthType.KAKAO_LOGIN_ALL};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return MyApplication.getGlobalApplicationContext();
                }
            };
        }
    }
}
