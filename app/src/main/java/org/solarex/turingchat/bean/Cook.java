package org.solarex.turingchat.bean;


import org.solarex.turingchat.utils.Logs;

public class Cook {
    public String name = "";
    public String icon = "";
    public String info = "";
    public String detailUrl = "";

    private static final String TAG = "Cook";

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Cook{name="+name+",info="+info+"}");
        Logs.d(TAG, "Cook | icon url = " + icon);
        Logs.d(TAG, "Cook | detailUrl = " + detailUrl);
        return sb.toString();
    }
}
