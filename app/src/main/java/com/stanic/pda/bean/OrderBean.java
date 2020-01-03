package com.stanic.pda.bean;

import java.util.List;

public class OrderBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"id":"bb570092e0334d0d8980f51e840f1955","crttime":null,"upttime":null,"ordernum":"1234565\u2026\u2026&测试","sendagency":null,"sendaddr":null,"reciveagency":"a784ca967f62467c9ee4d3b0b0482d8b","reciveaddr":null,"note":null,"statustype":null,"delflag":0,"status":0,"oversize":0,"tableprix":null},{"id":"53369414507447b4a51ce098b4afd7af","crttime":null,"upttime":null,"ordernum":"43354354dfg ","sendagency":null,"sendaddr":null,"reciveagency":"a784ca967f62467c9ee4d3b0b0482d8b","reciveaddr":null,"note":null,"statustype":null,"delflag":0,"status":0,"oversize":0,"tableprix":null},{"id":"84249985a61248a585ed5595624d593c","crttime":null,"upttime":null,"ordernum":"SJ2019111112337","sendagency":null,"sendaddr":null,"reciveagency":"07aa9667513d417f880da2427db5bf6c","reciveaddr":null,"note":null,"statustype":null,"delflag":0,"status":0,"oversize":0,"tableprix":null},{"id":"10b58d982bc948d0ae1b217f31885299","crttime":null,"upttime":null,"ordernum":"SJ2019111112111","sendagency":null,"sendaddr":null,"reciveagency":"29c311c7829c44b1aedbaded06bd37a7","reciveaddr":null,"note":null,"statustype":null,"delflag":0,"status":0,"oversize":0,"tableprix":null},{"id":"4d97e534ad744412ac39ed8480ca21db","crttime":null,"upttime":null,"ordernum":"SJ2019111112337","sendagency":null,"sendaddr":null,"reciveagency":"f51dbd487d644dff9bb4855607db38f3","reciveaddr":null,"note":null,"statustype":null,"delflag":0,"status":0,"oversize":0,"tableprix":null}]
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
         * id : bb570092e0334d0d8980f51e840f1955
         * crttime : null
         * upttime : null
         * ordernum : 1234565……&测试
         * sendagency : null
         * sendaddr : null
         * reciveagency : a784ca967f62467c9ee4d3b0b0482d8b
         * reciveaddr : null
         * note : null
         * statustype : null
         * delflag : 0
         * status : 0
         * oversize : 0
         * tableprix : null
         */

        private String id;
        private Object crttime;
        private Object upttime;
        private String ordernum;
        private Object sendagency;
        private Object sendaddr;
        private String reciveagency;
        private Object reciveaddr;
        private Object note;
        private Object statustype;
        private int delflag;
        private int status;
        private int oversize;
        private Object tableprix;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCrttime() {
            return crttime;
        }

        public void setCrttime(Object crttime) {
            this.crttime = crttime;
        }

        public Object getUpttime() {
            return upttime;
        }

        public void setUpttime(Object upttime) {
            this.upttime = upttime;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public Object getSendagency() {
            return sendagency;
        }

        public void setSendagency(Object sendagency) {
            this.sendagency = sendagency;
        }

        public Object getSendaddr() {
            return sendaddr;
        }

        public void setSendaddr(Object sendaddr) {
            this.sendaddr = sendaddr;
        }

        public String getReciveagency() {
            return reciveagency;
        }

        public void setReciveagency(String reciveagency) {
            this.reciveagency = reciveagency;
        }

        public Object getReciveaddr() {
            return reciveaddr;
        }

        public void setReciveaddr(Object reciveaddr) {
            this.reciveaddr = reciveaddr;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getStatustype() {
            return statustype;
        }

        public void setStatustype(Object statustype) {
            this.statustype = statustype;
        }

        public int getDelflag() {
            return delflag;
        }

        public void setDelflag(int delflag) {
            this.delflag = delflag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOversize() {
            return oversize;
        }

        public void setOversize(int oversize) {
            this.oversize = oversize;
        }

        public Object getTableprix() {
            return tableprix;
        }

        public void setTableprix(Object tableprix) {
            this.tableprix = tableprix;
        }
    }
}
