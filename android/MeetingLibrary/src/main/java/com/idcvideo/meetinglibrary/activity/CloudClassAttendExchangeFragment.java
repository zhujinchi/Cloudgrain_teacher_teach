package com.idcvideo.meetinglibrary.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassExchangeMessageListBean;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassMessageBean;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassStartBean;
import com.idcvideo.meetinglibrary.activity.bean.ExchangeBean;
import com.idcvideo.meetinglibrary.activity.bean.MessageStudentBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/20
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 9:09 AM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassAttendExchangeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CloudClassAttendExchangeFragment.class.getSimpleName();
    private CloudClassAttendExchangeAdapter cloudClassAttendExchangeAdapter;
    private List<CloudClassMessageBean> mList = new ArrayList<>();
    //private UserInfoViewModel mUserInfoViewModel;
    private String accessToken;
    private String classId;
    private EditText cloudClassExchangeEdit;
    private String tempClassId;
    private List<MessageStudentBean.DataBean.StudentBean> studentData;
    public static final int UPDATE = 0x1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    cloudClassAttendExchangeAdapter.setCloudClassListData(mList);
                    cloudClassAttendExchangeAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public static CloudClassAttendExchangeFragment newInstance(CloudClassStartBean mCloudClassStartBean) {
        CloudClassAttendExchangeFragment fragment = new CloudClassAttendExchangeFragment();
        Bundle args = new Bundle();
        args.putSerializable("mCloudClassStartBean", mCloudClassStartBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud_class_attend_exchange_layout, container, false);
        view.findViewById(R.id.cloud_class_exchange_send_tv).setOnClickListener(this);
        RecyclerView cloudClassAttendExchangeRv = view.findViewById(R.id.cloud_class_attend_exchange_rv);
        cloudClassExchangeEdit = view.findViewById(R.id.cloud_class_exchange_edit);
        cloudClassAttendExchangeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        cloudClassAttendExchangeRv.addItemDecoration(new SpacesItemDecoration(20));
        cloudClassAttendExchangeAdapter = new CloudClassAttendExchangeAdapter(onItemClickListener);
        cloudClassAttendExchangeRv.setAdapter(cloudClassAttendExchangeAdapter);
        initView();
        return view;
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getClassInfo();
                String tempAccessToken = SPUtil.get(getContext(), "accessToken", "accessToken").toString();
                String tempClassId = SPUtil.get(getContext(), "classId", "classId").toString();
                WebSocketDemo.getDefault().connect("ws://yundou.skyline.name:18002/immessage/" + tempClassId + "/" + tempAccessToken);
            }
        }).start();
    }


    /**
     * 点击条目监听事件
     */
    OnItemClickListener onItemClickListener = new OnItemClickListener<ExchangeBean>() {
        @Override
        public void onClick(View view, ExchangeBean mExchangeBean, int position) {

        }
    };

    /**
     * 获取学生老师信息
     */
    @SuppressLint("CheckResult")
    private void getClassInfo() {
        String tempAccessToken = SPUtil.get(getContext(), "accessToken", "").toString();
        String tempClassId = SPUtil.get(getContext(), "classId", "").toString();
        String tempClassMainId = SPUtil.get(getContext(), "classMainId", "").toString();
//        String tempAccoun" ";
//        String tempClassId = " ";
        String url = "http://yundou.skyline.name:18002/class/member";
        HashMap hashMap = new HashMap();
        hashMap.put("id", tempClassId);
        HttpClient.getInstance(getContext()).get(tempAccessToken, url, hashMap, new HttpClient.MyCallback() {
            @Override
            public void success(Response res) throws IOException {
                //Log.i("EvaluateInfo==", "EvaluateInfo==" + res.body().string());
                String bodys = res.body().string();
                Log.i("EvaluateInfo==", "EvaluateInfo==" + bodys);
                Gson gson = new Gson();
                MessageStudentBean dataBeans = gson.fromJson(bodys, MessageStudentBean.class);
                if (dataBeans != null) {
                    if (dataBeans != null) {
                        studentData = dataBeans.getData().getStudent();
                        getMessageInfo(studentData);
                    }
                }

            }

            @Override
            public void failed(IOException e) {
                //ToastUtils.showLong(MeetingActivity.this, e.getMessage());
            }
        });

    }


    /**
     * 获取消息列表
     */
    @SuppressLint("CheckResult")
    private void getMessageInfo(List<MessageStudentBean.DataBean.StudentBean> studentData) {
        String tempAccessToken = SPUtil.get(getContext(), "accessToken", "accessToken").toString();
        String tempClassMainId = SPUtil.get(getContext(), "classMainId", "").toString();
        String tempClassId = SPUtil.get(getContext(), "classId", "").toString();
//        String tempAccount = " ";
//        String tempClassId = " ";
        String url = "http://yundou.skyline.name:18002/imMessage/list";
        HashMap hashMap = new HashMap();
        hashMap.put("pageNumber", "1");
        hashMap.put("pageSize", "1000000");
        hashMap.put("classId", tempClassId);
        HttpClient.getInstance(getContext()).post(tempAccessToken, url, hashMap, new HttpClient.MyCallback() {
            @Override
            public void success(Response res) throws IOException {
                //Log.i("EvaluateInfo==", "EvaluateInfo==" + res.body().string());
                String bodys = res.body().string();
                Log.i("EvaluateInfo==", "EvaluateInfo==" + bodys);
                Gson gson = new Gson();
                CloudClassExchangeMessageListBean dataBean = gson.fromJson(bodys, CloudClassExchangeMessageListBean.class);
                if (dataBean != null) {
                    mList.clear();
                    if (!ListUtils.isEmpty(studentData)) {
                        for (int i = 0; i < studentData.size(); i++) {
                            String studentClassId = studentData.get(i).getClassId();
                            String studentName = studentData.get(i).getNickName();
                            List<CloudClassExchangeMessageListBean.DataBean.RecordsBean> data = dataBean.getData().getRecords();
                            if(!ListUtils.isEmpty(data)){
                                for (int j = 0; j < data.size(); j++) {
                                    CloudClassMessageBean cloudClassMessageBean = new CloudClassMessageBean();
                                    String mClassId = data.get(j).getClassId();
                                    String mMessage = data.get(j).getMessage();
                                    if (TextUtils.equals(studentClassId, mClassId)) {
                                        cloudClassMessageBean.setClassId(studentName);
                                        cloudClassMessageBean.setMessage(mMessage);
                                        mList.add(cloudClassMessageBean);
                                    }
                                }
                            }
                        }
                        Message message = handler.obtainMessage();
                        message.what = UPDATE;
                        message.obj = dataBean;
                        if (handler != null) {
                            handler.sendMessage(message);
                        }
                    }
                }

            }

            @Override
            public void failed(IOException e) {
                //ToastUtils.showLong(MeetingActivity.this, e.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cloud_class_exchange_send_tv) {
            tempClassId = SPUtil.get(getContext(), "classId", "classId").toString();
            String message = "{classId" + ": " + tempClassId + ",sender:" + "0," + "receiver:" + "3,message:" + cloudClassExchangeEdit.getText().toString().trim() + "}";
            WebSocketDemo.getDefault().sendMessage(message);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<CloudClassMessageBean> event) {
        if (!ListUtils.isEmpty(studentData)) {
            cloudClassExchangeEdit.setText("");
            getMessageInfo(studentData);
        }

    }
}
