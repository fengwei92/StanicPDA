package com.stanic.pda.bean;

public class QueryBean {

    /**
     * code : 1
     * msg : 查询成功
     * data : {"barcode":"1001321426","casecode":null,"storecode":null,"pdtname":"产品1224","agency":"代理商2","outcount":"1"}
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
         * barcode : 1001321426
         * casecode : null
         * storecode : null
         * pdtname : 产品1224
         * agency : 代理商2
         * outcount : 1
         */

        private String barcode;
        private String casecode;
        private String storecode;
        private String pdtname;
        private String agency;
        private String ordernum;
        private String outcount;

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public String getCasecode() {
            return casecode;
        }

        public void setCasecode(String casecode) {
            this.casecode = casecode;
        }

        public String getStorecode() {
            return storecode;
        }

        public void setStorecode(String storecode) {
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
