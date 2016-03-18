package org.solarex.turingchat.bean;

import org.solarex.turingchat.ViewHolder;

import java.util.ArrayList;


public class CookMsg extends Msg {
    private String text = "";
    private int type = -1;
    private ArrayList<Cook> cooks = null;

    public CookMsg(int type, String text){
        this.type = type;
        this.text = text;
    }

    public void setCooks(ArrayList<Cook> cooks){
        this.cooks = cooks;
    }

    public ArrayList<Cook> getCooks() {
        return cooks;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void destroy() {
        if (cooks != null){
            cooks.clear();
        }
    }

    @Override
    public void fillView(ViewHolder viewHolder) {
        return;
    }
}
