package org.solarex.turingchat.bean;

import android.widget.BaseAdapter;
import android.widget.TextView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.component.ArticleManager;
import org.solarex.turingchat.component.NestedListView;
import org.solarex.turingchat.utils.Logs;

import java.util.ArrayList;


public class ArticleMsg extends Msg {
    private String text = "";
    private int type = -1;
    private ArrayList<Article> articles = null;

    private static final String TAG = "ArticleMsg";

    private TextView mTvText = null;
    private NestedListView mListView = null;

    public ArticleMsg(int type, String text){
        this.type = type;
        this.text = text;
    }

    public void setArticles(ArrayList<Article> articles){
        this.articles = articles;
    }

    public ArrayList<Article> getArticles() {
        return articles;
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
        if (articles != null){
            articles.clear();
        }
    }

    @Override
    public void fillView(ViewHolder viewHolder) {
        Logs.d(TAG, "fillView | holder = " + viewHolder + ", this = " + this);
        if (viewHolder != null){
            mTvText = (TextView) viewHolder.getView(R.id.item_news_tv_text);
            if (mTvText != null){
                mTvText.setText(text);
            }

            mListView = (NestedListView) viewHolder.getView(R.id.item_news_lv);
            if (mListView != null){
                ArticleManager<Article> articleManager = new ArticleManager<>(mListView, articles);
                BaseAdapter adapter = articleManager.getAdapter();
                if (adapter != null){
                    mListView.setAdapter(adapter);
                }
            }
        }
        return;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Articles{type = " + type + ",text = " + text + ",list[");
        for (Article article : articles){
            sb.append(article);
        }
        sb.append("]}");
        return sb.toString();
    }
}
