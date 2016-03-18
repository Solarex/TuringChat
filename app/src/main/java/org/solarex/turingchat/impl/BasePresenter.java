package org.solarex.turingchat.impl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


public abstract class BasePresenter {
    protected Reference<IView> mViewRef;

    public void attachView(IView view){
        mViewRef = new WeakReference<IView>(view);
    }

    protected IView getView(){
        return mViewRef.get();
    }

    public boolean isViewAttached(){
        return mViewRef!=null && mViewRef.get()!=null;
    }

    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public abstract void initMsg(String msg);
    public abstract void sendMsg(String message);
    public abstract void showInputError();
}
