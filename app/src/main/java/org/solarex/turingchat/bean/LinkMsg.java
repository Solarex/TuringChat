package org.solarex.turingchat.bean;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.SolarexApplication;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.AppUtils;
import org.solarex.turingchat.utils.Logs;


public class LinkMsg extends Msg {
    private String text = "";
    private String url = "";
    private int type = -1;

    private static final String TAG = "LinkMsg";
    private TextView mTvLink = null;

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
        Logs.d(TAG, "fillView | this = " + this.toString() + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvLink = (TextView)viewHolder.getView(R.id.item_link_tv);
            if (mTvLink != null){
                SpannableString ss = new SpannableString(text);
                ss.setSpan(new UnderlineSpan(), 0, text.length(), 0);
                mTvLink.setText(ss);
                mTvLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtils.openUrl(SolarexApplication.getsInstance(), url);
                    }
                });
            }
        }
        return;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("LinkMsg{type=" + type + ",text=" + text + "}");
        Logs.d(TAG, "LinkMsg | url = " + url);
        return sb.toString();
    }
}
