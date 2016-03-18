package org.solarex.turingchat.bean;

import org.solarex.turingchat.ViewHolder;

import java.util.ArrayList;


public class ArticleMsg extends Msg {
    private String text = "";
    private int type = -1;
    private ArrayList<Article> articles = null;


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
        return;
    }
}
