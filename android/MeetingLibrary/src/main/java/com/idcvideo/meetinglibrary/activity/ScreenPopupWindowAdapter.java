package com.idcvideo.meetinglibrary.activity;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idcvideo.meetinglibrary.R;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity
 * +----------------------------------------------------------------------
 * | 功能描述:
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
public class ScreenPopupWindowAdapter extends RecyclerView.Adapter<ScreenPopupWindowAdapter.ViewHolder> {
    private Context mContext;
    private List<ScreenPopupWindowTeacherListBean.DataBean.RecordsBean> mCurrentData;
    private OnItemClickListener onItemClickListener;
    private OnItemClickListener onItemCollectClickListener;
    private int currentPosition;


    public ScreenPopupWindowAdapter(Context context) {
        this.mContext = context;
        //this.onItemClickListener = itemClickCallback;
        //this.onItemCollectClickListener=itemCollectClickCallback;
    }

    @Override
    public ScreenPopupWindowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.screen_popup_window_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScreenPopupWindowAdapter.ViewHolder holder, int position) {
        if (holder != null) {
            TextView dateTime = holder.itemView.findViewById(R.id.screen_popup_tv);
            TextView content = holder.itemView.findViewById(R.id.screen_popup_content);
            String date = mCurrentData.get(position).getAppraiseDate();
            String context = mCurrentData.get(position).getTeacherAppraise();
            String time = StrToDate(date);
            dateTime.setText(time+"：");
            content.setText(context);
        }
    }

    public void setCloudClassListData(List<ScreenPopupWindowTeacherListBean.DataBean.RecordsBean> newData) {
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
                    ScreenPopupWindowTeacherListBean.DataBean.RecordsBean cloudClassBean = newData.get(newItemPosition);
                    ScreenPopupWindowTeacherListBean.DataBean.RecordsBean oldCloudClassBean = mCurrentData.get(oldItemPosition);
                    return Objects.equals(cloudClassBean.getAppraiseDate(), oldCloudClassBean.getAppraiseDate())
                            && Objects.equals(cloudClassBean.getTeacherAppraise(), oldCloudClassBean.getTeacherAppraise());

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

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static String StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = null;
        try {
            Date date = format.parse(str);
            SimpleDateFormat format1 = new SimpleDateFormat("MM月dd日");
            time = format1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
