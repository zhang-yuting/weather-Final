package com.example.luxiangyang.weather_android.api;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import org.json.JSONObject;

public class OkHttpUtil {

    private static String baseMethod;
    private static String baseUrl;
    private static String filePath = "";
    private static String token;
    private static String url = "";

    public static <T> void getRequest(String url,final RequestCallBack<T> requestCallBack) {

        OkGo.<T>get(url).execute(new Callback<T>() {

            //请求开始
            @Override
            public void onStart(Request<T, ? extends Request> request) {
                requestCallBack.onStart(request);
            }

            //请求成功
            @Override
            public void onSuccess(Response<T> response) {
                requestCallBack.onSuccess(response);
            }

            //从缓存中读取成功
            @Override
            public void onCacheSuccess(Response<T> response) {
                requestCallBack.onCacheSuccess(response);
            }

            //请求失败
            @Override
            public void onError(Response<T> response) {
                requestCallBack.onError(response);
            }

            //请求完成
            @Override
            public void onFinish() {
                requestCallBack.onFinish();
            }

            //上传进度
            @Override
            public void uploadProgress(Progress progress) {
                requestCallBack.uploadProgress(progress);
            }

            //下载进度
            @Override
            public void downloadProgress(Progress progress) {
                requestCallBack.downloadProgress(progress);
            }

            //解析数据
            @Override
            public T convertResponse(okhttp3.Response response) throws Throwable {

                if (response.code() == 200) {
                    String jsonResult = response.body().string().trim();

                    JSONObject jsonObject = new JSONObject(jsonResult);
                    final String code = jsonObject.optString("resultCode", "");
                    final String message = jsonObject.optString("resultMsg", "");
                        T t = (T) requestCallBack.parseNetworkResponse(jsonResult);
                        return t;
                }
                return null;
            }
        });
    }

}
