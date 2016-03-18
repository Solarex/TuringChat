package org.solarex.turingchat.bean;


public class Cook {
    public String name = "";
    public String icon = "";
    public String info = "";
    public String detailUrl = "";

    public Cook(String name, String icon, String info, String detailUrl){
        this.name = name;
        this.icon = icon;
        this.info = info;
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public String getIcon() {
        return icon;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }
}
