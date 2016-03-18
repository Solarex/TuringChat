package org.solarex.turingchat.bean;

import android.widget.TextView;

import org.solarex.turingchat.ViewHolder;

public class TextMsg extends Msg {

    private String text = "";
    private int type = -1;
    private TextView mTextView = null;

    public TextMsg(int type, String message) {
        this.type = type;
        this.text = message;
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
        //no need
    }

    @Override
    public void fillView(ViewHolder viewHolder){
        /*
        mTextView = (TextView)viewHolder.getView(R.id.item_text_tv);
        */
        mTextView.setText(text);
    }
}
