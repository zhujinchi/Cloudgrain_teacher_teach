package com.idcvideo.meetinglibrary.activity.bean;

import java.util.List;

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
 * | 代码修改:（ljp - 3:25 PM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassAttendBean {

    private List<StudentBean> student;

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

    private List<TeacherBean> teacher;

    public static class StudentBean {
        /**
         * updateDate : 2020-11-17 22:16:36
         * stuId : 1011
         * isBind : 0.0
         * delFlag : 0
         * classId : 1321026940288573442
         * updateBy : 33333
         * id : 111
         * status : 4
         * nickName : 发牛仔
         * userImageId : TX678187887876
         * imgUrl :
         */

        private String updateDate;
        private String stuId;
        private double isBind;
        private String delFlag;
        private String classId;
        private String updateBy;
        private String id;
        private String status;
        private String nickName;
        private String userImageId;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public double getIsBind() {
            return isBind;
        }

        public void setIsBind(double isBind) {
            this.isBind = isBind;
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

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
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

        private String imgUrl;
    }

    public static class TeacherBean {
        /**
         * owner : 1
         * updateDate : 2020-10-24 10:21:46
         * nickName : 夏贝尔
         * delFlag : 0
         * classId : 1321026940288573442
         * createBy : 1001
         * teacherId : 1322832852864774145
         * updateBy : 1001
         * id : 3001
         * userImageId : 6719075303463448576
         * subjectCode : 102
         * status : 2
         * createDate : 2020-10-24 10:21:44
         * imgUrl : http://127.0.0.1:81/20201031173240/6721619425071505408.jpg
         */

        private String owner;
        private String updateDate;
        private String nickName;
        private String delFlag;
        private String classId;
        private String createBy;
        private String teacherId;
        private String updateBy;
        private String id;
        private String userImageId;
        private String subjectCode;

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
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

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
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

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        private String status;
        private String createDate;
        private String imgUrl;
    }
}
