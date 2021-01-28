package com.idcvideo.meetinglibrary.activity.bean;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.cloudclass.bean
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/8
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 4:33 PM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassWebSocketBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : {"updateDate":null,"receiver":"3","message":"回老家了","delFlag":"0","classId":"1321026415233015809","createBy":"1321433123681538049","sender":"0","updateBy":null,"id":"1336227189295230978","remarks":null,"createDate":"2020-12-08 16:32:42"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * updateDate : null
         * receiver : 3
         * message : 回老家了
         * delFlag : 0
         * classId : 1321026415233015809
         * createBy : 1321433123681538049
         * sender : 0
         * updateBy : null
         * id : 1336227189295230978
         * remarks : null
         * createDate : 2020-12-08 16:32:42
         */

        private Object updateDate;
        private String receiver;
        private String message;
        private String delFlag;
        private String classId;
        private String createBy;
        private String sender;

        public Object getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Object updateDate) {
            this.updateDate = updateDate;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        private Object updateBy;
        private String id;
        private Object remarks;
        private String createDate;
    }
}
