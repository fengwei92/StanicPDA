package com.stanic.pda.bean;

import java.util.List;

public class AgencyBean {


    /**
     * code : 0
     * msg : 数据查询成功
     * data : [{"code":"0003","name":"代理商2","pid":"e4bf44cd329f43f18f2c48d57e03e3db","id":"369E2FA6EE9F4100A71DB85F05749581"},{"code":"0002","name":"代理商1","pid":"e4bf44cd329f43f18f2c48d57e03e3db","id":"ff449e2da33d4df999e9347f78bcc9f5"}]
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
         * code : 0003
         * name : 代理商2
         * pid : e4bf44cd329f43f18f2c48d57e03e3db00
         * id : 369E2FA6EE9F4100A71DB85F05749581
         */

        private String code;
        private String name;
        private String pid;
        private String id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
