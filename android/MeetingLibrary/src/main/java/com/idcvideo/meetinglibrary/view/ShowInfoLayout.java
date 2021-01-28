package com.idcvideo.meetinglibrary.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.idcmeeting.library.manager.callback.IdcMediaMessage;
import com.idcmeeting.library.unity.phoneinfo;
import com.idcvideo.httplibrary.core.HkhKeyInfo;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.ListUtils;
import com.idcvideo.meetinglibrary.activity.SPUtil;
import com.idcvideo.meetinglibrary.activity.ToastUtils;
import com.idcvideo.meetinglibrary.activity.bean.EventMessage;
import com.idcvideo.meetinglibrary.core.BridgeControl;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * @author : idcvideo
 * @version : 1.0
 * @e-mail : wfeng_work@163.com
 * @date： : 2020/3/9 16:30
 * @pake : com.idcvideo.haokaihui.view
 * @Description :
 */
public class ShowInfoLayout extends LinearLayout implements MessageInfo {

    private String uTitleStr;
    private final TextView roll;
    private int mViewLeft = 3;
    private int mViewTop = 3;
    private String userName;
    private Context mContext;
    private String currentName;//当前学生号码
    private String cameraState;//摄像头开关状态
    private String microphoneState;
    private String cameraFlag;
    private String microphoneFlag;


    public String getMicrophoneState() {
        return microphoneState;
    }

    public void setMicrophoneState(String microphoneState) {
        this.microphoneState = microphoneState;
    }

    public String getCameraState() {
        return cameraState;
    }

    public void setCameraState(String cameraState) {
        this.cameraState = cameraState;
    }


    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }


    public ShowInfoLayout(Context context, String userNames, List<phoneinfo> mRsultList) {
        super(context);
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.cloud_class_meeting_text_view_layout, this);
        ImageView ears = view.findViewById(R.id.text_ears_iv);
        ImageView camera = view.findViewById(R.id.text_camera_iv);
        TextView tv = view.findViewById(R.id.text_view_tv);
        ImageView switchView = view.findViewById(R.id.text_switch_iv);
        ImageView sun = view.findViewById(R.id.text_sun_iv);
        ImageView evaluate = view.findViewById(R.id.evaluate_iv);
        cameraFlag = SPUtil.get(mContext, "cameraFlag", "").toString();
        microphoneFlag = SPUtil.get(mContext, "microphoneFlag", "").toString();
        if (!TextUtils.isEmpty(cameraFlag)) {
            if (TextUtils.equals(cameraFlag, "0")) {
                camera.setSelected(false);
            } else {
                camera.setSelected(true);
            }
        } else {
            if (TextUtils.equals(cameraState, "1")) {
                SPUtil.put(mContext, "cameraFlag", "0");
                camera.setSelected(false);
            } else {
                SPUtil.put(mContext, "cameraFlag", "1");
                camera.setSelected(true);
            }
        }
        if (!TextUtils.isEmpty(microphoneFlag)) {
            if (TextUtils.equals(microphoneFlag, "0")) {
                sun.setSelected(false);
            } else {
                sun.setSelected(true);
            }
        } else {
            if (TextUtils.equals(microphoneState, "1")) {
                SPUtil.put(mContext, "microphoneFlag", "0");
                sun.setSelected(false);
            } else {
                SPUtil.put(mContext, "microphoneFlag", "1");
                sun.setSelected(true);
            }
        }
        roll = view.findViewById(R.id.text_roll_tv);
        tv.setText(userNames);
        uTitleStr = HkhKeyInfo.getInstance().getUserName();
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera.isSelected()) {
                    //开启
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                SPUtil.put(mContext, "cameraFlag", "0");
                                camera.setSelected(false);
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "close_show_info_camera");
                                break;
                            }
                        }
                    }
                } else {
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                SPUtil.put(mContext, "cameraFlag", "1");
                                camera.setSelected(true);
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "open_show_info_camera");
                                break;
                            }
                        }
                    }

                }
            }
        });

        sun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sun.isSelected()) {
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                SPUtil.put(mContext, "microphoneFlag", "0");
                                sun.setSelected(false);
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "close_show_info_sun");
                                break;
                            }
                        }
                    }
                } else {
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                sun.setSelected(true);
                                SPUtil.put(mContext, "microphoneFlag", "1");
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "open_show_info_sun");
                                break;
                            }
                        }
                    }
                }
            }
        });
        ears.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ears.isSelected()) {
                    ears.setSelected(false);
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "close_show_info_ears");
                                break;
                            }
                        }
                    }
                } else {
                    ears.setSelected(true);
                    if (!ListUtils.isEmpty(mRsultList)) {
                        for (int i = 0; i < mRsultList.size(); i++) {
                            String number = mRsultList.get(i).getNumber();
                            if (TextUtils.equals(number, currentName)) {
                                IdcMediaKit.IdcMediaCustomDataChannel(number, "open_show_info_ears");
                                break;
                            }
                        }
                    }
                }
            }
        });
        switchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ListUtils.isEmpty(mRsultList)) {
                    for (int i = 0; i < mRsultList.size(); i++) {
                        String number = mRsultList.get(i).getNumber();
                        if (TextUtils.equals(number, currentName)) {
                            IdcMediaKit.IdcMediaCustomDataChannel(number, "open_show_info_switch");
                            break;
                        }
                    }
                }
            }
        });
        roll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (roll.isSelected()) {
                    for (int i = 0; i < mRsultList.size(); i++) {
                        String number = mRsultList.get(i).getNumber();
                        if (!TextUtils.equals(number, currentName)) {
                            BridgeControl.ConferenceMuteMember(number, 1);
                        } else {
                            BridgeControl.ConferenceMuteMember(number, 0);
                        }
                    }
                }
            }
        });

        evaluate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mRsultList.size(); i++) {
                    String number = mRsultList.get(i).getNumber();
                    if (TextUtils.equals(number, currentName)) {
                        //打开弹窗
                        SPUtil.put(context,"evaluate_open_number",number);
                        EventBus.getDefault().post("open_evaluate");
                        break;
                    }
                }

            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        requestLayout();
    }

    public void setmViewLeft(int viewLeft) {
        this.mViewLeft = viewLeft;
    }

    public int getmViewLeft() {
        return mViewLeft;
    }

    public int getmViewTop() {
        return mViewTop;
    }

    public void setmViewTop(int mViewTop) {
        this.mViewTop = mViewTop;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public void subsctibe(String userNmae, String message) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(message)) {
                    if (message.contains("close_show_info_hand")) {
                        roll.setSelected(false);
                    } else if (message.contains("open_show_info_hand")) {
                        roll.setSelected(true);
                    }
                }
            }
        }, 0);

    }

}
