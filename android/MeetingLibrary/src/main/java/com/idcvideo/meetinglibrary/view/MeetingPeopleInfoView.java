package com.idcvideo.meetinglibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MeetingPeopleInfoView extends TextView {

    private static final String TAG = "MeetingPeopleInfoView";
    private String mUserName;
    private String mUserId;
    private int mViewTop;
    private int mViewLeft;

    public MeetingPeopleInfoView(Context context) {
        super(context);
    }

    public MeetingPeopleInfoView(Context context , AttributeSet attrs){
        super(context , attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG,"childView left = "+left);
        Log.e(TAG,"childView top = "+top);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"BlankCanvas onTouchEvent");
        return super.onTouchEvent(event);
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public int getmViewTop() {
        return mViewTop;
    }

    public void setmViewTop(int mViewTop) {
        this.mViewTop = mViewTop;
    }

    public int getmViewLeft() {
        return mViewLeft;
    }

    public void setmViewLeft(int mViewLeft) {
        this.mViewLeft = mViewLeft;
    }
}
