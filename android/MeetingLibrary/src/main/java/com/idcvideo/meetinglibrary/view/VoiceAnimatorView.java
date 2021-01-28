package com.idcvideo.meetinglibrary.view;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.idcmeeting.library.R;
import com.idcmeeting.library.utils.CoreRecord;

/**
 * @author : idcvideo
 * @version : 1.0
 * @e-mail : wfeng_work@163.com
 * @date： : 2020/3/9 10:05
 * @pake : com.idcvideo.haokaihui.view
 * @Description :
 */
public class VoiceAnimatorView extends AppCompatImageView {

    public boolean flag = true;

    private boolean visibilityAble = true;

    private int time = 0;

    private String mUserName;

    private Activity mActivity;

    public VoiceAnimatorView(Context context) {
        super(context);
        setImageResource(R.drawable.micp_close);
    }

    public void setVisibilityAble(boolean visibility) {
        //Log.i(TAG, "setVisibilityAble is " + visibility);
        visibilityAble = visibility;
        if (!visibilityAble) {
            setVisibility(GONE);
        } else {
            setVisibility(VISIBLE);
        }
    }

    public void setmActivity(Activity activity){
        this.mActivity = activity;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    /**
     * power 0~100
     *
     * @param power
     */
    public void setVoicePower(int power) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CoreRecord.d("voice","setImageResource power = " + power);
                if (power == 0) {
                    setImageResource(R.drawable.micp_open_0);
                    time ++;
                } else if (power > 0 && power <= 12) {
                    setImageResource(R.drawable.micp_open_1);
                    time = 0;
                } else if (power > 12 && power <= 23) {
                    setImageResource(R.drawable.micp_open_2);
                    time = 0;
                } else if (power > 23 && power <= 34) {
                    setImageResource(R.drawable.micp_open_3);
                    time = 0;
                } else if (power > 34 && power <= 45) {
                    setImageResource(R.drawable.micp_open_4);
                    time = 0;
                } else if (power > 45 && power <= 56) {
                    setImageResource(R.drawable.micp_open_5);
                    time = 0;
                } else if (power > 56 && power <= 67) {
                    setImageResource(R.drawable.micp_open_6);
                    time = 0;
                } else if (power > 67 && power <= 78) {
                    setImageResource(R.drawable.micp_open_7);
                    time = 0;
                } else if (power > 78 && power <= 89) {
                    setImageResource(R.drawable.micp_open_8);
                    time = 0;
                } else if (power > 89 && power <= 100) {
                    setImageResource(R.drawable.micp_open_9);
                    time = 0;
                }
                if (time > 19){
                    setVisibility(GONE);
                    time = time > 500 ? 50 : time;
                } else {
                    setVisibility(VISIBLE);
                }
                if (!visibilityAble) {
                    setVisibility(GONE);
                } else {
                    setVisibility(VISIBLE);
                }
                CoreRecord.d("voice","setImageResource time = " + time);
            }
        });



    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setVoiceClose() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CoreRecord.d("voice","mUserName setVoiceClose");
                setVisibility(VISIBLE);
                setImageResource(R.drawable.micp_close);
                if (!visibilityAble) {
                    setVisibility(GONE);
                } else {
                    setVisibility(VISIBLE);
                }
            }
        });
    }
}
