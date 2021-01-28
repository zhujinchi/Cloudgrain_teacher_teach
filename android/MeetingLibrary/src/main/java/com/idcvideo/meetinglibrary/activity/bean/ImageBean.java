package com.idcvideo.meetinglibrary.activity.bean;

import java.util.List;

public class ImageBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":"6752064408736428032","createBy":"1321433123681538049","createDate":"2021-01-23 17:50:10","updateBy":"1321433123681538049","updateDate":"2021-01-23 17:50:10","delFlag":0,"studentId":"1321433123681538049","taskSubjectId":"101","startTime":"2021-01-23 15:46:55","endTime":"2021-01-23 15:48:10","costTime":0,"taskType":1,"content":"测试作业提交101","taskStatus":0,"score":0,"studentName":"张同同","files":[{"id":"6752063942052999168","fileName":"1611380624738.jpg","fileSize":919978,"fileType":"1","outerLink":"https://yundou.skyline.name/static/files/20210123174819/6752063942048804864.jpg"},{"id":"6752063942078164992","fileName":"1611380592345.jpg","fileSize":799633,"fileType":"1","outerLink":"https://yundou.skyline.name/static/files/20210123174819/6752063942073970688.jpg"}],"failCnt":0,"passCnt":0}
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
         * id : 6752064408736428032
         * createBy : 1321433123681538049
         * createDate : 2021-01-23 17:50:10
         * updateBy : 1321433123681538049
         * updateDate : 2021-01-23 17:50:10
         * delFlag : 0
         * studentId : 1321433123681538049
         * taskSubjectId : 101
         * startTime : 2021-01-23 15:46:55
         * endTime : 2021-01-23 15:48:10
         * costTime : 0
         * taskType : 1
         * content : 测试作业提交101
         * taskStatus : 0
         * score : 0.0
         * studentName : 张同同
         * files : [{"id":"6752063942052999168","fileName":"1611380624738.jpg","fileSize":919978,"fileType":"1","outerLink":"https://yundou.skyline.name/static/files/20210123174819/6752063942048804864.jpg"},{"id":"6752063942078164992","fileName":"1611380592345.jpg","fileSize":799633,"fileType":"1","outerLink":"https://yundou.skyline.name/static/files/20210123174819/6752063942073970688.jpg"}]
         * failCnt : 0
         * passCnt : 0
         */

        private String id;
        private String createBy;
        private String createDate;
        private String updateBy;
        private String updateDate;
        private int delFlag;
        private String studentId;
        private String taskSubjectId;
        private String startTime;
        private String endTime;
        private int costTime;
        private int taskType;
        private String content;
        private int taskStatus;
        private double score;
        private String studentName;
        private int failCnt;
        private int passCnt;
        private List<FilesBean> files;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
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

        public int getCostTime() {
            return costTime;
        }

        public void setCostTime(int costTime) {
            this.costTime = costTime;
        }

        public int getTaskType() {
            return taskType;
        }

        public void setTaskType(int taskType) {
            this.taskType = taskType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTaskStatus() {
            return taskStatus;
        }

        public void setTaskStatus(int taskStatus) {
            this.taskStatus = taskStatus;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
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

        public List<FilesBean> getFiles() {
            return files;
        }

        public void setFiles(List<FilesBean> files) {
            this.files = files;
        }

        public static class FilesBean {
            /**
             * id : 6752063942052999168
             * fileName : 1611380624738.jpg
             * fileSize : 919978
             * fileType : 1
             * outerLink : https://yundou.skyline.name/static/files/20210123174819/6752063942048804864.jpg
             */

            private String id;
            private String fileName;
            private int fileSize;
            private String fileType;
            private String outerLink;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public int getFileSize() {
                return fileSize;
            }

            public void setFileSize(int fileSize) {
                this.fileSize = fileSize;
            }

            public String getFileType() {
                return fileType;
            }

            public void setFileType(String fileType) {
                this.fileType = fileType;
            }

            public String getOuterLink() {
                return outerLink;
            }

            public void setOuterLink(String outerLink) {
                this.outerLink = outerLink;
            }
        }
    }
}
