package com.idcvideo.meetinglibrary.activity;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity.bean
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2021/1/13
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 5:35 PM）
 * +----------------------------------------------------------------------
 **/
public class StudentBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":"1321433123681538049","userAccount":"XH6768768762","userType":"1","userMail":null,"userImageId":"6738984588309897216","imgUrl":"/storage/emulated/0/Pictures/Image (1).jpg","mobilePhone":null,"actualName":"张爱玲","userSex":"1","nickName":"张爱玲-昵称","loginLastAddr":"171.43.158.143","loginLastTime":"2021-01-13 16:30:46","createBy":"1321433123681538049","createDate":"2021-01-13 16:30:46","updateBy":"1321433123681538049","updateDate":"2021-01-13 16:30:46","remarks":null,"delFlag":"0"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1321433123681538049
         * userAccount : XH6768768762
         * userType : 1
         * userMail : null
         * userImageId : 6738984588309897216
         * imgUrl : /storage/emulated/0/Pictures/Image (1).jpg
         * mobilePhone : null
         * actualName : 张爱玲
         * userSex : 1
         * nickName : 张爱玲-昵称
         * loginLastAddr : 171.43.158.143
         * loginLastTime : 2021-01-13 16:30:46
         * createBy : 1321433123681538049
         * createDate : 2021-01-13 16:30:46
         * updateBy : 1321433123681538049
         * updateDate : 2021-01-13 16:30:46
         * remarks : null
         * delFlag : 0
         */

        private String id;
        private String userAccount;
        private String userType;
        private Object userMail;
        private String userImageId;
        private String imgUrl;
        private Object mobilePhone;
        private String actualName;
        private String userSex;
        private String nickName;
        private String loginLastAddr;
        private String loginLastTime;
        private String createBy;
        private String createDate;
        private String updateBy;
        private String updateDate;
        private Object remarks;
        private String delFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Object getUserMail() {
            return userMail;
        }

        public void setUserMail(Object userMail) {
            this.userMail = userMail;
        }

        public String getUserImageId() {
            return userImageId;
        }

        public void setUserImageId(String userImageId) {
            this.userImageId = userImageId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(Object mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getActualName() {
            return actualName;
        }

        public void setActualName(String actualName) {
            this.actualName = actualName;
        }

        public String getUserSex() {
            return userSex;
        }

        public void setUserSex(String userSex) {
            this.userSex = userSex;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLoginLastAddr() {
            return loginLastAddr;
        }

        public void setLoginLastAddr(String loginLastAddr) {
            this.loginLastAddr = loginLastAddr;
        }

        public String getLoginLastTime() {
            return loginLastTime;
        }

        public void setLoginLastTime(String loginLastTime) {
            this.loginLastTime = loginLastTime;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }
}
