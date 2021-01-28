package com.idcvideo.meetinglibrary.activity;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassMessageBean;
import com.idcvideo.meetinglibrary.activity.bean.CloudClassWebSocketBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.utils
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/8
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 2:01 PM）
 * +----------------------------------------------------------------------
 **/
public class WebSocketDemo {
    private List<CloudClassMessageBean> list = new ArrayList<>();
    private final String TAG = WebSocketDemo.class.getSimpleName();

    private OkHttpClient CLIENT;
    private WebSocket mWebSocket;

    private static final WebSocketDemo ourInstance = new WebSocketDemo();

    public static WebSocketDemo getDefault() {
        return ourInstance;
    }

    private WebSocketDemo() {
        CLIENT = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    public void connect(String url) {
        if (mWebSocket != null) {
            mWebSocket.cancel();
        }
        Request request = new Request.Builder()
                .url(url)
                .build();
        mWebSocket = CLIENT.newWebSocket(request, new SocketListener());
    }

    public void sendMessage(String message) {
        mWebSocket.send(message);
    }

    public void sendMessage(byte... data) {
        ByteString bs = ByteString.of(data);
        mWebSocket.send(bs);
    }

    public void close(int code, String reason) {
        mWebSocket.close(code, reason);
    }

    class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            Log.i(TAG, "onOpen response=" + response);
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            Log.i(TAG, "onMessage text=" + text);
            CloudClassWebSocketBean cloudClassWebSocketBean = JSON.parseObject(text, CloudClassWebSocketBean.class);
            int code = cloudClassWebSocketBean.getCode();
            if (code == 200) {
                if (cloudClassWebSocketBean.getData() != null) {
                    String classId = cloudClassWebSocketBean.getData().getClassId();
                    String message = cloudClassWebSocketBean.getData().getMessage();
                    CloudClassMessageBean cloudClassMessageBean = new CloudClassMessageBean();
                    cloudClassMessageBean.setClassId(classId);
                    cloudClassMessageBean.setMessage(message);
                    list.add(cloudClassMessageBean);
                }
            }
            EventBus.getDefault().post(list);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            Log.i(TAG, "onMessage bytes=" + bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            Log.i(TAG, "onClosing code=" + code);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            Log.i(TAG, "onClosed code=" + code);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            Log.i(TAG, "onFailure t=" + t.getMessage());
        }
    }
}
