package org.solarex.turingchat.bean;

import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.Logs;


public class InputMsg extends Msg {
    private int type = -1;
    private String text = "";
    private TextView mTvInput = null;

    private static final String TAG = "InputMsg";

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
        Logs.d(TAG, "fillView | this = " + this.toString() + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvInput = (TextView)viewHolder.getView(R.id.item_input_tv);
            if (mTvInput != null){
                mTvInput.setText(text);
            }
        }
    }

    @Override
    public int getType(){
        return type;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("InputMsg{type="+type+",text="+text+"}");
        return sb.toString();
    }
}
