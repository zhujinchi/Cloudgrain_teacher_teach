package com.idcvideo.meetinglibrary.activity.bean;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity.bean
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/30
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 3:50 PM）
 * +----------------------------------------------------------------------
 **/
public class EventMessage {
    private int type;
    private String message;

    public EventMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {

        return "type="+type+"--message= "+message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
