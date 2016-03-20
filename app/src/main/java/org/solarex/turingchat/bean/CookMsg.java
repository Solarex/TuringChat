package org.solarex.turingchat.bean;

import android.widget.ImageView;
import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.Logs;

import java.util.ArrayList;


public class CookMsg extends Msg {
    private String text = "";
    private int type = -1;
    private ArrayList<Cook> cooks = null;

    private static final String TAG = "CookMsg";

    private TextView mTvText = null,mTvName = null,mTvInfo = null;
    private ImageView mIvIcon = null;

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
        Logs.d(TAG, "fillView | this = " + this.toString() + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvText = (TextView)viewHolder.getView(R.id.item_cook_tv_text);
            if (mTvText != null){
                mTvText.setText(text);
            }
            mTvName = (TextView)viewHolder.getView(R.id.item_cook_tv_name);
            if (mTvName != null){
                mTvName.setText(cooks.get(0).getName());
            }
            mTvInfo = (TextView)viewHolder.getView(R.id.item_cook_tv_info);
            if (mTvInfo != null){
                mTvInfo.setText(cooks.get(0).getInfo());
            }
            //Volley load image
        }
        return;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("CookMsg{type="+this.type+",text="+this.text+",list[");
        for (Cook cook : cooks){
            sb.append(cook);
        }
        sb.append("]}");
        return sb.toString();
    }
}
