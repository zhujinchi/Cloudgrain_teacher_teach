package com.idcvideo.meetinglibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;


import androidx.core.content.FileProvider;

import java.io.File;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.utils
 * +----------------------------------------------------------------------
 * | 功能描述:android打开相机工具
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/8
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 5:23 PM）
 * +----------------------------------------------------------------------
 **/
public class StartCameraUtils {
    public static void getStartCamera(Activity activity) {
        File cameraPhoto = new File(GlobalVariable.FILE_PATH);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = FileProvider.getUriForFile(
                activity,
                activity.getPackageName() + ".fileprovider",
                cameraPhoto);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        activity.startActivityForResult(takePhotoIntent, GlobalVariable.Start_Camera_code);
    }
}
