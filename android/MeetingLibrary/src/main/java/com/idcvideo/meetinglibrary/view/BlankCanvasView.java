package com.idcvideo.meetinglibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class BlankCanvasView extends RelativeLayout {

    private static final String TAG = "BlankCanvasView";

    public BlankCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int count = getChildCount();
        for (int i=0;i<count;i++) {
            //MeetingPeopleInfoView child = (MeetingPeopleInfoView)this.getChildAt(i);
            View childView = this.getChildAt(i);
            if (childView instanceof ShowInfoLayout) {
                ShowInfoLayout child = (ShowInfoLayout) this.getChildAt(i);
                int leftMargin = child.getmViewLeft();
                int topMargin = child.getmViewTop();
                Log.e(TAG, "getChildLeft = " + getChildAt(i) + "," + this.getChildAt(i).getLeft());
                Log.e(TAG, "getChildTop = " + getChildAt(i) + "," + this.getChildAt(i).getTop());
                child.layout(leftMargin, topMargin-40, leftMargin + child.getMeasuredWidth(), topMargin + child.getMeasuredHeight()); //以父容器左上角为原点进行布局
            } else if (childView instanceof ShowPictureLinearLayout) {
                ShowPictureLinearLayout child = (ShowPictureLinearLayout) this.getChildAt(i);
                int leftMargin = child.getmViewLeft();
                int topMargin = child.getmViewTop();
                Log.e(TAG, "getChildLeft = " + getChildAt(i) + "," + this.getChildAt(i).getLeft());
                Log.e(TAG, "getChildTop = " + getChildAt(i) + "," + this.getChildAt(i).getTop());
                child.layout(leftMargin, 0, leftMargin + child.getMeasuredWidth() , topMargin + child.getMeasuredHeight()); //以父容器左上角为原点进行布局
            }
        }
    }

}
