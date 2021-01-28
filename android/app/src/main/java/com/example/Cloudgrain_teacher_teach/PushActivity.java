package com.example.Cloudgrain_teacher_teach;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.idcvideo.httplibrary.InternetInfoView.CreateMeetingByAccessCodeView;
import com.idcvideo.httplibrary.InternetInfoView.UserLogonView;
import com.idcvideo.httplibrary.bean.AvailableMeetRoomList;
import com.idcvideo.httplibrary.bean.BaseBean;
import com.idcvideo.httplibrary.bean.Login;
import com.idcvideo.meetinglibrary.activity.HttpClient;
import com.idcvideo.meetinglibrary.activity.SActivity;
import com.idcvideo.meetinglibrary.activity.SPUtil;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * +----------------------------------------------------------------------
 * | com.example.testtttt
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/16
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 11:49 AM）
 * +----------------------------------------------------------------------
 **/
public class PushActivity extends SActivity implements UserLogonView {
    private static final int APPLY_PERMISSION_REQUEST = 8848;
    private boolean isJoin = true;
    private String account;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        account = getIntent().getStringExtra("account");
        password = getIntent().getStringExtra("password");


    }

    @Override
    protected void onResume() {
        super.onResume();
        //好开会账号密码登录
        if (requestPermission()) {
            IdcMediaKit.getInstance().IdcMediaUserLoginWithUserName(account, password, PushActivity.this, PushActivity.this);
        }
    }

    @Override
    public void UserLogonCuccess(Login.DataBean.UserBean userBean) {
        IdcMediaKit.getInstance().IdcMediaSelectAvailableMeetingRoomWithCompleted(PushActivity.this, createMeetingByAccessCodeView);
    }

    @Override
    public void UserLogonFailure(String s) {
        Toast.makeText(this, s + "失败", Toast.LENGTH_SHORT).show();
    }

    public boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.DISABLE_KEYGUARD) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED

            ) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.DISABLE_KEYGUARD,
                                Manifest.permission.READ_PHONE_STATE,
                        },
                        APPLY_PERMISSION_REQUEST);
                return false;
            } else {
                return true;
            }
        } else {
            // 系统版本高于6.0自动申请动态权限 系统低于6.0申请声音权限
            return true;
        }
    }

    CreateMeetingByAccessCodeView createMeetingByAccessCodeView = new CreateMeetingByAccessCodeView() {
        @Override
        public void CreateMeetingByAccessCodeCuccess(String utitleStr, int create) {
            Log.i("111", "access code success(1) is " + create);
            Toast.makeText(getApplicationContext(), utitleStr, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void CreateMeetingByAccessCodeNoRoom(BaseBean.ErrorResultBean errorResult) {
            Toast.makeText(getApplicationContext(), errorResult.resultMsg, Toast.LENGTH_SHORT).show();

        }

        @Override
        public void CreateMeetingByAccessCodeOnMessage(String HttpResultFailure) {
            Toast.makeText(getApplicationContext(), HttpResultFailure, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void CreateMeetingByAccessCodeFailure(String HttpResultFailure) {

        }

        @Override
        public void SelectAvailableMeetingRoom(List<AvailableMeetRoomList.DataBeanXX> list, String user) {
            if (isJoin) {
                isJoin = !isJoin;
                Log.i("222", "select room list ");
                Toast.makeText(getApplicationContext(), list.get(0).meetingAccessCode, Toast.LENGTH_SHORT).show();
                seleteMeetingRoomsDialog(list);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startClassInfo();
                    }
                }).start();
            } else {
                finish();
            }
            ;
        }
    };

    private void seleteMeetingRoomsDialog(List<AvailableMeetRoomList.DataBeanXX> roomlist) {


        String meetingAccessCode = null;
        meetingAccessCode = roomlist.get(0).getMeetingAccessCode();
        IdcMediaKit.getInstance()
                .IdcMediaCreateMeetingByAccessCodeWithAccessCode(meetingAccessCode, ""/** 192192,522222,17644259801 **/, "", "8888", PushActivity.this, createMeetingByAccessCodeView);


    }

    /**
     * 开始上课
     */
    private void startClassInfo() {
        String tempAccessToken = SPUtil.get(PushActivity.this, "accessToken", "accessToken").toString();
        String tempClassId = SPUtil.get(PushActivity.this, "classId", "").toString();
        String tempCourseId = SPUtil.get(PushActivity.this, "classMainId", "").toString();
        String url = "http://yundou.skyline.name:18002/class/teacher/courses/start";
        HashMap hashMap = new HashMap();
        hashMap.put("coursesId", tempCourseId);
        HttpClient.getInstance(PushActivity.this).post(tempAccessToken, url, hashMap, new HttpClient.MyCallback() {
            @Override
            public void success(Response res) {

            }

            @Override
            public void failed(IOException e) {
            }
        });
    }
}
