package com.revmedia.tugasakhir;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marvin Zeson on 1/26/2016.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<ArticleInfo> articleList;

    public ArticleAdapter(List<ArticleInfo> contactList) {
        this.articleList = contactList;
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder contactViewHolder, int i) {
        ArticleInfo ci = articleList.get(i);
        contactViewHolder.vTitle.setText(ci.title);
        contactViewHolder.vContent.setText(ci.content);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ArticleViewHolder(itemView);
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitle;
        protected TextView vContent;

        public ArticleViewHolder(View v) {
            super(v);
            vContent =  (TextView) v.findViewById(R.id.txtContent);
            vTitle = (TextView) v.findViewById(R.id.title);
        }
    }
}