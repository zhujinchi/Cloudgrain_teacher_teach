package com.idcvideo.meetinglibrary.view;

import android.content.Context;

import androidx.appcompat.widget.AppCompatImageView;

import com.idcmeeting.library.R;

public class NoVideoView extends AppCompatImageView {

    public NoVideoView(Context context) {
        super(context);
        setImageResource(R.drawable.meetingpeoplenovideo);
    }

}
