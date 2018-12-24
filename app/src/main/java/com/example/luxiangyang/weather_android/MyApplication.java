package com.example.luxiangyang.weather_android;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class MyApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Accept", "application/json;charset=UTF-8");

        //初始化
        OkGo.getInstance().init(this).addCommonHeaders(httpHeaders);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间


    }
}
