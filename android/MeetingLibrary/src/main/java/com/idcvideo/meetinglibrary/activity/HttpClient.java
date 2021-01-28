package com.idcvideo.meetinglibrary.activity;

import android.content.Context;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2021/1/13
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 1:53 PM）
 * +----------------------------------------------------------------------
 **/
public class HttpClient {
    private OkHttpClient client;
    private static HttpClient mClient;
    private Context context;
    private HttpClient(Context c) {
        context = c;
        client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .build();
    }

    public static HttpClient getInstance(Context c){
        if (mClient == null) {
            mClient = new HttpClient(c);
        }
        return  mClient;
    }


    // GET方法
    public void get(String headParam,String url, HashMap<String,String> param, MyCallback callback) {
        // 拼接请求参数
        if (!param.isEmpty()) {
            StringBuffer buffer = new StringBuffer(url);
            buffer.append('?');
            for (Map.Entry<String,String> entry: param.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append('=');
                buffer.append(entry.getValue());
                buffer.append('&');
            }
            buffer.deleteCharAt(buffer.length()-1);
            url = buffer.toString();
        }
        Request.Builder builder = new Request.Builder().addHeader("accessToken",headParam).url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.success(response);
            }
        });
    }

    public void get(String headStr,String url, MyCallback callback) {
        get(headStr,url, new HashMap<String, String>(), callback);
    }

    // POST 方法
    public void post(String token,String url, HashMap<String, String> param, MyCallback callback) {
        FormBody.Builder formBody = new FormBody.Builder();
        if(!param.isEmpty()) {
            for (Map.Entry<String,String> entry: param.entrySet()) {
                formBody.add(entry.getKey(),entry.getValue());
            }
        }
        RequestBody form = formBody.build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.addHeader("accessToken",token).post(form)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.success(response);
            }
        });
    }
    public interface MyCallback {
        void success(Response res) throws IOException;
        void failed(IOException e);
    }
}
