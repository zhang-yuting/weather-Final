package com.example.luxiangyang.weather_android.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.luxiangyang.weather_android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Util {

    private static View view;

    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();

        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 发送验证码
     * @param context
     */
    public static void sendPop(final Context context,final CommandCallback commandCallback) {

        view = LayoutInflater.from(context).inflate(R.layout.comment_edittext_layout, null);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final EditText edit_number = view.findViewById(R.id.edit_number);

        TextView comment_send = view.findViewById(R.id.tv_command_send);

        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commandCallback.releaseCommand(popupWindow,edit_number.getText().toString(),edit_number);
            }
        });

        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);

        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setFocusable(true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(false);

//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);//防止遮挡edittext

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.setContentView(view);

        edit_number.requestFocus();

        edit_number.setHint("手机号");

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        if (edit_number.hasFocus()) {
            softBoard(context);
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()

        {
            private String edittext;

            @Override
            public void onDismiss() {

                edittext = edit_number.getText().toString();

            }
        });

        //初始化监听软键盘显示隐藏状态
        Activity activity = (Activity) context;
        SoftKeyBoardListener.setListener(activity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });


    }

    /**
     * 打开关闭软键盘
     */
    public static void softBoard(final Context context) {

        Handler handler =
                new Handler();

        handler.postDelayed(new Runnable() {

            @Override

            public void run() {

                InputMethodManager imm = (InputMethodManager) context

                        .getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm.isActive()) {

                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,

                            InputMethodManager.HIDE_NOT_ALWAYS);

                }

            }

        }, 0);

    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Context context) {
        Activity activity = (Activity) context;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 对字符串处理特殊符号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
