package com.example.tacademy.ddakgi.data;

import android.util.Log;

import com.example.tacademy.ddakgi.util.StorageHelper;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Tacademy on 2017-03-03.
 */

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // Preference에서 cookies를 가져오는 작업을 수행
        // Set<String> preferences = SharedPreferenceBase.getSharedPreference(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE, new HashSet
        Set<String> preferences = StorageHelper.getInstance().getCookies("cook");

        if (preferences != null)
            for (String cookie : preferences) {
                Log.i("COOK", "획득쿠키" + cookie);
                builder.addHeader("Cookie", cookie);
            }

        // Web, Android, iOS 구분을 위해 User-Agent 세팅
        // builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");
        return chain.proceed(builder.build());
    }
}
