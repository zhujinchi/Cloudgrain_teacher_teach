package com.idcvideo.meetinglibrary.activity.bean;

import java.util.List;

public class AssignmentListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":"6753072164163481600","createDate":"2021-01-26 12:34:38","studentId":"1321094463362760706","taskSubjectId":"102","startTime":"2021-01-26 12:33:04","endTime":"2021-01-26 12:34:40","studentName":"雄安呗-昵称","failCnt":0,"passCnt":0},{"id":"6753061921035579392","createDate":"2021-01-26 11:53:56","studentId":"1321433123681538049","taskSubjectId":"102","startTime":"2021-01-27 05:51:42","endTime":"2021-01-27 05:53:21","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6753060463733043200","createDate":"2021-01-26 11:48:08","studentId":"1321433123681538049","taskSubjectId":"102","startTime":"2021-01-27 05:47:03","endTime":"2021-01-27 05:47:33","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6753060277401088000","createDate":"2021-01-26 11:47:24","studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-27 05:46:03","endTime":"2021-01-27 05:46:48","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6753047841994371072","createDate":"2021-01-26 10:57:59","studentId":"1321094463362760706","taskSubjectId":"102","startTime":"2021-01-26 10:56:52","endTime":"2021-01-26 10:58:01","studentName":"雄安呗-昵称","failCnt":0,"passCnt":0},{"id":"6752668927249805312","createDate":"2021-01-25 09:52:19","studentId":"1321433123681538049","taskSubjectId":"102","startTime":"2021-01-25 09:52:03","endTime":"2021-01-25 09:52:19","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6752662745827176448","createDate":"2021-01-25 09:27:45","studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-25 09:24:36","endTime":"2021-01-25 09:27:46","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6752068220708646912","createDate":"2021-01-23 18:05:19","studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-23 16:03:55","endTime":"2021-01-23 16:04:32","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6752065449716543488","createDate":"2021-01-23 17:54:19","studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-23 15:51:20","endTime":"2021-01-23 15:51:53","studentName":"张同同","failCnt":0,"passCnt":0},{"id":"6752064408736428032","createDate":"2021-01-23 17:50:10","studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-23 15:46:55","endTime":"2021-01-23 15:48:10","studentName":"张同同","failCnt":0,"passCnt":0}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6753072164163481600
         * createDate : 2021-01-26 12:34:38
         * studentId : 1321094463362760706
         * taskSubjectId : 102
         * startTime : 2021-01-26 12:33:04
         * endTime : 2021-01-26 12:34:40
         * studentName : 雄安呗-昵称
         * failCnt : 0
         * passCnt : 0
         */

        private String id;
        private String createDate;
        private String studentId;
        private String taskSubjectId;
        private String startTime;
        private String endTime;
        private String studentName;
        private int failCnt;
        private int passCnt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getTaskSubjectId() {
            return taskSubjectId;
        }

        public void setTaskSubjectId(String taskSubjectId) {
            this.taskSubjectId = taskSubjectId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public int getFailCnt() {
            return failCnt;
        }

        public void setFailCnt(int failCnt) {
            this.failCnt = failCnt;
        }

        public int getPassCnt() {
            return passCnt;
        }

        public void setPassCnt(int passCnt) {
            this.passCnt = passCnt;
        }
    }
}
