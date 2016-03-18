package org.solarex.turingchat.bean;

import android.widget.TextView;

import org.solarex.turingchat.ViewHolder;


public class InputMsg extends Msg {
    private int type = -1;
    private String text = "";
    private TextView mTv = null;

    public InputMsg(int type, String text){
        this.type = type;
        this.text = text;
    }


    @Override
    public void destroy() {
        //no need
    }


    @Override
    public void fillView(ViewHolder viewHolder) {
        //layout params
    }

    @Override
    public int getType(){
        return type;
    }

    public String getText(){
        return text;
    }
}
