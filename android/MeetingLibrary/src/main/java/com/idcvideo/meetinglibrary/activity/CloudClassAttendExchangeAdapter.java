package com.idcvideo.meetinglibrary.activity;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;



import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassMessageBean;

import java.util.List;
import java.util.Objects;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.assignment.adapter
 * +----------------------------------------------------------------------
 * | 功能描述:云课的交流区适配器
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
public class CloudClassAttendExchangeAdapter extends RecyclerView.Adapter<CloudClassAttendExchangeAdapter.CloudClassViewHolder> {
    private List<CloudClassMessageBean> mCurrentData;
    private OnItemClickListener onItemClickListener;

    public CloudClassAttendExchangeAdapter(OnItemClickListener itemClickCallback) {
        this.onItemClickListener = itemClickCallback;
    }

    @Override
    public CloudClassAttendExchangeAdapter.CloudClassViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View binding = LayoutInflater.from(parent.getContext()).inflate( R.layout.clound_class_attend_exchange_item_layout, parent, false);
        CloudClassViewHolder cloudClassViewHolder=new CloudClassViewHolder(binding);
        return cloudClassViewHolder;
    }

    @Override
    public void onBindViewHolder(CloudClassAttendExchangeAdapter.CloudClassViewHolder holder, int position) {

        CloudClassMessageBean exchangeBean = mCurrentData.get(position);
        TextView nameView = holder.itemView.findViewById(R.id.cloud_class_attend_exchange_item_name_tv);
        TextView contentView = holder.itemView.findViewById(R.id.cloud_class_attend_exchange_item_content_tv);
        String name = exchangeBean.getClassId();
        String content = exchangeBean.getMessage();
        nameView.setText(name+"：");
        contentView.setText(content);
    }


    public void setCloudClassListData(List<CloudClassMessageBean> newData) {
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
                    return mCurrentData.get(oldItemPosition).getClassId().equals(newData.get(newItemPosition).getClassId());
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    CloudClassMessageBean exchangeBean = newData.get(newItemPosition);
                   CloudClassMessageBean oldExchangeBean = mCurrentData.get(oldItemPosition);
                    return Objects.equals(exchangeBean.getClassId(), oldExchangeBean.getClassId())
                            && Objects.equals(exchangeBean.getMessage(), oldExchangeBean.getMessage());

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
