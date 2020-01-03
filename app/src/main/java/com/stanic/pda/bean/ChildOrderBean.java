package com.stanic.pda.bean;

import java.util.List;

public class ChildOrderBean {

    /**
     * code : 0
     * msg : 查询成功
     * data : [{"jourid":"5f2c1148a4ed4021a654f888066a637f","orderid":"bb570092e0334d0d8980f51e840f1955","proid":"323a8f22945a474f8fc9b8848ee6988f","pdtname":"进进进","plannum":136985,"sendnum":0,"delflag":0,"tableprix":null},{"jourid":"6b82e33574924cf98d460df7bda563f8","orderid":"bb570092e0334d0d8980f51e840f1955","proid":"6ec3364afb504aa3ae2fb218e8616174","pdtname":"进进进1","plannum":1233,"sendnum":0,"delflag":0,"tableprix":null},{"jourid":"34c0dac54ef64fa1803067ee8af5bb91","orderid":"bb570092e0334d0d8980f51e840f1955","proid":"8f6227ac77ca4ec7994f99e15ddb151b","pdtname":"进进进2","plannum":22333,"sendnum":0,"delflag":0,"tableprix":null}]
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
         * jourid : 5f2c1148a4ed4021a654f888066a637f
         * orderid : bb570092e0334d0d8980f51e840f1955
         * proid : 323a8f22945a474f8fc9b8848ee6988f
         * pdtname : 进进进
         * plannum : 136985
         * sendnum : 0
         * delflag : 0
         * tableprix : null
         */

        private String jourid;
        private String orderid;
        private String proid;
        private String pdtname;
        private int plannum;
        private int sendnum;
        private int delflag;
        private Object tableprix;

        public String getJourid() {
            return jourid;
        }

        public void setJourid(String jourid) {
            this.jourid = jourid;
        }

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

        public String getProid() {
            return proid;
        }

        public void setProid(String proid) {
            this.proid = proid;
        }

        public String getPdtname() {
            return pdtname;
        }

        public void setPdtname(String pdtname) {
            this.pdtname = pdtname;
        }

        public int getPlannum() {
            return plannum;
        }

        public void setPlannum(int plannum) {
            this.plannum = plannum;
        }

        public int getSendnum() {
            return sendnum;
        }

        public void setSendnum(int sendnum) {
            this.sendnum = sendnum;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public Object getTableprix() {
            return tableprix;
        }

        public void setTableprix(Object tableprix) {
            this.tableprix = tableprix;
        }
    }
}
