package com.example.Cloudgrain_teacher_teach;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.idcvideo.httplibrary.InternetInfoView.UserLogonView;
import com.idcvideo.httplibrary.bean.Login;
import com.idcvideo.meetinglibrary.activity.Beingcalled_activity;
import com.idcvideo.meetinglibrary.activity.MeetingActivity;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    private static final String channel = "toJava";

    @Override
    protected void onStart() {
        super.onStart();
        //好开会初始化
        IdcMediaKit.getInstance().initWithAccessToken("b7f5170b0e88479b217050fa7b210f63", true, MainActivity.this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new MethodChannel(getFlutterEngine().getDartExecutor().getBinaryMessenger(),channel).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                        if (methodCall.method!=null) {
                            result.success(toJava(methodCall.method));
                            IdcMediaKit.getInstance().IdcMediaLinkMeetingActivity(MeetingActivity.class);
                            IdcMediaKit.getInstance().IdcMediaLinkRingActivity(Beingcalled_activity.class);
                            getActivity().startActivity(new Intent(getContext(), MeetingActivity.class));
                            //好开会账号密码登录
                            //IdcMediaKit.getInstance().IdcMediaUserLoginWithUserName( "20112204077","CUKWjnlE" , MainActivity.this, MainActivity.this);

                            //好开会

                        } else {
                            result.notImplemented();
                        }
                    }
                }
        );
    }


    public String toJava(String name){
        System.out.println("传递的参数是"+name);
        return "好开会token："+name;
    }


}

