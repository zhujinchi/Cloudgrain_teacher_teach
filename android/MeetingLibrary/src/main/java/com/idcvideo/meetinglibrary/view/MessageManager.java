package com.idcvideo.meetinglibrary.view;

import android.util.Log;

import com.idcmeeting.library.manager.callback.IdcMediaMessage;
import com.idcvideo.meetinglibrary.core.IdcMediaKit;

import java.util.ArrayList;

public class MessageManager implements MessageInfo {

    private static final String TAG = "MessageManager";
    private String message = "";
    ArrayList<MessageInfo> list = new ArrayList<>();


    public MessageManager () {
        Log.i(TAG ,"new message info");
        Log.i(TAG,"hkhDebug set channel notify in message info.");
        IdcMediaKit.IdcMediaCustomDataChannelNotify(new IdcMediaMessage() {
            @Override
            public void agent(String userNmae, String message) {
                message = message;
                for (MessageInfo messageInfo:list) {
                    messageInfo.subsctibe(userNmae, message);
                }
                Log.i(TAG,"hkhDebug " + userNmae + "||" + message);
            }
        });
    }

    public void add(MessageInfo message) {
        Log.i(TAG ,"add message not null " + (message!=null));
        if (message != null) {
            list.add(message);
        }
    }

    public void Release () {
        Log.i(TAG ,"release all reference");
        list.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.i(TAG ,"finalize");
        super.finalize();
    }

    @Override
    public void subsctibe(String userNmae, String message) {

    }
}
