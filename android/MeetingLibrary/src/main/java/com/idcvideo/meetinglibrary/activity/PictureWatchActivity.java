package com.idcvideo.meetinglibrary.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.idcvideo.meetinglibrary.R;
import com.idcvideo.meetinglibrary.activity.bean.ImageBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

/**
 * +----------------------------------------------------------------------
 * |com.example.cloudgrainstudent.PictureWatchActivity
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/28
 * +----------------------------------------------------------------------
 * | @author: ljp
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 17:49）
 * +----------------------------------------------------------------------
 **/
public class PictureWatchActivity extends Activity implements View.OnClickListener {
    private ArrayList<Parcelable> mImPictureData;
    private int mCurrentPictureIndex;
    private String url;
    private String imagePath;
    private ViewPager viewPager;
    private TextView indexText;
    public static final int imageData = 0x1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case imageData:
                    ImageBean mAssignment = (ImageBean) msg.obj;
                    list = mAssignment.getData().getFiles();
                    viewPager.setAdapter(new MyAdapter());
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            //indexText.setText((position + 1) + "/" + mImPictureData.size());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
                    viewPager.setCurrentItem(mCurrentPictureIndex);
                    if (ListUtils.getSize(list) > 0) {
                        indexText.setText((mCurrentPictureIndex + 1) + "/" + list.size());
                    }
                    break;
            }
        }
    };
    private String id;
    private List<ImageBean.DataBean.FilesBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_whatch_activity);
        viewPager = findViewById(R.id.picture_watch_vp);
        findViewById(R.id.back_meeting_iv).setOnClickListener(this);
        indexText = findViewById(R.id.picture_watch_tv_picture_index);
        initView();
    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        new Thread(new Runnable() {
            @Override
            public void run() {
                getImageFile();
            }
        }).start();

    }

    private void getImageFile() {
        String tempAccessToken = SPUtil.get(PictureWatchActivity.this, "accessToken", "accessToken").toString();
//        String tempAccount = " ";
//        String tempClassId = " ";
        String url = "http://yundou.skyline.name:18002/taskFinishRec/get";
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        HttpClient.getInstance(PictureWatchActivity.this).get(tempAccessToken, url, hashMap, new HttpClient.MyCallback() {
            @Override
            public void success(Response res) throws IOException {
                //Log.i("EvaluateInfo==", "EvaluateInfo==" + res.body().string());
                String bodys = res.body().string();
                Log.i("EvaluateInfo==", "EvaluateInfo==" + bodys);
                Gson gson = new Gson();
                ImageBean dataBean = gson.fromJson(bodys, ImageBean.class);
                if (dataBean != null) {
                    Message message = handler.obtainMessage();
                    message.what = imageData;
                    message.obj = dataBean;
                    if (handler != null) {
                        handler.sendMessage(message);
                    }
                }

            }

            @Override
            public void failed(IOException e) {
                //ToastUtils.showLong(MeetingActivity.this, e.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_meeting_iv) {
            finish();
        }
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            final TouchImageView touchImageView = new TouchImageView(PictureWatchActivity.this);
            ViewGroup.LayoutParams layoutParams =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            touchImageView.setLayoutParams(layoutParams);
            touchImageView.setImageResource(R.color.bule_color);
            if (!ListUtils.isEmpty(list)) {
                url = list.get(position).getOuterLink();
            }

            RequestOptions requestBody = new RequestOptions();
            requestBody.error(R.mipmap.yt);
            if (!TextUtils.isEmpty(url)) {
                Glide.with(PictureWatchActivity.this).load(url).apply(requestBody).
                        into(new SimpleTarget<Drawable>(600, 600) {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                touchImageView.setImageDrawable(resource);
                            }
                        });
            } else {
                Glide.with(PictureWatchActivity.this).load(imagePath).apply(requestBody).
                        into(new SimpleTarget<Drawable>(600, 600) {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                touchImageView.setImageDrawable(resource);
                            }
                        });
            }
            touchImageView.setOnClickListener(new MyOnClickListener(position));
            container.addView(touchImageView);
            return touchImageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyOnClickListener implements View.OnClickListener {

        private int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    public static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

}
