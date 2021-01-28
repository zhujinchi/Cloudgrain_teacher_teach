package com.idcvideo.meetinglibrary.surface;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fvp.sip.fvpsipjni;
import com.idcmeeting.library.R;
import com.idcmeeting.library.manager.MemberManager;
import com.idcmeeting.library.manager.callback.MemberCallback;
import com.idcmeeting.library.unity.phoneinfo;
import com.idcmeeting.library.utils.CoreRecord;
import com.idcvideo.meetinglibrary.activity.MeetingActivity;
import com.idcvideo.meetinglibrary.activity.SPUtil;
import com.idcvideo.meetinglibrary.bean.MeetingPeopleVoiceBean;
import com.idcvideo.meetinglibrary.view.BlankCanvasView;
import com.idcvideo.meetinglibrary.view.MeetingPeopleInfoView;
import com.idcvideo.meetinglibrary.view.MessageManager;
import com.idcvideo.meetinglibrary.view.NoVideoView;
import com.idcvideo.meetinglibrary.view.ShowInfoLayout;
import com.idcvideo.meetinglibrary.view.ShowPictureLinearLayout;
import com.idcvideo.meetinglibrary.view.VoiceAnimatorView;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by shike
 * data 2019.07.23
 * describe 会议视频上蒙层，小窗显示会议人员名
 */
public class PeopleInfoFloatViewListener {

    private MessageManager messageManager;
    private ExecutorService singleThreadExecutor;
    private Timer timer;
    private int mAppHight;
    private int mAppWidth;
    private int ScreenSplitMode = -1;
    private int isShareMode = -1;
    private Context mContext;
    private Activity mActivity;
    private BlankCanvasView mBlankCanvasView;
    private List<phoneinfo> mRsultList = new ArrayList<>();
    private PeopleInfoFloatCallback peopleInfoFloatCallback;
    private static String mTagList = ""; //视频画面用户名称Tag，对应视频位置Tag
    private static final String TAG = "FloatViewListener";
    private static final int BIGTHANSCRENN = 2080;
    private boolean isMeeting = false;
    private int eMeetScrFlagLength = -1;
    private boolean mIsCloseVoice = false;
    private String uTitleStr;

    private HashMap<String, MeetingPeopleVoiceBean> allMeetingPeopleMap;
    private HashMap<String, HashMap<String, View>> viewMap;
    private HashMap<String, MeetingPeopleVoiceBean> voiceMap;
    private String mHostName;


    public boolean isMeeting() {
        return isMeeting;
    }

    public void setMeeting(boolean meeting) {
        isMeeting = meeting;
        if (meeting) {
            removeAllViews();
        }
        updataView();
    }

