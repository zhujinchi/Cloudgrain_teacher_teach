package com.idcvideo.meetinglibrary.activity;

import java.util.List;

/**
 * +----------------------------------------------------------------------
 * | com.idcvideo.meetinglibrary.activity.bean
 * +----------------------------------------------------------------------
 * | 功能描述:获取老师聊天列表
 * +----------------------------------------------------------------------
 * | 时　　间:2021/1/15
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 2:17 PM）
 * +----------------------------------------------------------------------
 **/
public class ScreenPopupWindowTeacherListBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"records":[{"updateDate":null,"classStuNum":9,"teacherName":"小文","className":"0晚辅导课程测试","scoreA":2,"coursesId":"1341736803850358786","delFlag":"0","score":3,"classId":"1341736803837775874","createBy":"1322815240747462658","teacherId":"1322815240747462658","scoreB":2,"scoreC":3,"updateBy":null,"scoreD":4,"scoreE":4,"teacherAppraise":"这是老师的课堂评语内容。","startTime":"2020-12-21 17:00:00","id":"99017048188780546","endTime":"2020-12-21 19:00:00","actualStuNum":4,"remarks":null,"createDate":"2020-12-21 17:00:00","appraiseDate":"2020-12-21 19:00:00"},{"updateDate":null,"classStuNum":9,"teacherName":"张三","className":"培优老师课程","scoreA":4,"coursesId":"1330073106863697921","delFlag":"0","score":4,"classId":"1321026304272703489","createBy":"1319927597175455746","teacherId":"1319927597175455746","scoreB":3,"scoreC":5,"updateBy":null,"scoreD":3,"scoreE":5,"teacherAppraise":"这是老师的课堂评语内容。","startTime":"2020-10-29 17:00:00","id":"1111","endTime":"2020-10-29 19:00:00","actualStuNum":4,"remarks":null,"createDate":"2020-10-29 17:00:00","appraiseDate":"2020-11-28 00:11:56"}],"total":2,"size":999,"current":1,"orders":[],"optimizeCountSql":true,"hitCount":false,"countId":null,"maxLimit":null,"searchCount":true,"pages":1}
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
         * records : [{"updateDate":null,"classStuNum":9,"teacherName":"小文","className":"0晚辅导课程测试","scoreA":2,"coursesId":"1341736803850358786","delFlag":"0","score":3,"classId":"1341736803837775874","createBy":"1322815240747462658","teacherId":"1322815240747462658","scoreB":2,"scoreC":3,"updateBy":null,"scoreD":4,"scoreE":4,"teacherAppraise":"这是老师的课堂评语内容。","startTime":"2020-12-21 17:00:00","id":"99017048188780546","endTime":"2020-12-21 19:00:00","actualStuNum":4,"remarks":null,"createDate":"2020-12-21 17:00:00","appraiseDate":"2020-12-21 19:00:00"},{"updateDate":null,"classStuNum":9,"teacherName":"张三","className":"培优老师课程","scoreA":4,"coursesId":"1330073106863697921","delFlag":"0","score":4,"classId":"1321026304272703489","createBy":"1319927597175455746","teacherId":"1319927597175455746","scoreB":3,"scoreC":5,"updateBy":null,"scoreD":3,"scoreE":5,"teacherAppraise":"这是老师的课堂评语内容。","startTime":"2020-10-29 17:00:00","id":"1111","endTime":"2020-10-29 19:00:00","actualStuNum":4,"remarks":null,"createDate":"2020-10-29 17:00:00","appraiseDate":"2020-11-28 00:11:56"}]
         * total : 2
         * size : 999
         * current : 1
         * orders : []
         * optimizeCountSql : true
         * hitCount : false
         * countId : null
         * maxLimit : null
         * searchCount : true
         * pages : 1
         */

        private int total;
        private int size;
        private int current;
        private boolean optimizeCountSql;
        private boolean hitCount;
        private Object countId;
        private Object maxLimit;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public boolean isOptimizeCountSql() {
            return optimizeCountSql;
        }

        public void setOptimizeCountSql(boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
        }

        public boolean isHitCount() {
            return hitCount;
        }

        public void setHitCount(boolean hitCount) {
            this.hitCount = hitCount;
        }

        public Object getCountId() {
            return countId;
        }

        public void setCountId(Object countId) {
            this.countId = countId;
        }

        public Object getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Object maxLimit) {
            this.maxLimit = maxLimit;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        private boolean searchCount;
        private int pages;
        private List<RecordsBean> records;

        public static class RecordsBean {
            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
                this.updateDate = updateDate;
            }

            public int getClassStuNum() {
                return classStuNum;
            }

            public void setClassStuNum(int classStuNum) {
                this.classStuNum = classStuNum;
            }

            public String getTeacherName() {
                return teacherName;
            }

            public void setTeacherName(String teacherName) {
                this.teacherName = teacherName;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public int getScoreA() {
                return scoreA;
            }

            public void setScoreA(int scoreA) {
                this.scoreA = scoreA;
            }

            public String getCoursesId() {
                return coursesId;
            }

            public void setCoursesId(String coursesId) {
                this.coursesId = coursesId;
            }

            public String getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(String delFlag) {
                this.delFlag = delFlag;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
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

            public int getScoreB() {
                return scoreB;
            }

            public void setScoreB(int scoreB) {
                this.scoreB = scoreB;
            }

            public int getScoreC() {
                return scoreC;
            }

            public void setScoreC(int scoreC) {
                this.scoreC = scoreC;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public int getScoreD() {
                return scoreD;
            }

            public void setScoreD(int scoreD) {
                this.scoreD = scoreD;
            }

            public int getScoreE() {
                return scoreE;
            }

            public void setScoreE(int scoreE) {
                this.scoreE = scoreE;
            }

            public String getTeacherAppraise() {
                return teacherAppraise;
            }

            public void setTeacherAppraise(String teacherAppraise) {
                this.teacherAppraise = teacherAppraise;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getActualStuNum() {
                return actualStuNum;
            }

            public void setActualStuNum(int actualStuNum) {
                this.actualStuNum = actualStuNum;
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

            public String getAppraiseDate() {
                return appraiseDate;
            }

            public void setAppraiseDate(String appraiseDate) {
                this.appraiseDate = appraiseDate;
            }

            /**
             * updateDate : null
             * classStuNum : 9
             * teacherName : 小文
             * className : 0晚辅导课程测试
             * scoreA : 2
             * coursesId : 1341736803850358786
             * delFlag : 0
             * score : 3
             * classId : 1341736803837775874
             * createBy : 1322815240747462658
             * teacherId : 1322815240747462658
             * scoreB : 2
             * scoreC : 3
             * updateBy : null
             * scoreD : 4
             * scoreE : 4
             * teacherAppraise : 这是老师的课堂评语内容。
             * startTime : 2020-12-21 17:00:00
             * id : 99017048188780546
             * endTime : 2020-12-21 19:00:00
             * actualStuNum : 4
             * remarks : null
             * createDate : 2020-12-21 17:00:00
             * appraiseDate : 2020-12-21 19:00:00
             */

            private Object updateDate;
            private int classStuNum;
            private String teacherName;
            private String className;
            private int scoreA;
            private String coursesId;
            private String delFlag;
            private int score;
            private String classId;
            private String createBy;
            private String teacherId;
            private int scoreB;
            private int scoreC;
            private Object updateBy;
            private int scoreD;
            private int scoreE;
            private String teacherAppraise;
            private String startTime;
            private String id;
            private String endTime;
            private int actualStuNum;
            private Object remarks;
            private String createDate;
            private String appraiseDate;
        }
    }
}
