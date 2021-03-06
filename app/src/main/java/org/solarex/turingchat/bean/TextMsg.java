package org.solarex.turingchat.bean;

import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.Logs;

public class TextMsg extends Msg {

    private String text = "";
    private int type = -1;
    private TextView mTvText = null;

    private static final String TAG = "TextMsg";

    public TextMsg(int type, String message) {
        this.type = type;
        this.text = message;
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
        Logs.d(TAG, "fillView | this = " + this + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvText = (TextView)viewHolder.getView(R.id.item_text_tv);
            if (mTvText != null){
                mTvText.setText(text);
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TextMsg{type="+type+",text="+text+"}");
        return sb.toString();
    }
}
