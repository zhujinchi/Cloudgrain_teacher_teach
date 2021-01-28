package com.idcvideo.meetinglibrary.activity.bean;

import java.util.List;

/**
 * +----------------------------------------------------------------------
 * | com.example.cloudgrainstudent.ui.cloudclass.bean
 * +----------------------------------------------------------------------
 * | 功能描述:
 * +----------------------------------------------------------------------
 * | 时　　间:2020/12/9
 * +----------------------------------------------------------------------
 * | @author: lijiapeng
 * +----------------------------------------------------------------------
 * | 版本信息: V1.0.0
 * +----------------------------------------------------------------------
 * | 代码修改:（ljp - 8:52 AM）
 * +----------------------------------------------------------------------
 **/
public class CloudClassExchangeMessageListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"records":[{"id":"1354357749748699138","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:17:05","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"},{"id":"1354357309615214594","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:15:20","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"},{"id":"1354357282113163265","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:15:13","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"}],"total":3,"size":1000000,"current":1,"orders":[],"optimizeCountSql":true,"hitCount":false,"countId":null,"maxLimit":null,"searchCount":true,"pages":1}
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
         * records : [{"id":"1354357749748699138","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:17:05","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"},{"id":"1354357309615214594","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:15:20","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"},{"id":"1354357282113163265","classId":"1353731978768257026","sender":"0","receiver":"3","message":"代代相传","createBy":"1322815240747462658","createDate":"2021-01-27 17:15:13","updateBy":null,"updateDate":null,"remarks":null,"delFlag":"0"}]
         * total : 3
         * size : 1000000
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
        private boolean searchCount;
        private int pages;
        private List<RecordsBean> records;
        private List<?> orders;

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

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public static class RecordsBean {
            /**
             * id : 1354357749748699138
             * classId : 1353731978768257026
             * sender : 0
             * receiver : 3
             * message : 代代相传
             * createBy : 1322815240747462658
             * createDate : 2021-01-27 17:17:05
             * updateBy : null
             * updateDate : null
             * remarks : null
             * delFlag : 0
             */

            private String id;
            private String classId;
            private String sender;
            private String receiver;
            private String message;
            private String createBy;
            private String createDate;
            private Object updateBy;
            private Object updateDate;
            private Object remarks;
            private String delFlag;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
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

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public Object getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(Object updateDate) {
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
}
