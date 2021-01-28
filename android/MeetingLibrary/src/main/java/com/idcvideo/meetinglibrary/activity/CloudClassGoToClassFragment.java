package com.idcvideo.meetinglibrary.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.idcvideo.meetinglibrary.R;
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
public class CloudClassGoToClassFragment extends Fragment implements View.OnClickListener {
    private CloudClassStartBean mCloudClassStartBean;
    private String mStartTime;
    private String mEndTime;
    private String mTeacherName;
    private String mContent;
    private String name;
    private TextView times, names, subject, content;

    public static CloudClassGoToClassFragment newInstance(CloudClassStartBean mCloudClassStartBean) {
        CloudClassGoToClassFragment fragment = new CloudClassGoToClassFragment();
        Bundle args = new Bundle();
        args.putSerializable("mCloudClassStartBean", mCloudClassStartBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud_class_go_to_class_layout, container, false);
        times = view.findViewById(R.id.details_time_tv);
        names = view.findViewById(R.id.details_name_tv);
        subject = view.findViewById(R.id.details_subject_tv);
        content = view.findViewById(R.id.details_content_tv);
        ImageView backView = view.findViewById(R.id.cloud_class_go_to_back_iv);
        backView.setOnClickListener(this);
        initView();
        return view;
    }

    private void initView() {
        String time = SPUtil.get(getContext(), "time", "").toString();
        String teacher = SPUtil.get(getContext(), "teacher", "").toString();
        String project = SPUtil.get(getContext(), "projectName", "").toString();
        String projectContent = SPUtil.get(getContext(), "projectContent", "").toString();
        subject.setText(project);
        times.setText(time);
        names.setText(teacher);
        content.setText(projectContent);

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cloud_class_go_to_back_iv) {
            getActivity().finish();
        }
    }
}
