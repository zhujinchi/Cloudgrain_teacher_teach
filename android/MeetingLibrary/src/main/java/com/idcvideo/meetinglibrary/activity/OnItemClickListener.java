package com.idcvideo.meetinglibrary.activity;

import android.view.View;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.interf
 * +----------------------------------------------------------------------
 * | 功能描述:点击事件接口
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/6
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 15:47）
 * +----------------------------------------------------------------------
 **/
public class OnItemClickListener<T> {
    /**
     * 单击
     *
     * @param view
     * @return
     */

    public void onClick(View view, T t) {
    }

    public void onClick(View view, T t, int position) {
    }

    public void onDeleteClick(View view, T t, int position, int childPosition) {
    }
}
