package com.idcvideo.meetinglibrary.activity;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.utils
 * +----------------------------------------------------------------------
 * | 功能描述:recycleview设置间距
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/6
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 16:45）
 * +----------------------------------------------------------------------
 **/
public class SpacesItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }
}
