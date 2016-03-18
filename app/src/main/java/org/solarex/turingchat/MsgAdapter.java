package org.solarex.turingchat;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.solarex.turingchat.bean.Msg;

import java.util.ArrayList;


public class MsgAdapter extends BaseAdapter {

    private LayoutInflater mInflater = null;
    private ArrayList<Msg> mMsgs = null;
    private SparseArray<Integer> mLayoutMaps = null;

    public MsgAdapter(Context context, ArrayList<Msg> msgs){
        mInflater = LayoutInflater.from(context);
        mMsgs = msgs;
        mLayoutMaps = new SparseArray<>();
        /*
        mLayoutMaps.put(Msg.TYPE_COOK, R.layout.item_cook);
        mLayoutMaps.put(Msg.TYPE_ERROR, R.layout.item_error);
        mLayoutMaps.put(Msg.TYPE_LINK, R.layout.item_link);
        mLayoutMaps.put(Msg.TYPE_NEWS, R.layout.item_news);
        mLayoutMaps.put(Msg.TYPE_TEXT, R.layout.item_text);
        */
    }
    @Override
    public int getViewTypeCount() {
        return Msg.MSG_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return mMsgs.get(position).getType();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mMsgs.size();
    }

    @Override
    public Object getItem(int position) {
        return mMsgs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMsgs.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = mMsgs.get(position);
        int layoutId = mLayoutMaps.get(msg.getType());
        ViewHolder viewHolder = ViewHolder.get(convertView, mInflater, layoutId, parent, position);
        msg.fillView(viewHolder);
        return viewHolder.getConvertView();
    }

}
