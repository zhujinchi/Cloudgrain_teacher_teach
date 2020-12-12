package com.idcvideo.meetinglibrary.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fvp.sip.CameraController;
import com.fvp.sip.MyVideoDecoder;
import com.fvp.sip.MyVideoRequest;
import com.fvp.sip.VideoFlagContacts;
import com.fvp.sip.VideoInOut;
import com.fvp.sip.VideoRequestCallback;
import com.fvp.sip.WHSize;
import com.idcmeeting.library.core.MeetingEntity;
import com.idcmeeting.library.manager.MemberManager;
import com.idcmeeting.library.manager.callback.MemberCallback;
import com.idcmeeting.library.message.MessageProcessLoop;
import com.idcmeeting.library.message.agent.CallHangupRingAgent;
import com.idcmeeting.library.surface.CameraSurfaceTextureListener;
import com.idcmeeting.library.surface.PeopleInfoFloatViewListener;
import com.idcmeeting.library.unity.phoneinfo;
import com.idcmeeting.library.utils.SharedPreferencesUtil;
import com.idcmeeting.library.utils.SystemUtil;
import com.idcmeeting.library.view.BlankCanvasView;
import com.idcvideo.httplibrary.InternetInfoView.InternetInfoView;
import com.idcvideo.httplibrary.InternetInfoView.MeetingRequestView;
import com.idcvideo.httplibrary.TreeViewAdapter.AddressbookUserType;
import com.idcvideo.httplibrary.TreeViewAdapter.User;
import com.idcvideo.httplibrary.bean.MeetingTaskItem;
import com.idcvideo.httplibrary.core.HkhKeyInfo;
import com.idcvideo.httplibrary.model.MeetingRequestModel;
import com.idcvideo.httplibrary.presenter.ContactsInternetUrlStatus;
import com.idcvideo.httplibrary.presenter.InternetInfoPresenter;
import com.idcvideo.httplibrary.presenter.MeetingRequestPresenter;
import com.idcvideo.httplibrary.presenter.MemberNicknamePresenter;
import com.idcvideo.httplibrary.utils.LogUtils;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.adapter.MeetingActivityDialogAdapter;
import com.idcvideo.meetinglibrary.adapter.MeetingActivityDialogJoinAdapter;
import com.idcvideo.meetinglibrary.adapter.MeetingActivityDialogSecondAdapter;
import com.idcvideo.meetinglibrary.adapter.MeetingManageDialogTitleAdapter;
import com.idcvideo.meetinglibrary.adapter.TreeViewAdapter.AddressBookAdapter;
import com.idcvideo.meetinglibrary.adapter.TreeViewAdapter.Node;
import com.idcvideo.meetinglibrary.adapter.TreeViewAdapter.TreeItemClickListener;
import com.idcvideo.meetinglibrary.adapter.basequickAdapter.MeetingTaskItemAdapter;
import com.idcvideo.meetinglibrary.broadcast.MeetingBroadcast;
import com.idcvideo.meetinglibrary.core.BridgeControl;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;
import com.idcvideo.meetinglibrary.fragment.MeetingDeskFragment;
import com.idcvideo.meetinglibrary.listener.ConfirmListener;
import com.idcvideo.meetinglibrary.listener.ControlChangeListener;
import com.idcvideo.meetinglibrary.listener.MeetingPersonStatusListener;
import com.idcvideo.meetinglibrary.manager.BluetoothDevicesManager;
import com.idcvideo.meetinglibrary.manager.NetworkManager;
import com.idcvideo.meetinglibrary.manager.ScreenRecordManager;
import com.idcvideo.meetinglibrary.manager.SharedModeScreenInfoManager;
import com.idcvideo.meetinglibrary.manager.callback.ScreenRecordCallback;
import com.idcvideo.meetinglibrary.screenshare.DesktopShareService;
import com.idcvideo.meetinglibrary.utils.ArrayListCacheUtils;
import com.idcvideo.meetinglibrary.utils.AudioUtils;
import com.idcvideo.meetinglibrary.utils.ButtonUtils;
import com.idcvideo.meetinglibrary.utils.EditTextUtils;
import com.idcvideo.meetinglibrary.utils.GetPhoneNumberFromMobile;
import com.idcvideo.meetinglibrary.utils.GlobalUtils;
import com.idcvideo.meetinglibrary.utils.NavigationBarUtil;
import com.idcvideo.meetinglibrary.utils.NotificationsPermissionUtils;
import com.idcvideo.meetinglibrary.utils.PermissionUtil;
import com.idcvideo.meetinglibrary.utils.PinyinUserComparator;
import com.idcvideo.meetinglibrary.utils.PopupDialog;
import com.idcvideo.meetinglibrary.utils.RandomUtils;
import com.idcvideo.meetinglibrary.utils.UiUtils;
import com.idcvideo.meetinglibrary.utils.popu.CloseMeetingPopupDialog;
import com.idcvideo.meetinglibrary.utils.popu.DisconnectDialog;
import com.idcvideo.meetinglibrary.view.InvitationViewModel;
import com.idcvideo.meetinglibrary.view.swipemenulistview.SwipeMenuListView;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.idcvideo.meetinglibrary.manager.SharedModeScreenInfoManager.SHARED_STREAM_HEADER_CONTENT_WAHT;

import static com.idcvideo.meetinglibrary.manager.SharedModeScreenInfoManager.SHARED_STREAM_HEADER_CONTENT_WAHT;

public class MeetingActivity extends SActivity implements InternetInfoView , MeetingRequestView, View.OnClickListener {

    BlankCanvasView blankCanvasView;
    ImageButton meetingTvGd;
    ImageView meetingTvGfImage;
    ImageView meetingTvJyImage;
    ImageView meetingTvFsImage;
    ImageView meetingLlMidAuto;
    ImageView meetingLlMid1;
    ImageView meetingLlMid2;
    ImageView meetingLlMid3;
    ImageView meetingLlMid4;
    ImageView meetingLlMid5;
    ImageView meetingLlMid6;
    ImageView showHandsIcon;
    ImageView backgroundImageView;
    ImageView netIv;
    ImageView meetingInfoImageview;
    ImageView meetingRecoderImageview;
    TextView meetingTvTime;
    TextView meetingTvMeetingtheme;
    TextView meetingLlLeft1;
    TextView meetingLlLeft3;
    TextView meetingRecoderTextView;
    TextView showHandsText;
    TextView sharePositonText;
    TextView shareDesktopSponsorName;
    LinearLayout meetingTvPicture;
    LinearLayout meetingTvQh; // 切换前后摄像头按钮
    LinearLayout llEnd;
    LinearLayout meetingTvYq; // 邀请
    LinearLayout meetingLlMid;
    LinearLayout meetingLlLeft2;
    LinearLayout meetingLlLeft;
    LinearLayout meetingTvRw;
    LinearLayout showLayout;
    LinearLayout llMeetingSharing;
    FrameLayout meetingDeskFragment;
    RelativeLayout meetingLlTop;
    RelativeLayout meetingTvFs;
    RelativeLayout meetingTvJy; // 禁言
    RelativeLayout meetingTvGf; // 扬声器
    RelativeLayout meetingTvRy; // 主持人
    RelativeLayout meetingActivity;
    RelativeLayout shareLayout;
    RelativeLayout showHands;
    RelativeLayout mLocatePreviewLayout;
    RelativeLayout meetingRecoder;

    TextureView meetingSfYd;
    SurfaceView meetingSfBd;

    private static final String TAG = "MeetingActivity";
    private static final int PHONE_CALL_RECIVER_AND_HANG_DOEN = 5984;
    private static final int APPLY_FOR_MEETINGCODE = 5983;
    public  static final int VIDEO_STREAM_HEADER_CONTENT_WAHT = 519618;
    public  static final int VIDEO_STREAM_HEADER_MERGE_MODE_WAHT = 519619;
    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private final static long MULTIPLE = 10000000;//三种通讯录的最大容量
    private static boolean isActive;  // 全局变量
    private static boolean openAudioSteam = false;
    private boolean isTagging;
    private boolean isSummary = false;
    private boolean isMeetingLockStatus;  // 会议锁定状态
    private boolean isErr = true;
    private boolean times = false;  // 全屏点击事件
    private boolean bdtimes = false;  // 本地
    private boolean qhtimes = false;  // 切换
    private boolean jytimes = false;  // 禁言
    private boolean wltimes = false;  // 静音
    private boolean jstimes = false;  // 举手
    private boolean jinyan = false;  // dialog 全体禁言
    private boolean leftll = false;
    private boolean left1 = false;
    private boolean left2 = false;
    private boolean isSendVideo = false;
    private boolean isAnswerPhoneCall = false;
    private boolean needMyVideoOpen = false;  // false:发送视频 true：停止发送
    private boolean needMyMicOpen = false;  // false:发送声音 true：停止发送
    private boolean isTransferPersonPermission;  // 是否转发 true:转发人员；false:非转发人员
    public  boolean isMeetingShared = false;  // 是否开启共享
    private boolean isMeetingPadShare;
    private boolean meetingFrameCaptivate = false;
    private int     iTransferType;  // 转发状态
    private int     netMeetingType;  // 会议类型：3-点对点
    private int     videowidch;
    private int     videoheight;
    private int     taskPageNo = 1;
    private int     tapDebugModeTimes = 0;
    private int     realScreenStatus;  // 库返回视频的真实分屏位置
    private int     screenWidth;
    private int     screenHeight;
    private int     netMeetingId = -1;
    private int     totalNumber;
    private int     mCurrentCounter;
    private int     contectTime = 0;

