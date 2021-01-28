package com.idcvideo.meetinglibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.AssignmentListBean;

import java.io.IOException;
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
public class CloudClassAttendSubmitJobFragment extends Fragment {
    private RecyclerView recyclerView;
    public static final int UPDATE = 0x1;
    private AssignmentListAdapter assignmentListAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    AssignmentListBean mAssignment = (AssignmentListBean) msg.obj;
                    List<AssignmentListBean.DataBean> list = mAssignment.getData();
                    assignmentListAdapter.setAssignmentListData(list);
                    assignmentListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud_class_attend_submit_job_layout, container, false);
        recyclerView = view.findViewById(R.id.assignment_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assignmentListAdapter = new AssignmentListAdapter(getContext(), onItemClick);
        recyclerView.setAdapter(assignmentListAdapter);
        initView();
        return view;
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getInfo();
            }
        }).start();
    }

    OnItemClickListener onItemClick = new OnItemClickListener<List<AssignmentListBean.DataBean>>() {
        @Override
        public void onClick(View view, List<AssignmentListBean.DataBean> dataBean, int position) {
            super.onClick(view, dataBean, position);
            Intent intent = new Intent(getContext(), PictureWatchActivity.class);
            intent.putExtra("id", dataBean.get(position).getId());
            getContext().startActivity(intent);
        }
    };

    /**
     * 获取作业
     */
    private void getInfo() {
        String tempAccessToken = SPUtil.get(getContext(), "accessToken", "accessToken").toString();
        String tempClassId = SPUtil.get(getContext(), "classId", "classId").toString();
//        String tempAccount = " ";
//        String tempClassId = " ";
        String url = "http://yundou.skyline.name:18002/taskFinishRec/taskNotice";
        HashMap hashMap = new HashMap();
        hashMap.put("coursesId", tempClassId);
        HttpClient.getInstance(getContext()).get(tempAccessToken, url, hashMap, new HttpClient.MyCallback() {
            @Override
            public void success(Response res) throws IOException {
                //Log.i("EvaluateInfo==", "EvaluateInfo==" + res.body().string());
                String bodys = res.body().string();
                Log.i("EvaluateInfo==", "EvaluateInfo==" + bodys);
                Gson gson = new Gson();
                AssignmentListBean dataBean = gson.fromJson(bodys, AssignmentListBean.class);
                if (dataBean != null) {
                    Message message = handler.obtainMessage();
                    message.what = UPDATE;
                    message.obj = dataBean;
                    if (handler != null) {
                        handler.sendMessage(message);
                    }
                }

            }

            @Override
            public void failed(IOException e) {
                //ToastUtils.showLong(MeetingActivity.this, e.getMessage());
            }
        });
    }

}
