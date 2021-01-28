package com.idcvideo.meetinglibrary.activity;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.AssignmentListBean;

import java.util.List;
import java.util.Objects;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity
 * +----------------------------------------------------------------------
 * | 功能描述:作业列表
 * +----------------------------------------------------------------------
 * | 时　　间:2021/1/13
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 3:18 PM）
 * +----------------------------------------------------------------------
 **/
public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.ViewHolder> {
    private Context mContext;
    private List<AssignmentListBean.DataBean> mCurrentData;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onItemCollectClickListener;
    private int currentPosition;


    public AssignmentListAdapter(Context context, OnItemClickListener itemClickCallback) {
        this.mContext = context;
        this.onItemClickListener = itemClickCallback;
        //this.onItemCollectClickListener=itemCollectClickCallback;
    }

    @Override
    public AssignmentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.assignment_list_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AssignmentListAdapter.ViewHolder holder, int position) {
        if (holder != null) {
            TextView assignmentStudent = holder.itemView.findViewById(R.id.assignment_student_tv);
            TextView look = holder.itemView.findViewById(R.id.assignment_look_tv);
            String name = mCurrentData.get(position).getStudentName();
            String time = mCurrentData.get(position).getStartTime();
            String subject = mCurrentData.get(position).getTaskSubjectId();
            switch (subject) {
                case "101":
                    subject = "语文";
                    break;
                case "102":
                    subject = "数学";
                    break;
                case "103":
                    subject = "英语";
                    break;
                case "104":
                    subject = "物理";
                    break;
                case "105":
                    subject = "化学";
                    break;
                case "106":
                    subject = "生物";
                    break;
                default:
                    break;
            }

            //String context = mCurrentData.get(position).getTeacherAppraise();
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(v, mCurrentData,position);
                    }
                }
            });
            assignmentStudent.setText(name +" "+time +" 提交了"+subject+"作业");
        }
    }

    public void setAssignmentListData(List<AssignmentListBean.DataBean> newData) {
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
                    return mCurrentData.get(oldItemPosition).getId().equals(newData.get(newItemPosition).getId());
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    AssignmentListBean.DataBean cloudClassBean = newData.get(newItemPosition);
                    AssignmentListBean.DataBean oldCloudClassBean = mCurrentData.get(oldItemPosition);
                    return Objects.equals(cloudClassBean.getId(), oldCloudClassBean.getId())
                            && Objects.equals(cloudClassBean.getStudentName(), oldCloudClassBean.getStudentName());

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

    protected class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
