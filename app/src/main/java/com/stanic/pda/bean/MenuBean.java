package com.stanic.pda.bean;

import java.io.Serializable;

public class MenuBean implements Serializable {

    private int logo;
    private String name;
    private String nameList;

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }
}
