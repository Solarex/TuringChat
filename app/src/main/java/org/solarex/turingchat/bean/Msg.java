package org.solarex.turingchat.bean;


import org.solarex.turingchat.ViewHolder;

public abstract class Msg {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_NEWS = 1;
    public static final int TYPE_LINK = 2;
    public static final int TYPE_COOK = 3;
    public static final int TYPE_ERROR = 4;
    public static final int TYPE_INPUT = 5;

    public static final int MSG_TYPE_COUNT = TYPE_INPUT + 1;

    public static Msg createFrom(int type, String message) {
        Msg msg = null;
        switch (type){
            case TYPE_TEXT:
                msg = new TextMsg(type, message);
                break;
            case TYPE_COOK:
                msg = new CookMsg(type, message);
                break;
            case TYPE_ERROR:
                msg = new TextMsg(type, message);
                break;
            case TYPE_LINK:
                msg = new LinkMsg(type, message);
                break;
            case TYPE_NEWS:
                msg = new ArticleMsg(type, message);
                break;
            case TYPE_INPUT:
                msg = new InputMsg(type, message);
                break;
            default:
                break;
        }
        return msg;
    }

    public int getType(){
        return -1;
    }

    public abstract void destroy();
    public abstract void fillView(ViewHolder viewHolder);
}
