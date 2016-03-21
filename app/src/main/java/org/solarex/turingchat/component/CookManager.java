package org.solarex.turingchat.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;
import org.solarex.turingchat.bean.Cook;

import java.util.ArrayList;

public class CookManager<Cook> extends NestedItemManager<Cook> {
    public CookManager(ListView listView, ArrayList<Cook> datas) {
        super(listView, datas);
    }

    @Override
    public BaseAdapter getAdapter() {
        return new CookAdapter(mContext, mDatas);
    }

    class CookAdapter extends BaseAdapter{
        private static final String TAG = "CookAdapter";

        private LayoutInflater mInflater = null;
        private ArrayList<Cook> mDatas = null;

        public CookAdapter(Context context, ArrayList<Cook> datas){
            this.mInflater = LayoutInflater.from(context);
            this.mDatas = datas;
        }
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int layoutId = R.layout.item_cook_nesteditem;
            ViewHolder viewHolder = ViewHolder.get(convertView, mInflater, layoutId, parent, position);
            org.solarex.turingchat.bean.Cook cook = (org.solarex.turingchat.bean.Cook) getItem(position);
            cook.fillView(viewHolder);
            return viewHolder.getConvertView();
        }
    }
}
