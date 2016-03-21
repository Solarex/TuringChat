package org.solarex.turingchat.component;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public abstract class NestedItemManager<T> {

    private static final String TAG = "NestedItemManager";

    protected ListView mListView = null;
    protected ArrayList<T> mDatas = null;
    protected Context mContext = null;

    public NestedItemManager(ListView listView, ArrayList<T> datas){
        mListView = listView;
        mContext = mListView.getContext();
        mDatas = datas;
    }

    public abstract BaseAdapter getAdapter();
}
