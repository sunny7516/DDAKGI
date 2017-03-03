package com.example.tacademy.ddakgi.data;

import android.util.Log;

import com.example.tacademy.ddakgi.util.StorageHelper;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Tacademy on 2017-03-03.
 */

public class ReceivedCookiesInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if(!originalResponse.headers("Set-Cookie").isEmpty()){
            HashSet<String> cookies = new HashSet<>();

            for(String header : originalResponse.headers("Set-Cookie")){
                Log.i("COOK", "저장쿠키"+header);
                cookies.add(header);
            }

            // Preference에 cookies를 넣어주는 작업을 수행
            // SharedPreferenceBase.putSharedPreference(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE, cookies);
            StorageHelper.getInstance().setCookies("cook",cookies);
        }
        return originalResponse;
    }
}