    private AlertDialog inviteMemberDialog;
    private AlertDialog alertjoinDialog;  // 参加会议的dialog
    private AlertDialog alertDialog;  // 主持会议的dialog
    private ArrayList<User> favoriteList = new ArrayList<>();  //收藏联系人信息
    private ArrayList<User> mobilePhoenList = new ArrayList<>();  // 保存的手机联系人信息
    private ArrayList<User> enterpriseList = new ArrayList<User>();
    private AddressBookAdapter<User> meetingContactsAdapter = null;
    private BluetoothDevicesManager mBluetoothDevicesManager;
    private com.fvp.sip.CameraController CameraController;
    private CameraSurfaceTextureListener mCameraSurfaceTextureListener;
    private EditText detailEditText;
    private FragmentTransaction fragmentTransaction;
    private HeadsetDetectReceiver receiver;
    private RelativeLayout netSignalDisplay;
    private RelativeLayout videoRl;
    private RelativeLayout meetingCameraLl;
    private RelativeLayout meetingInviteLl;
    private TextView meetingPipTv;
    private TextView netRecAudioTv;
    private TextView netSendAudioTv;
    private TextView netRecVideoTv;
    private TextView netSendVideoTv;
    private TextView netRecData;
    private TextView netSendData;
    private TextView netSignalDisplayShang;
    private TextView netSignalDisplayXia;
    private TextView netSignalDisplayYanshi;
    private TextView mNumber2;
    private TextView meetingTextView;
    private TextView meetingLock;
    private ImageButton no;
    private ImageView inviteIv;
    private ImageView meetingManageIv;
    private ImageView meetShareIcon;
    private ImageView cameraIv;
    private InternetInfoPresenter internetInfoPresenter;
    private Intent intentService = null;
    private List<String> detail;  // 会议详情集合
    private List<phoneinfo> resultList = new ArrayList<>();  // 创建会议界面传递过来的 联系人数据
    private List<phoneinfo> secondList = new ArrayList<>();  // 第二个dialog邀请的联系人的数据
    private ListView joinListview;
    private Looper mMeetingVideoDateLooper;
    private Map<Integer, phoneinfo> resultMap = new HashMap();  // 已经选择的联系人信息
    private MeetingActivityDialogAdapter hostAdapter;
    private MeetingActivityDialogJoinAdapter joinAdapter;
    private MeetingManageDialogTitleAdapter hostTitleAdapter;
    private MeetingManageDialogTitleAdapter joinTitleAdapter;
    private MeetingDeskFragment meetingDeskFragment1;
    private MediaProjectionManager mMediaProjectionManager;
    private MeetingVideoDataHandler mMeetingVideoDateHandler;
    private MeetingActivityMemberCallback meetingActivityMemberCallback;
    private MeetingPerson meetingPerson;
    private MeetingTaskItemAdapter subscribeFragmentAdapter;
    private MemberNicknamePresenter memberNicknamePresenter;
    private MeetingRequestPresenter meetingRequestPresenter;
    private MeetingBroadcast meetingBroadcast;
    private MyVideoDecoder mrVideoDecoder;
    private MyVideoRequest myVideoRequest;
    private PeopleInfoFloatViewListener mPeopleInfoFloatViewListener;
    private RecyclerView meetingTaskRY;
    private RecyclerView meetingContactsRecyclerView = null;
    private String meetingNumber;
    private String meetingDetailId;
    private String mHostMan = "";  // 会议主持人
    private String uTitleStr;  // 电话手机号
    private String mediaStatusTag = "";
    private String ScreenNumber = "1";  // 默认8分屏
    private String shareSponorNumber = "";  // 共享发起人的号码
    private Surface meetingSurface;
    private PinyinUserComparator pinyinUserComparator;
    private ProgressDialog screenRecordSaveProgressDialog;
    private SharedModeScreenInfoManager mSharedModeScreenInfoManager;
    private ScreenRecordManager screenRecordManager;
    private MeetingScreenRecordCallback meetingScreenRecordCallback;
    private SwipeMenuListView manageListview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextureView cameraPreviewTexture;
    public  VideoInOut videoInOut;
    private View manageDialogView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  // 华为手机偶现竖屏，添加横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.background_meeting);
        this.getWindow().setBackgroundDrawable(drawable);
        setContentView(R.layout.activity_meeting2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        getWindow().addFlags(WindowManager.LayoutParams.
                FLAG_KEEP_SCREEN_ON);  // 应用运行时，保持屏幕高亮，不锁屏
        // 所有底边按钮的点击事件
        initView();
        Log.i(TAG, "on create");
        ImageView netSignalDisplayExitIv = (ImageView) findViewById(R.id.net_signal_display_exit_iv);
        RelativeLayout meetingTvGdLl = (RelativeLayout) findViewById(R.id.meeting_tv_gd_ll);
        NavigationBarUtil.hideNavigationBar(getWindow());
        IdcMediaKit.getInstance().setMeetingMediaKit(MeetingActivity.this);
        //----------------------------------
        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        ActivityEntity.getInstance().setCurrentActivity(this);
        Log.d(" timer.schedule ", "-=-=-=>>onCreate...runnable = " + Thread.currentThread().getId());
        meetingCameraLl.setOnClickListener(this);
        meetingTvGdLl.setOnClickListener(this);
        meetingInviteLl.setOnClickListener(this);
        meetingRecoder.setOnClickListener(this);
        netSignalDisplayExitIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netSignalDisplay.setVisibility(View.GONE);
            }
        });
        netSignalDisplay.getBackground().setAlpha(200);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();// 屏幕宽（像素，如：720px）
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();// 屏幕高（像素，如：1280px）
        MeetingEntity.getInstance().setScreenWidth(screenWidth);
        MeetingEntity.getInstance().setScreenHeight(screenHeight);
        if ((mMeetingVideoDateLooper = Looper.myLooper()) != null) {
            mMeetingVideoDateHandler = new MeetingVideoDataHandler(mMeetingVideoDateLooper);
        } else if ((mMeetingVideoDateLooper = Looper.getMainLooper()) != null) {
            mMeetingVideoDateHandler = new MeetingVideoDataHandler(mMeetingVideoDateLooper);
        } else {
            mMeetingVideoDateHandler = null;
        }
        if (handler != null) {
            handler.postDelayed(runable, 1000);
        }
        if (mSharedModeScreenInfoManager == null) {
            mSharedModeScreenInfoManager = new SharedModeScreenInfoManager();
            mSharedModeScreenInfoManager.setHandler(mMeetingVideoDateHandler);
        }
        getTitle();
        if (meetingBroadcast == null) {
            meetingBroadcast = new MeetingBroadcast();
            meetingBroadcast.registCast(this);
        }
        MeetingEntity.getInstance().setMeetingState(true);
        meetingActivityMemberCallback = new MeetingActivityMemberCallback();
        MemberManager.getInstance().setMeetingActivityCallback(meetingActivityMemberCallback);
        memberNicknamePresenter = new MemberNicknamePresenter();
        MemberManager.getInstance().updateNickName(memberNicknamePresenter);

        meetingNumber = MeetingEntity.getInstance().getMeetingNumber();  // 获取会议id
        uTitleStr = HkhKeyInfo.getInstance().getUserName();  // 从本地取出手机号
        Log.e("meetingnumber", "meetingnumber=" + meetingNumber);
        Log.d(TAG, "FVPhone Get Detail(07), number is " + meetingNumber);

        BridgeControl.ConferenceDetail();
        BridgeControl.ConferenceMemberVersion(0);
        SharedPreferencesUtil.getsInstance().put("NumberListInfoVersion", 0);

        BridgeControl.ConferenceSelectChannel(false);
        mCameraSurfaceTextureListener = new CameraSurfaceTextureListener();
        try {
            meetingPerson = new MeetingPerson();  // 动态注册广播
            IntentFilter filter = new IntentFilter();
            filter.addAction("MeetingPersons");
            filter.addAction("MeetingDetails");
            filter.addAction("deleteMembers");
            filter.addAction("addMembers");
            filter.addAction("closeMeetings");
            filter.addAction("PersonStates");
            filter.addAction("NoSpeaks");
            filter.addAction("MeetingPowers");
            filter.addAction("MeetingControls");
            filter.addAction("stopVideos");
            filter.addAction("ConferenceMode");
            filter.addAction("ScreenNumbers");
            filter.addAction("MeetingTagging");
            filter.addAction("MeetingShareds");
            filter.addAction("HangUpCallMeeting");
            filter.addAction("SharingControl");
            filter.addAction("MeetingEndDeskShared");//结束桌面共享
            filter.addAction("MeetingHandup");
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(meetingPerson, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPeopleInfoFloatViewListener = new PeopleInfoFloatViewListener();
        mCameraSurfaceTextureListener.setPositionLayout(mLocatePreviewLayout);
        touchinit();
        try {
            receiver = new HeadsetDetectReceiver();  // 耳机监听
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
            registerReceiver(receiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBluetoothDevicesManager = new BluetoothDevicesManager();
        mBluetoothDevicesManager.init(this);
        Log.i(TAG, "fvpOpenAudio open Audio stream open is " + openAudioSteam);
        if (!openAudioSteam) {  // 打开声音
            BridgeControl.ConferenceMicPhone(1);
            MeetingEntity.getInstance().getJniContentClass().NativeMeetingAudioOpen();
            openAudioSteam = true;
            Log.i(TAG, "fvpOpenAudio open.");
        }
        int[] widhe = new int[2];
        int videoback = BridgeControl.ConferenceSize(widhe);
        if (videoback == 0 && widhe[0] > 0 && widhe[1] > 0) {
            videowidch = widhe[0];
            videoheight = widhe[1];
            LogUtils.d(TAG, "meeting display  videowidth ==>" + videowidch + " videoHeight ==>" + videoheight);
        } else {
            videowidch = 1280;
            videoheight = 720;
        }
        backinit();
        getMeetingType(3);
        int[] locations = new int[2];
        meetingSfYd.getLocationInWindow(locations);
        LogUtils.d("TransformOffset", "float x =" + locations[0]);
        openLocalVideoAndMoveVideoMethod(videowidch, videoheight);
        initCameraOrAudioMethod();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this); // 注册EventBus
        }
        netMeetingId = MeetingEntity.getInstance().getNetMeetingId();
        setMeetingSummary();


        mPeopleInfoFloatViewListener.invaledateView(MeetingActivity.this, blankCanvasView, resultList);
        if (NetworkManager.isMobileConnected()) {
            UiUtils.showToastLong(getResources().getString(R.string.get_meeting_type_mobile_hint));
        }
        Log.i(TAG, "on create success.");
    }

    private void updateMeetingSurfaceView(int videowidch, int videoheight ,boolean isP2P) {
        if (screenWidth == 0 && screenHeight == 0) {
            LogUtils.i(TAG, "updateMeetingSurfaceView method screenWidth = 0 screenHeight = 0");
            return;
        }
        MeetingEntity.getInstance().setScreenWidth(screenHeight);
        MeetingEntity.getInstance().setScreenHeight(screenWidth);
        /*float scale_width = screenWidth / videowidch;  // 获取拉伸的宽度比例
        float scale_hight = screenHeight / videoheight;  // 获取拉伸高度的比例
        if (scale_width > scale_hight) {  // 保持高度不变进行宽度拉伸
            int mVideoWidth = (int) (videowidch * scale_width);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVideoWidth, screenHeight);

            LogUtils.e("updateMeetingSurfaceView", "scale_width>scale_hight      mVideoWidth =" + mVideoWidth + " , screenHeight =" + screenHeight);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            *//*meetingSfYd.setLayoutParams(params);
            meetingSfYd.requestLayout();
            blankCanvasView.setLayoutParams(params);
            meetingDeskFragment.setLayoutParams(params);*//*
            videoRl.setLayoutParams(params);
        } else {
            int mVideoWidth = (int) (videowidch * scale_hight);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mVideoWidth, screenHeight);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            MeetingEntity.getInstance().setScreenWidth(mVideoWidth);
            MeetingEntity.getInstance().setScreenHeight(screenHeight);
            LogUtils.e("updateMeetingSurfaceView", "scale_width < scale_hight    " +
                    "  mVideoWidth =" + mVideoWidth + " , screenHeight =" + screenHeight);
            *//*meetingSfYd.setLayoutParams(params);
            meetingSfYd.requestLayout();
            blankCanvasView.setLayoutParams(params);
            meetingDeskFragment.setLayoutParams(params);*//*
            videoRl.setLayoutParams(params);
        }*/
    }

    private void initCameraOrAudioMethod() {
        if (!SharedPreferencesUtil.getsInstance().getBoolean(VideoFlagContacts.JOIN_MEETING_CLOSE_AUDIO_TAG, false)) {
            StartSendVoice();
            BridgeControl.ConferenceMute(1);
        } else {
            if (meetingTvJyImage != null) {
                meetingTvJyImage.setImageResource(R.drawable.meeting_jingyin);
            }
            jytimes = false;
        }
        if (SharedPreferencesUtil.getsInstance().getBoolean(VideoFlagContacts.JOIN_MEETING_CLOSE_CAMERA_TAG, false)) {
            cameraIv.setImageResource(R.drawable.meeting_icon_camera_close);
            BridgeControl.ConferenceCameraSend(1);
            BridgeControl.ConferenceCamera(1);
            mCameraSurfaceTextureListener.setStopSpfs(true);
            left1 = true;
        } else {
            BridgeControl.ConferenceCameraSend(0);
            BridgeControl.ConferenceCamera(0);
            mCameraSurfaceTextureListener.setStopSpfs(false);
            left1 = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "on start");
        if (GlobalUtils.getLocalMeetingStatus(true)) {
            if (NetworkManager.isConnected()) {
                Log.d(TAG, "FVPhone Get Detail(08), number is " + meetingNumber);
                BridgeControl.ConferenceDetail();
            } else {
                Log.w(TAG,"No network may layout wrong.");
            }
            LogUtils.i(TAG, "The phone is sharing desk and get meeting details");
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        int orientation = newConfig.orientation;
        String msg = null;
        if (orientation == 1) {
            msg = "port";
            if (netMeetingType == 3) {
                if (
                    !(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT) ){
                    Log.i(TAG, "config change protrait h " + screenHeight + " w " + screenWidth);
                    Matrix transform = new Matrix();
                    float sx = (float) (screenWidth / (screenHeight + 0.00001));
                    float sy = (float) (screenHeight / (screenWidth + 0.00001));
                    float px = (float) (screenWidth / 2.0);
                    float py = (float) (screenHeight / 2.0);
                    transform.preScale(sy, sx, px, py);
                    transform.postRotate(90, px, py);
                    meetingSfYd.setTransform(transform);
                    if (mCameraSurfaceTextureListener != null) {
                        mCameraSurfaceTextureListener.setP2P(true ,true);
                    }
                    cameraPreviewTexture.setTransform(new Matrix());
                }
            }
        } else if (orientation == 2) {
            msg = "land";
            if (netMeetingType == 3) {
                if (
                    !(getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                            || getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE) ){
                    Log.i(TAG, "config change landspace h " + screenHeight + " w " + screenWidth);
                    Matrix transform = new Matrix();
                    meetingSfYd.setTransform(transform);
                    if (mCameraSurfaceTextureListener != null) {
                        mCameraSurfaceTextureListener.setP2P(true ,false);
                    }
                    Matrix matrix = new Matrix();
                    matrix.preRotate(-90 ,320 ,180);
                    matrix.postScale(1.7777f ,0.5625f ,320 ,180 );
                    cameraPreviewTexture.setTransform(matrix);
                }
            }
        }
        Log.i(TAG, "onConfigurationChanged orientation " + msg);
        //newConfig.setLayoutDirection();
        super.onConfigurationChanged(newConfig);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public void setTransferPersonPermission(boolean transferPersonPermission) {
        isTransferPersonPermission = transferPersonPermission;
        if (meetingDeskFragment1 != null) {
            meetingDeskFragment1.setTransferPersonPermission(isTransferPersonPermission);
        }
        if (isTransferPersonPermission) {
            shareLayout.setVisibility(View.GONE);
            Log.i("desktopdisappear", "shareLayout disappear GONE position is 8");
            showHands.setVisibility(View.VISIBLE);
            meetingTvQh.setVisibility(View.INVISIBLE);  // 隐藏按钮，避免误触
            meetingTvFs.setVisibility(View.GONE);
            meetingTvRy.setVisibility(View.GONE);
            meetingTvYq.setVisibility(View.GONE);
            meetingTvRw.setVisibility(View.GONE);
            meetingTvPicture.setVisibility(View.GONE);
            meetingTvJy.setVisibility(View.GONE);
            meetingLlLeft1.setVisibility(View.GONE);
            meetingCameraLl.setVisibility(View.GONE);
        } else {
            if (times) {
                meetingTvQh.setVisibility(View.VISIBLE);
            } else {
                meetingTvQh.setVisibility(View.GONE);
            }
            meetingTvRy.setVisibility(View.VISIBLE);
            meetingTvFs.setVisibility(View.VISIBLE);
            meetingTvYq.setVisibility(View.VISIBLE);
            meetingTvRw.setVisibility(View.GONE);
            meetingTvJy.setVisibility(View.VISIBLE);
            if (GlobalUtils.getShareType(true) == 3 || (GlobalUtils.getShareType(true) == 0 && !isMeetingShared)) {
                shareLayout.setVisibility(View.VISIBLE);
            } else {
                shareLayout.setVisibility(View.GONE);
                Log.i("desktopdisappear", "shareLayout disappear GONE position is 9");
            }
            if (GlobalUtils.getLocalMeetingStatus(true)) {
                shareLayout.setVisibility(View.GONE);
                Log.i("desktopdisappear", "shareLayout disappear GONE position is 11");
            }
            StopSendVideo();
            showHands.setVisibility(View.GONE);
            meetingLlLeft1.setVisibility(View.VISIBLE);
            meetingCameraLl.setVisibility(View.VISIBLE);
        }
    }

    private void setNetMeetingType(int sNetMeetingType) {
        Log.i(TAG, "sNetMeetingType is " + sNetMeetingType);
        if (sNetMeetingType == netMeetingType) {
            return;
        }
        netMeetingType = sNetMeetingType;

        if (netMeetingType == 3) { //点对点呼叫
            Log.i(TAG,"signle p2p in " + getRequestedOrientation() + " ,config " + getResources().getConfiguration().orientation);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            updateMeetingSurfaceView(videowidch ,videoheight, true);
            Matrix transform = new Matrix();
            float sx = (float) (screenWidth / (screenHeight + 0.00001));
            float sy = (float) (screenHeight / (screenWidth + 0.00001));
            float px = (float) (screenWidth / 2.0);
            float py = (float) (screenHeight / 2.0);
            transform.preScale(sy, sx, px, py);
            transform.postRotate(90, px, py);
            meetingSfYd.setTransform(transform);
            if (mCameraSurfaceTextureListener != null) {
                mCameraSurfaceTextureListener.setP2P(true ,true);
                Log.i(TAG,"signle p2p set protrait true ");
            }
        } else { //会议
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);  // 强制为横屏
            if (mCameraSurfaceTextureListener != null) {
                mCameraSurfaceTextureListener.setP2P(false ,false);
            }
            LogUtils.e(TAG, "netMeetingType 625 netMeetingType=" + netMeetingType);  // 是会议
            Matrix matrix = meetingSfYd.getMatrix();
            matrix.reset();
            meetingSfYd.setTransform(matrix);
            /*Matrix meetingMatrix = cameraPreviewTexture.getMatrix();
            meetingMatrix.reset();
            cameraPreviewTexture.setTransform(meetingMatrix);*/
            if (mCameraSurfaceTextureListener != null) {
                LogUtils.d(TAG, "CameraSurfaceTextureListener rebind not null");
                mCameraSurfaceTextureListener.onRebindCameraPreview(MeetingActivity.this);
            }
            updateMeetingSurfaceView(videowidch ,videoheight , false);
        }
        ShowAllWidgt();
    }

    private void getMeetingType(int type) {
        Log.i(TAG, "get meeting type is " + type);
        if (type == 3) {
            //是点对点呼叫
            setNetMeetingType(3);
            BridgeControl.ConferenceSelectChannel(false);
        } else {
            //大会议室举手，是否是转发人员
            try {
                iTransferType = BridgeControl.ConferenceTransferType();
                LogUtils.e(TAG, "hands TransferType getMeetingType = " + iTransferType);
                if (iTransferType == 1) {
                    LogUtils.e(TAG, "hands iTransferType == 1");
                    setTransferPersonPermission(true);
                } else if (iTransferType == 0) {
                    LogUtils.e(TAG, "hands iTransferType == 0");
                    setTransferPersonPermission(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            setNetMeetingType(1);
        }
    }

    private int summaryId;

    public void setMeetingSummary() {
        if (meetingRequestPresenter == null) {
            meetingRequestPresenter = new MeetingRequestPresenter(MeetingActivity.this);
        }
        meetingRequestPresenter.selectMeetingDetail(netMeetingId);
    }

    private VideoRequestCallback MVQback = new VideoRequestCallback() {
        @Override
        public void ReceiveData(byte[] data, int length) {
            if (isActive && mrVideoDecoder != null) {
                if (mrVideoDecoder.getStart()) {
                    try {
                        mrVideoDecoder.decodeFrame(data, length);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    /**
     * 事件初始化
     * 触摸事件 拖动改变窗口位置
     */
    private void touchinit() {
        meetingActivity.setOnTouchListener(new View.OnTouchListener() {
            private int endPostion = -1;  // 结束位置
            private int startPostion = -1;  // 起始位置
            private float startY;  // 起始y轴；
            private float startX;  // 起始x轴；

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startY = event.getRawY();
                        // TODO: 2018/11/3  托平
                        startPostion = BridgeControl.ConferenceCalcPos(screenWidth, screenHeight, Math.abs(realScreenStatus), (int) startX, (int) startY);
                        return false;

                    case MotionEvent.ACTION_MOVE:
                        /** 起始坐标 等于startx starty 然后 起始坐标等于上一次的结束坐标
                         *  也就是移动中的坐标 最后起始坐标等于移动中的坐标 结束坐标等于抬起坐标
                         *  计算出需要移动的距离 **/
                        /*float dx = event.getRawX() - startX;
                        float dy = event.getRawY() - startY;
                        float startMX = event.getRawX() - dx;
                        float startMY = event.getRawY() - dy;
                        startX = event.getRawX();
                        startY = event.getRawY();
                        byte[] from = new byte[20];
                        int a= 0xFFFF00;
                        fvpsipjni.FVPhoneCoreDesktopSharingSendLabel(0,from,20,0);*/
                        return true;

                    case MotionEvent.ACTION_UP:
                        float endX = event.getRawX();
                        float endY = event.getRawY();
                        if (startX == endX) {  // 先判断点击还是滑动事件
                            TouchScreenWidgtStrategy();
                        } else {
                            endPostion = BridgeControl.ConferenceCalcPos(screenWidth, screenHeight, Math.abs(realScreenStatus), (int) endX, (int) endY);
                            String sPosition = startPostion + "," + endPostion;
                            LogUtils.e(TAG, "sPotion = " + sPosition);
                            if (!isTagging) {  // 0是移动列表号码 1是移动窗口 先做移动窗口
                                if (startPostion != -1 & endPostion != -1 & mHostMan.equals(uTitleStr) && startPostion != endPostion) {
                                    BridgeControl.ConferenceMove(sPosition);
                                }
                            }
                        }
                        return false;
                }
                return false;
            }
        });
    }

    // 回调初始化
    private void backinit() {
        meetingSfYd.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                if (videoInOut == null) {
                    Log.i(TAG ,"new video in out 2");
                    videoInOut = new VideoInOut(MeetingActivity.this);
                    videoInOut.setPeopleInfoFloatViewListener(mPeopleInfoFloatViewListener);
                    videoInOut.setCameraSurfaceTextureListener(mCameraSurfaceTextureListener);
                    mCameraSurfaceTextureListener.setVideoInOut(videoInOut);
                }
                if (mrVideoDecoder == null) {
                    mrVideoDecoder = new MyVideoDecoder();
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (myVideoRequest == null) {
                    myVideoRequest = new MyVideoRequest(MeetingActivity.this, MVQback ,videoInOut);
                    myVideoRequest.setHandler(mMeetingVideoDateHandler);
                }
                WHSize whSize;
                if (videoheight == 270) {
                    whSize = new WHSize(640, 360);
                } else {
                    whSize = new WHSize(videowidch, videoheight);
                }
                String mine = "video/avc";
                if (mrVideoDecoder != null) {
                    mrVideoDecoder.resetDecode();
                    meetingSurface = new Surface(surface);
                    mrVideoDecoder.createDecoder(whSize.width, whSize.height, mine, meetingSurface);
                }
                if (videoheight == 270) {
                    myVideoRequest.setPreViewBuff(640, 360);
                } else {
                    myVideoRequest.setPreViewBuff(videowidch, videoheight);
                }
                LogUtils.e(TAG, "VideoReceiver VideoReceiver==VideoReceiver");
                myVideoRequest.startPreview();
                BridgeControl.ConferenceShareFrame();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (myVideoRequest != null) {
                    myVideoRequest.release();
                    myVideoRequest = null;
                }
                if (mrVideoDecoder != null) {
                    mrVideoDecoder.release();
                    mrVideoDecoder = null;
                }
                if (meetingSurface != null) {
                    meetingSurface.release();
                    meetingSurface = null;
                }
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {}
        });
    }

    /**
     * 打开本地视频画面与移动本地画面方法
     * @param videowidth    视频画面的宽
     * @param videoheight   视频画面的高
     */
    private void openLocalVideoAndMoveVideoMethod(int videowidth, int videoheight) {
        mCameraSurfaceTextureListener.setmActivityContext(this);
        mCameraSurfaceTextureListener.setmTextureView(cameraPreviewTexture);
        mCameraSurfaceTextureListener.setVideoWidthAndVideoHeight(videowidth, videoheight);
        int screenOffsetWidth = MeetingEntity.getInstance().getScreenWidth();
        int screenOffsetHeight = MeetingEntity.getInstance().getScreenHeight();
        mCameraSurfaceTextureListener.setScreenWidth(screenOffsetWidth);
        mCameraSurfaceTextureListener.setScreenHeight(screenOffsetHeight);
        mCameraSurfaceTextureListener.setmContext(MeetingActivity.this);
        mCameraSurfaceTextureListener.tvSetAlpha(0);
        cameraPreviewTexture.setSurfaceTextureListener(mCameraSurfaceTextureListener);
    }

    private void applyForMeeting(int meeting_show_handup, String msg, boolean b, int i) {
        showHandsIcon.setImageResource(meeting_show_handup);
        showHandsText.setText(msg);
        jstimes = b;
        if (meetingNumber != null && uTitleStr != null) {
            BridgeControl.RaiseHand(i);
        }
    }

    private void inVisibleMeetingFunLlLeft() {
        if (leftll) {
            meetingLlLeft.setVisibility(View.GONE);
            leftll = false;
            meetingTvFsImage.setImageResource(R.drawable.meeting_more);
        }
    }

    private void meetingTaskS() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MeetingActivity.this);
        final View dialogView = LayoutInflater.from(MeetingActivity.this)
                .inflate(R.layout.meetingtasks, null);
        customizeDialog.setView(dialogView);
        AlertDialog alertDialog = customizeDialog.create();
        ImageView createTask = dialogView.findViewById(R.id.meeting_tasks_iv);
        meetingTaskRY = dialogView.findViewById(R.id.meeting_tasks_ry);
        meetingTextView = dialogView.findViewById(R.id.meeting_tasks_tv);
        swipeRefreshLayout = dialogView.findViewById(R.id.meeting_tasks_sw);
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        createTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeetingActivity.this, CreateTaskDialogActivity.class);
                intent.putExtra("meetingId", netMeetingId + "");
                startActivity(intent);
            }
        });

        subscribeFragmentAdapter = new MeetingTaskItemAdapter(R.layout.adapter_task_fragment_item, new ArrayList<MeetingTaskItem.DataBean.ListBean>());
        meetingTaskRY.setHasFixedSize(true);
        meetingTaskRY.setLayoutManager(new LinearLayoutManager(MeetingActivity.this));
        meetingTaskRY.setAdapter(subscribeFragmentAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        subscribeFragmentAdapter.setNewData(new ArrayList<MeetingTaskItem.DataBean.ListBean>());
                        taskPageNo = 1;
                        getMeetingTask(taskPageNo);
                    }
                }, 1500);
            }
        });

        getMeetingTask(taskPageNo);
        subscribeFragmentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            private String executorUsername;
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MeetingActivity.this, MeetingTaskItemActivity.class);
                List<MeetingTaskItem.DataBean.ListBean> data = adapter.getData();
                Integer taskId = data.get(position).taskId;
                String createUsername = data.get(position).createUsername;
                List<MeetingTaskItem.DataBean.ListBean.TaskMemberListBean> taskMemberList = data.get(position).taskMemberList;
                for (int i = 0; i < taskMemberList.size(); i++) {
                    if (taskMemberList.get(i).userType == 2) {
                        executorUsername = taskMemberList.get(i).username;
                    }
                }
                intent.putExtra("URL", "ItemDetailTask");
                intent.putExtra("TaskId", taskId + "");
                intent.putExtra("createUsername", createUsername);  // 执行人和创建人
                intent.putExtra("executorUsername", executorUsername);
                intent.putExtra("state", data.get(position).status);
                startActivity(intent);
            }
        });

        subscribeFragmentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //Log.e("isErr", "isErr=" + isErr + " mCurrentCounter=" + mCurrentCounter + " totalNumber=" + totalNumber);
                mCurrentCounter = subscribeFragmentAdapter.getData().size();
                if (mCurrentCounter >= totalNumber) {
                    subscribeFragmentAdapter.loadMoreEnd();
                } else {
                    if (isErr) {
                        taskPageNo = 1;
                        getMeetingTask(taskPageNo);
                        subscribeFragmentAdapter.loadMoreComplete();
                    } else {
                        subscribeFragmentAdapter.loadMoreFail();
                    }
                }
            }

        }, meetingTaskRY);
        Window w = alertDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    public void getMeetingTask(int pageNo) {
        if (meetingRequestPresenter == null) {
            meetingRequestPresenter = new MeetingRequestPresenter(MeetingActivity.this);
        }
        meetingRequestPresenter.selectTaskEnqueue(netMeetingId ,pageNo);
    }

    /**
     * 会议信息弹窗。如有资源文件空指针，需要解决渠道定制造成的代码冲突。
     * created by shike , 20200512
     * */
    private void MeetingDetailDialog() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MeetingActivity.this);
        final View dialogView = LayoutInflater.from(MeetingActivity.this)
                .inflate(R.layout.meeting_detail, null);
        customizeDialog.setView(dialogView);
        final AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);

        final TextView meetingDetailDialogName = (TextView) alertDialog.findViewById(R.id.meeting_detail_name);
        final TextView meetingDetailDialogNumber = (TextView) alertDialog.findViewById(R.id.meeting_detail_number);
        final TextView meetingDetailDialogSummary = (TextView) alertDialog.findViewById(R.id.meeting_detail_room_summery);
        final TextView meetingDetailDialogPasswd = (TextView) alertDialog.findViewById(R.id.meeting_detail_password);
        final TextView meetingDetailDialogAdminPasswd = (TextView) alertDialog.findViewById(R.id.meeting_detail_admin_passwd);
        final TextView meetingDetailDialogAdminName = (TextView) alertDialog.findViewById(R.id.meeting_detail_admin_name);
        final ImageView meetingDetailMessageCopy = (ImageView) alertDialog.findViewById(R.id.copy_button);
        final ImageButton meetingDetailDialogClose = (ImageButton) alertDialog.findViewById(R.id.meeting_dialog_close);

        meetingDetailDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        meetingDetailMessageCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboardManager != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getString(R.string.meeting_activity_detail_phone_number)).append(TextUtils.isEmpty(meetingDetailAccessCode) ? getString(R.string.none) : meetingDetailAccessCode).append("\r\n");
                    stringBuilder.append(getString(R.string.meeting_theme_mh)).append(TextUtils.isEmpty(meetingDetailTopic) ? getString(R.string.none) : meetingDetailTopic).append("\r\n");
                    stringBuilder.append(getString(R.string.meeting_activity_detail_come_room_passwd)).append(TextUtils.isEmpty(meetingDetailShowPwd) ? getString(R.string.none) : meetingDetailShowPwd).append("\r\n");
                    stringBuilder.append(getString(R.string.admin_pwd_number)).append(TextUtils.isEmpty(meetingDetailAdminPassword) ? getString(R.string.none) : meetingDetailAdminPassword).append("\r\n");
                    ClipData clipData = ClipData.newPlainText(stringBuilder.toString(),stringBuilder.toString());
                    clipboardManager.setPrimaryClip(clipData);
                    UiUtils.showToast(getString(R.string.meeting_activity_detail_success));
                } else {
                    UiUtils.showToast(getString(R.string.meeting_activity_detail_failure));
                }
            }
        });
        meetingDetailDialogName.setText(TextUtils.isEmpty(meetingDetailUserName) ? getString(R.string.none) : meetingDetailUserName);
        meetingDetailDialogNumber.setText(TextUtils.isEmpty(meetingDetailAccessCode) ? getString(R.string.none) : meetingDetailAccessCode);
        meetingDetailDialogSummary.setText(TextUtils.isEmpty(meetingDetailTopic) ? getString(R.string.none) : meetingDetailTopic);
        meetingDetailDialogPasswd.setText(TextUtils.isEmpty(meetingDetailShowPwd) ? getString(R.string.none) : meetingDetailShowPwd);
        meetingDetailDialogAdminPasswd.setText(TextUtils.isEmpty(meetingDetailAdminPassword) ? getString(R.string.none) : meetingDetailAdminPassword);
        meetingDetailDialogAdminName.setText(TextUtils.isEmpty(mHostMan) ? getString(R.string.none) : mHostMan);
        if (TextUtils.isEmpty(meetingDetailUserName) ||TextUtils.isEmpty(meetingDetailAccessCode) || TextUtils.isEmpty(meetingDetailTopic)
                ||TextUtils.isEmpty(meetingDetailShowPwd) || TextUtils.isEmpty(meetingDetailAdminPassword) ||TextUtils.isEmpty(meetingDetailUserName)) {
            requestMeetingDetail();
        }
        Window w = alertDialog.getWindow();
        if (w != null) {
            w.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams layoutParams = w.getAttributes();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay();
            int width = 0;
            int height = 0;
            width = (int) (d.getHeight() * 1.2);
            height = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.width = width;
            layoutParams.height = height;
            alertDialog.getWindow().setAttributes(layoutParams);
        }
    }

    private void meetingDetailS() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MeetingActivity.this);
        final View dialogView = LayoutInflater.from(MeetingActivity.this)
                .inflate(R.layout.meetingdetails, null);
        customizeDialog.setView(dialogView);
        final AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        detailEditText = dialogView.findViewById(R.id.meeting_detail_et);
        Button btn = dialogView.findViewById(R.id.meeting_detail_btn);
        ImageButton closeButtom = dialogView.findViewById(R.id.meeting_dialog_close);
        setMeetingSummary();
        detailEditText.setSelection(detailEditText.getText().length());
        if (isSummary) {
            detailEditText.setFocusable(true);
            detailEditText.setFocusableInTouchMode(true);
            detailEditText.setClickable(true);
            btn.setEnabled(true);
            btn.setVisibility(View.VISIBLE);
        } else {
            detailEditText.setFocusable(false);
            detailEditText.setFocusableInTouchMode(false);
            detailEditText.setClickable(false);
            btn.setEnabled(false);
            btn.setVisibility(View.GONE);
        }
        detailEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isSummary) {
                    detailEditText.setFocusable(true);
                    detailEditText.setFocusableInTouchMode(true);
                    detailEditText.setClickable(true);
                    btn.setEnabled(true);
                    btn.setVisibility(View.VISIBLE);
                }
                LogUtils.i("1471", "detailEditText loaction is event x =" + event.getX() + " y =" + event.getY());
                return false;
            }
        });

        closeButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = detailEditText.getText().toString().trim();
                boolean b = noContainsEmoji(trim);
                if (b) {
                    UiUtils.showToast(getString(R.string.meeting_activity_summary_no_emoji));
                    return;
                }
                if (TextUtils.isEmpty(trim)) {
                    UiUtils.showToast(getString(R.string.meeting_activity_summary_not_empty));
                    return;
                }
                if (meetingRequestPresenter == null) {
                    meetingRequestPresenter = new MeetingRequestPresenter(MeetingActivity.this);
                }
                meetingRequestPresenter.editSummary(netMeetingId ,trim ,summaryId);
                alertDialog.dismiss();
            }
        });
        Window w = alertDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        int width = 0;
        int height = 0;
        width = (int) (d.getHeight() * 1.2);
        height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = width;
        layoutParams.height = height;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    private boolean noContainsEmoji(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isEmojiCharacter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    // 参会者观看的管理员列表
    private void JoinDiaLog() {
        if (alertjoinDialog == null) {
            AlertDialog.Builder customizeDialog =
                    new AlertDialog.Builder(MeetingActivity.this);
            final View dialogView = LayoutInflater.from(MeetingActivity.this)
                    .inflate(R.layout.meetingjoindialog, null);
            customizeDialog.setView(dialogView);
            alertjoinDialog = customizeDialog.create();
            mNumber2 = dialogView.findViewById(R.id.meetingdialog_number);
            no = dialogView.findViewById(R.id.meetingdialog_no);
            joinListview = dialogView.findViewById(R.id.meetingdialog_list_join);
        }
        alertjoinDialog.show();
        BridgeControl.ConferenceSubscribe(true);
        Log.d(TAG, "Jni sip new notifycation module ,normal status subscribe is true. " + String.valueOf(netMeetingId));
        alertjoinDialog.setCanceledOnTouchOutside(true);
        alertjoinDialog.setOnDismissListener(v -> {
            meetingManageIv.setImageResource(R.drawable.meeting_gl);
            BridgeControl.ConferenceSubscribe(false);
            Log.d(TAG, "Jni sip new notifycation module ,normal status subscribe is false.(1)");
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertjoinDialog != null) {
                    alertjoinDialog.dismiss();
                }
                BridgeControl.ConferenceSubscribe(false);
                Log.d(TAG, "Jni sip new notifycation module ,normal status subscribe is false.(2)");
            }
        });
        joinAdapter = new MeetingActivityDialogJoinAdapter(getApplicationContext(), meetingNumber, mHostMan);
        joinListview.setAdapter(joinAdapter);
        if (resultList != null) {
            joinAdapter.setDataSourceLists(resultList, netMeetingId);
        }
        LogUtils.e(TAG, "person---size=" + resultList.size());
        mNumber2.setText(getString(R.string.list_of_part_people));
        joinTitleAdapter = new MeetingManageDialogTitleAdapter(resultList, mNumber2);
        Window w = alertjoinDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertjoinDialog.getWindow().setAttributes(layoutParams);
        setMeetingSummary();
        //获取会议列表
        if (resultList == null || resultList.size() == 0) {
            BridgeControl.ConferenceMemberVersion(0);
        } else {
            int numberListInfoVersion = SharedPreferencesUtil.getsInstance().getInt("NumberListInfoVersion", 0);
            BridgeControl.ConferenceMemberVersion(numberListInfoVersion);
        }
        Log.d(TAG, "FVPhone Get Detail(09), number is " + meetingNumber);
        BridgeControl.ConferenceDetail();
    }

    // 主持人的管理员列表
    public void FirstDialog() {
        if (alertDialog == null) {
            AlertDialog.Builder customizeDialog = new AlertDialog.Builder(MeetingActivity.this);
            manageDialogView = LayoutInflater.from(MeetingActivity.this).inflate(R.layout.meetingdialog, null);
            customizeDialog.setView(manageDialogView);
            alertDialog = customizeDialog.create();
        }
        alertDialog.show();  // 报错
        BridgeControl.ConferenceSubscribe(true);
        Log.d(TAG, "Jni sip new notifycation module ,presenter status subscribe is true." + String.valueOf(netMeetingId));
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setOnDismissListener(v -> {
            meetingManageIv.setImageResource(R.drawable.meeting_gl);
            BridgeControl.ConferenceSubscribe(false);
            Log.d(TAG, "Jni sip new notifycation module ,presenter status subscribe is false.(1)");
        });
        ImageButton no = manageDialogView.findViewById(R.id.meetingdialog_no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                BridgeControl.ConferenceSubscribe(false);
                Log.d(TAG, "Jni sip new notifycation module ,presenter status subscribe is false.(2)");
            }
        });
        ListView mListview = manageDialogView.findViewById(R.id.meetingdialog_list);
        TextView closeM = manageDialogView.findViewById(R.id.meetingdialog_jshy);
        closeM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeControl.ConferenceClose();
                BridgeControl.CallHangUp();
                releaseCamera();
                closeMeetingRoom();
                finish();
            }
        });

        hostAdapter = new MeetingActivityDialogAdapter(MeetingActivity.this, meetingNumber, mHostMan, ScreenNumber, uTitleStr);
        hostAdapter.setMainListener(new ControlChangeListener() {
            @Override
            public void close(String meetingNumber, String oldController, String newController) {
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.dialog_control, findViewById(R.id.dialog_con));
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MeetingActivity.this).setView(layout);
                builder.setCancelable(false);
                TextView dialogControlTv = layout.findViewById(R.id.dialog_control);
                dialogControlTv.setText(getString(R.string.meeting_activity_transfer_administor) + newController + "?");
                final Button cancelBtn = layout.findViewById(R.id.change_controller_cancel);
                final Button okBtn = layout.findViewById(R.id.change_controller_ok);
                final android.app.AlertDialog alertDialog = builder.create();
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BridgeControl.ConferenceHost(oldController ,newController);
                        setMoreAndShareLayoutGone();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                Window w = alertDialog.getWindow();
                if(w != null) {
                    w.setGravity(Gravity.CENTER);
                    WindowManager.LayoutParams layoutParams = w.getAttributes();
                    WindowManager m = getWindowManager();
                    Display d = m.getDefaultDisplay();
                    layoutParams.width = (int) (d.getWidth() * 0.45);
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    alertDialog.getWindow().setAttributes(layoutParams);
                }
            }
        });

        hostAdapter.setMeetingPersonStatusListener(new MeetingPersonStatusListener() {
            @Override
            public void changeMeetingPersonStatus(String meetingNumber, String meetingPersonNumber, int position) {
                if (meetingRequestPresenter == null) {
                    meetingRequestPresenter = new MeetingRequestPresenter(MeetingActivity.this);
                }
                meetingRequestPresenter.inviteEnqueue(detail ,meetingPersonNumber ,meetingNumber);
            }
        });

        mListview.setAdapter(hostAdapter);
        if (resultList != null) {
            LogUtils.e(TAG, "person resultList !=null");
            LogUtils.e(TAG, "person resultList=" + resultList.size());
            if (resultList != null) {
                if (resultList.size() > 32) {
                    UiUtils.showToast(getString(R.string.toast_24));
                    return;
                }
                LogUtils.e(TAG, "person resultList setNdkLibraryDataSourceLists");
                hostAdapter.setDataSourceLists(resultList, netMeetingId);
            }
        }
        TextView mNumber = manageDialogView.findViewById(R.id.meetingdialog_number);
        if (resultList == null) {
            mNumber.setText("");
        } else {
            mNumber.setText(getString(R.string.list_of_part_people));
        }
        hostTitleAdapter = new MeetingManageDialogTitleAdapter(resultList, mNumber);
        meetingLock = manageDialogView.findViewById(R.id.meetingdialog_lock);  // 锁定会议

        if (isMeetingLockStatus) {
            Drawable lockTop = getResources().getDrawable(R.drawable.meeting_lock);
            meetingLock.setCompoundDrawablesWithIntrinsicBounds(null, lockTop, null, null);
            meetingLock.setText(getResources().getString(R.string.meet_open));
        } else {
            Drawable unlockTop = getResources().getDrawable(R.drawable.meeting_unlock);
            meetingLock.setCompoundDrawablesWithIntrinsicBounds(null, unlockTop, null, null);
            meetingLock.setText(getResources().getString(R.string.meet_close));
        }
        meetingLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMeetingLockStatus) {
                    meetingLock(2);
                } else {
                    meetingLock(1);
                }
            }
        });

        final TextView JinYan = manageDialogView.findViewById(R.id.meetingdialog_jinyan);  // 禁言按钮
        JinYan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jinyan) {
                    Drawable top = getResources().getDrawable(R.drawable.tcjya);
                    JinYan.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    BridgeControl.ConferenceMute(0);
                    jinyan = false;
                } else {
                    Drawable top = getResources().getDrawable(R.drawable.qtjyb);
                    JinYan.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    BridgeControl.ConferenceMute(1);
                    jinyan = true;
                }
            }
        });

        if (jinyan) {
            Drawable top = getResources().getDrawable(R.drawable.qtjyb);
            JinYan.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        } else {
            Drawable top = getResources().getDrawable(R.drawable.tcjya);
            JinYan.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        }
        Window w = alertDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes(layoutParams);
        setMeetingSummary();
        if (resultList == null || resultList.size() == 0) {
            BridgeControl.ConferenceMemberVersion(0);
        } else {
            int numberListInfoVersion = SharedPreferencesUtil.getsInstance().getInt("NumberListInfoVersion", 0);
            BridgeControl.ConferenceMemberVersion(numberListInfoVersion);
        }
        Log.d(TAG, "FVPhone Get Detail(10), number is " + meetingNumber);
        BridgeControl.ConferenceDetail();
    }

    /**
     * 会议锁定
     * falg 1表示锁定 2表示解锁
     */
    private void meetingLock(int flag) {
        if (meetingRequestPresenter == null) {
            meetingRequestPresenter = new MeetingRequestPresenter(this);
        }
        meetingRequestPresenter.meetingLockEnqueue(flag ,netMeetingId);
    }

    // 邀请按钮
    private void SecondDialog() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MeetingActivity.this);
        final View dialogView = LayoutInflater.from(MeetingActivity.this)
                .inflate(R.layout.meetingdialogsecond, null);
        customizeDialog.setView(dialogView);
        inviteMemberDialog = customizeDialog.create();
        inviteMemberDialog.show();
        inviteMemberDialog.setCanceledOnTouchOutside(true);
        RelativeLayout share = dialogView.findViewById(R.id.meetingdialog_seconde_share);
        RelativeLayout tvyq = dialogView.findViewById(R.id.meetingdialog_seconde_yq);
        RelativeLayout tvlxr = dialogView.findViewById(R.id.meetingdialog_seconde_lxr);
        ImageButton tvexit = dialogView.findViewById(R.id.meetingdialog_seconde_exit);
        share.setOnClickListener(new View.OnClickListener() {  // 分享按钮
            @Override
            public void onClick(View v) {
                LogUtils.d("share", "share_click");
                //分享按鈕
                try {
                    if (ActivityEntity.getInstance().getAppSharedActivityType() == null) {
                        LogUtils.d("share", "No Activity Set");
                        return;
                    }
                    String packageName =  ActivityEntity.getInstance().getAppSharedActivityType().getName();
                    Log.i(TAG, "share app is " + packageName);
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(MeetingActivity.this, packageName));
                    Bundle bundle = new Bundle();
                    bundle.putString("topic", meetingDetailTopic == null ? "" : meetingDetailTopic);
                    bundle.putString("pwd", meetingDetailShowPwd == null ? "" : meetingDetailShowPwd);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG ,"share app info failed.");
                }
            }
        });

        TextView Invite2 = dialogView.findViewById(R.id.meetingdialog_seconde_yqyh);  // 邀请用户
        manageListview = dialogView.findViewById(R.id.meetingdialog_seconde_list);
        tvyq.setOnClickListener(new View.OnClickListener() {  // 邀请按钮
            @Override
            public void onClick(View v) {
                SecondDialogYQ();
            }
        });
        tvlxr.setOnClickListener(new View.OnClickListener() {  // 联系人按钮
            @Override
            public void onClick(View v) {
                SecondDialogLXR();
            }
        });

        Invite2.setOnClickListener(new View.OnClickListener() {  // 邀请用户按钮
            @Override
            public void onClick(View v) {
                if (meetingRequestPresenter == null) {
                    meetingRequestPresenter = new MeetingRequestPresenter(MeetingActivity.this);
                }
                meetingRequestPresenter.inviteEnqueue(detail ,secondList ,meetingNumber);
                inviteMemberDialog.dismiss();
            }
        });
        tvexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteMemberDialog.dismiss();
            }
        });
        inviteMemberDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                inviteIv.setImageResource(R.drawable.meeting_icon_invitation);
                secondList.clear();
            }
        });
        Window w = inviteMemberDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        inviteMemberDialog.getWindow().setAttributes(layoutParams);
    }

    // 邀请按钮 邀请按钮
    private void SecondDialogYQ() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.invite_user_layout, null);
        InvitationViewModel invitationViewModel = new InvitationViewModel(layout);
        AlertDialog.Builder builder = null;
        try {
            builder = new AlertDialog.Builder(this).setView(invitationViewModel.getLayout());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.e("SystemUtil.getDeviceBrand()", "SystemUtil.getDeviceBrand() =" + SystemUtil.getDeviceBrand());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        invitationViewModel.setConfirmListener(new ConfirmListener<String>() {
            @Override
            public void onCallback(String msg) {
                if (TextUtils.isEmpty(msg)) {
                    UiUtils.showToast(getString(R.string.toast_23));
                    return;
                } else if (TextUtils.equals(msg,"dismiss")){
                    alertDialog.dismiss();
                    return;
                }
                boolean find = false;
                if (secondList != null) {
                    for (phoneinfo phone : secondList) {
                        if (!TextUtils.isEmpty(phone.getNumber()) && phone.getNumber().equals(msg)) {
                            find = true;
                            break;
                        }
                    }
                }
                if (find) {
                    UiUtils.showToast(getResources().getString(R.string.meeting_invite_people_duplicate));
                    return;
                }
                ArrayList<phoneinfo> newList = new ArrayList<>();
                phoneinfo con = new phoneinfo();
                con.setNumber(msg);
                newList.add(con);
                secondList.addAll(newList);
                setSecodeListDate(secondList);
                alertDialog.dismiss();
            }
        });

        Window w = alertDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    // 邀请按钮 联系人按钮
    private void SecondDialogLXR() {
        AlertDialog.Builder customizeDialog =
                new AlertDialog.Builder(MeetingActivity.this);
        final View dialogView = LayoutInflater.from(MeetingActivity.this)
                .inflate(R.layout.meeting_dialog_contacts, null);
        customizeDialog.setView(dialogView);
        final AlertDialog alertDialog = customizeDialog.create();
        alertDialog.show();
        TextView LXRexit = dialogView.findViewById(R.id.meeting_dialog_contacts_no);
        final TextView LXRok = dialogView.findViewById(R.id.meeting_dialog_contacts_ok);
        meetingContactsRecyclerView = dialogView.findViewById(R.id.meeting_dialog_contacts_list);
        meetingContactsRecyclerView.setLayoutManager(new LinearLayoutManager(MeetingActivity.this));
        meetingContactsAdapter = new AddressBookAdapter<>(MeetingActivity.this);  // 发送网络请求，请求企业联系人列表

        Context internetContext = MeetingActivity.this;
        if (internetInfoPresenter == null) {
            internetInfoPresenter = new InternetInfoPresenter(internetContext, this);
        }
        resultMap.clear();
        internetInfoPresenter.SetUrl(ContactsInternetUrlStatus.ShowCreateTeamStatus, 1);
        meetingContactsAdapter.setListener(new TreeItemClickListener() {
            @Override
            public void OnClick(Node node) {
                afterTouchRecyclerViewItem(node);
                LXRok.setText(getString(R.string.confirm));
            }

            @Override
            public void onLongClick(Node node) {
            }
        });

        LXRexit.setOnClickListener(new View.OnClickListener() {  // 按取消按钮
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        LXRok.setOnClickListener(new View.OnClickListener() {  // 按确定按钮
            @Override
            public void onClick(View v) {
                List<phoneinfo> resultListdialog = new ArrayList<>();
                for (Map.Entry<Integer, phoneinfo> entry : resultMap.entrySet()) {
                    resultListdialog.add(entry.getValue());
                }
                secondList.addAll(resultListdialog);
                for (int i = 0; i < secondList.size() - 1; i++) {  // 对集合去重
                    for (int j = secondList.size() - 1; j > i; j--) {
                        if (secondList.get(j).getNumber().equals(secondList.get(i).getNumber())) {
                            secondList.remove(j);
                        }
                    }
                }
                setSecodeListDate(secondList);  // 将数据设置给listview
                alertDialog.dismiss();
            }
        });
        Window w = alertDialog.getWindow();
        w.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = w.getAttributes();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        layoutParams.width = (int) (d.getHeight() * 1.2);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.getWindow().setAttributes(layoutParams);
    }

    // 企业通讯录网络请求MVP
    @Override
    public void ShowStringSuccess(String result) {
    }

    @Override
    public void ShowStringFail(String message) {
    }

    /**
     * 显示企业通讯录,拼接联系人数据可以抽象为一个类
     * 从手机本地缓存读取“我的收藏”，从手机读取“手机联系人”，从网络得到“企业通讯录”
     */
    @Override
    public void ShowSelectAddrbookCompany(List<User> users) {
        if (favoriteList != null) {
            favoriteList.clear();
        }
        favoriteList = new ArrayList<>();
        try {
            ArrayListCacheUtils arrayListCacheUtils = new ArrayListCacheUtils();
            favoriteList = arrayListCacheUtils.loadListCache(MeetingActivity.this, "json");
        } catch (Exception e) {
            favoriteList.clear();
            favoriteList.add(new User(0, 0, getString(R.string.my_fav), 0 , AddressbookUserType.FavoriteContact));
        }
        if (favoriteList == null) {
            favoriteList = new ArrayList<User>();
        }
        for (User item : favoriteList) {
            if (item.getUserPhoneInfo() != null) {
                item.setUserPhoneInfo(false);
            }
        }
        if (favoriteList.size() == 0) {
            favoriteList.add(new User(0, 0, getString(R.string.my_fav), 0 , AddressbookUserType.FavoriteContact));
        }
        if (mobilePhoenList != null) {
            mobilePhoenList.clear();
        }
        mobilePhoenList = new ArrayList<User>();
        mobilePhoenList.add(new User(MULTIPLE, MULTIPLE, getString(R.string.phone_cotacts), 0 , AddressbookUserType.MobileContact));
        GetPhoneNumberFromMobile getPhoneNumberFromMobile = new GetPhoneNumberFromMobile();
        List<phoneinfo> LocalUerInfoList = getPhoneNumberFromMobile.getPhoneNumberFromMobile(MeetingActivity.this);
        if (LocalUerInfoList != null && LocalUerInfoList.size() > 0) {
            for (int i = 0; i < LocalUerInfoList.size(); i++) {
                String name = LocalUerInfoList.get(i).getName();
                String number = LocalUerInfoList.get(i).getNumber();
                if (name == null || number == null) {
                    continue;
                }
                mobilePhoenList.add(new User((long) (MULTIPLE + i + 1), MULTIPLE, name, 1, LocalUerInfoList.get(i) , AddressbookUserType.MobileContact));
            }
        }
        if (enterpriseList != null) {
            enterpriseList.clear();
        }
        enterpriseList = new ArrayList<User>();
        try {
            for (User user : users) {
                if (user != null) {
                    user.setPid(user.getPid() + MULTIPLE);
                    user.setId(user.getNid() + MULTIPLE);
                }
            }
            enterpriseList.addAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pinyinUserComparator == null) {
            pinyinUserComparator = new PinyinUserComparator();
        }
        try {
            Collections.sort(favoriteList ,pinyinUserComparator);
            Collections.sort(mobilePhoenList ,pinyinUserComparator);
        } catch (Exception e) {
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
        meetingContactsAdapter.setDataSourceLists(favoriteList ,mobilePhoenList ,enterpriseList);
        meetingContactsAdapter.closedAllNode(false);
        meetingContactsRecyclerView.setAdapter(meetingContactsAdapter);
        meetingContactsAdapter.notifyDataSetChanged();
    }


    /**
     * 企业通讯录点击联系人
     * @param node 选中的RecyclerView节点，添加至选中list。
     */
    public void afterTouchRecyclerViewItem(Node node) {
        if (node == null || node.getUserPhoneInfo() == null || node.getUserPhoneInfo().getNumber() == null
                || enterpriseList == null || enterpriseList.size() == 0) {
            return;
        }
        int position = -1;
        phoneinfo info = node.getUserPhoneInfo();
        String number = node.getUserPhoneInfo().getNumber();

        for (int i = 0; i < favoriteList.size(); i++) {
            if (favoriteList.get(i) == null || favoriteList.get(i).getUserPhoneInfo() == null
                    || favoriteList.get(i).getUserPhoneInfo().getNumber() == null) {
                continue;
            }
            if (favoriteList.get(i).getUserPhoneInfo().getNumber().equals(number)) {
                position = i + (int)(MULTIPLE * 3);
                break;
            }
        }

        for (int i = 0; i < mobilePhoenList.size(); i++) {
            if (mobilePhoenList.get(i) == null || mobilePhoenList.get(i).getUserPhoneInfo() == null
                    || mobilePhoenList.get(i).getUserPhoneInfo().getNumber() == null) {
                continue;
            }
            if (mobilePhoenList.get(i).getUserPhoneInfo().getNumber().equals(number)) {
                position = i + (int)MULTIPLE;
                break;
            }
        }

        for (int i = 0; i < enterpriseList.size(); i++) {
            if (enterpriseList.get(i) == null || enterpriseList.get(i).getUserPhoneInfo() == null
                    || enterpriseList.get(i).getUserPhoneInfo().getNumber() == null) {
                continue;
            }
            if (enterpriseList.get(i).getUserPhoneInfo().getNumber().equals(number)) {
                position = i;
                break;
            }
        }
        if (node.getUserPhoneInfo().ischeck) {
            resultMap.put(position, info);
        } else {
            resultMap.remove(position);
        }
        meetingContactsAdapter.notifyDataSetChanged();
    }


    private ArrayList<Integer> netCheckPowerArray = new ArrayList();

    private void showSignalDisplay() {
        int[] videobyte = new int[10];
        byte[] pcARArr = new byte[128];
        byte[] pcASArr = new byte[128];
        byte[] pcVRArr = new byte[128];
        byte[] pcVSArr = new byte[128];
        byte[] pcDRArr = new byte[128];
        byte[] pcDSArr = new byte[128];

        int jiCallType = -1;
        if (netMeetingType == 3) {
            jiCallType = 3;  // 点对点
        } else {
            if (GlobalUtils.getLocalMeetingStatus(false)) {
                if (GlobalUtils.getShareType(false) == 0) {
                    jiCallType = 2;  // 接受共享
                } else {
                    jiCallType = 1;  // 发送共享
                }
            } else {
                jiCallType = 0;  // 视频会议
            }
        }

        int statInfoMessage = BridgeControl.ConferenceStateInfo(pcARArr,pcASArr ,
        pcVRArr,pcVSArr ,
        pcDRArr,pcDSArr ,
        jiCallType  ,videobyte);
        if (ButtonUtils.showSignelDiologLog()) {
            Log.i("showSignalDisplay", "showSignalDisplay jiCallType(0 1 2 3) = " + jiCallType + "  type(0 1) = " + videobyte[0] + " shangxing = " + videobyte[1] + " xiaxing = " + videobyte[2] +
                    " yanchi = " + videobyte[3] + " badnet(0) = " + videobyte[4]);
        }

        String pcARArrText = new String(pcARArr);
        String pcASArrText = new String(pcASArr);
        String pcVRArrText = new String(pcVRArr);
        String pcVSArrText = new String(pcVSArr);
        String pcDRArrText = new String(pcDRArr);
        String pcDSArrText = new String(pcDSArr);
        if (statInfoMessage != -1) {
            netRecAudioTv.setText(getString(R.string.meeting_activity_network_info_voice_recieve) + pcARArrText);
            netSendAudioTv.setText(getString(R.string.meeting_activity_network_info_voice_send) + pcASArrText);
            netRecVideoTv.setText(getString(R.string.meeting_activity_network_info_video_recieve) + pcVRArrText);
            netSendVideoTv.setText(getString(R.string.meeting_activity_network_info_video_send) + pcVSArrText);
            netRecData.setText(getString(R.string.meeting_activity_network_info_data_recieve) + pcDRArrText);
            netSendData.setText(getString(R.string.meeting_activity_network_info_data_send) + pcDSArrText);
        }
        if (statInfoMessage != -1) {
            // 信号 高清 是4格信号 标清3 流畅2 语音1
            int shangxing = videobyte[1];  // %
            int xiaxing = videobyte[2];  // %
            int yanshi = videobyte[3];  // ms
            int showShangXing = 0;

            if (shangxing == 0) {
                showShangXing = 100 + RandomUtils.generatingRandomNumbers();
            } else {
                showShangXing = 100 - shangxing;
            }
            int showXiaXing = 0;
            if (xiaxing == 0) {
                showXiaXing = 100 + RandomUtils.generatingRandomNumbers();
            } else {
                showXiaXing = 100 - xiaxing;
            }
            int showYanShi = yanshi + 100;

            if (netSignalDisplayShang != null) {
                netSignalDisplayShang.setText(getString(R.string.meeting_activity_network_info_upload) + showShangXing + "pps");
                netSignalDisplayXia.setText(getString(R.string.meeting_activity_network_info_download) + showXiaXing + "pps");
                netSignalDisplayYanshi.setText(getString(R.string.meeting_activity_network_info_perferce) + showYanShi + "ms");
            }

            getNetSignalSetIV(shangxing, xiaxing, yanshi, 1);

            int networkQuality = videobyte[4]; // 0是网络信号差 1是标清
            if (netCheckPowerArray != null && !isTagging && jiCallType != 3) {  // 网络检测
                if (networkQuality == 0) {
                    netCheckPowerArray.add(1);
                } else {
                    netCheckPowerArray.add(0);
                }
                if (netCheckPowerArray.size() >= 12) {
                    int count = 0;
                    for (int i = 0; i < netCheckPowerArray.size(); i++) {
                        int status = netCheckPowerArray.get(i);
                        if (status == 1) {
                            count++;
                        }
                    }
                    if (count >= 6) {
                        netCheckPowerArray.clear();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UiUtils.showToastLong(getString(R.string.meeting_activity_bad_network));
                            }
                        });
                    } else {
                        netCheckPowerArray.clear();
                    }
                }
            } else {
                netCheckPowerArray = new ArrayList<>();
            }
        }
    }

    // 上行丢包率 下行丢包率 延时 视频级别 123  返回值 12345
    public int fvpEstimateNetSignal(int iUpPackageLossRate, int iDownPackageLossRate, int iDelay, int iVideoLevel) {
        int iSignalLevel = 500;
        int iDelayRate = 0;

        iSignalLevel = (100 - iUpPackageLossRate) * (100 - iDownPackageLossRate) * iSignalLevel;
        if (iDelay < 100) {
            iDelayRate = 100;
        } else if (iDelay < 150) {
            iDelayRate = 90;
        } else if (iDelay < 200) {
            iDelayRate = 60;
        } else {
            iDelayRate = 20;
        }

        LogUtils.e("iSignalLevel", "iSignalLevel=" + iSignalLevel + " ,iDelayRate=" + iDelayRate + " iVideoLevel=" + iVideoLevel);
        iSignalLevel = iSignalLevel * iDelayRate * (5 - iVideoLevel) / 50000;
        iSignalLevel /= 10000;

        return iSignalLevel;
    }

    private void getNetSignalSetIV(int shangxing, int xiaxing, int yanshi, int videoSizeType) {
        int netSignal = fvpEstimateNetSignal(shangxing, xiaxing, yanshi, videoSizeType);
        if (isTagging) {
            netSignal = 3;
        }
        switch (netSignal) {
            case 1:
                if (netIv != null) {
                    netIv.setImageResource(R.drawable.xinhao_b);
                }
                break;

            case 2:
                if (netIv != null) {
                    netIv.setImageResource(R.drawable.xinhao_c);
                }
                break;

            case 3:
                if (netIv != null) {
                    netIv.setImageResource(R.drawable.xinhao_d);
                }
                break;

            case 4:
                if (netIv != null) {
                    netIv.setImageResource(R.drawable.xinhao_e);
                }
                break;

            case 5:
                if (netIv != null) {
                    netIv.setImageResource(R.drawable.xinhao_f);
                }
                break;
        }
    }

    String meetingDetailUserName = "";
    String meetingDetailAccessCode;
    String meetingDetailShowPwd;
    String meetingDetailTopic;
    String meetingDetailAdminPassword = "";
    private void requestMeetingDetail() {
        if (meetingRequestPresenter == null) {
            meetingRequestPresenter = new MeetingRequestPresenter(this);
        }
        meetingRequestPresenter.meetingDetailEnqueue(meetingDetailId);
    }

    // 点击邀请弹出的列表
    private void setSecodeListDate(List<phoneinfo> contacts) {
        MeetingActivityDialogSecondAdapter mAdapter = new MeetingActivityDialogSecondAdapter(contacts, getApplicationContext());
        manageListview.setAdapter(mAdapter);
    }

    // 设置标注状态
    public void setTagging(Boolean tagging) {
        if (tagging) {
            isMeetingShared = true;
            if (mCameraSurfaceTextureListener != null) {
                mCameraSurfaceTextureListener.setMeetingShared(isMeetingShared);
            }
            addFragment();
            meetingLlTop.setVisibility(View.VISIBLE);
        } else {
            removeFragment();
            if (!left1) {
                StartOrStopSendVideo();
            }
        }
        isTagging = tagging;
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setIsTag(isTagging);
        }
    }

    private void stopShareDesk() {
        Log.d(TAG, "Desktop Share Detail Log:\n"
                + "\t stopShareDesk.");
        if (GlobalUtils.getShareType(false) == 0) {
            Log.i(TAG, "Desktop shar already stop");
        }
        GlobalUtils.setShareType(0);
        sharePositonText.setText(getString(R.string.share));
        if (intentService != null) {
            LogUtils.d(TAG, "Desktop Share Detail Log:\n"
                    + "\t stopService intentService.");
            stopService(intentService);
        }
        intentService = null;
        if (mrVideoDecoder != null) {
            mrVideoDecoder.setiIkey(0);
        }
        if (!isTransferPersonPermission) {
            StartOrStopSendVideo();
        }
        BridgeControl.ConferenceShareFrame();
        if (showHands != null && showHands.getVisibility() != View.VISIBLE) {
            if (shareLayout != null) {
                shareLayout.setVisibility(View.VISIBLE);
            }
        }
        if (shareDesktopSponsorName != null) {
            shareSponorNumber = "";
            shareDesktopSponsorName.setText("");
            shareDesktopSponsorName.setVisibility(View.GONE);
        }
        backgroundImageView.setVisibility(View.GONE);
        isMeetingShared = false;
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setMeetingShared(isMeetingShared);
            mCameraSurfaceTextureListener.setPcShareStatus(false);
        }
        if (mCameraSurfaceTextureListener != null && Build.VERSION.SDK_INT == 23) {
            if (mCameraSurfaceTextureListener.getmTextureView() == null) {
                openLocalVideoAndMoveVideoMethod(videowidch, videoheight);
                mCameraSurfaceTextureListener.setStopSpfs(left1);
            }
        }
    }

    private void openShareDesk() {
        Log.d(TAG, "Desktop Share Detail Log:\n"
                + "\t openShareDesk.");
        BridgeControl.PreLabel();
        if (mPeopleInfoFloatViewListener != null) {
            mPeopleInfoFloatViewListener.removeAllViews();
        } else {
            Log.e(TAG ,"People info float listener ,open share desk.");
        }
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setPcShareStatus(true);
        }
        EditTextUtils.setDesktopSponorHint(shareSponorNumber, shareDesktopSponsorName, resultList, MeetingActivity.this);
        isMeetingShared = true;
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setMeetingShared(isMeetingShared);
        }
    }

    private void deskShareScreen() {
        Log.d(TAG, "Desktop Share Detail Log:\n"
                + "\t deskShareScreen.");
        try {
            sharePositonText.setText(getString(R.string.meeting_activity_exit_share));
            backgroundImageView.setVisibility(View.VISIBLE);
            openShareDesk();
            if (intentService == null) {
                LogUtils.d(TAG, "Desktop Share Detail Log:\n"
                        + "\t startService intentService.");
                intentService = new Intent(this, DesktopShareService.class);
                startService(intentService);
            }
            moveTaskToBack(true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"start desktop share failure, message is " + e.getMessage());
            UiUtils.showToastLong(getString(R.string.meeting_activity_desktop_share_start_failure));
            endDeskShare();
        }
    }

    // 结束桌面共享 异步
    private void endDeskShare() {
        Log.d(TAG, "Desktop Share Detail Log:\n"
                + "\t endDeskShare.");
        GlobalUtils.setLocalMeetingStatus(false);
        stopShareDesk();
        Log.i(TAG, "Desktop share control, local desktop sharing stop.");
        BridgeControl.ConferenceDesktop(0);
        SharedPreferencesUtil.getsInstance().put(VideoFlagContacts.END_SHARED_DESKTOP_BY_USER, true);
    }

    public void checkCaptureIntent() {
        GlobalUtils.setShareType(2);
        GlobalUtils.setLocalMeetingStatus(false);
        startCaptureIntent();
    }

    // --------------------------------- 录屏 -------------------------------
    private void startCaptureIntent() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
    }

    MediaProjection mediaProjection = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            if (mediaProjection == null) {
                GlobalUtils.setShareType(0);
                GlobalUtils.setLocalMeetingStatus(false);
                meetShareIcon.setImageResource(R.drawable.shareicon);
                return;
            } else {
                MeetingEntity.getInstance().setMediaProjection(null);
                MeetingEntity.getInstance().setMediaProjection(mediaProjection);
            }
            Log.i(TAG, "Desktop share control, local desktop sharing start.");
            BridgeControl.ConferenceDesktop(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.meeting_ll_camera) {
            if (left1) {
                if (netMeetingType == 3) {
                    StopSendVideo();
                }
                BridgeControl.ConferenceCamera(0);
            } else {
                if (netMeetingType == 3) {
                    StartSendVideo();
                }
                BridgeControl.ConferenceCamera(1);
            }
        } else if (id == R.id.meeting_ll_invite_3) {
            inviteIv.setImageResource(R.drawable.meeting_icon_invitation_check);
            SecondDialog();
        } else if (id == R.id.meeting_tv_gd_ll) {
            if (mHostMan.equals(uTitleStr) && !isFinishing()) {
                HiddenAllWidgt();
                CloseMeetingPopupDialog.create(MeetingActivity.this,
                        view -> {
                            dissolveMeetingRoom();
                            finish();
                        },
                        view -> {
                            closeMeetingRoom();
                            finish();
                        }).show();
            } else {
                closeMeetingRoom();
                finish();
            }
        } else if (id == R.id.net_iv) {
            if (netMeetingType != 3) { //不是点对点呼叫
                HiddenAllWidgt();
                netSignalDisplay.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.meeting_ll_left_3) {
            SecondDialog();
        } else if (id == R.id.meeting_tv_yq) {  // 邀请按钮 转做会议纪要功能
            meetingDetailS();
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
            inVisibleMeetingFunLlLeft();
        } else if (id == R.id.meeting_tv_rw) {  // 会议任务按钮
            meetingTaskS();
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
            inVisibleMeetingFunLlLeft();
        } else if (id == R.id.meeting_tv_picture) {
            if (bdtimes) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            } else {
                meetingLlMid.setVisibility(View.VISIBLE);
                bdtimes = true;
            }
            inVisibleMeetingFunLlLeft();
        } else if (id == R.id.meeting_tv_gd) {
            if (mHostMan.equals(uTitleStr) && !isFinishing()) {
                HiddenAllWidgt();
                CloseMeetingPopupDialog.create(MeetingActivity.this,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dissolveMeetingRoom();
                                finish();
                            }
                        } ,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                closeMeetingRoom();
                                finish();
                            }
                        }).show();
            } else {
                closeMeetingRoom();
                finish();
            }
        } else if (id == R.id.meeting_tv_qh) {
            IdcMediaChangeCamera();
            inVisibleMeetingFunLlLeft();
        } else if (id == R.id.meeting_tv_fs) {
            /*ShowPopoverUtils instance = ShowPopoverUtils.getInstance(MeetingActivity.this,
                        R.layout.layout_custom_menu, R.menu.menu_pop);
                instance.setOnMenuClickListener(new OptionMenuView.OnOptionMenuClickListener() {
                    @Override
                    public boolean onOptionMenuClick(int position, OptionMenu menu) {
                        return false;
                    }
                });
                instance.setFont(PopupView.SITE_TOP, PopupView.SITE_RIGHT, PopupView.SITE_BOTTOM, PopupView.SITE_LEFT);
                instance.showMenu(meetingTvFs);*/
            if (leftll) {  // 视频发送
                meetingLlLeft.setVisibility(View.GONE);
                meetingTvFsImage.setImageResource(R.drawable.meeting_more);
                leftll = false;
            } else {  // 点击不发送
                meetingLlLeft.setVisibility(View.VISIBLE);
                meetingTvFsImage.setImageResource(R.drawable.meeting_more_check);
                leftll = true;
            }
                /*if (meetPopuWindow!=null){
                    meetPopuWindow.dismiss();
                }*/
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
        } else if (id == R.id.meeting_tv_jy) {  // 禁言 本地禁言
            if (jytimes) {
                if (netMeetingType == 3) {
                    StopSendVoice();
                }
                BridgeControl.ConferenceMute(0);
            } else {
                if (netMeetingType == 3) {
                    StartSendVoice();
                }
                BridgeControl.ConferenceMute(1);
            }
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
        } else if (id == R.id.meeting_tv_gf) {  // 功放按钮
            if (wltimes) {
                BridgeControl.ConferenceSpeaker(0);
                meetingTvGfImage.setImageResource(R.drawable.meeting_ysq);
                wltimes = false;
            } else {
                BridgeControl.ConferenceSpeaker(1);
                meetingTvGfImage.setImageResource(R.drawable.meeting_ysq_g);
                wltimes = true;
            }
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
        } else if (id == R.id.meeting_tv_ry) {
            meetingManageIv.setImageResource(R.drawable.meeting_gl_check);
            if (mHostMan.equals(uTitleStr)) {  // 如果是主持人
                FirstDialog();
            } else {  // 如果不是主持人只能观看列表
                JoinDiaLog();
            }
            if (meetingLlMid.getVisibility() == View.VISIBLE) {
                meetingLlMid.setVisibility(View.GONE);
                bdtimes = false;
            }
        } else if (id == R.id.meeting_activity) {// 点击事件是通过touch事件实现的 但是点击事件不能删除 否则无法触发
        } else if (id == R.id.meeting_ll_mid_auto) {  // 自动分屏
            ScreenNumber = "0";
            BridgeControl.ConferenceSplit(0);
        } else if (id == R.id.meeting_ll_mid_1) {  // 一分屏
            ScreenNumber = "1";
            BridgeControl.ConferenceSplit(1);
        } else if (id == R.id.meeting_ll_mid_2) {/** 7+1分屏 0号位 x的范围0-960 y的范围 270-810
         1号位 x的范围961-1920 y的范围 270-810  **/
            ScreenNumber = "11";
            BridgeControl.ConferenceSplit(11);
        } else if (id == R.id.meeting_ll_mid_3) {/** 8+2分屏
         0号位 x的范围450-1360 y的范围 0-540
         1号位 x的范围0-960 y的范围 540-1080
         2号位 x的范围960-1920 y的范围 540-1080 **/
            ScreenNumber = "12";
            BridgeControl.ConferenceSplit(12);
        } else if (id == R.id.meeting_ll_mid_4) {/** 12+1分屏
         0号位 x的范围0-960 y的范围 0-540
         1号位 x的范围960-1920 y的范围 0-540
         2号位 x的范围0-960 y的范围 540-1080
         3号位 x的范围960-1920 y的范围 540-1080 **/
            ScreenNumber = "13";
            BridgeControl.ConferenceSplit(13);
        } else if (id == R.id.meeting_ll_mid_5) {/** 8分屏
         0号位 x的范围0-1440 y的范围 0-750
         1号位 x的范围1441-1920 y的范围 0-270
         2号位 x的范围1441-1920 y的范围 270-540
         3号位 x的范围1441-1920 y的范围 540-810
         4号位 x的范围0-480 y的范围 811-1080
         5号位 x的范围480-960 y的范围 811-1080
         6号位 x的范围960-1440 y的范围 811-1080
         7号位 x的范围1440-1920 y的范围 811-1080 **/
            ScreenNumber = "8";
            BridgeControl.ConferenceSplit(4);
        } else if (id == R.id.meeting_ll_mid_6) {/** 10分屏
         0号位 x的范围0-960 y的范围 270-810
         1号位 x的范围961-1920 y的范围 270-810
         2号位 x的范围0-480 y的范围 0-270
         3号位 x的范围480-960 y的范围 0-270
         4号位 x的范围960-1440 y的范围 0-270
         5号位 x的范围1440-1920 y的范围 0-270
         6号位 x的范围0-480 y的范围 811-1080
         7号位 x的范围480-960 y的范围 811-1080
         8号位 x的范围960-1440 y的范围 811-1080
         9号位 x的范围1440-1920 y的范围 811-1080 **/
            ScreenNumber = "10";
            BridgeControl.ConferenceSplit(5);
        } else if (id == R.id.meeting_ll_left_1) {
            if (left1) {
                if (netMeetingType == 3) {
                    StopSendVideo();
                }
                BridgeControl.ConferenceCamera(0);
            } else {
                if (netMeetingType == 3) {
                    StartSendVideo();
                }
                BridgeControl.ConferenceCamera(1);
            }
        } else if (id == R.id.meeting_ll_left_2) {
            if (left2) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) meetingSfBd.getLayoutParams();
                int height = 1;
                int width = 1;
                params.width = width;
                params.height = height;
                meetingSfBd.setLayoutParams(params);
                meetingPipTv.setText(getString(R.string.meeting_activity_open_p_in_p));
                left2 = false;
            } else {  // 点击本地视频变大
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) meetingSfBd.getLayoutParams();
                int height = 180;
                int width = 320;
                params.width = width;
                params.height = height;//640 480    540
                meetingSfBd.setLayoutParams(params);
                meetingPipTv.setText(getString(R.string.meeting_activity_close_p_in_p));
                left2 = true;
            }
            inVisibleMeetingFunLlLeft();
        } else if (id == R.id.meeting_share_some) {  // 共享
            IdcMediaMineCancel();
        } else if (id == R.id.btn_sharing_desk) {  // 共享桌面
            llMeetingSharing.setVisibility(View.GONE);
            IdcMediaPrepareDesktop();
        } else if (id == R.id.btn_sharing_white_board) {  // 白板
            llMeetingSharing.setVisibility(View.GONE);
            IdcMediaPrepareBroad(1);

        } else if (id == R.id.meeting_show_hands) {  // 大会议室举手
            IdcMediaRaiseHands();
        } else if (id == R.id.meeting_tv_time) {
            tapDebugModeTimes++;
            if (tapDebugModeTimes >= 6) {
                tapDebugModeTimes = 0;
                if (MeetingEntity.getInstance().isRecord()) {
                    UiUtils.showToastLong(getString(R.string.meeting_activity_close_video_record));
                    MeetingEntity.getInstance().setRecord(false);
                } else {
                    UiUtils.showToastLong(getString(R.string.meeting_activity_open_video_record));
                    MeetingEntity.getInstance().setRecord(true);
                }
            }
        } else if (id == R.id.meeting_info_imageview) {
            MeetingDetailDialog();
        } else if (id == R.id.meeting_recoder) {
            idcMediaRecord();
        }
    }

    private void initView() {
        blankCanvasView = findViewById(R.id.blank_canvas_view);
        meetingTvGd = findViewById(R.id.meeting_tv_gd );
        meetingTvGfImage = findViewById(R.id.meeting_tv_gf_image );
        meetingTvJyImage = findViewById(R.id.meeting_tv_jy_image );
        meetingTvFsImage = findViewById(R.id.meeting_tv_fs_image );
        meetingLlMidAuto = findViewById(R.id.meeting_ll_mid_auto );
        meetingLlMid1 = findViewById(R.id.meeting_ll_mid_1 );
        meetingLlMid2 = findViewById(R.id.meeting_ll_mid_2 );
        meetingLlMid3 = findViewById(R.id.meeting_ll_mid_3 );
        meetingLlMid4 = findViewById(R.id.meeting_ll_mid_4 );
        meetingLlMid5 = findViewById(R.id.meeting_ll_mid_5 );
        meetingLlMid6 = findViewById(R.id.meeting_ll_mid_6 );
        showHandsIcon = findViewById(R.id.show_hands_icon );
        backgroundImageView = findViewById(R.id.bd_iv );
        netIv = findViewById(R.id.net_iv );
        meetingInfoImageview = findViewById(R.id.meeting_info_imageview );
        meetingTvTime = findViewById(R.id.meeting_tv_time );
        meetingTvMeetingtheme = findViewById(R.id.meeting_tv_meetingtheme );
        meetingLlLeft1 = findViewById(R.id.meeting_ll_left_1 );
        meetingLlLeft3 = findViewById(R.id.meeting_ll_left_3);
        showHandsText = findViewById(R.id.show_hands_text );
        sharePositonText = findViewById(R.id.sharepositiontext );
        shareDesktopSponsorName = findViewById(R.id.share_desktop_man_name_textview );
        meetingTvPicture = findViewById(R.id.meeting_tv_picture );
        meetingTvQh = findViewById(R.id.meeting_tv_qh );
        llEnd = findViewById(R.id.meeting_ll_end );
        meetingTvYq = findViewById(R.id.meeting_tv_yq );
        meetingLlMid = findViewById(R.id.meeting_ll_mid );
        meetingLlLeft2 = findViewById(R.id.meeting_ll_left_2 );
        meetingLlLeft = findViewById(R.id.meeting_ll_left );
        meetingTvRw = findViewById(R.id.meeting_tv_rw );
        showLayout = findViewById(R.id.show_linear_layout );
        llMeetingSharing = findViewById(R.id.ll_meeting_sharing );
        meetingDeskFragment = findViewById(R.id.meeting_desk_fragment );
        meetingLlTop = findViewById(R.id.meeting_ll_top );
        meetingTvFs = findViewById(R.id.meeting_tv_fs );
        meetingTvJy = findViewById(R.id.meeting_tv_jy );
        meetingTvGf = findViewById(R.id.meeting_tv_gf);
        meetingTvRy = findViewById(R.id.meeting_tv_ry );
        meetingActivity = findViewById(R.id.meeting_activity );
        shareLayout = findViewById(R.id.meeting_share_some );
        showHands = findViewById(R.id.meeting_show_hands );
        mLocatePreviewLayout = findViewById(R.id.meeting_local_video_rl );
        meetingSfYd = findViewById(R.id.meeting_sf_yd );
        meetingSfBd = findViewById(R.id.meeting_sf_bd );
        meetingPipTv = findViewById(R.id.meeting_pip_tv);
        inviteIv = (ImageView) findViewById(R.id.invite_iv);
        meetingManageIv = (ImageView) findViewById(R.id.meeting_manage_iv);
        meetShareIcon = (ImageView) findViewById(R.id.meet_share_icon);
        cameraIv = (ImageView) findViewById(R.id.camera_iv);
        cameraPreviewTexture = (TextureView) findViewById(R.id.meeting_texture_bd);
        videoRl = (RelativeLayout) findViewById(R.id.video_rl);
        meetingCameraLl = (RelativeLayout) findViewById(R.id.meeting_ll_camera);
        meetingInviteLl = (RelativeLayout) findViewById(R.id.meeting_ll_invite_3);
        netRecAudioTv = (TextView) findViewById(R.id.net_rec_audio_tv);
        netSendAudioTv = (TextView) findViewById(R.id.net_send_audio_tv);
        netRecVideoTv = (TextView) findViewById(R.id.net_rec_video_tv);
        netSendVideoTv = (TextView) findViewById(R.id.net_send_video_tv);
        netRecData = (TextView) findViewById(R.id.net_rec_data);
        netSendData = (TextView) findViewById(R.id.net_send_data);
        netSignalDisplayShang = (TextView) findViewById(R.id.net_signal_display_shang);
        netSignalDisplayXia = (TextView) findViewById(R.id.net_signal_display_xia);
        netSignalDisplayYanshi = (TextView) findViewById(R.id.net_signal_display_yanshi);
        netSignalDisplay = (RelativeLayout) findViewById(R.id.net_signal_display);
        meetingRecoder = (RelativeLayout) findViewById(R.id.meeting_recoder);
        meetingRecoderImageview = (ImageView) findViewById(R.id.meeting_recoder_imageview);
        meetingRecoderTextView = (TextView) findViewById(R.id.meeting_recoder_textview);

        TextView sharingDeskTextview = (TextView) findViewById(R.id.btn_sharing_desk);
        TextView wihteBroadTextview = (TextView) findViewById(R.id.btn_sharing_white_board);

        meetingTvGd.setOnClickListener(this);
        meetingTvPicture.setOnClickListener(this);
        meetingTvQh.setOnClickListener(this);
        meetingTvFs.setOnClickListener(this);
        meetingTvJy.setOnClickListener(this);
        showHands.setOnClickListener(this);
        shareLayout.setOnClickListener(this);
        meetingTvGf.setOnClickListener(this);
        meetingTvRy.setOnClickListener(this);
        meetingActivity.setOnClickListener(this);
        meetingTvYq.setOnClickListener(this);
        meetingLlMid1.setOnClickListener(this);
        meetingLlMid2.setOnClickListener(this);
        meetingLlMid3.setOnClickListener(this);
        meetingLlMid4.setOnClickListener(this);
        meetingLlMid5.setOnClickListener(this);
        meetingLlMid6.setOnClickListener(this);
        meetingLlLeft1.setOnClickListener(this);
        meetingLlLeft2.setOnClickListener(this);
        meetingTvRw.setOnClickListener(this);
        meetingLlLeft3.setOnClickListener(this);
        meetingLlMidAuto.setOnClickListener(this);
        netIv.setOnClickListener(this);
        netRecAudioTv.setOnClickListener(this);
        meetingInfoImageview.setOnClickListener(this);
        meetingTvTime.setOnClickListener(this);
        sharingDeskTextview.setOnClickListener(this);
        wihteBroadTextview.setOnClickListener(this);
    }


    @Override
    public void MeetingDetailCuccess(String accessCode, String topic, String userName, String password) {
        meetingDetailAccessCode = TextUtils.isEmpty(accessCode) ? "" : accessCode;
        meetingDetailTopic = TextUtils.isEmpty(topic) ? "" : topic;
        meetingDetailUserName = TextUtils.isEmpty(userName) ? "" : userName;
        meetingDetailShowPwd = TextUtils.isEmpty(password) ? getString(R.string.none) : password;

        if (!TextUtils.isEmpty(meetingDetailAccessCode)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getString(R.string.meeting_num) + " ")
                    .append(":")
                    .append(meetingDetailAccessCode);
            if (meetingTvMeetingtheme != null) {
                meetingTvMeetingtheme.setText(stringBuilder.toString());
            }
        }
    }

    @Override
    public void InvitePeopleCuccess() {
        UiUtils.showToast(getString(R.string.meeting_activity_sms_send_success));
        if (secondList != null) {
            secondList.clear();
        }
    }

    @Override
    public void LockOrUnklockSuccess(boolean isLock) {
        isMeetingLockStatus = isLock;
        Drawable lockTop = getResources().getDrawable(R.drawable.meeting_lock);
        Drawable unlockTop = getResources().getDrawable(R.drawable.meeting_unlock);
        if (isMeetingLockStatus) {
            if (meetingLock != null) {
                meetingLock.setText(getResources().getString(R.string.meet_open));
                if (lockTop != null) {
                    meetingLock.setCompoundDrawablesWithIntrinsicBounds(null, lockTop, null, null);
                }
            }
        } else {
            if (meetingLock != null) {
                meetingLock.setText(getResources().getString(R.string.meet_close));
                if (unlockTop != null) {
                    meetingLock.setCompoundDrawablesWithIntrinsicBounds(null, unlockTop, null, null);
                }
            }
        }
    }

    @Override
    public void SelectTaskSuccess(List<MeetingTaskItem.DataBean.ListBean> list, int totalNum ,int pageNo) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (meetingTextView != null) {
            meetingTextView.setVisibility(View.GONE);
        }
        if (meetingTaskRY != null) {
            meetingTaskRY.setVisibility(View.VISIBLE);
        }
        totalNumber = totalNum;
        if (list != null && list.size() != 0) {
            if (pageNo == 1) {
                subscribeFragmentAdapter.setNewData(list);
                isErr = true;
            } else {
                subscribeFragmentAdapter.addData(list);
                isErr = true;
            }
        }
    }

    @Override
    public void SelectTaskNoMore(int totalNum) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        isErr = false;
        totalNumber = totalNum;
        if (subscribeFragmentAdapter.getData().size() == 0) {
            if (meetingTextView != null) {
                meetingTextView.setVisibility(View.VISIBLE);
                meetingTextView.setText(getString(R.string.current_task));
            }
            if (meetingTaskRY != null) {
                meetingTaskRY.setVisibility(View.GONE);
            }

        } else {
            if (totalNum == 0) {
                meetingTextView.setVisibility(View.VISIBLE);
                meetingTextView.setText(getString(R.string.current_task));
            }
            if(meetingTaskRY != null) {
                meetingTaskRY.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void ShowSummrySuccess(int id, boolean canEditSummary, boolean isMeetingLock, String summaryContent) {
        // 0:关闭 1:开启
        isMeetingLockStatus = isMeetingLock;
        summaryId = id;
        isSummary = canEditSummary;
        if (detailEditText != null) {
            detailEditText.setText(TextUtils.isEmpty(summaryContent) ? "" : summaryContent);
        }
    }

    @Override
    public void HttpConnectOnMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            UiUtils.showToast(message);
        }
    }

    @Override
    public void HttpConnectFailure(String message, MeetingRequestModel.MEETING_REQUEST_TYPE ErrorType) {
        if (ErrorType != null) {
            Log.i(TAG ,"Http connect fialure error. Type is " + ErrorType.ordinal());
        } else {
            Log.i(TAG ,"Http connect fialure error.");
        }
        if (!TextUtils.isEmpty(message)) {
            UiUtils.showToast(message);
        }

        switch (ErrorType) {
            case MEETING_LOCK_OR_UNLOCK_FAILURE:
                Drawable unlockTop = getResources().getDrawable(R.drawable.meeting_unlock);
                isMeetingLockStatus = false;
                if (meetingLock != null) {
                    if (unlockTop != null) {
                        meetingLock.setCompoundDrawablesWithIntrinsicBounds(null, unlockTop, null, null);
                    }
                }
                break;

            case SELECT_TASK_PAGENO_SICK_NET:
            case SELECT_TASK_PAGENO_NO_MORE:
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                isErr = false;
                if (meetingTextView != null) {
                    meetingTextView.setVisibility(View.VISIBLE);
                    meetingTextView.setText(getString(R.string.current_task));
                }
                if (meetingTaskRY != null) {
                    meetingTaskRY.setVisibility(View.GONE);
                }
                break;

            case EDIT_SUMMARY:
                UiUtils.showToast(getString(R.string.toast_36));
                break;

            case SELCTED_MEETING_TYPE:
                isSummary = false;
                break;

            default:
        }

    }


    class MeetingPerson extends BroadcastReceiver {
        private boolean onlyFirstTime = true;

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String action = intent.getAction();
                int screenStatus;
                switch (action) {
                    case "MeetingPersons":  // 广播接收画板
                        List<phoneinfo> resultListCallBack = (List<phoneinfo>) intent.getSerializableExtra("MeetingPerson");
                        if (resultList == null || resultList.size() == 0) {
                            resultList = resultListCallBack;
                        } else if (resultListCallBack != null && resultListCallBack.size() != 0) {
                            for (int i = 0; i < resultListCallBack.size(); i++) {
                                phoneinfo updateinfo = resultListCallBack.get(i);
                                boolean isExist = false;
                                for (int j = 0; j < resultList.size(); j++) {
                                    phoneinfo respinfo = resultList.get(j);
                                    if (updateinfo.getNumber().equals(respinfo.getNumber())) {
                                        isExist = true;
                                        resultList.set(j, updateinfo);
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    resultList.add(updateinfo);
                                }
                            }
                        }
                        String onlineMemberAccountNum = intent.getStringExtra("MeetingOnlineAccount");
                        String allMemberAccountNum = intent.getStringExtra("MeetingSumAccount");
                        String infoVersion = intent.getStringExtra("MeetingNumberListInfoVersion");
                        SharedPreferencesUtil.getsInstance().put("NumberListInfoVersion", Integer.parseInt(infoVersion));
                        LogUtils.e(TAG, "person resultList 1559i = " + resultList.size() + " nfoVersion = " + infoVersion + " onlineAccount = " + onlineMemberAccountNum + " SumAccount = " + allMemberAccountNum);
                        MemberManager.getInstance().updateMemberList(resultList);
                        MemberManager.getInstance().updateNickName(memberNicknamePresenter);
                        boolean muteMeetMode = true;
                        for (phoneinfo person : resultList) {
                            if (person.getStatus().equals("4") && !person.getSpeak().equals("1")) {
                                muteMeetMode = false;
                            }
                        }
                        if (muteMeetMode && netMeetingType != 3) {
                            LogUtils.d("Audio", "muteMeetMode");
                            StartSendVoice();  // 入会时全体禁言
                        }
                        if (mHostMan.equals(uTitleStr)) {// 如果是主持人
                            if (hostAdapter != null) {
                                hostAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (hostTitleAdapter != null) {
                                hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        } else {
                            // 如果不是主持人只能观看列表
                            if (joinAdapter != null) {
                                joinAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (joinTitleAdapter != null) {
                                joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        }
                        break;

                    case "MeetingDetails":
                        Log.d(TAG, "Meeting detail.");
                        detail = (List<String>) intent.getSerializableExtra("MeetingDetail");
                        shareSponorNumber = intent.getStringExtra("shareSponorNumber");
                        String isShareOpen = intent.getStringExtra("isShareing");
                        String shareType = intent.getStringExtra("shareType");
                        if (onlyFirstTime) {
                            if (GlobalUtils.getIsMeetingRoomRemoteSharing() == 0) {
                                if (isShareOpen.equals("1")) {
                                    BridgeControl.ConferenceSelectChannel(true);
                                } else {
                                    BridgeControl.ConferenceSelectChannel(false);
                                }
                            }
                        }
                        GlobalUtils.setIsMeetingRoomRemoteSharing(isShareOpen.equals("1") ? Integer.valueOf(isShareOpen) : 0);
                        int hostManIndex = 12;
                        if (detail.size() > hostManIndex) {
                            String Host = detail.get(hostManIndex);
                            mHostMan = Host;
                            StringBuilder HomeThemeStr = new StringBuilder();
                            meetingDetailAdminPassword = intent.getStringExtra("AdminPassword");
                            if (!TextUtils.isEmpty(uTitleStr)) {
                                HomeThemeStr.append(getResources().getString(R.string.host_number) + uTitleStr + "  ");
                            }

                            meetingDetailId = detail.get(15);//获取meetingid
                            netMeetingId = Integer.parseInt(detail.get(15));
                            requestMeetingDetail();
                            Log.e(TAG, "netmeetingid 1575 netMeetingId=" + netMeetingId);
                            if (onlyFirstTime) {
                                String meetingID = intent.getStringExtra("MeetingId");
                                if (TextUtils.isEmpty(meetingID) || meetingID.trim().length() == 0) {
                                    getMeetingType(3);
                                } else {
                                    getMeetingType(1);
                                }
                                // 入会后会议是否共享、批注
                                String Taggings = detail.get(16);
                                if (Taggings.equals("1") && GlobalUtils.getShareType(true) == 0) {
                                    String tagginWitch = detail.get(17);  // 如果是开启标注   17/18 保存宽高
                                    String tagginHight = detail.get(18);
                                    Log.e(TAG, "1714 tagginWitch=" + tagginWitch + " tagginHight=" + tagginHight);
                                    SharedPreferencesUtil.getsInstance().put("tagginWitch", tagginWitch);
                                    SharedPreferencesUtil.getsInstance().put("tagginHight", tagginHight);
                                    addFragment(); // 开启标注 界面上铺一个全屏的fragment 右边是4个选项 铅笔刷子橡皮颜色
                                    isTagging = true;
                                    if (mCameraSurfaceTextureListener != null) {
                                        mCameraSurfaceTextureListener.setIsTag(isTagging);
                                    }
                                    if (detail.get(19) != null && detail.get(19).equals("1")) {//桌面并开启标注
                                        shareLayout.setVisibility(View.GONE);
                                        Log.i("desktopdisappear", "shareLayout disappear GONE position is 1");
                                        setTagging(true);
                                        isMeetingPadShare = true;
                                        GlobalUtils.setLocalMeetingStatus(true);
                                    } else if (detail.get(19) != null && detail.get(19).equals("2") ||
                                            detail.get(19) != null && detail.get(19).equals("4")) {
                                        String sharenumber = detail.get(14);  // 平板打开的白板
                                        if (uTitleStr.equals(sharenumber)) {
                                            shareLayout.setVisibility(View.VISIBLE);
                                        } else {
                                            shareLayout.setVisibility(View.GONE);
                                            Log.i("desktopdisappear", "shareLayout disappear GONE position is 2");
                                        }
                                        isMeetingPadShare = true;
                                        GlobalUtils.setLocalMeetingStatus(true);
                                    } else {
                                        shareLayout.setVisibility(View.GONE);  // pc白板
                                        Log.i("desktopdisappear", "shareLayout disappear GONE position is 3");
                                        setTagging(true);
                                        isMeetingPadShare = true;
                                        GlobalUtils.setLocalMeetingStatus(true);
                                    }
                                    LogUtils.e("MeetingActivity", "MeetingDetails,GlobalUtils.getShareType()=" + GlobalUtils.getShareType(true) + ",Taggings=" + Taggings);
                                } else {
                                    if (GlobalUtils.getShareType(true) == 0) {
                                        String MeetingSharedType = detail.get(19);  // 0白板,1桌面,2安卓平板
                                        if (MeetingSharedType.equals("1")) {
                                            BridgeControl.ConferenceSelectChannel(true);
                                            BridgeControl.ConferenceShareFrame();
                                            shareLayout.setVisibility(View.GONE);
                                            Log.i("desktopdisappear", "shareLayout disappear GONE position is 4");
                                            String sharenumber = detail.get(14);
                                            if (uTitleStr.equals(sharenumber)) {
                                                shareLayout.setVisibility(View.VISIBLE);
                                            } else {
                                                shareLayout.setVisibility(View.GONE);
                                                Log.i("desktopdisappear", "shareLayout disappear GONE position is 5");
                                            }
                                            openShareDesk();
                                            if (mSharedModeScreenInfoManager != null) {
                                                mSharedModeScreenInfoManager.startProcess();
                                            }
                                            GlobalUtils.setLocalMeetingStatus(true);
                                            /*splitScreenLayout.setVisibility(View.GONE);
                                            closeCameraLayout.setVisibility(View.GONE);*/
                                        } else {
                                            isMeetingShared = false;
                                            if (mCameraSurfaceTextureListener != null) {
                                                mCameraSurfaceTextureListener.setMeetingShared(isMeetingShared);
                                            }
                                        }
                                        if (MeetingSharedType.equals("3") || MeetingSharedType.equals("4") || MeetingSharedType.equals("5")) {
                                            if (GlobalUtils.getShareType(true) == 0) {
                                                BridgeControl.ConferenceSelectChannel(true);
                                                BridgeControl.ConferenceShareFrame();
                                                openShareDesk();
                                                shareLayout.setVisibility(View.GONE);
                                                if (mSharedModeScreenInfoManager != null) {
                                                    mSharedModeScreenInfoManager.startProcess();
                                                }
                                                Log.i("desktopdisappear", "shareLayout disappear GONE position is 6");
                                            }
                                            GlobalUtils.setLocalMeetingStatus(true);
                                        }
                                        LogUtils.e("MeetingActivity", "MeetingDetails,GlobalUtils.getShareType()=" + GlobalUtils.getShareType(true) + ",MeetingSharedType=" + MeetingSharedType + "，Taggings=" + Taggings);
                                    } else {
                                        LogUtils.e("MeetingActivity", "MeetingDetails,GlobalUtils.getShareType()=" + GlobalUtils.getShareType(true));
                                    }
                                }
                            } else {
                                LogUtils.i(TAG, "meeting detail not the first time come in.");
                            }
                            onlyFirstTime = false;
                            if (mCameraSurfaceTextureListener != null) {
                                mCameraSurfaceTextureListener.setPcShareStatus(GlobalUtils.getLocalMeetingStatus(true));
                            }
                            String muteMode = intent.getStringExtra("meetingMuteMode");
                            if (muteMode.equals("1")) {  // 入会时全体禁言模式
                                LogUtils.e(TAG, "meetingMode is Mute.");
                                for (int i = 0; i < resultList.size(); i++) {
                                    resultList.get(i).setSpeak("1");
                                }
                                StartSendVoice();
                                jinyan = true;
                            } else {
                                jinyan = false;
                            }
                            Log.e("utitleStr","utitleStr ="+ uTitleStr +", HostMan ="+ mHostMan);
                            if (netMeetingType == 1) {  // 在会议模式时，非点对点模式
                                if (uTitleStr.equals(mHostMan)) {
                                    meetingLlLeft3.setVisibility(View.VISIBLE);
                                    meetingInviteLl.setVisibility(View.VISIBLE);
                                    meetingTvPicture.setVisibility(View.VISIBLE);
                                    if (hostAdapter != null) {
                                        hostAdapter.setHostMan(mHostMan);
                                    }
                                } else {
                                    meetingLlLeft3.setVisibility(View.GONE);
                                    meetingInviteLl.setVisibility(View.GONE);
                                    meetingTvPicture.setVisibility(View.GONE);
                                    if (joinAdapter != null) {
                                        joinAdapter.setHostMan(mHostMan);
                                    }
                                    if (joinTitleAdapter != null) {
                                        joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                                    }
                                }
                            }
                            String number = detail.get(7);
                            screenStatus = Integer.parseInt(number);

                            if (screenStatus >= 0) {
                                ScreenNumber = "0";
                            } else {
                                if (screenStatus == -1) {
                                    ScreenNumber = "1";
                                } else if (screenStatus == -11) {
                                    ScreenNumber = "11";
                                } else if (screenStatus == -12) {
                                    ScreenNumber = "12";
                                } else if (screenStatus == -13) {
                                    ScreenNumber = "13";
                                }
                            }
                            changeBtnBg(ScreenNumber);
                            String hostMember = intent.getStringExtra("HostMan");
                            if (!TextUtils.isEmpty(hostMember)) {
                                mHostMan = hostMember;
                                Log.i(TAG, "meeting detail control change host man is " + mHostMan);
                                if (!hostMember.equals(mHostMan) && !hostMember.equals(uTitleStr)) {
                                    if (joinAdapter != null) {
                                        joinAdapter.setHostMan(mHostMan);
                                    }
                                }
                            }
                        }
                        break;

                    case "deleteMembers":
                        String deleteMember = intent.getStringExtra("deleteMember");
                        for (int i = 0; i < resultList.size(); i++) {
                            if (resultList.get(i).getNumber().equals(deleteMember)) {
                                resultList.remove(i);
                                if (mHostMan.equals(uTitleStr)) {//如果是主持人
                                    if (hostAdapter != null) {
                                        hostAdapter.setDataSourceLists(resultList, netMeetingId);
                                    }
                                    if (hostTitleAdapter != null) {
                                        hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                                    }
                                } else {
                                    // 如果不是主持人只能观看列表
                                    if (joinAdapter != null) {
                                        joinAdapter.setDataSourceLists(resultList, netMeetingId);
                                    }
                                    if (joinTitleAdapter != null) {
                                        joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                                    }
                                }
                            }
                        }
                        break;

                    case "addMembers":
                        List<phoneinfo> addMemberList = (List<phoneinfo>) intent.getSerializableExtra("addMember");
                        if (addMemberList != null) {
                            for (int i = 0; i < addMemberList.size(); i++) {
                                phoneinfo addpinfo = addMemberList.get(i);
                                boolean isExist = false;
                                for (int j = 0; j < resultList.size(); j++) {
                                    phoneinfo respinfo = resultList.get(j);
                                    if (addpinfo.getNumber().equals(respinfo.getNumber())) {
                                        isExist = true;
                                        if (respinfo.getName() != null) {
                                            addpinfo.setName(respinfo.getName());
                                        }
                                        resultList.set(j, addpinfo);
                                        break;
                                    }
                                }
                                if (!isExist) {
                                    resultList.add(addpinfo);
                                }
                            }
                        }
                        MemberManager.getInstance().updateMemberList(resultList);
                        MemberManager.getInstance().updateNickName(memberNicknamePresenter);
                        if (mHostMan.equals(uTitleStr)) {//如果是主持人
                            if (hostAdapter != null) {
                                hostAdapter.setDataSourceLists(resultList, netMeetingId);//
                            }
                            if (hostTitleAdapter != null) {
                                hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        } else {
                            // 如果不是主持人只能观看列表
                            if (joinAdapter != null) {
                                joinAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (joinTitleAdapter != null) {
                                joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        }
                        String iUserNameStr = HkhKeyInfo.getInstance().getUserName();
                        for (int j = 0; j < addMemberList.size(); j++) {
                            phoneinfo info = addMemberList.get(j);
                            if (iUserNameStr.equals(info.getNumber())) {
                                if (info.getSpeak().equals("1")) {
                                    StartSendVoice();
                                } else if (info.getSpeak().equals("0")) {
                                    StopSendVoice();
                                }
                                break;
                            }
                        }
                        break;

                    case "PersonStates":
                        List<phoneinfo> personState = (List<phoneinfo>) intent.getSerializableExtra("Personstate");
                        if (resultList.size() > 0) {
                            for (int j = 0; j < personState.size(); j++) {
                                phoneinfo info = personState.get(j);
                                for (int i = 0; i < resultList.size(); i++) {
                                    phoneinfo rinfo = resultList.get(i);
                                    if (rinfo.getNumber().equals(info.getNumber())) {
                                        if (rinfo.getNumber().equals(info.getNumber())) {
                                            if (rinfo.getName() != null) {
                                                info.setName(rinfo.getName());
                                            }
                                            resultList.set(i, info);
                                        }
                                    }
                                }
                                if (info.getNumber().equals(uTitleStr)) {
                                    if (info.getSpeak().equals("1")) {
                                        StartSendVoice();
                                    } else if (info.getSpeak().equals("0")) {
                                        StopSendVoice();
                                    }
                                }
                            }
                            MemberManager.getInstance().updateMemberList(resultList);
                            MemberManager.getInstance().updateNickName(memberNicknamePresenter);
                            if (mHostMan.equals(uTitleStr)) {//如果是主持人
                                if (hostAdapter != null) {
                                    hostAdapter.setDataSourceLists(resultList, netMeetingId);
                                }
                                if (hostTitleAdapter != null) {
                                    hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                                }
                            } else {
                                // 如果不是主持人只能观看列表
                                if (joinAdapter != null) {
                                    joinAdapter.setDataSourceLists(resultList, netMeetingId);
                                }
                                if (joinTitleAdapter != null) {
                                    joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                                }
                            }
                        }
                        if (personState.size() > 0 && !TextUtils.isEmpty(uTitleStr)) {  // 本成员被会控请离
                            for (phoneinfo person : personState) {
                                if (person.getNumber().equals(uTitleStr) && person.getStatus().equals("5")) {
                                    UiUtils.showToast(getApplicationContext().getResources().getString(R.string.meeting_activity_exit_by_admin));
                                    closeMeetingRoom();
                                    finish();
                                    break;
                                }
                            }
                        }
                        break;

                    case "NoSpeaks":
                        String noSpeak = intent.getStringExtra("NoSpeak");
                        String[] noSpeaksplit = noSpeak.split("=");
                        if (noSpeaksplit[1].equals("all")) {
                            // 全体禁言
                            if (noSpeaksplit[0].equals("open")) {
                                // 开启全体禁言
                                for (int i = 0; i < resultList.size(); i++) {
                                    resultList.get(i).setSpeak("1");
                                }
                                if (netMeetingType != 3) {
                                    StartSendVoice();
                                }
                            }
                        } else {
                            // 单人禁言
                            if (noSpeaksplit[0].equals("open")) {
                                // 开启单人禁言
                                String meetingPersonNumber = noSpeaksplit[1];
                                for (int i = 0; i < resultList.size(); i++) {
                                    if (resultList.get(i).getNumber().equals(meetingPersonNumber)) {
                                        resultList.get(i).setSpeak("1");
                                    }
                                }
                                if (meetingPersonNumber.equals(uTitleStr) && netMeetingType != 3) {
                                    StartSendVoice();
                                }
                            } else {
                                // 关闭单人禁言
                                String meetingPersonNumber = noSpeaksplit[1];
                                for (int i = 0; i < resultList.size(); i++) {
                                    if (resultList.get(i).getNumber().equals(meetingPersonNumber)) {
                                        resultList.get(i).setSpeak("0");
                                    }
                                }
                                if (meetingPersonNumber.equals(uTitleStr) && netMeetingType != 3) {
                                    StopSendVoice();
                                }
                            }
                        }
                        MemberManager.getInstance().updateMemberList(resultList);
                        if (mHostMan.equals(uTitleStr)) {//如果是主持人
                            if (hostAdapter != null) {
                                hostAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (hostTitleAdapter != null) {
                                hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        } else {
                            // 如果不是主持人只能观看列表
                            if (joinAdapter != null) {
                                joinAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (joinTitleAdapter != null) {
                                joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        }
                        if (noSpeaksplit[1].equals(uTitleStr) || noSpeaksplit[1].equals("all")) {
                            if (noSpeaksplit[0].equals("open")) {
                                StartSendVoice();
                            } else if (noSpeaksplit[0].equals("close")) {
                                StopSendVoice();
                            }
                        }
                        break;

                    case "stopVideos":
                        String stopVideos = intent.getStringExtra("stopVideo");
                        String[] stopVideo = stopVideos.split("=");
                        String stopVideoState = stopVideo[0];
                        String stopVideoNumber = stopVideo[1];
                        if (stopVideoState.equals("open")) {
                            // 开启单人禁言
                            for (int i = 0; i < resultList.size(); i++) {
                                if (resultList.get(i).getNumber().equals(stopVideoNumber)) {
                                    resultList.get(i).setRecvvideo("1");
                                }
                            }
                        } else {
                            // 关闭单人禁言
                            for (int i = 0; i < resultList.size(); i++) {
                                if (resultList.get(i).getNumber().equals(stopVideoNumber)) {
                                    resultList.get(i).setRecvvideo("0");
                                }
                            }
                        }
                        MemberManager.getInstance().updateMemberList(resultList);
                        if (mHostMan.equals(uTitleStr)) {//如果是主持人
                            if (hostAdapter != null) {
                                hostAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (hostTitleAdapter != null) {
                                hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        } else {
                            // 如果不是主持人只能观看列表
                            if (joinAdapter != null) {
                                joinAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (joinTitleAdapter != null) {
                                joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        }
                        if (stopVideoNumber.equals(uTitleStr)) {
                            if (stopVideoState.equals("open")) {
                                StartSendVideo();
                            } else if (stopVideoState.equals("close")) {
                                StopSendVideo();
                            }
                        }
                        break;

                    case "ConferenceMode":
                        String MeetingVoiceMake = intent.getStringExtra("ConferenceVoiceMake");
                        String MeetingModeType = intent.getStringExtra("ConferenceModeType");
                        LogUtils.d("close_voice", "ConferenceMode MeetingVoiceMake =" + MeetingVoiceMake);
                        LogUtils.d("close_voice", "ConferenceMode MeetingModeType =" + MeetingModeType);
                        MemberManager.getInstance().updateMemberList(resultList);
                        if (mHostMan.equals(uTitleStr)) {  // 如果是主持人
                            if (hostAdapter != null) {
                                hostAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (hostTitleAdapter != null) {
                                hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        } else {
                            // 如果不是主持人只能观看列表
                            if (joinAdapter != null) {
                                joinAdapter.setDataSourceLists(resultList, netMeetingId);
                            }
                            if (joinTitleAdapter != null) {
                                joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                            }
                        }
                        break;

                    case "closeMeetings":
                        String closeMeeting = intent.getStringExtra("closeMeeting");
                        if (closeMeeting.equals(meetingNumber)) {
                            BridgeControl.CallHangUp();
                            closeMeetingRoom();
                            finish();
                        }
                        break;

                    case "MeetingPowers":
                        // 权限移交 70013 1 5555 180010 2向3移交会议权限 是否接受 是 否
                        try {
                            String meetingPower = intent.getStringExtra("MeetingPower");
                            String[] meetingPowers = meetingPower.split(" ");
                            String meetingPower1 = meetingPowers[2];
                            if (meetingPower1.equals(uTitleStr)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (alertDialog != null) {
                                            alertDialog.dismiss();
                                        }
                                        if (alertjoinDialog != null) {
                                            alertjoinDialog.dismiss();
                                        }
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (alertjoinDialog != null) {
                                            alertjoinDialog.dismiss();
                                        }
                                        if (alertDialog != null) {
                                            alertDialog.dismiss();
                                        }
                                        setMeetingSummary();
                                        setMoreAndShareLayoutGone();
                                        UiUtils.showToast(getString(R.string.tips_6));
                                    }
                                });
                            }
                            Log.d(TAG, "Make Call Normal FVPhone Get Detail(11), number is " + meetingNumber);
                            BridgeControl.ConferenceDetail();
                            String hostMember = meetingPowers[3];
                            if (TextUtils.isEmpty(hostMember)) {
                                mHostMan = hostMember;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "MeetingControls":
                        try {
                            String meetingControl = intent.getStringExtra("MeetingControl");
                            String[] meetingControls = meetingControl.split(" ");
                            String meetingMan2 = meetingControls[3];
                            if (!TextUtils.isEmpty(meetingMan2) && (meetingMan2.equals(mHostMan)
                                    || meetingMan2.equals(uTitleStr)) || mHostMan.equals(uTitleStr)) {
                                if (alertDialog != null) {
                                    alertDialog.dismiss();
                                }
                                if (alertjoinDialog != null) {
                                    alertjoinDialog.dismiss();
                                }
                                if (inviteMemberDialog != null) {
                                    inviteMemberDialog.dismiss();
                                }
                                Log.d(TAG, "Make Call Normal FVPhone Get Detail(13), number is " + meetingNumber);
                                BridgeControl.ConferenceDetail();
                            }
                            mHostMan = meetingMan2;
                            if (hostAdapter != null) {
                                hostAdapter.setHostMan(mHostMan);
                            }
                            if (joinAdapter != null) {
                                joinAdapter.setHostMan(mHostMan);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                    case "ScreenNumbers":
                        String ScreenNumber1 = intent.getStringExtra("ScreenNumber");
                        Log.e(TAG, "screennumber=" + ScreenNumber1);
                        // 分屏Status >= 0 为 自动分屏   -1 为1分  -2为2分 -3 为三分 -4 为四分
                        screenStatus = Integer.parseInt(ScreenNumber1);
                        if (screenStatus >= 0) {
                            ScreenNumber = "0";
                        } else {
                            if (screenStatus == -1) {
                                ScreenNumber = "1";
                            } else if (screenStatus == -11) {
                                ScreenNumber = "11";
                            } else if (screenStatus == -12) {
                                ScreenNumber = "12";
                            } else if (screenStatus == -13) {
                                ScreenNumber = "13";
                            }
                        }
                        changeBtnBg(ScreenNumber);
                        break;

                    case "MeetingTagging":
                        String Taggings2 = intent.getStringExtra("Taggings");
                        BridgeControl.ConferenceShareFrame();
                        if (Taggings2.equals("1")) {
                            // 开启标注 界面上铺一个全屏的fragment 右边是4个选项 铅笔刷子橡皮颜色
                            addFragment();
                            isTagging = true;
                            if (mCameraSurfaceTextureListener != null) {
                                mCameraSurfaceTextureListener.setIsTag(isTagging);
                            }
                            setTagging(true);
                        } else {
                            // 关闭标注
                            removeFragment();
                            isTagging = false;
                            if (mCameraSurfaceTextureListener != null) {
                                mCameraSurfaceTextureListener.setIsTag(isTagging);
                            }
                            setTagging(false);
                            BridgeControl.ConferenceFrame();
                        }
                        break;

                    case "MeetingShareds":
                        String meetingShared = intent.getStringExtra("MeetingShared");
                        String MeetingSharedType = intent.getStringExtra("MeetingSharedType");
                        shareSponorNumber = intent.getStringExtra("shareSponorNumber");
                        GlobalUtils.setIsMeetingRoomRemoteSharing(Integer.valueOf(meetingShared));
                        if (meetingShared.equals("1")) {//开启=1 关闭=0
                            shareLayout.setVisibility(View.GONE);
                            llMeetingSharing.setVisibility(View.GONE);
                            meetShareIcon.setImageResource(R.drawable.shareicon);
                            Log.d("desktopdisappear", "shareLayout disappear GONE position is 7");
                            // 0白板;1桌面;3平板共享桌面;2平板共享白板
                            if (MeetingSharedType.equals("1")) {
                                BridgeControl.ConferenceSelectChannel(true);
                                openShareDesk();
                            } else if (MeetingSharedType.equals("3")) {
                                // 共享电子桌面
                                BridgeControl.ConferenceSelectChannel(true);
                                openShareDesk();
                            } else if (MeetingSharedType.equals("2")) {
                                isMeetingPadShare = true;
                                BridgeControl.ConferenceSelectChannel(true);
                            }
                            if (mSharedModeScreenInfoManager != null) {
                                mSharedModeScreenInfoManager.startProcess();
                            }
                            EditTextUtils.setDesktopSponorHint(shareSponorNumber, shareDesktopSponsorName, resultList, MeetingActivity.this);
                        } else if (meetingShared.equals("0")) {
                            isMeetingPadShare = false;
                            BridgeControl.ConferenceSelectChannel(false);
                            if (isTagging) {
                                setTagging(false);
                            }
                            if (showHands != null && showHands.getVisibility() != View.VISIBLE) {
                                shareLayout.setVisibility(View.VISIBLE);
                            }
                            if (mSharedModeScreenInfoManager != null) {
                                mSharedModeScreenInfoManager.stopProcess();
                            }
                            stopShareDesk();
                            BridgeControl.ConferenceFrame();
                        }
                        if (mCameraSurfaceTextureListener != null) {
                            mCameraSurfaceTextureListener.setPcShareStatus(GlobalUtils.getLocalMeetingStatus(true));
                        }
                        break;

                    case "SharingControl":
                        String meetingSharedNumber = intent.getStringExtra("MeetingSharedNumber");
                        String meetingSharedStatus = intent.getStringExtra("MeetingSharedStatus");
                        String meetingSharedisShare = intent.getStringExtra("MeetingSharedisShare");
                        Log.d(TAG, "Desktop Share Detail Log:\n"
                                + "\t meetingSharedNumber = " + meetingSharedNumber
                                + "\t meetingSharedStatus = " + meetingSharedStatus
                                + "\t meetingSharedisShare = " + meetingSharedisShare
                                + "\t GlobalUtils.getShareType() = " + GlobalUtils.getShareType(true)
                                + "\t GlobalUtils.getLocalMeetingStatus() = " + GlobalUtils.getLocalMeetingStatus(true));
                        if (meetingSharedisShare.equals("1")) {//打开共享
                            if (meetingSharedStatus.equals("1")) {//共享成功
                                if (GlobalUtils.getShareType(true) == 1) {
                                    // 手机暂时不能共享白板
                                    IdcMediaPrepareBroad(2);
                                } else if (GlobalUtils.getShareType(true) == 2) {
                                    if (uTitleStr.equals(mHostMan)) {
                                        // 会控
                                    } else {
                                        // 非会控
                                    }
                                    BridgeControl.ConferenceSelectChannel(true);
                                    deskShareScreen();
                                    GlobalUtils.setShareType(3);
                                    GlobalUtils.setLocalMeetingStatus(true);
                                } else {
                                    UiUtils.showToastLong(getString(R.string.meeting_activity_try_again_later));
                                }
                                if (mSharedModeScreenInfoManager != null) {
                                    mSharedModeScreenInfoManager.startProcess();
                                }
                            } else {  // 共享失败
                                UiUtils.showToastLong(getString(R.string.meeting_activity_share_desktop_start_failure));
                                if (GlobalUtils.getShareType(true) == 3) {
                                    mediaProjection = null;
                                }
                                GlobalUtils.setShareType(0);
                                GlobalUtils.setLocalMeetingStatus(false);
                            }
                        } else {  // 关闭共享
                            if (!meetingSharedStatus.equals("1")) {  // 结束共享失败
                                UiUtils.showToastLong(getString(R.string.meeting_activity_share_desktop_stop_failure));
                            } else {
                                BridgeControl.ConferenceSelectChannel(false);
                            }
                            if (GlobalUtils.getShareType(true) == 3) {
                                UiUtils.showToastLong(getString(R.string.meeting_activity_share_desktop_stop_success));
                                stopShareDesk();
                                BridgeControl.ConferenceShareFrame();
                                BridgeControl.ConferenceFrame();
                            }
                            if (GlobalUtils.getShareType(true) == 1) {
                                BridgeControl.stopWhiteBoard();
                                setTagging(false);
                                stopShareDesk();
                                isMeetingPadShare = false;
                            }
                            GlobalUtils.setShareType(0);
                            GlobalUtils.setLocalMeetingStatus(false);
                            if (mSharedModeScreenInfoManager != null) {
                                mSharedModeScreenInfoManager.stopProcess();
                            }
                        }
                        BridgeControl.ConferenceFrame();
                        if (mPeopleInfoFloatViewListener != null) {
                            mPeopleInfoFloatViewListener.setMeeting(GlobalUtils.getLocalMeetingStatus(true));
                        } else {
                            Log.e(TAG ,"People info float listener ,set meeting status.");
                        }
                        break;

                    case "changeShareTypes":

                        break;

                    case "MeetingEndDeskShared":
                        endDeskShare();
                        break;

                    case "HangUpCallMeeting":
                        String meetingNumberReconnct = meetingNumber;
                        BridgeControl.CallHangUp();
                        DisconnectDialog.create(MeetingActivity.this, getString(R.string.title_6), getString(R.string.meeting_activity_net_error_contents), getString(R.string.meeting_activity_net_error_try),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(NetworkManager.isConnected()) {
                                            Log.i(TAG, "Service reconnect,Network connect.");
                                            new Thread() {
                                                @Override
                                                public void run() {
                                                    super.run();
                                                    String number = meetingNumberReconnct;
                                                    try {
                                                        Thread.sleep(3000);
                                                        Log.d(TAG, "Make Call Normal FVPhone Get Detail(17), number is " + number);
                                                        BridgeControl.ConferenceDetail();
                                                        MeetingEntity.getInstance().setMeetingNumber(number);
                                                        Log.i(TAG, "Service hangup and reconnect now. Meeting number is" + number);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }.start();
                                        } else {
                                            UiUtils.showToast(getString(R.string.meeting_activity_check_network_detail));
                                            Log.i(TAG, "Service reconnect,Network unconnect.");
                                        }
                                        finish();
                                        Log.i(TAG, "Service hangup but reconnect.");
                                    }
                                }, getString(R.string.cancel),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //NONE
                                        closeMeetingRoom();
                                        finish();
                                        Log.i(TAG, "Service hangup so exit meeting room.");
                                    }
                                }, false, false, false).show();
                        break;

                    case "MeetingHandup":
                        // 大会议室举手
                        int handupCode = intent.getIntExtra("Handup", -1);
                        if (handupCode == 0) {
                            setTransferPersonPermission(false);
                            if (!isTransferPersonPermission && !left1) {
                                StartOrStopSendVideo();
                            }
                            UiUtils.showToast(getString(R.string.meeting_activity_permission_normal));
                            jstimes = false;
                        } else if (handupCode == 1 || handupCode == 2) {
                            setTransferPersonPermission(true);
                            showHandsIcon.setImageResource(R.drawable.meeting_show_handup);
                            showHandsText.setText(getString(R.string.ask_speech));
                            if (handupCode == 2) {
                                UiUtils.showToast(getString(R.string.meeting_activity_apply_deny));
                                jstimes = false;
                            }
                            if (handupCode == 1) {
                                UiUtils.showToast(getString(R.string.meeting_activity_being_transfer_person));
                                setMoreAndShareLayoutGone();
                                jstimes = false;
                            }
                        }
                        break;

                    case "android.intent.action.SCREEN_OFF":
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setMoreAndShareLayoutGone() {
        if (llMeetingSharing.getVisibility() == View.VISIBLE) {
            llMeetingSharing.setVisibility(View.GONE);
        }
        if (meetingLlLeft.getVisibility() == View.VISIBLE) {
            meetingLlLeft.setVisibility(View.GONE);
        }
    }

    private class MeetingActivityMemberCallback implements MemberCallback {

        @Override
        public void updateMemberListInfo(ArrayList<phoneinfo> NumberMatchNickName) {
            Log.i("MemberManager", "phoneinfo list updateMemberListInfo.");
            for (phoneinfo newStatePeople : NumberMatchNickName) {
                for (phoneinfo oldStatePeople : resultList) {
                    if (oldStatePeople != null && oldStatePeople.getNumber().equals(newStatePeople.getNumber())) {
                        oldStatePeople.setHideSensitiveInfo(newStatePeople.getHideSensitiveInfo());
                        oldStatePeople.setNickName(newStatePeople.getName());
                        oldStatePeople.setName(newStatePeople.getName());
                        break;
                    }
                }
            }
        }

        @Override
        public void updateMemberNickName() {
            Log.i("MemberManager", "phoneinfo list updateMemberNickName.");
            if (mHostMan.equals(uTitleStr)) {  // 如果是主持人
                if (hostAdapter != null) {
                    hostAdapter.setDataSourceLists(resultList, netMeetingId);
                }
                if (hostTitleAdapter != null) {
                    hostTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                }
            } else {
                // 如果不是主持人只能观看列表
                if (joinAdapter != null) {
                    joinAdapter.setDataSourceLists(resultList, netMeetingId);
                }
                if (joinTitleAdapter != null) {
                    joinTitleAdapter.setNdkLibraryDataSourceLists(resultList);
                }
            }
            EditTextUtils.setDesktopSponorHint(shareSponorNumber, shareDesktopSponsorName, resultList, MeetingActivity.this);
        }
    }

    public void addFragment() {
        SharedPreferencesUtil.getsInstance().put("shareTag", "1");
        FragmentManager supportFragmentManager = MeetingActivity.this.getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        if (meetingDeskFragment.getVisibility() == View.GONE) {
            meetingDeskFragment1 = new MeetingDeskFragment();
            fragmentTransaction.replace(R.id.meeting_desk_fragment, meetingDeskFragment1);
            fragmentTransaction.commitAllowingStateLoss();
            meetingDeskFragment.setVisibility(View.VISIBLE);
        }
        if (meetingDeskFragment1 != null) {
            meetingDeskFragment1.setTransferPersonPermission(isTransferPersonPermission);
        }
        if (mSharedModeScreenInfoManager != null) {
            mSharedModeScreenInfoManager.startProcess();
        }
    }

    public void removeFragment() {
        meetingDeskFragment.setVisibility(View.GONE);
        if (fragmentTransaction != null) {
            fragmentTransaction.remove(meetingDeskFragment1);
        }
        if (meetingDeskFragment1 != null) {
            if (meetingDeskFragment1.getSketchView() != null) {
                meetingDeskFragment1.getSketchView().erase();
                meetingDeskFragment1.getSketchView().setAlpha(0);
                meetingDeskFragment1.getSketchView().setVisibility(View.GONE);
            }
        }
        if (mSharedModeScreenInfoManager != null) {
            mSharedModeScreenInfoManager.stopProcess();
        }
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "on resume");
        if (!isActive) {
            // app 从后台唤醒，进入前台
            isActive = true;
            Log.e(TAG, "ACTIVITY 程序从后台唤醒");
            BridgeControl.ConferenceShareFrame();
        }
        super.onResume();
        if (subscribeFragmentAdapter != null && meetingTextView != null && meetingTaskRY != null) {
            getMeetingTask(taskPageNo);
        }

        if (GlobalUtils.launchEntry) {
            GlobalUtils.launchEntry = false;
            // 当前共享桌回到会议界面后隐藏红点
            LogUtils.i(TAG, "hide --onResume---判斷隱藏--removeSmallWindow--");
        }
        if (MeetingEntity.getInstance().isRecord()) {
            UiUtils.showToastLong(getString(R.string.meeting_activity_video_record_doing));
        }
        MessageProcessLoop.getInstance().UnsubscribeAll();
        MessageProcessLoop.getInstance().subscribeNotify(this, new CallHangupRingAgent() {
            @Override
            public void agency() {
                LogUtils.d(TAG, "from sip msg hangup (13) " + " close meeting");
                releaseCamera();
                closeMeetingRoom();
                UiUtils.showToast(getResources().getString(R.string.receive_server_exit_meeting));
                finish();
            }
        });
        Log.i(TAG, "on resume success.");
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "on stop");
        if (!isAppOnForeground()) {
            //app 进入后台
            isActive = false;//记录当前已经进入后台
            Log.e(TAG, "ACTIVITY 程序进入后台");
        }
        super.onStop();
        Log.i(TAG, "on stop success.");
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "on pause");
        super.onPause();
        Log.i(TAG, "on pause success.");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.ACTION_DOWN:
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
            case KeyEvent.KEYCODE_HOME:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "on restart");
        if (mCameraSurfaceTextureListener != null) {
            LogUtils.d(TAG, "CameraSurfaceTextureListener getmInstance != null");
            mCameraSurfaceTextureListener.onRebindCameraPreview(this);
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "on destory");
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);//注销EventBus
        }
        if (GlobalUtils.getShareType(true) == 3) {
            endDeskShare();
        }
        GlobalUtils.Reset();
        isSummary = false;
        SharedPreferencesUtil.getsInstance().put(VideoFlagContacts.BY_ADDING_MEETING_TAG, false);
        if (internetInfoPresenter != null) {
            internetInfoPresenter.Destory();
            internetInfoPresenter = null;
        }
        if (meetingRequestPresenter != null) {
            meetingRequestPresenter.Destory();
            meetingRequestPresenter = null;
        }
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (meetingPerson != null) {
            unregisterReceiver(meetingPerson);
        }
        BridgeControl.CallHangUp();
        try {
            releaseCamera();
            if (blankCanvasView != null) {
                blankCanvasView.removeAllViews();
                blankCanvasView = null;
            }
            if (intentService != null) {
                stopService(intentService);
                intentService = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBluetoothDevicesManager.destroy();
        mBluetoothDevicesManager = null;
        //TODO move these lines code to BluetoothManager destory methods
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (audioManager.isBluetoothScoOn()) {
                audioManager.setBluetoothScoOn(false);
                audioManager.stopBluetoothSco();
            }
        }
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.Destory();
        }
        //TODO release all resource
        if (cameraPreviewTexture != null) {
            cameraPreviewTexture.setSurfaceTextureListener(null);
            cameraPreviewTexture = null;
        }
        if (meetingContactsRecyclerView != null) {
            meetingContactsRecyclerView.setAdapter(null);
            meetingContactsRecyclerView = null;
        }
        if (meetingContactsAdapter != null) {
            meetingContactsAdapter = null;
        }
        if (favoriteList != null) {
            favoriteList.clear();
            favoriteList = null;
        }
        if (mobilePhoenList != null) {
            mobilePhoenList.clear();
            mobilePhoenList = null;
        }
        if (enterpriseList != null) {
            enterpriseList.clear();
            enterpriseList = null;
        }
        mSharedModeScreenInfoManager.setHandler(null);
        mSharedModeScreenInfoManager = null;
        mMeetingVideoDateLooper = null;
        meetingActivityMemberCallback = null;
        if (mMeetingVideoDateHandler != null) {
            mMeetingVideoDateHandler.removeCallbacks(null);
            mMeetingVideoDateHandler = null;
        }
        if (mPeopleInfoFloatViewListener != null) {
            mPeopleInfoFloatViewListener.freeAll();
        } else {
            Log.e(TAG ,"People info float listener ,free all.");
        }
        MeetingEntity.getInstance().setMeetingState(false);
        if (meetingBroadcast != null) {
            meetingBroadcast.unRegistCast(this);
            meetingBroadcast = null;
        }
        MemberManager.getInstance().Destory();
        MeetingEntity.getInstance().setMeetingNumber("");
        IdcMediaKit.getInstance().setMeetingMediaKit(null);
        super.onDestroy();
        Log.i(TAG, "on destory success.");
    }

    // 结束会议
    private void dissolveMeetingRoom() {
        if (!mHostMan.equals(uTitleStr)) {
            return;
        }
        BridgeControl.ConferenceClose();
        if (resultList != null) {
            resultList.clear();
        }
        closeMeetingRoom();
    }

    // 退出会议
    private void closeMeetingRoom() {
        if (GlobalUtils.getShareType(true) == 3) {  // 共享桌面时退出会议结束共享流程
            endDeskShare();
            UiUtils.showToast(getString(R.string.meeting_activity_share_desktop_stop_success));
        }
        if (isTagging) {
            BridgeControl.ConferenceSelectChannel(false);
            if (meetingDeskFragment1 != null) {
                if (meetingDeskFragment1.getSketchView() != null) {
                    meetingDeskFragment1.getSketchView().closeSketchView();
                }
            }
        }
        BridgeControl.CallHangUp();
        releaseCamera();
        if (mPeopleInfoFloatViewListener != null) {
            mPeopleInfoFloatViewListener.freeAll();
        } else {
            Log.e(TAG ,"People info float listener ,free all ,close meeting before.");
        }
        if (blankCanvasView != null) {
            blankCanvasView.removeAllViews();
            blankCanvasView = null;
        }
        if (mPeopleInfoFloatViewListener != null) {
            mPeopleInfoFloatViewListener.freeAll();
        } else {
            Log.e(TAG ,"People info float listener ,free all ,close meeting after.");
        }
        MeetingEntity.getInstance().setMeetingNumber("");
        MemberManager.getInstance().Destory();
    }

    public synchronized void releaseCamera() {
        Log.d(TAG, "release camera.");
        try {
            if (screenRecordManager != null) {
                Log.d(TAG, "screen record release");
                screenRecordManager.destroy();
                screenRecordManager = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "close scrren record , excption is " + e.getMessage());
            CrashReport.postCatchedException(e);
        }
        try {
            BridgeControl.ConferenceSpeakerReceive(0);
            BridgeControl.ConferenceSpeaker(0);
            BridgeControl.ConferenceCameraSend(0);
            if (handler != null) {
                handler.removeCallbacks(runable);
            }
        } catch (Exception e) {
            Log.e(TAG, "MainActivity 关闭界面释放资源报错=" + e.toString());
            e.printStackTrace();
        }
        try {
            Log.i(TAG, "fvpOpenAudio close Audio stream open is " + openAudioSteam);
            if (openAudioSteam) {
                BridgeControl.ConferenceMicPhone(0);
                openAudioSteam = false;
                Log.i(TAG, "fvpOpenAudio close.");
                try {
                    MeetingEntity.getInstance().getJniContentClass().NativeMeetingAudioClose();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "close meeting audio thread failure.");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Release camera and close audio exception is " + e.toString());
            e.printStackTrace();
        }
        try {
            if (myVideoRequest != null) {
                myVideoRequest.setHandler(null);
                myVideoRequest.release();
                myVideoRequest = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "release my video request resource excption is " + e.getMessage());
        }
        try {
            if (CameraController != null) {
                CameraController.stopPreview();
                CameraController.destroyCamera();
                CameraController = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "release camera resource excption is " + e.getMessage());
        }
        try {
            if (mrVideoDecoder != null) {
                mrVideoDecoder.release();
                mrVideoDecoder = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "release mr video request resource excption is " + e.getMessage());
        }
        try {
            if (receiver != null) {
                unregisterReceiver(receiver);
                receiver = null;
            }
            if (meetingPerson != null) {
                unregisterReceiver(meetingPerson);
                meetingPerson = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "release regist receiver excption is " + e.getMessage());
        }
    }

    Runnable runable = new Runnable() {
        @Override
        public void run() {  // 10s倒数
            contectTime++;
            String minute = "", second = "";
            int fen = contectTime / 60;
            int miao = contectTime % 60;
            if (fen < 10) {
                minute = "0" + fen;
            } else {
                minute = fen + "";
            }
            if (miao < 10) {
                second = "0" + miao;
            } else {
                second = "" + miao;
            }
            if (meetingTvTime != null) {
                meetingTvTime.setText(minute + ":" + second);
                LogUtils.d(TAG, "显示的秒数" + contectTime + "");
            } else {
                LogUtils.d(TAG, "显示的秒数 meetingTvTime==null");
            }
            showSignalDisplay();
            if (netMeetingType == 2 && !isSendVideo && !isTagging
                    && (contectTime >= 15 && contectTime <= 17)) {  // 非点对点入会，15秒未解码成功退出会议
                UiUtils.showToastLong(getResources().getString(R.string.no_video_stream_meeting_type));
                closeMeetingRoom();
                finish();
            }

            if (contectTime == 2) {
                PermissionUtil.showPermissionDialog(MeetingActivity.this, PermissionUtil.isCameraPermissionGranted(
                        MeetingActivity.this, "android.permission.CAMERA"), PermissionUtil.
                        isAudioPermissionGranted(MeetingActivity.this, "android.permission.RECORD_AUDIO"));
                LogUtils.d("showPermissionDialog", "CameraSurfaceTextureListener.getmInstance().isCameraIsFailed() =" +
                        PermissionUtil.isCameraPermissionGranted(MeetingActivity.this, "android.permission.CAMERA")
                        + " , " + "AudioIsOpen =" + PermissionUtil.isAudioPermissionGranted(
                        MeetingActivity.this, "android.permission.RECORD_AUDIO"));
            }
            if (contectTime >= 9 && contectTime <= 10) {
                HiddenAllWidgt();
            }

            if (handler != null) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    /**
     * 接受共享、批注、白板，在墙下时，不需要发送视频流
     * 流量从250Kbs-500Kbs 减少至0；
     */
    private void StartOrStopSendVideo() {
        Log.e(TAG, "StartOrStopSendVideo, need open is " + needMyVideoOpen);
        if (netMeetingType == 3) {
            Log.e(TAG,"netMeetingType is p2p.");
            return;
        }
        if(needMyVideoOpen){
            StopSendVideo();
        } else {
            StartSendVideo();
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case PHONE_CALL_RECIVER_AND_HANG_DOEN:
                    if (mBluetoothDevicesManager != null && !mBluetoothDevicesManager.getBluetoothEarListening()) {
                        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        if (audioManager != null) {
                            audioManager.setMode(AudioManager.MODE_NORMAL);
                            audioManager.setSpeakerphoneOn(true);;
                            Log.d(TAG, "PhoneState is " + audioManager.getMode() + ", speaker = " + audioManager.isSpeakerphoneOn());
                        }
                    }
                    isAnswerPhoneCall = false;
                    break;

                case APPLY_FOR_MEETINGCODE:
                    applyForMeeting(R.drawable.meeting_show_handup, getString(R.string.ask_speech), false, 0);
                    break;
            }
        }
    };

    class HeadsetDetectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
                if (intent.hasExtra("state")) {
                    int state = intent.getIntExtra("state", 0);
                    if (state == 1) {
                        // UiUtils.showToast("插入耳机");
                        AudioUtils.setSpeakerphoneOn(MeetingActivity.this, false);
                    } else if (state == 0) {
                        // UiUtils.showToast("拔出耳机");
                        AudioUtils.setSpeakerphoneOn(MeetingActivity.this, true);
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBusMsg(String newcall) {
        if (newcall.equals("PhoneStateIDLE")) {
            Log.d(TAG, "PhoneState is IDLE. Before Answer Phone Call is " + isAnswerPhoneCall);
            if (isAnswerPhoneCall && handler != null) {
                Message message = handler.obtainMessage();
                message.what = PHONE_CALL_RECIVER_AND_HANG_DOEN;
                handler.sendMessageDelayed(message, 7000);
            }

        } else if (newcall.equals("PhoneStateRing")) {
            Log.d(TAG, "PhoneState is Ring.");
        } else if (newcall.equals("PhoneStateOffBook")) {
            Log.d(TAG, "PhoneState is OffBook. Before Answer Phone Call is " + isAnswerPhoneCall);
            if (!isAnswerPhoneCall) {
                StartSendVoice();
                BridgeControl.ConferenceMute(1);
                isAnswerPhoneCall = true;
            }
        } else if ("hiddleButton".equals(newcall)) {
            if (times) {
                llEnd.setVisibility(View.GONE);//底边控制栏
                meetingLlTop.setVisibility(View.GONE);
                meetingTvQh.setVisibility(View.GONE);
                meetingTvGd.setVisibility(View.GONE);
                meetingLlLeft.setVisibility(View.GONE);
                netSignalDisplay.setVisibility(View.GONE);

                if (mHostMan.equals(uTitleStr)) {
                    // 分屏按钮消失 分屏模式控件
                    meetingLlMid.setVisibility(View.GONE);
                    bdtimes = false;
                }
                leftll = false;
                times = false;
            } else {
                llEnd.setVisibility(View.VISIBLE);
                meetingLlTop.setVisibility(View.VISIBLE);
                if (isTransferPersonPermission) {
                    meetingTvQh.setVisibility(View.GONE);
                } else {
                    meetingTvQh.setVisibility(View.VISIBLE);
                }
                if (llMeetingSharing != null && llMeetingSharing.getVisibility() == View.VISIBLE) {
                    meetingTvQh.setVisibility(View.VISIBLE);
                }
                meetingTvGd.setVisibility(View.VISIBLE);

                times = true;
            }
        } else if (VideoFlagContacts.RECV_JNI_VIDEO_INFO.equals(newcall)) {
            isSendVideo = true;
        } else if (TextUtils.equals("NetWorkChange", newcall)) {
            UiUtils.showToast(getResources().getString(R.string.sactivity_network_reconnected_info));
        } else if (TextUtils.equals("close_meeting", newcall)) {
            closeMeetingRoom();
            finish();
        }
    }

    public void changeBtnBg(String screenNumber) {
        switch (screenNumber) {
            case "0":
                meetingLlMidAuto.setImageResource(R.drawable.fpauto_checked);
                meetingLlMid1.setImageResource(R.drawable.fp1);
                meetingLlMid2.setImageResource(R.drawable.fp7_1);
                meetingLlMid3.setImageResource(R.drawable.fp8_2);
                meetingLlMid4.setImageResource(R.drawable.fp12_1);
                break;

            case "1":
                meetingLlMidAuto.setImageResource(R.drawable.fpauto);
                meetingLlMid1.setImageResource(R.drawable.fp1a);
                meetingLlMid2.setImageResource(R.drawable.fp7_1);
                meetingLlMid3.setImageResource(R.drawable.fp8_2);
                meetingLlMid4.setImageResource(R.drawable.fp12_1);
                break;

            case "11":
                meetingLlMidAuto.setImageResource(R.drawable.fpauto);
                meetingLlMid1.setImageResource(R.drawable.fp1);
                meetingLlMid2.setImageResource(R.drawable.fp7_1a);
                meetingLlMid3.setImageResource(R.drawable.fp8_2);
                meetingLlMid4.setImageResource(R.drawable.fp12_1);
                break;

            case "12":
                meetingLlMidAuto.setImageResource(R.drawable.fpauto);
                meetingLlMid1.setImageResource(R.drawable.fp1);
                meetingLlMid2.setImageResource(R.drawable.fp7_1);
                meetingLlMid3.setImageResource(R.drawable.fp8_2a);
                meetingLlMid4.setImageResource(R.drawable.fp12_1);
                break;

            case "13":
                meetingLlMidAuto.setImageResource(R.drawable.fpauto);
                meetingLlMid1.setImageResource(R.drawable.fp1);
                meetingLlMid2.setImageResource(R.drawable.fp7_1);
                meetingLlMid3.setImageResource(R.drawable.fp8_2);
                meetingLlMid4.setImageResource(R.drawable.fp12_1a);
                break;

            case "8":
            case "10":
                meetingLlMid1.setImageResource(R.drawable.fp1);
                meetingLlMid2.setImageResource(R.drawable.fp7_1);
                meetingLlMid3.setImageResource(R.drawable.fp8_2);
                meetingLlMid4.setImageResource(R.drawable.fp12_1);
                break;
        }
    }

    private void StartSendVoice() {
        Log.i(TAG, "Start Send Voice. jytimes is " + jytimes + ",need open is " + needMyMicOpen);
        if (netMeetingType != 3 && needMyMicOpen) {
            LogUtils.i(TAG, "no need close voice.");
            return;
        }
        if (meetingTvJyImage != null) {
            meetingTvJyImage.setImageResource(R.drawable.meeting_jingyin_g);
        }
        BridgeControl.ConferenceSpeakerReceive(1);
        jytimes = true;
        LogUtils.e("StartSendVoice", "utitleStr =" + uTitleStr);
    }

    private void StopSendVoice() {
        Log.i(TAG, "Stop Send Voice. jytimes is " + jytimes + ",need open is " + needMyMicOpen);
        if (netMeetingType != 3 && !needMyMicOpen) {
            LogUtils.i(TAG, "no need open voice.");
            return;
        }
        if (meetingTvJyImage != null) {
            meetingTvJyImage.setImageResource(R.drawable.meeting_jingyin);
        }
        BridgeControl.ConferenceSpeakerReceive(0);
        jytimes = false;
    }

    private void StartSendVideo() {
        Log.i(TAG, "Start Send Video.");
        left1 = true;
        cameraIv.setImageResource(R.drawable.meeting_icon_camera_close);
        BridgeControl.ConferenceCameraSend(1);
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setStopSpfs(true);
        }
    }

    private void StopSendVideo() {
        Log.i(TAG, "Stop Send Video.");
        left1 = false;
        cameraIv.setImageResource(R.drawable.meeting_icon_camera_open);
        BridgeControl.ConferenceCameraSend(0);
        BridgeControl.ConferenceFrame();
        if (mCameraSurfaceTextureListener != null) {
            mCameraSurfaceTextureListener.setStopSpfs(false);
        }
    }

    private void TouchScreenWidgtStrategy() {
        Log.i(TAG, "Touch screen once.Widgt state change apply " + times + ".");
        if (times || netSignalDisplay.getVisibility() == View.VISIBLE) {
            HiddenAllWidgt();
        } else {
            ShowAllWidgt();
        }
    }

    private void HiddenAllWidgt() {
        Log.i(TAG, "Hidden all widgt. netMeetingType is " + netMeetingType);
        leftll = false;
        times = false;
        if (netMeetingType != 3) {  // 会议模式
            if (llEnd != null) {
                llEnd.setVisibility(View.GONE);
            }
            if (meetingLlTop != null) {
                meetingLlTop.setVisibility(View.GONE);
                netSignalDisplay.setVisibility(View.GONE);
            }
            if (meetingTvQh != null) {
                meetingTvQh.setVisibility(View.GONE);
            }
            if (meetingTvGd != null) {
                meetingTvGd.setVisibility(View.GONE);
            }
            if (llMeetingSharing != null) {
                llMeetingSharing.setVisibility(View.GONE);
            }
            if (meetingLlLeft != null) {
                meetingLlLeft.setVisibility(View.GONE);
            }
            /*
            if (meetPopuWindow != null) {
                meetPopuWindow.dismiss();
            }
            */
            meetingTvFsImage.setImageResource(R.drawable.meeting_more);
            meetShareIcon.setImageResource(R.drawable.shareicon);
            if (netSignalDisplay != null) {
                netSignalDisplay.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(mHostMan) && !TextUtils.isEmpty(uTitleStr)) {
                if (mHostMan.equals(uTitleStr)) {  // 分屏模式控件
                    meetingLlMid.setVisibility(View.GONE);
                    bdtimes = false;
                }
            }

        } else {  // 点对点模式
            if (llEnd != null) {
                llEnd.setVisibility(View.GONE);
            }
            if (meetingLlTop != null) {
                meetingLlTop.setVisibility(View.GONE);
            }
            if (meetingTvQh != null) {
                meetingTvQh.setVisibility(View.GONE);
            }
            if (meetingTvGd != null) {
                meetingTvGd.setVisibility(View.GONE);
            }
            if (meetingLlLeft != null) {
                meetingLlLeft.setVisibility(View.GONE);
            }
            if (meetingTvRy != null) {
                meetingTvRy.setVisibility(View.GONE);
            }
            if (meetingTvYq != null) {
                meetingTvYq.setVisibility(View.GONE);
            }
            if (meetingTvRw != null) {
                meetingTvRw.setVisibility(View.GONE);
            }
            if (meetingTvPicture != null) {
                meetingTvPicture.setVisibility(View.GONE);
            }
        }
    }

    private void ShowAllWidgt() {
        Log.i(TAG, "Show all widgt. netMeetingType is " + netMeetingType);
        times = true;
        if (netMeetingType != 3) {// 会议模式
            if (llEnd != null) {
                llEnd.setVisibility(View.VISIBLE);
            }
            if (meetingLlTop != null) {
                meetingLlTop.setVisibility(View.VISIBLE);
            }
            if (meetingTvGd != null) {
                meetingTvGd.setVisibility(View.VISIBLE);
            }
            if (meetingTvQh != null) {
                if (isTransferPersonPermission) {
                    meetingTvQh.setVisibility(View.GONE);
                } else {
                    meetingTvQh.setVisibility(View.VISIBLE);
                }
            }
            if (llMeetingSharing != null && llMeetingSharing.getVisibility() == View.VISIBLE) {
                meetingTvQh.setVisibility(View.VISIBLE);
            }
            if (meetingRecoder != null) {
                meetingRecoder.setVisibility(View.VISIBLE);
            }
            if (meetingInfoImageview != null) {
                meetingInfoImageview.setVisibility(View.VISIBLE);
            }
        } else {  // 点对点模式
            if (llEnd != null) {
                llEnd.setVisibility(View.VISIBLE);
            }
            if (meetingLlTop != null) {
                meetingLlTop.setVisibility(View.VISIBLE);
            }
            if (meetingTvQh != null) {
                meetingTvQh.setVisibility(View.VISIBLE);
            }
            if (meetingTvGd != null) {
                meetingTvGd.setVisibility(View.VISIBLE);
            }
            if (meetingTvRw != null) {
                meetingTvRw.setVisibility(View.GONE);
            }
            if (meetingTvRy != null) {
                meetingTvRy.setVisibility(View.GONE);
            }
            if (meetingTvYq != null) {
                meetingTvYq.setVisibility(View.GONE);
            }
            if (meetingTvPicture != null) {
                meetingTvPicture.setVisibility(View.GONE);
            }
            if (meetingInfoImageview != null) {
                meetingInfoImageview.setVisibility(View.GONE);
            }
            if (meetingInviteLl != null) {
                meetingInviteLl.setVisibility(View.GONE);
            }
            if (meetingRecoder != null) {
                meetingRecoder.setVisibility(View.GONE);
            }
            if (shareLayout != null) {
                shareLayout.setVisibility(View.GONE);
                Log.i("desktopdisappear", "shareLayout disappear GONE position is 12");
            }
            //更多 会议管理 会议纪要 画面布局 隐藏
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 8092) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                UiUtils.showToast(getResources().getString(R.string.acitvity_share_message_no_authority));
            }
        }
    }

    private void processMicDataHeader() {
        if (TextUtils.isEmpty(uTitleStr)) {
            return;
        }
        String[] viewTagList = mediaStatusTag.split(",");
        boolean findMyOnScreen = false;
        boolean myMicOpen = false;
        for (int i = 0 ; i < viewTagList.length ; i++) {
            String[] viewTag = viewTagList[i].split(" ");
            if (viewTag.length < 5) {
                continue;
            }
            if (viewTag[0].equals(uTitleStr)) {
                findMyOnScreen= true;
                myMicOpen = viewTag[1].equals("0");
            }
        }
        needMyMicOpen = findMyOnScreen && myMicOpen;
        if (findMyOnScreen) {
            if (myMicOpen) {
                StopSendVoice();
            } else {
                StartSendVoice();
            }
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getNumber().equals(uTitleStr)) {
                    if(myMicOpen) {
                        resultList.get(i).setSpeak("0");
                    } else {
                        resultList.get(i).setSpeak("1");
                    }
                }
            }
            if (hostAdapter != null) {
                hostAdapter.setDataSourceLists(resultList, netMeetingId);
            }
            if (joinAdapter != null) {
                joinAdapter.setDataSourceLists(resultList, netMeetingId);
            }
        }
    }

    /**   |--号码[0]-发送声音[1]-发送视频[2]-分屏位置[3]-主辅流[4]--|
     *    视频流头部信息       是否在屏上       是否打开摄像头  执行操作后摄像头状态
     *    A 0 1 0 0,B 0 0 0 0        Y          Y           Y
     *    A 0 0 0 0,B 0 0 0 0        Y          N           N
     *    B 0 0 0 0                  N          -           N
     * */
    private void processVideoDataHeader() {
        if (TextUtils.isEmpty(uTitleStr)) {
            return;
        }
        String[] viewTagList = mediaStatusTag.split(",");
        needMyVideoOpen = false;
        for (int i = 0 ; i < viewTagList.length ; i++) {
            String[] viewTag = viewTagList[i].split(" ");
            if (viewTag.length < 5) {
                continue;
            }
            if (viewTag[0].equals(uTitleStr)) {
                needMyVideoOpen = viewTag[2].equals("0");
                //mainStreamScreenPosition = viewTag[3];
            }
        }
        //Log.d(TAG,"stream header handle, main stream open is " + findMainStreamOpen + " ,sub stream open is " + findSubStreamOpen + ",needMyVideoOpen is " + needMyVideoOpen + ",isStopSendVideo is " + isStopSendVideo);
        StartOrStopSendVideo();
    }

    public class MeetingVideoDataHandler extends Handler {
        private MeetingVideoDataHandler() {}

        public MeetingVideoDataHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case VIDEO_STREAM_HEADER_MERGE_MODE_WAHT:
                    Log.i(TAG, "stream header handle, merge mode msg obj is " + (int) (msg.obj));

                    realScreenStatus = (int) (msg.obj);
                    processVideoDataHeader();
                    break;

                case VIDEO_STREAM_HEADER_CONTENT_WAHT:
                    Log.i(TAG, "stream header handle, contents msg obj is " + (String) (msg.obj));

                    String mTag = (String) (msg.obj);
                    if (mTag != null) {
                        mediaStatusTag = mTag;
                    }
                    processVideoDataHeader();
                    processMicDataHeader();
                    break;

                case SHARED_STREAM_HEADER_CONTENT_WAHT:
                    Log.i(TAG, "shared header handle, contents msg obj is " + (String) (msg.obj));

                    String mSharedTag = (String) (msg.obj);
                    if (mSharedTag != null) {
                        mediaStatusTag = mSharedTag;
                    }
                    processVideoDataHeader();
                    processMicDataHeader();
                    break;
            }
        }

    }

    private class MeetingScreenRecordCallback implements ScreenRecordCallback {
        @Override
        public void screenRecordRunning() {
            if (meetingRecoderImageview != null) {
                meetingRecoderImageview.setImageResource(R.drawable.video_record_start);
            }
            if (meetingRecoderTextView != null) {
                meetingRecoderTextView.setText(R.string.activity_meeting_usb_record_open);
            }
            meetingFrameCaptivate = true;
        }

        @Override
        public void screenRecordStoped() {
            if (meetingRecoderImageview != null) {
                meetingRecoderImageview.setImageResource(R.drawable.video_record_stop);
            }
            if (meetingRecoderTextView != null) {
                meetingRecoderTextView.setText(R.string.activity_meeting_usb_record_close);
            }
            meetingFrameCaptivate = false;
        }

        @Override
        public void usbDeviceUnMounted() {
            UiUtils.showToastLong(getString(R.string.activity_meeting_usb_record_plugout));
        }

        @Override
        public void usbDeviceStorageFull() {
            UiUtils.showToastLong(getString(R.string.activity_meeting_usb_record_check_full_storaage));
        }

        @Override
        public void showHintMessage(String message) {
            if (!TextUtils.isEmpty(message)) {
                UiUtils.showToastLong(message);
            }
        }
    }

    /**
     * 成员列表
     * */
    public List<phoneinfo> IdcMediaGetMemberList () {
        return resultList;
    }

    /**
     * 屏幕
     * */
    public void IdcMediaPrepareDesktop () {
        if (NotificationsPermissionUtils.isNotificationEnabled(MeetingActivity.this)) {
            checkCaptureIntent();
        } else {
            PopupDialog.create(MeetingActivity.this, getString(R.string.title_5), getString(R.string.dialog_open_share_notification), getString(R.string.confirm),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            NotificationsPermissionUtils.requestNotify(MeetingActivity.this);
                        }
                    }, null, null, true, true, true).show();
            GlobalUtils.setLocalMeetingStatus(true);
        }
    }

    /**
     * 画板
     * */
    public void IdcMediaPrepareBroad (int type) {
        int width1 = getWindowManager().getDefaultDisplay().getWidth();
        int height1 = getWindowManager().getDefaultDisplay().getHeight();
        if (type == 1) {
            if (!isMeetingPadShare) {
                GlobalUtils.setShareType(1);
                BridgeControl.desktopSharingControlPrepare( width1, height1);
                meetShareIcon.setImageResource(R.drawable.shareicon);
                sharePositonText.setText(getString(R.string.meeting_activity_exit_share));
            }
        } else if (type == 2) {
            if (!isMeetingPadShare) {
                BridgeControl.desktopSharingControlStart( width1, height1);
                isMeetingPadShare = true;
                setTagging(true);
            }
        }
    }

    /**
     *  取消我的发起
     * */
    public void IdcMediaMineCancel () {
        if (GlobalUtils.getShareType(true) == 1) {
            BridgeControl.stopWhiteBoard();
            GlobalUtils.setShareType(0);
            isMeetingShared = false;
            setTagging(false);
            stopShareDesk();
            isMeetingPadShare = false;
        } else if (GlobalUtils.getShareType(true) == 2) {
            UiUtils.showToast(getString(R.string.meeting_activity_desktop_sharing_prepare));
        } else if (GlobalUtils.getShareType(true) == 3) {
            // 结束共享桌面
            endDeskShare();
            meetShareIcon.setImageResource(R.drawable.shareicon);
        } else {
            if (llMeetingSharing.getVisibility() == View.GONE) {
                int[] locations = new int[2];
                shareLayout.getLocationInWindow(locations);
                float xOffset = (shareLayout.getWidth() - UiUtils.dip2px(MeetingActivity.this, 100)) / 2;
                float marginX = locations[0];
                LogUtils.d("getLocationInWindow", "locations[0] =" + locations[0]
                        + "," + "shareLayout.getWidth() =" + shareLayout.getWidth() + "xOffset =" + xOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) llMeetingSharing.getLayoutParams();
                params.setMarginStart((int) marginX);
                llMeetingSharing.setLayoutParams(params);
                llMeetingSharing.setVisibility(View.VISIBLE);
                meetShareIcon.setImageResource(R.drawable.shareicon_check);
            } else {
                meetShareIcon.setImageResource(R.drawable.shareicon);
                llMeetingSharing.setVisibility(View.GONE);
            }
        }
    }

    /**
     *  切换摄像头
     * */
    private void IdcMediaChangeCamera() {
        if (qhtimes) {
            // CameraController.change(MeetingActivity.this, previewCallback);
            if (mCameraSurfaceTextureListener != null) {
                mCameraSurfaceTextureListener.change(MeetingActivity.this);
            }
            qhtimes = false;
        } else {
            // CameraController.change(MeetingActivity.this, previewCallback);
            if (mCameraSurfaceTextureListener != null) {
                mCameraSurfaceTextureListener.change(MeetingActivity.this);
            }
            qhtimes = true;
        }
        if (meetingLlMid.getVisibility() == View.VISIBLE) {
            meetingLlMid.setVisibility(View.GONE);
            bdtimes = false;
        }
    }

    /**
     *  举手
     * */
    private void IdcMediaRaiseHands() {
        LogUtils.e(TAG, "RaiseHand" + " meetingNumber:" + meetingNumber + ",utitleStr" + uTitleStr);
        if (!jstimes) {
            applyForMeeting(R.drawable.meeting_show_handdown, getString(R.string.meeting_activity_cancel_show_hands), true, 1);
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.what = APPLY_FOR_MEETINGCODE;
                handler.sendMessageDelayed(message, 30000);
            }
        } else {
            applyForMeeting(R.drawable.meeting_show_handup, getString(R.string.ask_speech), false, 0);
        }
    }

    /**
     *  录制
     * */
    private void idcMediaRecord() {
        if (screenRecordManager == null) {
            Log.d(TAG, "screen record create a new manager.");
            screenRecordManager = new ScreenRecordManager();
            screenRecordManager.init(this);
        }
        if (screenRecordManager.getCallback() == null || meetingScreenRecordCallback == null) {
            if (meetingScreenRecordCallback == null) {
                meetingScreenRecordCallback = new MeetingScreenRecordCallback();
            }
            screenRecordManager.setCallback(meetingScreenRecordCallback);
            Log.d(TAG, "screen record manager set a new callback.");
        }
        if (!meetingFrameCaptivate) {
            screenRecordManager.startRecord();
        } else {
            screenRecordManager.stopRecord(false);
            if (screenRecordSaveProgressDialog == null && !isFinishing()) {
                screenRecordSaveProgressDialog = new ProgressDialog(MeetingActivity.this);
                screenRecordSaveProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                screenRecordSaveProgressDialog.setCancelable(false);
                screenRecordSaveProgressDialog.setTitle(getString(R.string.meeting_activity_saving_screen_record_file));
                screenRecordSaveProgressDialog.setProgressNumberFormat(0 + getString(R.string.meeting_activity_hint_record_time_start) + getString(R.string.meeting_activity_hint_record_time_end));
                screenRecordSaveProgressDialog.setMax(80);
                screenRecordSaveProgressDialog.setProgress(0);
                screenRecordSaveProgressDialog.show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        int progerss = 0;
                        while (progerss <= 80) {
                            if (screenRecordSaveProgressDialog != null && screenRecordSaveProgressDialog.isShowing()) {
                                screenRecordSaveProgressDialog.setProgress(progerss);
                                screenRecordSaveProgressDialog.setProgressNumberFormat(progerss/10 + getString(R.string.meeting_activity_hint_record_time_start) + getString(R.string.meeting_activity_hint_record_time_end));
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                break;
                            }
                            progerss ++;
                        }
                        if (screenRecordSaveProgressDialog != null && !isFinishing()) {
                            screenRecordSaveProgressDialog.dismiss();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UiUtils.showToastLong(getString(R.string.activity_meeting_usb_record_end));
                                }
                            });
                        }
                        screenRecordSaveProgressDialog = null;
                    }
                }.start();
            }
        }
    }
}
