package org.solarex.turingchat.impl;

import org.solarex.turingchat.bean.Msg;

import java.util.ArrayList;


public abstract class IModel {

    static interface ModelSaveCallback{
        public void onSaveSuccess(ArrayList<Msg> mMsgs);
    }

    public ModelSaveCallback mSaveCallback = null;
    protected ArrayList<Msg> mMsgs = null;

    public IModel(){}

    public IModel(ModelSaveCallback saveCallback, ArrayList<Msg> msgs){
        mSaveCallback = saveCallback;
        mMsgs = msgs;
    }

    public abstract void saveMsg(String Msg);
    public abstract void saveMsg(Msg msg);
    public abstract void clearMsg();
}