    public PeopleInfoFloatViewListener(String uTitleStr) {
        if (messageManager == null) {
            Log.i(TAG, "message info manager");
            messageManager = new MessageManager();
        }
        messageManager.Release();
        this.uTitleStr = uTitleStr;
        allMeetingPeopleMap = new HashMap<>();
        voiceMap = new HashMap<>();
        viewMap = new HashMap<>();
        singleThreadExecutor = Executors.newSingleThreadExecutor();
        peopleInfoFloatCallback = new PeopleInfoFloatCallback();
        MemberManager.getInstance().setPeopleInfoFloatCallback(peopleInfoFloatCallback);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Looper.prepare();
                CoreRecord.d(" timer.schedule ", "-=-=-=>>TimerTask...runnable = " + Thread.currentThread().getId());
                byte[] energyStatistics = new byte[1024];
                int byteLen = fvpsipjni.FVPhoneCoreEnergyStatisticsGet(energyStatistics, energyStatistics.length);
                String s = new String(energyStatistics, 0, byteLen);
                showMeetingPeopleVoice(s);
//                Looper.loop();
                CoreRecord.d("energyStatistics", "energyStatistics =" + s + " , " + "byteLen =" + byteLen);
            }
        }, 10, 200);
        if (MemberManager.getInstance().getPeopleInfoFloatCallback() == null) {
            Log.e(TAG, "set people float callback.");
            if (peopleInfoFloatCallback == null) {
                peopleInfoFloatCallback = new PeopleInfoFloatCallback();
            }
            MemberManager.getInstance().setPeopleInfoFloatCallback(peopleInfoFloatCallback);
        }
    }

    public void CheckMeetSrcFloatFlag(final byte[] iMeetScrFlag, final int iMeetScrFlagLength, int iMergeMode, int shareMode) {
        if (mContext == null || mBlankCanvasView == null
            // || this.mRsultList == null || this.mRsultList.size() == 0
        ) {
            return;
        }
        if (iMeetScrFlag == null || iMeetScrFlag.length == 0) {
            return;
        }
        if (iMeetScrFlagLength == 0) {
            removeAllViews();
            this.eMeetScrFlagLength = 0;
            return;
        }
        String meetScrFlagStr = new String(iMeetScrFlag, 0, iMeetScrFlagLength);
        if (meetScrFlagStr.length() == 0) {
            return;
        }
        if (this.isShareMode != shareMode || this.ScreenSplitMode != iMergeMode || this.eMeetScrFlagLength != iMeetScrFlagLength
                || mTagList == null || !mTagList.equals(meetScrFlagStr) || this.mRsultList == null || this.mRsultList.size() == 0) {
            this.ScreenSplitMode = iMergeMode;
            this.isShareMode = shareMode;
            this.eMeetScrFlagLength = iMeetScrFlagLength;
            mTagList = meetScrFlagStr;
            updataView();
        }
    }

    public void invaledateView(Activity mActivity, BlankCanvasView blankCanvasView, List<phoneinfo> rsultList) {
        if (rsultList == null || mActivity == null || blankCanvasView == null) {
            return;
        }
        this.mContext = mActivity;
        this.mActivity = mActivity;
        if (this.mBlankCanvasView != null) {
            this.mBlankCanvasView.removeAllViews();
        }
        this.mBlankCanvasView = blankCanvasView;
        /*if (this.mRsultList != null && rsultList.size() > 0) {
            this.mRsultList = rsultList;
        }*/
        updataView();
    }

    private void showAllList() {
        for (int i = 0; i < this.mRsultList.size(); i++) {
            CoreRecord.d(TAG, "i = " + i + "================");
            if (this.mRsultList.get(i).getNumber() != null) {
                CoreRecord.d(TAG, "getNumber:" + this.mRsultList.get(i).getNumber());
            }
            if (this.mRsultList.get(i).getNickName() != null) {
                CoreRecord.d(TAG, "getNickName:" + this.mRsultList.get(i).getNickName());
            }
            if (this.mRsultList.get(i).getName() != null) {
                CoreRecord.d(TAG, "getName:" + this.mRsultList.get(i).getName());
            }
        }
    }

    private void getScreenSize() {
        if (mContext == null) {
            return;
        }
//        int screenWidth = SharedPreferencesTool.getInteger(myApplication.getInstance(), "screenWidth", -1);
        int screenWidth = mBlankCanvasView.getWidth();

//        int screenHeight = SharedPreferencesTool.getInteger(myApplication.getInstance(), "screenHeight", -1);
        int screenHeight = mBlankCanvasView.getHeight();
        mAppHight = screenHeight;
        mAppWidth = screenWidth;
//        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        if (mWindowManager != null) {
//            mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
//            LogUtils.d(TAG, "mAppHight = " + displayMetrics.heightPixels + ",mAppWidth = " + displayMetrics.widthPixels
//                    + ",screenHeight = " + screenHeight + ",screenWidth =" + screenWidth);
//            IDCLog.log(this.getClass(), TAG, TAG, "mAppHight = " + displayMetrics.heightPixels + ",mAppWidth = " + displayMetrics.widthPixels
//                    + ",screenHeight = " + screenHeight + ",screenWidth =" + screenWidth);
//            mAppHight = screenHeight;
//            mAppWidth = screenWidth;
//        } else {
//            mAppHight = screenHeight;
//            mAppWidth = screenWidth;
//        }
    }

    private void updataView() {
        removeAllViews();
        createAllViews();
    }


    private void createAllViews() {
        if (isShareMode != 0 || isMeeting) {
            removeAllViews();
            return;
        }
        if (mContext == null || mBlankCanvasView == null ||
                mRsultList == null
            // || mRsultList.size() == 0
        ) {
            return;
        }
        if (mTagList == null || mTagList.length() == 0) {
            return;
        }
        CoreRecord.i(TAG, "mTagList=" + mTagList + ",mRsultList.size:" + mRsultList.size());

        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int nativeAppHight, nativeAppWidth, nativeScreenMode;
                    String[] viewTagList = mTagList.split(",");
                    ArrayList<phoneinfo> ScrennMemberList = new ArrayList<>();
                    for (int i = 0; i < viewTagList.length; i++) {
                        //viewTag[0]:用Tag值去resultList找名字 viewTag[3]：从库得到小View的坐标
                        String[] viewTag = viewTagList[i].split(" ");
                        if (viewTag.length < 5) {
                            continue;
                        }
                        phoneinfo phone = new phoneinfo(viewTag[0], "", "", viewTag[1], viewTag[2], "", "", "", "", viewTag[4]);
                        setIsCloseVoice(viewTag[0] + viewTag[4], viewTag[1].equals("1"), viewTag[4].equals("1"));
                        //
                        ScrennMemberList.add(phone);
                        String userName = viewTag[0];
                        String mobileNum = viewTag[0];
                        int[] mViewPotion = new int[2];
                        int[] mViewBlockPotion = new int[2];
                        /*
                            if ( viewTag[3] != null && Integer.valueOf(viewTag[3]) >= ScreenSplitMode ) {
                                continue ;
                            }
                         */
                        for (int j = 0; j < mRsultList.size(); j++) {
                            if (viewTag[0].equals(mRsultList.get(j).getNumber())) {
                                if (!TextUtils.isEmpty(mRsultList.get(j).getNickName())) {
                                    userName = mRsultList.get(j).getNickName();
                                    CoreRecord.i(TAG, "i:" + i + ",j" + j + ",getNickName()");
                                    if (!userName.endsWith("会场") && userName.length() > 8) {
                                        userName = userName.substring(0, 8) + "...";
                                    }
                                } else if (!TextUtils.isEmpty(mRsultList.get(j).getName())) {
                                    userName = mRsultList.get(j).getName();
                                    CoreRecord.i(TAG, "i:" + i + ",j" + j + ",getName()");
                                    if (!userName.endsWith("会场") && userName.length() > 8) {
                                        userName = userName.substring(0, 8) + "...";
                                    }
                                } else if (!TextUtils.isEmpty(mRsultList.get(j).getNumber())) {
                                    userName = mRsultList.get(j).getNumber();
                                    CoreRecord.i(TAG, "i:" + i + ",j" + j + ",getNumber()");
                                    if (userName.startsWith("+86")) {
                                        userName = userName.substring(3);
                                    }
                                    if (userName.length() >= 7) {
                                        userName.replace(" ", "");
                                        userName = userName.substring(0, 3) + "****" + userName.substring(7);
                                    }
                                }
                                mobileNum = mRsultList.get(j).getNumber();
                                break;
                            }
                        }
                        if ("".equals(userName)) {
                            CoreRecord.i(TAG, "continue");
                            continue;
                        }
                        if (mAppWidth == 0 || mAppHight == 0) {
                            getScreenSize();
                        }
                        nativeAppHight = mAppHight;
                        nativeAppWidth = mAppWidth;
                        nativeScreenMode = ScreenSplitMode;
                        CoreRecord.i(TAG, "nativeAppHight=" + nativeAppHight + ",nativeAppHight=" + nativeAppHight + ",nativeScreenMode:" + nativeScreenMode + ",viewTag[3]:" + viewTag[3] + ",mViewPotion:" + mViewPotion);
                        int result = fvpsipjni.FVPhoneCoreSubPicPositionGet(nativeAppWidth, nativeAppHight, nativeScreenMode, Integer.valueOf(viewTag[3]), mViewPotion);
                        int resultPeopleInfo = fvpsipjni.FVPhoneCorePictureMixerSubPicSizeGet(nativeAppWidth, nativeAppHight, nativeScreenMode, Integer.valueOf(viewTag[3]), mViewBlockPotion);
                        if (result == -1) {
                            continue;
                        }
                        if (mRsultList != null && mRsultList.size() != 0) {
                            if (mContext == null) {
                                return;
                            }
                            Log.i(TAG, "mViewPotion0==" + mViewPotion[0] + "===" + mViewPotion[1] + "mViewBlockPotion" + mViewBlockPotion[0] + "====" + mViewBlockPotion[1]);
                            ShowInfoLayout showInfoLayout = new ShowInfoLayout(mContext, userName, mRsultList);
                            showInfoLayout.setMinimumWidth(mViewBlockPotion[0]);
                            showInfoLayout.setMinimumHeight(44);
                            showInfoLayout.setmViewLeft(mViewPotion[0]);
                            showInfoLayout.setmViewTop(mViewPotion[1] + mViewBlockPotion[1] - 45);
                            showInfoLayout.setUserName(viewTag[0] + viewTag[4]);
                            showInfoLayout.setTag(viewTag[0] + viewTag[4]);
                            showInfoLayout.setCurrentName(viewTag[0]);
                            showInfoLayout.setCameraState(viewTag[2]);
                            showInfoLayout.setMicrophoneState(viewTag[1]);
                            messageManager.add(showInfoLayout);
                            CoreRecord.e("setOrientation", "mViewPotion[0] =" + mViewPotion[0] + "," + "mViewPotion[1] =" + mViewPotion[1]);
                            MeetingPeopleInfoView mMeetingPeopleInfoView = new MeetingPeopleInfoView(mContext);
                            mMeetingPeopleInfoView.setTextColor(mContext.getResources().getColor(R.color.people_info_float_view_white));
                            //mMeetingPeopleInfoView.setBackgroundColor( mContext.getResources().getColor(R.color.black) );
                            mMeetingPeopleInfoView.setBackgroundResource(R.drawable.float__people_name_view);
                            if (phone.getSubStreamType().equals("1")) {
                                mMeetingPeopleInfoView.setText(userName + " (辅流)");
                            } else {
                                mMeetingPeopleInfoView.setText(userName);
                            }
                            mMeetingPeopleInfoView.setmUserId(viewTag[0]);
                            mMeetingPeopleInfoView.setmUserName(viewTag[0] + viewTag[4]);
                            mMeetingPeopleInfoView.setTextSize(11);
                            mMeetingPeopleInfoView.setAlpha(0.5f);
                            mMeetingPeopleInfoView.setTag(viewTag[0] + viewTag[4]);
                            //showInfoLayout.addView(mMeetingPeopleInfoView);

                            VoiceAnimatorView voiceAnimatorView = new VoiceAnimatorView(mContext);
                            voiceAnimatorView.setmActivity(mActivity);
                            voiceAnimatorView.setTag(viewTag[0] + viewTag[4]);
                            voiceAnimatorView.setUserName(viewTag[0] + viewTag[4]);
                            if (phone.getSubStreamType().equals("1")) {
                                voiceAnimatorView.setVisibilityAble(false);
                            }

                            if (allMeetingPeopleMap != null && allMeetingPeopleMap.get(viewTag[0] + viewTag[4]) != null) {
                                MeetingPeopleVoiceBean meetingPeopleVoiceBean = allMeetingPeopleMap.get(viewTag[0] + viewTag[4]);
                                voiceAnimatorView.setTime(meetingPeopleVoiceBean.getVoiceTime());
                                if (viewTag[1].equals("1")) {
                                    voiceAnimatorView.setVoiceClose();
                                } else {
                                    voiceAnimatorView.setVoicePower(meetingPeopleVoiceBean.getVoicePower());
                                }

                            }
                            //showInfoLayout.addView(voiceAnimatorView);

                            HashMap<String, View> widgetViewMap = new HashMap<>();
                            widgetViewMap.put(viewTag[0] + viewTag[4], voiceAnimatorView);
                            if (viewMap != null) {
                                viewMap.put(viewTag[0] + viewTag[4], widgetViewMap);
                            }
                            ShowPictureLinearLayout showRelativeLayout = null;
                            if (viewTag[2].equals("1")) {
                                showRelativeLayout = new ShowPictureLinearLayout(mContext);
                                showRelativeLayout.setmViewLeft(mViewPotion[0]);
                                showRelativeLayout.setmViewTop(mViewPotion[1]);
                                showRelativeLayout.setOrientation(LinearLayout.HORIZONTAL);
                                NoVideoView imageView = new NoVideoView(mContext);
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                imageView.setTag(viewTag[0] + viewTag[4]);
                                showRelativeLayout.setTag(viewTag[0] + viewTag[4]);
                                showRelativeLayout.addView(imageView);
                                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                                layoutParams.width = mViewBlockPotion[0];
                                layoutParams.height = mViewBlockPotion[1];
                                imageView.setLayoutParams(layoutParams);
                            }

                            if (mBlankCanvasView != null) {
                                for (int m = 0; m < mBlankCanvasView.getChildCount(); m++) {
                                    View childView = mBlankCanvasView.getChildAt(m);
                                    if (childView instanceof ShowInfoLayout) {
                                        ShowInfoLayout showInfoContainerLayout = (ShowInfoLayout) childView;
                                        /*for (int j = 0; j < showInfoContainerLayout.getChildCount(); j = j + 2) {
                                            View showInfoChildView = showInfoContainerLayout.getChildAt(j);
                                            if (showInfoChildView.getTag().equals(mMeetingPeopleInfoView.getTag())) {
                                                showInfoContainerLayout.removeView(showInfoContainerLayout.getChildAt(j));
                                                showInfoContainerLayout.removeView(showInfoContainerLayout.getChildAt(j + 1));
                                                CoreRecord.i(TAG, "showInfoChildView" + ",remove=" + showInfoChildView.getTag());
                                                break;
                                            }

                                        }*/

                                        /*if (showInfoContainerLayout.getTag().equals(showInfoLayout.getTag())) {
                                            mBlankCanvasView.removeViewAt(m);
                                            CoreRecord.i(TAG, "showInfoContainerLayout" + ",remove=" + showInfoContainerLayout.getTag());
                                            break;
                                        }*/
                                    } else if (childView instanceof ShowPictureLinearLayout) {
                                        ShowPictureLinearLayout showChildLayout = (ShowPictureLinearLayout) childView;
                                        if (viewTag[2].equals("0") && showChildLayout.getTag().equals(viewTag[0] + viewTag[4])) {
                                            showChildLayout.removeAllViews();
                                            mBlankCanvasView.removeViewAt(m);
                                            CoreRecord.i(TAG, "imageView" + ",remove=" + showChildLayout.getTag());
                                            break;
                                        }
                                    }
                                }
                                if (viewTag[2].equals("1") && showRelativeLayout != null) {
                                    mBlankCanvasView.addView(showRelativeLayout);
                                    CoreRecord.i(TAG, "imageView" + ",add=" + showRelativeLayout.getTag());
                                }
                                /*if (!mHostMan.equals(uTitleStr)) {
                                    mBlankCanvasView.addView(showInfoLayout);
                                }*/
                                // mBlankCanvasView.addView(showInfoLayout);
                                CoreRecord.i(TAG, "mMeetingPeopleInfoView" + ",add=" + mMeetingPeopleInfoView.getTag());
                            }
                            if (TextUtils.equals(phone.getNumber(), mHostName)) {
                                showInfoLayout.setVisibility(View.GONE);
                            } else {
                                showInfoLayout.setVisibility(View.VISIBLE);
                            }
                            mBlankCanvasView.addView(showInfoLayout);
                        }
                    }
                    if (MemberManager.getInstance().getPeopleInfoFloatCallback() == null) {
                        Log.e(TAG, "set people float callback.");
                        if (peopleInfoFloatCallback == null) {
                            peopleInfoFloatCallback = new PeopleInfoFloatCallback();
                        }
                        MemberManager.getInstance().setPeopleInfoFloatCallback(peopleInfoFloatCallback);
                    }
                    MemberManager.getInstance().updateMemberListByVideoStream(ScrennMemberList);
                    ScrennMemberList.clear();
                }
            });
        }
    }

    public void removeAllViews() {
        try {
            if (mActivity != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBlankCanvasView != null) {
                            mBlankCanvasView.removeAllViews();
                        }
                    }
                });
            }
            if (viewMap != null && viewMap.size() > 0) {
                viewMap.clear();
            }

            /*
                if( mListMeetingPeopleInfoView != null && mListMeetingPeopleInfoView.size()!= 0 ){
                    mListMeetingPeopleInfoView.clear();
                }
             */
        } catch (Exception e) {
            CoreRecord.e(TAG, "removeAllViews failure");
            e.printStackTrace();
        }
    }


    public void freeAll() {

        CoreRecord.e(TAG, "freeAll()");
        /*
            if ( mListMeetingPeopleInfoView != null ) {
                mListMeetingPeopleInfoView.clear();
            }
         */
        if (mBlankCanvasView != null) {
            mBlankCanvasView.removeAllViews();
        }

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (allMeetingPeopleMap != null) {
            allMeetingPeopleMap.clear();
        }

        if (viewMap != null) {
            viewMap.clear();
        }

        if (voiceMap != null) {
            voiceMap.clear();
        }

        peopleInfoFloatCallback = null;

        if (singleThreadExecutor != null) {
            singleThreadExecutor.shutdown();
            singleThreadExecutor = null;
        }
        mContext = null;
        /*
            mBlankCanvasView = null;
            if (mRsultList!=null){
                mRsultList.clear();
            }
            mRsultList = null;
            mContext = null ;
        */
        CoreRecord.e(TAG, "exit freeAll()");
    }


    public void showMeetingPeopleVoice(String peopleVoiceInfo) {

        if (mContext == null || mBlankCanvasView == null ||
                this.mRsultList == null || this.mRsultList.size() == 0
                || this.allMeetingPeopleMap == null) {
            return;
        }

        String[] allVoiceInfos = peopleVoiceInfo.split(",");
        ArrayList<MeetingPeopleVoiceBean> meetingPeopleVoiceBeans = new ArrayList<>();
        for (int i = 0; i < allVoiceInfos.length; i++) {
            String childVoiceInfo = allVoiceInfos[i];
            String[] split = childVoiceInfo.split(" ");
            MeetingPeopleVoiceBean meetingPeopleVoiceBean = new MeetingPeopleVoiceBean();
            meetingPeopleVoiceBean.setUserName(split[0] + "0");
            meetingPeopleVoiceBean.setVoicePower(Integer.parseInt(split[1]));
            meetingPeopleVoiceBeans.add(meetingPeopleVoiceBean);
            if (allMeetingPeopleMap != null) {
                MeetingPeopleVoiceBean tmpVB = allMeetingPeopleMap.get(meetingPeopleVoiceBean.getUserName());
                if (tmpVB == null) {
                    allMeetingPeopleMap.put(meetingPeopleVoiceBean.getUserName(), meetingPeopleVoiceBean);
                }
            }

        }

        if (mBlankCanvasView.getChildCount() > 0 && meetingPeopleVoiceBeans.size() > 0 && viewMap.size() > 0) {
            for (int i = 0; i < meetingPeopleVoiceBeans.size(); i++) {
                MeetingPeopleVoiceBean meetingPeopleVoiceBean = meetingPeopleVoiceBeans.get(i);
                if (viewMap != null && viewMap.get(meetingPeopleVoiceBean.getUserName()) != null && viewMap.get(meetingPeopleVoiceBean.getUserName()).get(meetingPeopleVoiceBean.getUserName()) != null) {

                    VoiceAnimatorView voiceAnimatorViewLoc = (VoiceAnimatorView) viewMap.get(meetingPeopleVoiceBean.getUserName()).get(meetingPeopleVoiceBean.getUserName());
                    if (voiceAnimatorViewLoc != null) {
                        MeetingPeopleVoiceBean voiceBean = allMeetingPeopleMap.get(meetingPeopleVoiceBean.getUserName());
                        MeetingPeopleVoiceBean voiceCloseBean = voiceMap.get(meetingPeopleVoiceBean.getUserName());
                        CoreRecord.d("voiceAnimatorViewLoc", "VoicePower =" + meetingPeopleVoiceBean.getVoicePower());
                        CoreRecord.d("voiceAnimatorViewLoc", "voiceBean  isCloseVoice =" + voiceBean.isCloseVoice());
                        if (voiceBean != null) {
                            if (voiceCloseBean != null) {
                                voiceBean.setCloseVoice(voiceCloseBean.isCloseVoice());
                            }
                            if (voiceBean.isCloseVoice()) {
                                voiceAnimatorViewLoc.setVoiceClose();
                            } else {
                                voiceAnimatorViewLoc.setVoicePower(meetingPeopleVoiceBean.getVoicePower());
                            }
                            int voiceTime = voiceBean.getVoiceTime();
                            if (meetingPeopleVoiceBean.getVoicePower() == 0) {
                                voiceBean.setVoiceTime(++voiceTime);
                            } else {
                                voiceBean.setVoiceTime(0);
                            }
                            voiceBean.setVoicePower(meetingPeopleVoiceBean.getVoicePower());
                        }
                    }
                }
            }
        }
    }


    private void setIsCloseVoice(String num, boolean isCloseVoice, boolean isVisibility) {
        if (TextUtils.isEmpty(num)) {
            return;
        }
        CoreRecord.d("setIsCloseVoice", "num =" + num + " , " + "isCloseVoice =" + isCloseVoice);
        MeetingPeopleVoiceBean voiceBean = new MeetingPeopleVoiceBean();
        voiceBean.setCloseVoice(isCloseVoice);
        voiceBean.setVisibility(isVisibility);
        voiceBean.setUserName(num);
        if (voiceMap != null) {
            voiceMap.put(num, voiceBean);
        }
    }

    private class PeopleInfoFloatCallback implements MemberCallback {

        @Override
        public void updateMemberListInfo(final ArrayList<phoneinfo> NumberMatchNickName) {
            Log.e("MemberManager", "phoneinfo list updateMemberListInfo  PeopleFloatView.");
            mRsultList = NumberMatchNickName;
        }

        @Override
        public void updateMemberNickName() {
            Log.e("MemberManager", "phoneinfo list updateMemberNickName PeopleFloatView.");
            updataView();
        }
    }

    public void setHostName(String name) {
        Log.i(TAG, "Host name is " + name);
        if (!TextUtils.isEmpty(name) && !name.equals(mHostName)) {
            Log.i(TAG, "Host name is " + name + " ,update view");
            mHostName = name;
            updataView();
        }
    }
}




