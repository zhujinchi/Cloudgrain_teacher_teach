package com.example.Cloudgrain_teacher_teach;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.idcvideo.meetinglibrary.activity.Beingcalled_activity;
import com.idcvideo.meetinglibrary.activity.MeetingActivity;
import com.idcvideo.meetinglibrary.activity.SPUtil;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    private static final String channel = "toJava";
    private String account;
    private String password;

    @Override
    protected void onStart() {
        super.onStart();
        //好开会初始化
        IdcMediaKit.getInstance().initWithAccessToken("b7f5170b0e88479b217050fa7b210f63", false, MainActivity.this);
        IdcMediaKit.getInstance().IdcMediaLinkMeetingActivity(MeetingActivity.class);
        IdcMediaKit.getInstance().IdcMediaLinkRingActivity(Beingcalled_activity.class);

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
                            String userId = methodCall.method.toString();
                            Intent intent=new Intent(MainActivity.this, PushActivity.class);
                            intent.putExtra("account",account);
                            intent.putExtra("password",password);
                            startActivity(intent);

                        } else {
                            result.notImplemented();
                        }
                    }
                }
        );
    }


    public String toJava(String name){
        Log.i("hh","=========="+name);
        account = name.split("\\._.")[0];
        password = name.split("\\._.")[1];
        //20110109518.AakiJuIC.3d913e34134b4706ba4a4bab33454f98.1341736803837775874
        String accessToken = name.split("\\._.")[2];
        String coursesId = name.split("\\._.")[3];
        String classId = name.split("\\._.")[4];
        String classTime = name.split("\\._.")[5];
        String classTeacher = name.split("\\._.")[6];
        String classSubject = name.split("\\._.")[7];
        String classContent = name.split("\\._.")[8];

        SPUtil.put(MainActivity.this,"accessToken",accessToken);
        SPUtil.put(MainActivity.this,"classId",classId);
        SPUtil.put(MainActivity.this,"classMainId",coursesId);
        SPUtil.put(MainActivity.this,"time",classTime);
        SPUtil.put(MainActivity.this,"teacher",classTeacher);
        SPUtil.put(MainActivity.this,"projectName",classSubject);
        SPUtil.put(MainActivity.this,"projectContent",classContent);

        //SPUtil.get(MainActivity.this,"account","account").toString();

        System.out.println("传递的参数是"+name.split("\\.")[0]);
        return "好开会token："+name;
    }
}
