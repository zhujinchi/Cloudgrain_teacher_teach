package com.idcvideo.meetinglibrary.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.blankj.utilcode.util.LogUtils;
import com.fvp.sip.ringTone;
import com.idcmeeting.library.message.MessageProcessLoop;
import com.idcmeeting.library.message.agent.CallFailedAgent;
import com.idcmeeting.library.message.agent.CallHangupRingAgent;
import com.idcmeeting.library.message.agent.CallinConnectAgent;
import com.idcmeeting.library.message.agent.CalloutConnectAgent;
import com.idcmeeting.library.message.agent.CalloutNoanswerAgent;
import com.idcmeeting.library.message.agent.CalloutRejectAgent;
import com.idcmeeting.library.utils.SharedPreferencesUtil;
import com.idcvideo.httplibrary.core.HkhKeyInfo;
import com.idcvideo.httplibrary.utils.WaitMeetingToast;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.core.BridgeControl;
import com.idcvideo.meetinglibrary.utils.NavigationBarUtil;
import com.idcvideo.meetinglibrary.utils.ScreenUtils;
import com.idcvideo.meetinglibrary.utils.UiUtils;
import com.idcvideo.meetinglibrary.utils.VibratorUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Beingcalled_activity extends SActivity implements View.OnClickListener {

    private static final String TAG = "beingcalled";
    public static final boolean SHOW_ANIMATION = false;
    private static final int MEETING_NUMBER_EMPTY_WHAT = 21;
    private String userToken;
    private String number, action_ , ring ,protrait;
    private TextView tv_callstate;
    private TextView callName;
    private TextView callNumber;
    private TextView beingcalled2;
    private ImageButton begincall_end;
    private LinearLayout lin_laidian;
    private Runnable runnable;
    private MediaPlayer mp;
    private static ringTone mRingSound;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            LogUtils.e(TAG, " Handler msg.what:" + msg.what);
            VibratorUtil.stop(Beingcalled_activity.this);
            switch (what) {

                case MEETING_NUMBER_EMPTY_WHAT:
                    UiUtils.showToast(getString(R.string.meeting_number_call_in_empty));
                    if (SHOW_ANIMATION) {
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    finish();
                    if (SHOW_ANIMATION) {
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
        NavigationBarUtil.hideNavigationBar(getWindow());
        number = getIntent().getStringExtra("number");
        action_ = getIntent().getStringExtra("action_");
        protrait = getIntent().getStringExtra("protrait_");
        ring = getIntent().getStringExtra("ring");

        if (!TextUtils.isEmpty(ring)) {
            mRingSound = new ringTone();
            mRingSound.startRingTone(this);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_beingcalled);
        ImageButton jieting = findViewById(R.id.jieting);
        ImageButton jujie = findViewById(R.id.jujie);
        lin_laidian = findViewById(R.id.lin_laidian);
        tv_callstate = findViewById(R.id.tv_callstate);
        TextView callCode = findViewById(R.id.callCode);
        callName = findViewById(R.id.callname);//新增名字
        callNumber = findViewById(R.id.callnumber);
        TextView callusername = findViewById(R.id.callusename);
        beingcalled2 = findViewById(R.id.beingcalled_2);
        begincall_end = findViewById(R.id.begincall_end);
        jujie.setOnClickListener(this);
        jieting.setOnClickListener(this);
        begincall_end.setOnClickListener(this);

        String name = getIntent().getStringExtra("name");
        callCode.setText(number);
        callusername.setText(name);

        ActivityEntity.getInstance().setCurrentActivity(this);
        userToken = HkhKeyInfo.getInstance().getUserToken();
        MessageProcessLoop.getInstance().UnsubscribeAll();
        setAnswerOrCallType();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        WaitMeetingToast.getInstance().setClose();
        LogUtils.e(TAG, "onCreate");
    }

    private void setAnswerOrCallType() {
        MessageProcessLoop.getInstance().subscribeNotify(this,
                new CallinConnectAgent() {
                    @Override
                    public void agency() {
                        Log.d(TAG, "message what is " + "7");
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        number = "";
                        String packageName =  ActivityEntity.getInstance().getMeetingActivity().getName();
                        Log.i(TAG, "meetin app is " + packageName);
                        Intent vIntent = new Intent();
                        vIntent.setComponent(new ComponentName(Beingcalled_activity.this, packageName));
                        startActivity(vIntent);
                        if (!(isFinishing() || isDestroyed())) {
                            Log.i(TAG, "need to finish");
                        } else {
                            Log.i(TAG, "no need to finish");
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                },
                new CalloutConnectAgent() {
                    @Override
                    public void agency() {
                        Log.d(TAG, "message what is " + "10");
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        number = "";
                        String packageName =  ActivityEntity.getInstance().getMeetingActivity().getName();
                        Log.i(TAG, "meetin app is " + packageName);
                        Intent vIntent = new Intent();
                        vIntent.setComponent(new ComponentName(Beingcalled_activity.this, packageName));
                        startActivity(vIntent);
                        if (!(isFinishing() || isDestroyed())) {
                            Log.i(TAG, "need to finish");
                        } else {
                            Log.i(TAG, "no need to finish");
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                },
                new CalloutRejectAgent() {
                    @Override
                    public void agency() {
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        if (mp != null) {
                            mp.stop();
                            mp = null;
                        }
                        int i =  BridgeControl.CallReject();
                        LogUtils.e(TAG, " Handler  RejectCode: " + i);
                        switch (i) {
                            case 404:
                                Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_ont_empty), Toast.LENGTH_SHORT).show();
                                break;
                            case 486:
                            case 480:
                                Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_busy), Toast.LENGTH_SHORT).show();
                                break;
                            case 487:
                                Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_passwd_error), Toast.LENGTH_SHORT).show();
                                break;
                            case 518:
                                Toast.makeText(Beingcalled_activity.this, getString(R.string.meeting_status_518_wrong_info), Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(Beingcalled_activity.this, getString(R.string.toast_2), Toast.LENGTH_SHORT);
                                break;
                        }
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                },
                new CalloutNoanswerAgent() {
                    @Override
                    public void agency() {
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_passwd_no_answer), Toast.LENGTH_SHORT);
                        BridgeControl.CallHangUp();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                },
                new CallHangupRingAgent() {
                    @Override
                    public void agency() {
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        BridgeControl.CallHangUp();
                        Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_passwd_end), Toast.LENGTH_SHORT).show();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                },
                new CallFailedAgent() {
                    @Override
                    public void agency() {
                        VibratorUtil.stop(Beingcalled_activity.this);
                        if (mRingSound != null) {
                            mRingSound.stopRingTone();
                            mRingSound = null;
                        }
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                        finish();
                        if (SHOW_ANIMATION) {
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
                });
        if (!TextUtils.isEmpty(action_)) {
            lin_laidian.setVisibility(View.VISIBLE);
            begincall_end.setVisibility(View.GONE);
            //VibratorUtil.Vibrate(this, 100);
            boolean screenBrightness = ScreenUtils.getScreenBrightness(this);
            if (!screenBrightness) {
                ScreenUtils.setScreenBrightness(this);
            }
            mp = new MediaPlayer();
            try {
                Uri defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                String path = defaultUri.getPath();
                LogUtils.e(TAG, "路径" + path);
                mp.setDataSource(this, RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
                CrashReport.postCatchedException(e);
            }
            tv_callstate.setText("");
            //addNotificaction(); //极光推送实现
        } else {
            if (TextUtils.isEmpty(number)) {
                Log.e(TAG, "Meeting number is empty.");
                if (handler != null) {
                    Message message = handler.obtainMessage();
                    message.what = MEETING_NUMBER_EMPTY_WHAT;
                    handler.sendMessageDelayed(message, 4000);
                } else {
                    UiUtils.showToast(getString(R.string.meeting_number_call_in_empty));
                    finish();
                }
            } else {
                Log.i(TAG ,"Beingcall number is " + number + " ,token is " + userToken);
                BridgeControl.CallMakeConnect(number, 1, "" , 1);
                SharedPreferencesUtil.getsInstance().put("shareTag", "0");
            }

            begincall_end.setVisibility(View.VISIBLE);
            lin_laidian.setVisibility(View.GONE);
            callName.setVisibility(View.GONE);
            callNumber.setVisibility(View.GONE);
            beingcalled2.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.jieting) {  // 取消通知
            try {
                VibratorUtil.stop(this);
                BridgeControl.CallAnswer();
                if (mRingSound != null) {
                    mRingSound.stopRingTone();
                    mRingSound = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                CrashReport.postCatchedException(e);
            }
        } else if (id == R.id.jujie) {
            if (mRingSound != null) {
                mRingSound.stopRingTone();
                mRingSound = null;
            }
            BridgeControl.CallHangUp();
            Toast.makeText(this, getString(R.string.being_activity_message_passwd_hang), Toast.LENGTH_SHORT).show();
            LogUtils.e(TAG, "挂断通话fvpHangupCall");
            if (SHOW_ANIMATION) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
            finish();
            if (SHOW_ANIMATION) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } else if (id == R.id.begincall_end) {
            if (mRingSound != null) {
                mRingSound.stopRingTone();
                mRingSound = null;
            }
            BridgeControl.CallHangUp();
            String s = tv_callstate.getText().toString();
            if (getString(R.string.calling).equals(s)) {
                VibratorUtil.stop(this);
                if (SHOW_ANIMATION) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
                finish();
                if (SHOW_ANIMATION) {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }
    }

    private int contectTime = 0;
    private class ActivityDisappearRound implements Runnable {
        @Override
        public void run() {
            Log.d(TAG ,"contectTimne = " + contectTime);
            contectTime++;
            if (contectTime > 60) {
                Toast.makeText(Beingcalled_activity.this, getString(R.string.being_activity_message_passwd_end), Toast.LENGTH_SHORT).show();
                LogUtils.i(TAG, "Activity disappear round disappear. Hang up call.");
                finish();
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        LogUtils.e(TAG, "onDestroy");
        VibratorUtil.stop(Beingcalled_activity.this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (mRingSound != null) {
            mRingSound.stopRingTone();
            mRingSound = null;
        }
        super.onDestroy();
        Log.i(TAG, "onDestroy success.");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtils.e(TAG, "keyCode =" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            BridgeControl.CallHangUp();
            VibratorUtil.stop(Beingcalled_activity.this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        LogUtils.e(TAG, "onResume");
        LogUtils.i(TAG, "onResume");
        super.onResume();
        if (runnable == null) {
            runnable = new ActivityDisappearRound();
            if (handler != null) {
                handler.postDelayed(runnable, 1000);
            }
        }
    }

    @Override
    protected void onPause() {
        LogUtils.e(TAG, "onPause");
        super.onPause();
        Log.i(TAG, "onPause success.");
    }

    @Override
    protected void onStop() {
        LogUtils.e(TAG, "onStop");
        LogUtils.i(TAG, "onStop");
        super.onStop();
        VibratorUtil.stop(Beingcalled_activity.this);
        if (handler != null) {
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            } else {
                handler.removeCallbacksAndMessages(null);
            }
        }
        runnable = null;
        Log.i(TAG, "onStop success.");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusMsg(String EventMessage) {
        if (EventMessage == null) {
            LogUtils.e(TAG, "getEventBusMsg is null.");
            return;
        }
        LogUtils.i(TAG, "getEventBusMsg is " + EventMessage);
        if (EventMessage.equals("havePWD")) {  // 查询到有密码的会议
            final View layout = getLayoutInflater().inflate(R.layout.dialog_pwd, findViewById(R.id.dialog_pwd));
            AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(layout);
            builder.setTitle(getString(R.string.being_activity_message_passwd));
            builder.setCancelable(false);
            builder.setPositiveButton(getString(R.string.confirm), null)
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BridgeControl.CallMakeConnect(number, 1, "" , 1);
                            SharedPreferencesUtil.getsInstance().put("shareTag", "0");
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    EditText etname = layout.findViewById(R.id.dialog_pwd_et);
                    String trim = etname.getText().toString().trim();
                    if (TextUtils.isEmpty(trim)) {
                        UiUtils.showToast(getString(R.string.being_activity_message_passwd_empty));
                    } else {
                        BridgeControl.CallMakeConnect(number, 1, trim , 1);
                        SharedPreferencesUtil.getsInstance().put("shareTag", "0");
                        alertDialog.dismiss();
                    }
                }
            });
        } else if (EventMessage.equals("NoPWD")) {  // 查询到会议无密码
            BridgeControl.CallMakeConnect(number, 1, "", 1);
            SharedPreferencesUtil.getsInstance().put("shareTag", "0");
        }
    }

}
