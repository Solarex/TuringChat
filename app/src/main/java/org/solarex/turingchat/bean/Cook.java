package org.solarex.turingchat.bean;


import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.AppConfig;
import org.solarex.turingchat.utils.ImageLoader;
import org.solarex.turingchat.utils.Logs;

public class Cook {
    public String name = "";
    public String icon = "";
    public String info = "";
    public String detailUrl = "";

    private TextView mTvName = null,mTvInfo = null;
    private ImageView mIvIcon = null;

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

    //fillview for self usage
    public void fillView(ViewHolder viewHolder){
        Logs.d(TAG, "fillView | this = " + this + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvName = (TextView)viewHolder.getView(R.id.item_cook_tv_name);
            if (mTvName != null){
                mTvName.setText(name);
            }
            mTvInfo = (TextView)viewHolder.getView(R.id.item_cook_tv_info);
            if (mTvInfo != null){
                mTvInfo.setText(info);
            }

            mIvIcon = (ImageView)viewHolder.getView(R.id.item_cook_iv_icon);
            // load image, fix this later
            if (mIvIcon != null && !TextUtils.isEmpty(icon)){
                ImageLoader.getInstance(mIvIcon.getContext()).bindBitmap(icon, mIvIcon, AppConfig.IMAGE_WIDTH, AppConfig.IMAGE_HEIGHT);
            }
            Article.OnItemClicked listener = new Article.OnItemClicked(detailUrl);
            mTvName.setOnClickListener(listener);
            mTvInfo.setOnClickListener(listener);
            mIvIcon.setOnClickListener(listener);
        }
    }
}
