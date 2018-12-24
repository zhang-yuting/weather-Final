package com.example.luxiangyang.weather_android.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.OptionsPickerView;
import com.example.luxiangyang.weather_android.MainActivity;
import com.example.luxiangyang.weather_android.R;
import com.example.luxiangyang.weather_android.api.OkHttpUtil;
import com.example.luxiangyang.weather_android.bean.RegionBean;
import com.example.luxiangyang.weather_android.api.RequestCallBack;
import com.example.luxiangyang.weather_android.util.CommandCallback;
import com.example.luxiangyang.weather_android.util.Util;
import com.example.luxiangyang.weather_android.bean.WeatherBean;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.function.Consumer;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DataViewModel {

    private MainActivity activity;
    private ArrayList<RegionBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private TextView select_city;
    private TextView tv_temperature;
    private TextView tv_city;
    private TextView tv_weather;
    private TextView tv_detail;
    private TextView tv_high;
    private TextView tv_high2;
    private TextView tv_low2;
    private TextView tv_low;
    private TextView tv_quality;
    private TextView tv_fengxiang;
    private TextView tv_fengli;
    private TextView tv_send;
    private TextView tv_weather_yesterday;
    private TextView tv_weather_today;
    private WeatherBean.DataBean.ForecastBean forecastBean;

    public DataViewModel(MainActivity mainActivity) {
        this.activity = mainActivity;
        initPermission();
        initJsonData();
        initWeather("北京市");
    }

    /**
     * 初始化权限
     */
    private void initPermission() {




    }


    /**
     * 初始化
     */
    private void init() {


        select_city = (TextView) activity.findViewById(R.id.select_city);
        tv_temperature = (TextView) activity.findViewById(R.id.tv_temperature);//温度
        tv_city = (TextView) activity.findViewById(R.id.tv_city);//城市
        tv_weather = (TextView) activity.findViewById(R.id.tv_weather);//天气
        tv_detail = (TextView) activity.findViewById(R.id.tv_detail);//详情
        tv_weather_today = (TextView) activity.findViewById(R.id.tv_weather_today);//今天天气
        tv_weather_yesterday = (TextView) activity.findViewById(R.id.tv_weather_yesterday);//今天天气
        tv_high = (TextView) activity.findViewById(R.id.tv_high);//高温
        tv_high2 = (TextView) activity.findViewById(R.id.tv_high2);//高温
        tv_low2 = (TextView) activity.findViewById(R.id.tv_low2);//低温
        tv_low = (TextView) activity.findViewById(R.id.tv_low);//低温
        tv_quality = (TextView) activity.findViewById(R.id.tv_quality);
        tv_fengxiang = (TextView) activity.findViewById(R.id.tv_fengxiang);
        tv_fengli = (TextView) activity.findViewById(R.id.tv_fengli);
        tv_send = (TextView) activity.findViewById(R.id.tv_send);

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Util.sendPop(activity, new CommandCallback() {
                    @Override
                    public void releaseCommand(final PopupWindow popupWindow, String s, final EditText commentEdittext) {
                        //申请发送短信权限
                        RxPermissions rxPermission = new RxPermissions(activity);
                        rxPermission.requestEach(Manifest.permission.READ_PHONE_STATE).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted){
                                    //申请发送短信权限
                                    RxPermissions rxPermission2 = new RxPermissions(activity);
                                    rxPermission2.requestEach(Manifest.permission.SEND_SMS).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new io.reactivex.functions.Consumer<Permission>() {
                                        @Override
                                        public void accept(Permission permission) throws Exception {
                                            if (permission.granted){
                                                SmsManager smsManager= SmsManager.getDefault();
                                                smsManager.sendTextMessage(commentEdittext.getText().toString().trim(),null,forecastBean.getHigh()+"\n"+forecastBean.getLow()+"\n"+forecastBean.getFengli()+"\n"+forecastBean.getFengxiang()+"\n"+forecastBean.getType()+forecastBean.getDate(),null,null);
                                                popupWindow.dismiss();
                                                Toast.makeText(activity,"发送成功",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(activity,"拒绝了该权限",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(activity,"拒绝了该权限",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });



                    }
                });


            }
        });


        select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView pickerView = new OptionsPickerView.Builder(activity, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {

                        String s = options3Items.get(options1).get(options2).get(options3);

                        initWeather(s);


                    }
                }).setTitleText("")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                        .setCancelColor(Color.BLACK)//取消按钮文字颜色
                        .setContentTextSize(16)
                        .build();

                pickerView.setPicker(options1Items, options2Items, options3Items);
                pickerView.show();


            }
        });
    }

    private void initWeather(String str) {

        OkHttpUtil.getRequest("https://www.apiopen.top/weatherApi?city="+str, new RequestCallBack<WeatherBean>() {
            @Override
            public void onSuccess(Response<WeatherBean> response) {
                WeatherBean weatherBean = response.body();

                WeatherBean.DataBean data = weatherBean.getData();
                forecastBean = weatherBean.getData().getForecast().get(0);
                WeatherBean.DataBean.YesterdayBean yesterday = weatherBean.getData().getYesterday();

                tv_temperature.setText(data.getWendu());
                    tv_city.setText(data.getCity());
                    tv_detail.setText(data.getGanmao());
                    tv_weather.setText(forecastBean.getType());
                    if(data.getAqi() != null){
                        String s = data.getAqi() + "";
                        Integer integer = Integer.valueOf(s);
                        if (integer > 50 && integer < 90){
                            tv_quality.setText("空气良 "+ integer);
                        }else if (integer >= 90){
                            tv_quality.setText("空气优 "+ integer);
                        }else{
                            tv_quality.setText("空气一般 "+ integer);
                        }
                    }
                    tv_weather_yesterday.setText(yesterday.getType());
                    tv_weather_today.setText(forecastBean.getType());
                    tv_high.setText(forecastBean.getHigh());
                    tv_high2.setText(yesterday.getHigh());
                    tv_low.setText(forecastBean.getLow());
                    tv_low2.setText(yesterday.getLow());
                    String s = Util.stringFilter(forecastBean.getFengli());

                    tv_fengli.setText("风力:"+s);
                    tv_fengxiang.setText("风力:"+ forecastBean.getFengxiang());

            }

            @Override
            public WeatherBean parseNetworkResponse(String jsonResult) {
                Log.e("JS",jsonResult);
                Gson gson = new Gson();
                WeatherBean weatherBean = JSON.parseObject(jsonResult, WeatherBean.class);

                return weatherBean;
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        });
    }

    /**
     * 解析数据
     */
    private void initJsonData() {   //解析数据

        //  获取json数据
        String jsonData = Util.getJson("china_city_data.json", activity);
        //用Gson 转成实体
        ArrayList<RegionBean> jsonBean = parseData(jsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);

        }
        init();
    }


    public ArrayList<RegionBean> parseData(String result) {//Gson 解析
        ArrayList<RegionBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                RegionBean entity = gson.fromJson(data.optJSONObject(i).toString(), RegionBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

}
