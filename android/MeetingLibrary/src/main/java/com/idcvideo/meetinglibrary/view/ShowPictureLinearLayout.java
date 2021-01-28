package com.idcvideo.meetinglibrary.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class ShowPictureLinearLayout extends LinearLayout {

    private int mViewLeft = 3;
    private int mViewTop = 3;
    private String userName;

    public ShowPictureLinearLayout(Context context) {
        super(context);
        this.setLayoutDirection(HORIZONTAL);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < getChildCount(); i++) {
            if (i >= 1) {
                View child = getChildAt(i);
                int leftMargin = 10 + getChildAt(i-1).getMeasuredWidth();
                child.layout(leftMargin, 0, leftMargin + child.getMeasuredWidth(), child.getMeasuredHeight());
            }
        }
        requestLayout();
    }

    public void setmViewLeft(int viewLeft) {
        this.mViewLeft = viewLeft;
    }

    public int getmViewLeft() {
        return mViewLeft;
    }

    public int getmViewTop() {
        return mViewTop;
    }

    public void setmViewTop(int mViewTop) {
        this.mViewTop = mViewTop;
    }
}
