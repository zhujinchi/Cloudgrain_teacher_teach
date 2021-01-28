package com.idcvideo.meetinglibrary.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassAttendBean;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassStartBean;


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
 * | 代码修改:（ljp - 9:08 AM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassAttendDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = CloudClassAttendDetailsFragment.class.getSimpleName();
    private CloudClassAttendDetailsAdapter cloudClassAttendDetailsAdapter;
    private String classId;
    private RecyclerView cloudClassAttendDetailsRv;

    public static CloudClassAttendDetailsFragment newInstance(CloudClassStartBean mCloudClassStartBean) {
        CloudClassAttendDetailsFragment fragment = new CloudClassAttendDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("mCloudClassStartBean", mCloudClassStartBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud_class_attend_details_layout, container, false);
        cloudClassAttendDetailsRv = view.findViewById(R.id.cloud_class_attend_details_rv);
        ImageView backIv = view.findViewById(R.id.cloud_class_back_iv);
        backIv.setOnClickListener(this);
        cloudClassAttendDetailsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        cloudClassAttendDetailsRv.addItemDecoration(new SpacesItemDecoration(20));
        cloudClassAttendDetailsAdapter = new CloudClassAttendDetailsAdapter(onItemClickListener);
        cloudClassAttendDetailsRv.setAdapter(cloudClassAttendDetailsAdapter);
        initView();
        return view;
    }

    private void initView() {
        CloudClassStartBean mCloudClassStartBean = (CloudClassStartBean) SPBeanUtils.getObject(getContext(), "mCloudClassStartBean");
        if (!TextUtils.isEmpty(classId)) {
            classId = mCloudClassStartBean.getClassId();
        }
    }

    /**
     * 点击条目监听事件
     */
    OnItemClickListener onItemClickListener = new OnItemClickListener<CloudClassAttendBean.StudentBean>() {
        @Override
        public void onClick(View view, CloudClassAttendBean.StudentBean mCloudClassPayBean, int position) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cloud_class_back_iv) {
            getActivity().finish();
        }
    }
}
