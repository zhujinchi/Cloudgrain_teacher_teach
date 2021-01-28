package com.idcvideo.meetinglibrary.bean;

/**
 * @author : idcvideo
 * @version : 1.0
 * @e-mail : wfeng_work@163.com
 * @date： : 2020/3/9 18:00
 * @pake : com.idcvideo.haokaihui.bean
 * @Description :
 */
public class MeetingPeopleVoiceBean {

    private String userName;
    private int voicePower = -1;

    private boolean closeVoice;

    private boolean isExist = true;

    private boolean visibility;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public boolean isCloseVoice() {
        return closeVoice;
    }

    public void setCloseVoice(boolean closeVoice) {
        this.closeVoice = closeVoice;
    }

    private int voiceTime;

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        if (voiceTime > 500){
            voiceTime = 50;
        }
        this.voiceTime = voiceTime;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setVisibility(boolean closeVoice) {
        this.visibility = closeVoice;
    }

    public int getVoicePower() {
        return voicePower;
    }

    public void setVoicePower(int voicePower) {
        this.voicePower = voicePower;
    }
}
