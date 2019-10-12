package com.stanic.pda.bean;

public class BoxRelationBean {

    /**
     * code : 11
     * msg : 该盒已与其他箱码关联
     * data : 1567751818966
     */

    private int code;
    private String msg;
    private Object data;
    private String caseCode;
    private String boxCode;

    public String getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(String boxCode) {
        this.boxCode = boxCode;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
