package org.solarex.turingchat.bean;


public class Article {
    private String article = "";
    private String source = "";
    private String icon = "";
    private String detailUrl = "";

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
}
