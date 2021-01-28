package com.idcvideo.meetinglibrary.activity.bean;

import java.util.List;

public class MessageStudentBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"student":[{"updateDate":null,"stuId":"1321433123681538049","nickName":"张同学","isBind":1,"delFlag":"0","imgUrl":"https://yundou.skyline.name/static/files/20210119001721/6750349907834105856.jpg","classId":"1353731978755674114","createBy":"1330506149584777218","updateBy":null,"id":"1353736436076089346","userImageId":"6738984588309897216","remarks":"已审核通过","status":"4","createDate":"2021-01-26 00:08:12"},{"updateDate":"2021-01-27 15:49:15","stuId":"1321094463362760706","nickName":"雄安呗","isBind":1,"delFlag":"0","imgUrl":"https://yundou.skyline.name/static/files/20201218153538/6738984588305702912.jpg","classId":"1353731978755674114","createBy":"1321094463362760706","updateBy":"1330506149584777218","id":"1353959086689980418","userImageId":"6738984588309897216","remarks":"已审核通过","status":"4","createDate":"2021-01-26 14:52:56"}],"teacher":[{"owner":"1","updateDate":null,"nickName":"小文","delFlag":"0","imgUrl":"https://yundou.skyline.name/static/files/20201218153538/6738984588305702912.jpg","classId":"1353731978755674114","createBy":"1322815240747462658","teacherId":"1322815240747462658","updateBy":null,"id":"1353731978826977282","userImageId":"6738984588309897216","subjectCode":null,"remarks":null,"status":"2","createDate":"2021-01-25 23:50:30"}]}
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
        private List<StudentBean> student;
        private List<TeacherBean> teacher;

        public List<StudentBean> getStudent() {
            return student;
        }

        public void setStudent(List<StudentBean> student) {
            this.student = student;
        }

        public List<TeacherBean> getTeacher() {
            return teacher;
        }

        public void setTeacher(List<TeacherBean> teacher) {
            this.teacher = teacher;
        }

        public static class StudentBean {
            /**
             * updateDate : null
             * stuId : 1321433123681538049
             * nickName : 张同学
             * isBind : 1
             * delFlag : 0
             * imgUrl : https://yundou.skyline.name/static/files/20210119001721/6750349907834105856.jpg
             * classId : 1353731978755674114
             * createBy : 1330506149584777218
             * updateBy : null
             * id : 1353736436076089346
             * userImageId : 6738984588309897216
             * remarks : 已审核通过
             * status : 4
             * createDate : 2021-01-26 00:08:12
             */

            private Object updateDate;
            private String stuId;
            private String nickName;
            private int isBind;
            private String delFlag;
            private String imgUrl;
            private String classId;
            private String createBy;
            private Object updateBy;
            private String id;
            private String userImageId;
            private String remarks;
            private String status;
            private String createDate;

            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
                this.updateDate = updateDate;
            }

            public String getStuId() {
                return stuId;
            }

            public void setStuId(String stuId) {
                this.stuId = stuId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getIsBind() {
                return isBind;
            }

            public void setIsBind(int isBind) {
                this.isBind = isBind;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
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

            public String getUserImageId() {
                return userImageId;
            }

            public void setUserImageId(String userImageId) {
                this.userImageId = userImageId;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }

        public static class TeacherBean {
            /**
             * owner : 1
             * updateDate : null
             * nickName : 小文
             * delFlag : 0
             * imgUrl : https://yundou.skyline.name/static/files/20201218153538/6738984588305702912.jpg
             * classId : 1353731978755674114
             * createBy : 1322815240747462658
             * teacherId : 1322815240747462658
             * updateBy : null
             * id : 1353731978826977282
             * userImageId : 6738984588309897216
             * subjectCode : null
             * remarks : null
             * status : 2
             * createDate : 2021-01-25 23:50:30
             */

            private String owner;
            private Object updateDate;
            private String nickName;
            private String delFlag;
            private String imgUrl;
            private String classId;
            private String createBy;
            private String teacherId;
            private Object updateBy;
            private String id;
            private String userImageId;
            private Object subjectCode;
            private Object remarks;
            private String status;
            private String createDate;

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
                this.updateDate = updateDate;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
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

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
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

            public String getUserImageId() {
                return userImageId;
            }

            public void setUserImageId(String userImageId) {
                this.userImageId = userImageId;
            }

            public Object getSubjectCode() {
                return subjectCode;
            }

            public void setSubjectCode(Object subjectCode) {
                this.subjectCode = subjectCode;
            }

            public Object getRemarks() {
                return remarks;
            }

            public void setRemarks(Object remarks) {
                this.remarks = remarks;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
