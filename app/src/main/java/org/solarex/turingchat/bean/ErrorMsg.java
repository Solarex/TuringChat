package org.solarex.turingchat.bean;

import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.Logs;


public class ErrorMsg extends Msg {
    private int type = -1;
    private String text = "";
    private TextView mTvError = null;
    private static final String TAG = "ErrorMsg";

    public ErrorMsg(int type, String text){
        this.type = type;
        this.text = text;
    }

    @Override
    public void destroy() {
        //no need
    }


    @Override
    public void fillView(ViewHolder viewHolder) {
        Logs.d(TAG, "fillView | this = " + this + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvError = (TextView)viewHolder.getView(R.id.item_error_tv);
            if (mTvError != null){
                mTvError.setText(text);
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
        sb.append("ErrorMsg{type="+type+",text="+text+"}");
        return sb.toString();
    }
}
