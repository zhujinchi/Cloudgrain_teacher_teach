package com.idcvideo.meetinglibrary.activity.bean;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.cloudclass.bean
 * +----------------------------------------------------------------------
 * | 功能描述:交流
 * +----------------------------------------------------------------------
 * | 时　　间:2020/11/14
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 12:41 PM）
 * +----------------------------------------------------------------------
 **/
public class ExchangeBean {
    private String name;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
