package com.stanic.pda.bean;

import java.util.List;

public class ProductBean {


    /**
     * msg : 数据查询成功
     * code : 1
     * data : [{"PDTID":"a7e1578af8064c518c31bb5c7963ae81","产品名称":"桐谷和人"},{"PDTID":"b24efc1660374686958c1303911ebd88","产品名称":"阿斯顿"},{"PDTID":"70b71d14003a4ac4b2f814aa0ec927cd","产品名称":"阿斯顿1"},{"PDTID":"f63f2d7fded149e8aea0ca80f3e2a57e","产品名称":"阿斯顿11"},{"PDTID":"1e0b56c094d045feabffae3ca19d5674","产品名称":"阿斯顿3"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * PDTID : a7e1578af8064c518c31bb5c7963ae81
         * 产品名称 : 桐谷和人
         */

        private String PDTID;
        private String PDTNAME;

        public String getPDTID() {
            return PDTID;
        }

        public void setPDTID(String PDTID) {
            this.PDTID = PDTID;
        }

        public String getPDTNAME() {
            return PDTNAME;
        }

        public void setPDTNAME(String PDTNAME) {
            this.PDTNAME = PDTNAME;
        }
    }
}
