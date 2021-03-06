package com.example.tacademy.ddakgi.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tacademy on 2017-02-24.
 */
public class Net {
    private static Net ourInstance = new Net();

    public static Net getInstance() {
        return ourInstance;
    }

    private Net() {
    }

    //////////////////////////////////////////////////////////////////
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://ec2-52-78-67-145.ap-northeast-2.compute.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Retrofit getRetrofit() {
        return retrofit;
    }

    //////////////////////////////////////////////////////////////////
    MemberImpFactory memberImpFactory;

    // Getter

    public MemberImpFactory getMemberImpFactory() {
        if (memberImpFactory == null) {
            memberImpFactory = retrofit.create(MemberImpFactory.class);
        }
        return memberImpFactory;
    }
    //////////////////////////////////////////////////////////////////
}
