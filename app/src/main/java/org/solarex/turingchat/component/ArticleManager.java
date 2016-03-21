package org.solarex.turingchat.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.ViewHolder;

import java.util.ArrayList;

public class ArticleManager<Article> extends NestedItemManager<Article> {
    public ArticleManager(ListView listView, ArrayList<Article> datas) {
        super(listView, datas);
    }

    @Override
    public BaseAdapter getAdapter() {
        return new ArticleAdapter(mContext, mDatas);
    }

    class ArticleAdapter extends BaseAdapter{
        private static final String TAG = "ArticleAdapter";

        private LayoutInflater mInflater = null;
        private ArrayList<Article> mDatas = null;
        public ArticleAdapter(Context context, ArrayList<Article> mDatas){
            this.mInflater = LayoutInflater.from(context);
            this.mDatas = mDatas;
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
            int layoutId = R.layout.item_news_nesteditem;
            ViewHolder viewHolder = ViewHolder.get(convertView, mInflater, layoutId, parent, position);
            org.solarex.turingchat.bean.Article article = (org.solarex.turingchat.bean.Article) getItem(position);
            article.fillView(viewHolder);
            return viewHolder.getConvertView();
        }
    }
}
