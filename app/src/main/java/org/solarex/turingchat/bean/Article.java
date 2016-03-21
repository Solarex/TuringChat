package org.solarex.turingchat.bean;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.SolarexApplication;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.utils.AppUtils;
import org.solarex.turingchat.utils.Logs;

public class Article {
    private String article = "";
    private String source = "";
    private String icon = "";
    private String detailUrl = "";

    private static final String TAG = "Article";

    private TextView mTvSource = null, mTvArticle = null;
    private ImageView mIvIcon = null;

    public Article(String article, String source, String icon, String detailUrl){
        this.article = article;
        this.source = source;
        this.icon = icon;
        this.detailUrl = detailUrl;
    }

    public String getArticle() {
        return article;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public String getIcon() {
        return icon;
    }

    public String getSource() {
        return source;
    }

    //fillview for self usage
    public void fillView(ViewHolder viewHolder){
        Logs.d(TAG, "fillView | this = " + this + ",holder = " + viewHolder);
        if (viewHolder != null){
            mTvSource = (TextView)viewHolder.getView(R.id.item_news_tv_source);
            if (mTvSource != null){
                mTvSource.setText(source);
            }
            mTvArticle = (TextView)viewHolder.getView(R.id.item_news_tv_article);
            if (mTvArticle != null){
                mTvArticle.setText(article);
            }
            mIvIcon = (ImageView)viewHolder.getView(R.id.item_news_iv_icon);
            //load image, fix this later

            OnItemClicked listener = new OnItemClicked(detailUrl);
            mTvSource.setOnClickListener(listener);
            mTvArticle.setOnClickListener(listener);
            mIvIcon.setOnClickListener(listener);
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Article{article = " + article + ",source = " + source + "}");
        Logs.d(TAG, "icon = " + icon);
        Logs.d(TAG, "detailUrl = " + detailUrl);
        return sb.toString();
    }

    public static class OnItemClicked implements View.OnClickListener{
        private String url = "";
        public OnItemClicked(String url){
            this.url = url;
        }
        @Override
        public void onClick(View v) {
            AppUtils.openUrl(SolarexApplication.getsInstance(), url);
        }
    }
}
