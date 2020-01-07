package com.stanic.pda.bean;

import java.util.List;

public class DayOutBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : [{"barcode":null,"casecode":null,"storecode":null,"pdtname":"产品1224","agency":"代理商2","outcount":"1"}]
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
         * barcode : null
         * casecode : null
         * storecode : null
         * pdtname : 产品1224
         * agency : 代理商2
         * outcount : 1
         */

        private Object barcode;
        private Object casecode;
        private Object storecode;
        private String pdtname;
        private String agency;
        private String outcount;
        private String ordernum;

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public Object getBarcode() {
            return barcode;
        }

        public void setBarcode(Object barcode) {
            this.barcode = barcode;
        }

        public Object getCasecode() {
            return casecode;
        }

        public void setCasecode(Object casecode) {
            this.casecode = casecode;
        }

        public Object getStorecode() {
            return storecode;
        }

        public void setStorecode(Object storecode) {
            this.storecode = storecode;
        }

        public String getPdtname() {
            return pdtname;
        }

        public void setPdtname(String pdtname) {
            this.pdtname = pdtname;
        }

        public String getAgency() {
            return agency;
        }

        public void setAgency(String agency) {
            this.agency = agency;
        }

        public String getOutcount() {
            return outcount;
        }

        public void setOutcount(String outcount) {
            this.outcount = outcount;
        }
    }
}
