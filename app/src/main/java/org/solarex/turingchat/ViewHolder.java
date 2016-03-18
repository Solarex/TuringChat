package org.solarex.turingchat;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.solarex.turingchat.utils.Logs;

public class ViewHolder {

    private static final String TAG = "ViewHolder";

    private View mConvertView = null;
    private int position = -1;
    private SparseArray<View> mViews = null;

    public static ViewHolder get(View convertView, LayoutInflater inflater,
                                 int layoutId, ViewGroup parent, int position){
        Logs.d(TAG, "get | convertView = " + convertView + ",position = " + position);
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder(inflater, layoutId, parent, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.position = position;
        }
        Logs.d(TAG, "get | holder = " + holder);
        return holder;
    }

    private ViewHolder(LayoutInflater inflater, int layoutId, ViewGroup parent, int position){
        mConvertView = inflater.inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        this.position = position;
        mViews = new SparseArray<>();
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        Logs.d(TAG, "getView | viewId = " + viewId + ", view = " + view);
        return (T) view;
    }

    public View getConvertView(){
        Logs.d(TAG, "getConvertView | mConvertView = " + mConvertView);
        return mConvertView;
    }
}
