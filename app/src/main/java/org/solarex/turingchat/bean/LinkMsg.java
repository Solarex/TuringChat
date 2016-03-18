package org.solarex.turingchat.bean;

import org.solarex.turingchat.ViewHolder;


public class LinkMsg extends Msg {
    private String text = "";

    private String url = "";
    private int type = -1;

    public LinkMsg(int type, String text){
        this.type = type;
        this.text = text;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public void destroy() {
        //no need
    }

    @Override
    public void fillView(ViewHolder viewHolder) {
        return;
    }
}
