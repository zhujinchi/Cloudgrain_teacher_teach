package com.idcvideo.meetinglibrary.activity.bean;

import java.io.Serializable;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.cloudclass.bean
 * +----------------------------------------------------------------------
 * | 功能描述:云课待开始
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/3
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 4:46 PM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassStartBean implements Serializable {

    /**
     * date : 2020-11-01
     * depict : 课程描述
     * teacherName : llll
     * stuNum : 8
     * totalPrice : 500.00
     * className : 培优老师课程
     * coursesId : 1321026415467896834
     * classId : 1321026415233015809
     * teacherId : 1319927597175455746
     * startTime : 14:30
     * endTime : 19:00
     * subjectCode : 102
     * actualStuNum : 2
     * classType : 2
     */

    private String date;
    private String depict;
    private String teacherName;
    private String stuNum;
    private String totalPrice;
    private String className;
    private String coursesId;
    private String classId;
    private String teacherId;
    private String startTime;
    private String endTime;
    private String subjectCode;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(String coursesId) {
        this.coursesId = coursesId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getActualStuNum() {
        return actualStuNum;
    }

    public void setActualStuNum(String actualStuNum) {
        this.actualStuNum = actualStuNum;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    private String actualStuNum;
    private String classType;
}
