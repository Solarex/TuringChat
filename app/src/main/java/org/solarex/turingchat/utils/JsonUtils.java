package org.solarex.turingchat.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.solarex.turingchat.bean.Article;
import org.solarex.turingchat.bean.ArticleMsg;
import org.solarex.turingchat.bean.Cook;
import org.solarex.turingchat.bean.CookMsg;
import org.solarex.turingchat.bean.LinkMsg;
import org.solarex.turingchat.bean.Msg;

import java.net.CookieHandler;
import java.util.ArrayList;


public class JsonUtils {
    private static final String TAG = "JsonUtils";
    public static Msg parse(String s) {
        Msg msg = null;
        String text = null;
        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = jsonObject.getInt(MsgFields.CODE);
            Logs.d(TAG, "parse | code = " + code);
            switch (code){
                case MsgFields.CODE_ERROR_0:
                case MsgFields.CODE_ERROR_1:
                case MsgFields.CODE_ERROR_2:
                case MsgFields.CODE_ERROR_3:
                    text = jsonObject.getString(MsgFields.ERROR_TEXT);
                    msg = Msg.createFrom(Msg.TYPE_ERROR, text);
                    break;
                case MsgFields.CODE_TEXT:
                    text = jsonObject.getString(MsgFields.TEXT_TEXT);
                    msg = Msg.createFrom(Msg.TYPE_TEXT, text);
                    break;
                case MsgFields.CODE_LINK:
                    text = jsonObject.getString(MsgFields.LINK_TEXT);
                    String url = jsonObject.getString(MsgFields.LINK_URL);
                    msg = Msg.createFrom(Msg.TYPE_LINK, text);
                    ((LinkMsg)msg).setUrl(url);
                    break;
                case MsgFields.CODE_COOK:
                    text = jsonObject.getString(MsgFields.COOK_TEXT);
                    msg = Msg.createFrom(Msg.TYPE_COOK, text);
                    JSONArray jsonArray = jsonObject.getJSONArray(MsgFields.COOK_LIST);
                    int len = jsonArray.length();
                    ArrayList<Cook> cooks = new ArrayList<>();
                    JSONObject tmp = null;
                    Cook cook = null;
                    String name = null, icon = null, info = null, detailUrl = null;
                    for (int i = 0; i < len; i++){
                        tmp = jsonArray.getJSONObject(i);
                        name = tmp.getString(MsgFields.COOK_NAME);
                        icon = tmp.getString(MsgFields.COOK_ICON);
                        info = tmp.getString(MsgFields.COOK_INFO);
                        detailUrl = tmp.getString(MsgFields.COOK_URL);
                        cook = new Cook(name, icon, info, detailUrl);
                        cooks.add(cook);
                        cook = null;
                    }
                    ((CookMsg)msg).setCooks(cooks);
                    break;
                case MsgFields.CODE_NEWS:
                    text = jsonObject.getString(MsgFields.NEWS_TEXT);
                    msg = Msg.createFrom(Msg.TYPE_NEWS, text);
                    JSONArray tmpArray = jsonObject.getJSONArray(MsgFields.NEWS_LIST);
                    int length = tmpArray.length();
                    ArrayList<Article> articles = new ArrayList<>();
                    JSONObject tmptmp = null;
                    Article tmpArticle = null;
                    String article = null, source = null, articleIcon = null, articleDetailUrl = null;
                    for (int i = 0; i < length; i++){
                        tmptmp = tmpArray.getJSONObject(i);
                        article = tmptmp.getString(MsgFields.NEWS_ARTICLE);
                        source = tmptmp.getString(MsgFields.NEWS_SOURCE);
                        articleIcon = tmptmp.getString(MsgFields.NEWS_ICON);
                        articleDetailUrl = tmptmp.getString(MsgFields.NEWS_URL);
                        tmpArticle = new Article(article, source, articleIcon, articleDetailUrl);
                        articles.add(tmpArticle);
                        tmpArticle = null;
                    }
                    ((ArticleMsg)msg).setArticles(articles);
                    break;
                default:
                    break;
            }
        } catch (Exception e){
            Logs.d(TAG, "parse | exception = " + e);
        }

        return msg;
    }
}
