package com.idcvideo.meetinglibrary.activity;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassAttendBean;

import java.util.List;
import java.util.Objects;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.assignment.adapter
 * +----------------------------------------------------------------------
 * | 功能描述:云课的班级详情适配器
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/12
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 9:03 AM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassAttendDetailsAdapter extends RecyclerView.Adapter<CloudClassAttendDetailsAdapter.CloudClassViewHolder> {
    private List<CloudClassAttendBean.StudentBean> mCurrentData;
    private OnItemClickListener onItemClickListener;

    public CloudClassAttendDetailsAdapter(OnItemClickListener itemClickCallback) {
        this.onItemClickListener = itemClickCallback;
    }

    @Override
    public CloudClassAttendDetailsAdapter.CloudClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View binding = LayoutInflater.from(parent.getContext()).inflate( R.layout.cloud_class_details_item_layout, parent, false);
        CloudClassViewHolder cloudClassViewHolder=new CloudClassViewHolder(binding);
        return cloudClassViewHolder;
    }

    @Override
    public void onBindViewHolder(CloudClassAttendDetailsAdapter.CloudClassViewHolder holder, int position) {
        CloudClassAttendBean.StudentBean mCloudClassBean = mCurrentData.get(position);

    }


    public void setCloudClassAttendDetailsListData(List<CloudClassAttendBean.StudentBean> newData) {
        if (mCurrentData == null) {
            mCurrentData = newData;
            notifyItemRangeInserted(0, ListUtils.getSize(newData));
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ListUtils.getSize(mCurrentData);
                }

                @Override
                public int getNewListSize() {
                    return ListUtils.getSize(newData);
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCurrentData.get(oldItemPosition).getClassId() == newData.get(newItemPosition).getClassId();
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CloudClassAttendBean.StudentBean cloudClassBean = newData.get(newItemPosition);
                    CloudClassAttendBean.StudentBean oldCloudClassBean = mCurrentData.get(oldItemPosition);
                    return Objects.equals(cloudClassBean.getNickName(), oldCloudClassBean.getNickName())
                            && Objects.equals(cloudClassBean.getStatus(), oldCloudClassBean.getStatus());

                }
            });
            mCurrentData = newData;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemCount() {
        return ListUtils.getSize(mCurrentData);
    }

    protected class CloudClassViewHolder extends RecyclerView.ViewHolder {

        public CloudClassViewHolder(View binding) {
            super(binding);
        }
    }
}
