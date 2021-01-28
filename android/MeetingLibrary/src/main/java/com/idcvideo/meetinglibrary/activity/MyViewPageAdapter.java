package com.idcvideo.meetinglibrary.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

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
 * | 代码修改:（ljp - 8:55 AM）
 * +----------------------------------------------------------------------
 **/
public class MyViewPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList;

    public MyViewPageAdapter(FragmentManager fragment, List<Fragment> fragmentList) {
        super(fragment);
        this.mFragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return ListUtils.getSize(mFragmentList);
    }
}
