package org.solarex.turingchat.impl;

import org.solarex.turingchat.bean.Msg;

import java.util.ArrayList;


public class ModelImpl extends IModel {

    public ModelImpl(ModelSaveCallback modelSaveCallback, ArrayList<Msg> mMsgs) {
        super(modelSaveCallback, mMsgs);
    }

    @Override
    public void saveMsg(String msg) {
        this.mMsgs.add(Msg.createFrom(Msg.TYPE_TEXT, msg));
        mSaveCallback.onSaveSuccess(mMsgs);
    }

    @Override
    public void saveMsg(Msg message){
        this.mMsgs.add(message);
        mSaveCallback.onSaveSuccess(mMsgs);
    }

    @Override
    public void clearMsg(){
        for (Msg msg : mMsgs){
            msg.destroy();
            msg = null;
        }
        mMsgs.clear();
    }
}
